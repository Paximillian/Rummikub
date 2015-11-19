

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent;

import consoleSpecificRummikubImplementations.mvcViewComponent.gameViewElements.CardSetView;
import consoleSpecificRummikubImplementations.mvcViewComponent.gameViewElements.CardView;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yafita870
 */
public class Game {
    private final int PENALTY_DRAW_COUNT = 3;
    
    private Deck deck = new Deck();
    private final Board board = new Board();
    private final ArrayList<Player> players = new ArrayList<Player>();
    
    private int currentPlayerTurn = 0;
    public Player getCurrentPlayer(){
        return players.get(currentPlayerTurn);
    }
    public void advancePlayerTurn(){
        ++currentPlayerTurn;
        
        if(currentPlayerTurn >= players.size()){
            currentPlayerTurn = 0;
        }
        
        addTileToPlayer();
    }
    
    public boolean addTileToPlayer()
    {
        if(deck.isDeckEmpty())
            return false;
        
        getCurrentPlayer().addTileToHand(deck.getNextTile());
        return true;
    }

    public Player addNewPlayer(String newName, boolean isBotStatus)
    {
        Player newPlayer = new Player(newName, isBotStatus, newHand());
        this.players.add(newPlayer);
        return newPlayer;
    }

    private Player addNewPlayer(Player newPlayer)
    {
        this.players.add(newPlayer);
        return newPlayer;
    }
    
    private ArrayList<Tile> newHand()
    {
        ArrayList<Tile> newHand = new ArrayList<Tile>();
        for(int i = 0; i < 14; ++i){
            newHand.add(this.deck.getNextTile());
        }
        return newHand;
    }
    
    public ArrayList<Player> getPlayers(){
        return players;
    }
    
    public Board getBoard(){
        return board;
    }

    public boolean isLegalGameState() {
        return board.isBoardLegal();
    }

    private void setDeck(Deck newDeck) {
        deck = newDeck;
    }

    private void setPlayerTurnTo(Player playerToSetTurnFor) throws IllegalArgumentException{
        if(!players.contains(playerToSetTurnFor)){
            throw new IllegalArgumentException("Player is not in the game");
        }
        
        currentPlayerTurn = players.indexOf(playerToSetTurnFor);
    }

    public void moveCard(int fromSetID, int fromCardID, int toSetID, int toPositionID) throws IllegalArgumentException {
        Sequence sourceCardSet = null;
        Sequence targetCardSet = null;
        
        //Checking if set ID is valid.
        if(fromSetID < 0 || fromSetID > board.getSequences().size() 
            || toSetID < 0 || toSetID > board.getSequences().size()){
            throw new IllegalArgumentException("Illegal source set ID");
        }
        
        //Can't place a card TO the hand.
        if(toSetID == 0){
            throw new IllegalArgumentException("Can't move a card to your hand.");
        }
        //Getting the target set's reference
        else{
            targetCardSet = board.getSequences().get(toSetID - 1);
        }
        
        //Checking if card ID is out of range.
        if(fromCardID < 0){
            throw new IllegalArgumentException("Illegal source card ID");
        }
            
        //Getting the source set's reference
        if(fromSetID == 0){
            //Checking if the source card position is out of range.
            if(fromCardID > getCurrentPlayer().getHand().size()){
                throw new IllegalArgumentException("Illegal source card ID");
            }
            
            sourceCardSet = getCurrentPlayer().getHand();
        }
        else{
            //Checking if the source card position is out of range.
            if(fromCardID > board.getSequences().get(fromSetID - 1).size()){
                throw new IllegalArgumentException("Illegal source card ID");
            }
            
            sourceCardSet = board.getSequences().get(fromSetID - 1);
        }
        
        //Checking if the target card position is out of range.
        if(toPositionID > board.getSequences().get(toSetID - 1).size() + 1 || toPositionID < 0){
            throw new IllegalArgumentException("Illegal target position ID");
        }
        else{
            targetCardSet = board.getSequences().get(toSetID - 1);
        }
        
        Tile movedCard = sourceCardSet.getTileAt(fromCardID - 1);
        
        //If the indices changed for the card to be removed
        if(targetCardSet == sourceCardSet){
            if(fromCardID > toPositionID){
                ++fromCardID;
            }
        }
        
        targetCardSet.addTileToSequence(movedCard, toPositionID - 1);
        sourceCardSet.removeTileAt(fromCardID - 1);
    }
    
    @Override
    public Game clone() throws CloneNotSupportedException{
        Game clonedGame = new Game();
        //Clone the board
        Board clonedBoard = this.getBoard().clone();
        
        //Clone the players
        for(Player player : this.getPlayers()){
            Player clonedPlayer = clonedGame.addNewPlayer(player.clone());
            
            //If this player is the active one, we'll set them to be the active player in the cloned
            //game too.
            if(player == this.getCurrentPlayer()){
                clonedGame.setPlayerTurnTo(clonedPlayer);
            }
        }
        
        //Clone the deck
        Deck clonedDeck = deck.clone();
        clonedGame.setDeck(clonedDeck);
        
        return clonedGame;
    }

    public void applyPenaltyDraw() {
        for(int i = 0; i < PENALTY_DRAW_COUNT; ++i){
            addTileToPlayer();
        }
    }
}
