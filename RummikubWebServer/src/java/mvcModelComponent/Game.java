

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yafita870
 */
public class Game {
    private final int PENALTY_DRAW_COUNT = 3;
    
    private int currentPlayerTurn = 0;
    
    private Deck deck = new Deck();
    private Board board = new Board();
    private final ArrayList<Player> players = new ArrayList<Player>();
    
    private int aiPlayerCount = 0;
    
    private String gameName;

    /**
     * Get the value of gameName
     *
     * @return the value of gameName
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Set the value of gameName
     *
     * @param gameName new value of gameName
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    
    public Player getCurrentPlayer(){
        return players.get(currentPlayerTurn);
    }
    
    public void advancePlayerTurn(){
        getCurrentPlayer().clearCardsPlayedThisTurn();
        
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

    public int addNewPlayer(String newName, int playerId, boolean isBotStatus)
    {
        Player newPlayer = new Player(newName, playerId, isBotStatus, newHand());
        
        if(isBotStatus){
            this.players.add(newPlayer);
            aiPlayerCount++;
        }
        else{
            players.add(players.size() - aiPlayerCount, newPlayer);
        }
        
        return players.indexOf(newPlayer);
    }

    public int addNewPlayer(Player newPlayer)
    {        
        if(newPlayer.isBot()){
            this.players.add(newPlayer);
            aiPlayerCount++;
        }
        else{
            players.add(players.size() - aiPlayerCount, newPlayer);
        }
        
        return players.indexOf(newPlayer);
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
        return board.isBoardLegal() && getCurrentPlayer().isTurnLegalForPlayer();
    }

    private void setDeck(Deck newDeck) {
        deck = newDeck;
    }

    public  void setPlayerTurnTo(Player playerToSetTurnFor) throws IllegalArgumentException{
        if(!players.contains(playerToSetTurnFor)){
            throw new IllegalArgumentException("Player is not in the game");
        }
        
        currentPlayerTurn = players.indexOf(playerToSetTurnFor);
    }

    public void applyPenaltyDraw() {
        for(int i = 0; i < PENALTY_DRAW_COUNT; ++i){
            addTileToPlayer();
        }
    }

    private void setBoard(Board newBoard) {
        board = newBoard;
    }

    public void moveCard(int fromSetID, int fromCardID, int toSetID, int toPositionID) throws IllegalArgumentException {
        Sequence sourceCardSet = null;
        Sequence targetCardSet = null;
        
        //Checking if set ID is valid.
        if(fromSetID < 0 || fromSetID > board.getSequences().size() 
            || toSetID < 0 || toSetID > board.getSequences().size()){
            return;
        }
        
        //Can't place a card TO the hand.
        if(toSetID == 0){
            return;
        }
        //Getting the target set's reference
        else{
            targetCardSet = board.getSequences().get(toSetID - 1);
        }
        
        //Checking if card ID is out of range.
        if(fromCardID < 0){
            return;
        }
            
        //Getting the source set's reference
        if(fromSetID == 0){
            //Checking if the source card position is out of range.
            if(fromCardID > getCurrentPlayer().getHand().size()){
                return;
            }
            
            sourceCardSet = getCurrentPlayer().getHand();
        }
        else{
            //Checking if the source card position is out of range.
            if(fromCardID > board.getSequences().get(fromSetID - 1).size()){
                return;
            }
            
            sourceCardSet = board.getSequences().get(fromSetID - 1);
        }
        
        //Checking if the target card position is out of range.
        if(toPositionID > board.getSequences().get(toSetID - 1).size() + 1 || toPositionID < 0){
            return;
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
        
        if(toPositionID >= targetCardSet.size()){
            targetCardSet.addTileToSequence(movedCard);
        }
        else{       
            targetCardSet.addTileToSequence(movedCard, toPositionID - 1);
        }
        
        sourceCardSet.removeTileAt(fromCardID - 1);
        
        getCurrentPlayer().addToCardsPlayedThisTurn(movedCard);
    }
    
    @Override
    public Game clone() throws CloneNotSupportedException{
        Game clonedGame = new Game();
        
        clonedGame.setGameName(this.getGameName());
        //Clone the board1
      
        Board clonedBoard = this.getBoard().clone();
        
        //Clone the players
        for(Player player : this.getPlayers()){
            Player clonedPlayer = clonedGame.getPlayers().get(clonedGame.addNewPlayer(player.clone()));
            
            //If this player is the active one, we'll set them to be the active player in the cloned
            //game too.
            if(player == this.getCurrentPlayer()){
                clonedGame.setPlayerTurnTo(clonedPlayer);
            }
        }
        
        //Clone the deck
        Deck clonedDeck = deck.clone();
        clonedGame.setDeck(clonedDeck);
        
        clonedGame.setBoard(clonedBoard);
        
        return clonedGame;
    }

    public boolean checkGameEnded() {
        boolean playerFinishedHand = false;
        
        for(Player player : getPlayers()){
            if(player.getHand().size() == 0){
                playerFinishedHand = true;
            }
        }
        
        return deck.isDeckEmpty() || playerFinishedHand;
    }
    
    public boolean removeTileFromDeck(Tile tile)
    {
        return this.deck.removeTileFromDeck(tile);
    }

    public List<Player> getComputerPlayers() {
        List<Player> computerPlayers = new ArrayList<>();
        
        for(Player player : players){
            if(player.isBot()){
                computerPlayers.add(player);
            }
        }
        
        return computerPlayers;
    }

    public List<Player> getHumanPlayers() {
        List<Player> humanPlayers = new ArrayList<>();
        
        for(Player player : players){
            if(!player.isBot()){
                humanPlayers.add(player);
            }
        }
        
        return humanPlayers;
    }
}
