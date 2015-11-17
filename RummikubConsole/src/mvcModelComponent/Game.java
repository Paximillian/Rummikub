

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent;

import consoleSpecificRummikubImplementations.mvcViewComponent.gameViewElements.CardSetView;
import consoleSpecificRummikubImplementations.mvcViewComponent.gameViewElements.CardView;
import java.util.ArrayList;

/**
 *
 * @author yafita870
 */
public class Game {
    
    private final Deck deck = new Deck();
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
    }
    
    public boolean addTileToPlayer()
    {
        if(deck.isDeckEmptey())
            return false;
        
        getCurrentPlayer().addTileToHand(deck.getTile());
        return true;
    }

    public Player newPlayer(String newName, boolean isBotStatus)
    {
        Player newPlayer = new Player(newName, isBotStatus, newHand());
        this.players.add(newPlayer);
        return newPlayer;
    }
    
    private ArrayList<Tile> newHand()
    {
        ArrayList<Tile> newHand = new ArrayList<Tile>();
        for(int i = 0; i < 14; ++i){
            newHand.add(this.deck.getTile());
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

    public void moveCard(int fromSetID, int fromCardID, int toSetID, int toPositionID) throws IllegalArgumentException {
        Sequence sourceCardSet = null;
        Sequence targetCardSet = null;
        
        //Checking if set ID is valid.
        if(fromSetID < 0 || fromSetID >= board.getSequences().size() 
            || toSetID < 0 || toSetID >= board.getSequences().size()){
            throw new IllegalArgumentException("Illegal set ID");
        }
        
        //Can't place a card TO the hand.
        if(toSetID == 0){
            throw new IllegalArgumentException("Illegal set ID");
        }
        //Getting the target set's reference
        else{
            targetCardSet = board.getSequences().get(toSetID - 1);
        }
        
        //Checking if card ID is out of range.
        if(fromCardID < 0){
            throw new IllegalArgumentException("Illegal card ID");
        }
            
        //Getting the source set's reference
        if(fromSetID == 0){
            //Checking if the source card position is out of range.
            if(fromCardID >= getCurrentPlayer().getHand().size()){
                throw new IllegalArgumentException("Illegal card ID");
            }
            
            sourceCardSet = getCurrentPlayer().getHand();
        }
        else{
            //Checking if the source card position is out of range.
            if(fromCardID >= board.getSequences().get(fromSetID - 1).size()){
                throw new IllegalArgumentException("Illegal card ID");
            }
            
            sourceCardSet = board.getSequences().get(fromSetID - 1);
        }
        
        //Checking if the target card position is out of range.
        if(toPositionID > board.getSequences().get(toSetID - 1).size() || toPositionID < 0){
            throw new IllegalArgumentException("Illegal card ID");
        }
        else{
            targetCardSet = board.getSequences().get(toSetID - 1);
        }
        
        Tile movedCard = sourceCardSet.getTileAt(fromCardID - 1);
        targetCardSet.addTileToSequence(movedCard, toPositionID);
    }
    
    @Override
    public Game clone() throws CloneNotSupportedException{
        Game clonedGame = new Game();
        clonedGame = (Game)super.clone();
        
        return clonedGame;
    }
}
