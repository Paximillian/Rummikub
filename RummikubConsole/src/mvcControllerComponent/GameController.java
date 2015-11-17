/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent;

import consoleSpecificRummikubImplementations.mvcViewComponent.errorModule.ErrorDisplayer;
import mvcControllerComponent.turnMenuCommands.EndTurnCommand;
import mvcControllerComponent.turnMenuCommands.BustAMoveCommand;
import mvcControllerComponent.turnMenuCommands.SaveAsCommand;
import mvcControllerComponent.turnMenuCommands.SaveCommand;
import consoleSpecificRummikubImplementations.mvcViewComponent.gameMenus.Menu;
import consoleSpecificRummikubImplementations.mvcViewComponent.gameViewElements.*;
import consoleSpecificRummikubImplementations.mvcViewComponent.inputRequestMenus.InputRequester;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mvcControllerComponent.mainMenuCommands.*;
import mvcModelComponent.*;

/**
 *
 * @author Mor
 */

//Game controller is a singleton that manages the current game.
public class GameController {
    private static final int MAX_PLAYER_COUNT = 4;
    private static final int MIN_PLAYER_COUNT = 2;
    private static final int PENALTY_DRAW_COUNT = 3;
    
    private static GameController instance;
        
    private int numberOfPlayers;
    private int numberOfComputerPlayers;
    private String gameName;
    
    private Game gameState;
    private Game gameStateBackup;
    
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private boolean gameReady = false;
    
    public static GameController getInstance(){
        if(instance == null){
            instance = new GameController();
        }
        
        return instance;
    }
    
    private GameController(){
        gameState = new Game();
    }

    public void endGame() {
        gameEnded = true;
    }
    
    public boolean hasGameEnded(){
        return gameEnded;
    }
    
    boolean getGameReady() {
        return gameReady;
    }
    
    public void setNumberOfPlayers(int playerCount) throws IllegalArgumentException, IllegalStateException{
        if(gameStarted){
            throw new IllegalStateException("Can't set the number of players of a game that already started");
        }
        
        if(playerCount > MAX_PLAYER_COUNT || playerCount < MIN_PLAYER_COUNT){
            throw new IllegalArgumentException(String.format("Illegal number of players(Legal range is %d-%d)", MIN_PLAYER_COUNT, MAX_PLAYER_COUNT));
        }
        
        numberOfPlayers = playerCount;
    }

    public void setNumberOfComputerPlayers(int computerPlayerCount) throws IllegalArgumentException, IllegalStateException{
        if(gameStarted){
            throw new IllegalStateException("Can't set the number of players of a game that already started");
        }
        
        if(computerPlayerCount < 0 || computerPlayerCount > numberOfPlayers){
            throw new IllegalArgumentException(String.format("Illegal number of players(Legal range is %d-%d)", MIN_PLAYER_COUNT, MAX_PLAYER_COUNT));
        }
        
        numberOfComputerPlayers = computerPlayerCount;
    }

    public void setGameName(String nameOfGame) throws IllegalStateException {
        if(gameStarted){
            throw new IllegalStateException("Can't set the name of a game that already started");
        }
        
        gameName = nameOfGame;
    }

    public void addPlayer(String playerName, boolean isBot) throws IllegalStateException, IllegalArgumentException {
        if(gameStarted){
            throw new IllegalStateException("Can't add a player to a game that already started");
        }
        
        //Name can't already exist.
        if(gameState.getPlayers().stream().noneMatch(player -> player.getName().equals(playerName))){
            //Name can't be empty
            if(playerName.length() == 0){
                throw new IllegalArgumentException("Player name can't be empty");
            }
            else{
                gameState.newPlayer(playerName, isBot);
            }
        }
        else{
            if(isBot){
                addPlayer(String.format("B%s", playerName), isBot);
            }
            else{
                throw new IllegalArgumentException("This player name already exists.");
            }
        }
                
        //If all players have been added.
        if(gameState.getPlayers().size() == numberOfPlayers){
            gameReady = true;
        }
    }
    
    public void resetGame(){
        instance = null;
    }

    //Start a new game.
    void startGame() {
        GameView gameView = generateGameView();
        try{
            gameStateBackup = gameState.clone();
        }
        catch(CloneNotSupportedException e){
            ErrorDisplayer.showError(e.getMessage());
        }
        
        //While the game is running, we'll print the game state and ask the user to enter an action.
        while(!gameEnded){
            gameView.printComponent();
            
            //Show the action menu;
            Menu actionMenu = new Menu();
            Map menuItems = new HashMap();
            menuItems.put("Bust a Move", new BustAMoveCommand());
            menuItems.put("Save", new SaveCommand());
            menuItems.put("Save As", new SaveAsCommand());
            menuItems.put("Done", new EndTurnCommand());
            actionMenu.showMenu(menuItems);
        }
    }
    
    //When a turn ends we check the validity of all the changes, if they're legal, we'll set the new state.
    public void endTurn(){        
        if(gameState.isLegalGameState()){
           gameStateBackup = gameState;
        }
        else{
            for(int i = 0; i < PENALTY_DRAW_COUNT; ++i){
                gameState.addTileToPlayer();
            }
        }
        
        gameState.advancePlayerTurn();
    }

    private GameView generateGameView() {
        GameView newGameView = new GameView();
        
        //Add the players
        for(Player player : gameState.getPlayers()){
            PlayerView playerView = new PlayerView(player.getName(), player.isBot());
            
            //Add the players' cards
            for(Tile card : player.getHand().getTiles()){
                CardView cardView = new CardView(card.toString());
                
                playerView.addCardToHand(cardView);
            }
            
            newGameView.addPlayer(playerView);
            
            //We're comparing references to tell if this is the player whose turn it is.
            if(gameState.getCurrentPlayer() == player){
                newGameView.setCurrentPlayer(playerView);
            }
        }
            
        BoardView boardView = new BoardView();
        for(Sequence sequence : gameState.getBoard().getSequences()){
            CardSetView cardSet = new CardSetView();
            
            for(Tile tile : sequence.getTiles()){
                cardSet.addCard(new CardView(tile.toString()));
            }
            
            boardView.addCardSet(cardSet);
        }
        
        newGameView.setBoard(boardView);
        
        return newGameView;
    }

    public void moveCard(int fromSetID, int fromCardID, int toSetID, int toPositionID) {
        try{
            gameState.moveCard(fromSetID, fromCardID, toSetID, toPositionID);
        }
        catch(Exception e){
            ErrorDisplayer.showError(e.getMessage());
        }
    }
}
