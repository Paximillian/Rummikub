/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent;

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
    
    private static GameController instance;
        
    private int numberOfPlayers;
    private int numberOfComputerPlayers;
    private String gameName;
    
    private Game gameState;
    
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
        //Create a new game, make it into a Game view.
        GameView gameView = generateGameView();
        
        //While the game is running, we'll print the game state and ask the user to enter an action.
        while(!gameEnded){
            gameView = generateGameView();
            gameView.printComponent();
            
            //Show the action menu;
            Menu actionMenu = new Menu();
            Map menuItems = new HashMap();
            menuItems.put("Add Card To Series", new AddCardToSeriesCommand());
            menuItems.put("Combine Series", new CombineSeriesCommand());
            menuItems.put("Split Series", new SplitSeriesCommand());
            menuItems.put("Move Card Between Series", new MoveCardCommand());
            menuItems.put("Done", new EndTurnCommand());
            actionMenu.showMenu(menuItems);
            
            //Request input from the view component
            int fromSetID = InputRequester.RequestInt("ID of set you want to move a card from:");
            int fromCardID = InputRequester.RequestInt("ID of card in the set that you want to move:");
            int toSetID = InputRequester.RequestInt("ID of set you want to move a card to:");
            int toPositionID = InputRequester.RequestInt("ID of position in the set you want to move to:");
            
            //Move the cards around according to input
            gameView.moveCard(fromSetID, fromCardID, toSetID, toPositionID);
        }
    }

    //TODO: Fix with Eitan's Game object.
    private GameView generateGameView() {
        GameView newGameView = new GameView();
        
        //Add the players
        for(Player player : gameState.getPlayers()){
            PlayerView playerView = new PlayerView(player.getName());
            
            //Add the players' cards
            for(Tile card : player.getHand()){
                CardView cardView = new CardView(card.toString());
                
                playerView.addCardToHand(cardView);
            }
        }
                
        return newGameView;
    }
}
