/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import mvcModelComponent.*;
import mvcModelComponent.xmlHandler.*;

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
    
    private Game gameState;
    private Game gameStateBackup;
        
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private boolean gameReady = false;
    
    private String lastSaveName = "";
    
    private VBox gameSceneView;
        
    public static GameController getInstance(){
        if(instance == null){
            instance = new GameController();
        }
        
        return instance;
    }
    public void loadGame(Game gameToLoad)
    {
        gameState = gameToLoad;
    }
    
    private GameController(){
        gameState = new Game();
    }

    public void endGame() {
        gameEnded = true;
    }
    
    public boolean hasGameEnded(){
        return gameEnded = gameState.checkGameEnded();
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
        
        gameState.setGameName(nameOfGame);
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
                gameState.addNewPlayer(playerName, isBot);
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
    
    //When a turn ends we check the validity of all the changes, if they're legal, we'll set the new state.
    public void endTurn() throws CloneNotSupportedException{        
        if(gameState.isLegalGameState()){
           if(gameState.getCurrentPlayer().getCardsPlayedThisTurn().size() > 0){
               gameState.getCurrentPlayer().setPlacedFirstSequence(true);
           }
        }
        else{
           clearLastPlay();
           gameState.applyPenaltyDraw();
        }
        
        gameState.advancePlayerTurn();
        gameStateBackup = gameState.clone();
        
        //If the game ended, move to the main menu
        if(!hasGameEnded()){
            startTurn();
        }
    }

    public void moveCard(MoveInfo moveInfo){
        gameState.moveCard(moveInfo.fromSetID, moveInfo.fromCardID, moveInfo.toSetID, moveInfo.toPositionID);
    }
    
    public void aiMoveCard() {
        try{
            MoveInfo moveInfo = gameState.getCurrentPlayer().requestMove(gameState);
            
            if(moveInfo != null){
                moveCard(moveInfo);
            }
            else{
                endTurn();
            }
        }
        catch(Exception e){
            Platform.runLater(() -> {
                System.out.println(e.getMessage());
            });
        }
    }

    public void saveGameAs() {
        FileChooser fileChooser = new FileChooser();       
        File file = fileChooser.showSaveDialog(null);
        
        if(file != null){
            String filePath = file.getAbsolutePath();  

            lastSaveName = filePath;
            new Thread (() -> saveGameToLastName()).start();
        }
         
    }

    public void saveGame() {
        if(lastSaveName.equals("")){
            saveGameAs();
        }
        else{
            Thread thread = new Thread(() -> { 
                saveGameToLastName();
            });
            
            thread.setDaemon(false);
            thread.start();
        }
    }
    
    private void saveGameToLastName(){
        XmlHandler xmlHandler = new XmlHandler(); 
        
        try{
            if(!xmlHandler.saveGame(lastSaveName, this.gameStateBackup)){
                saveGameAs();
            }
        }
        catch(Exception e){
            saveGameAs();
        }
    }

    public void clearLastPlay() throws CloneNotSupportedException {
        gameState = gameStateBackup.clone();
    }

    //If this is a turn of a bot, we'll requeset a move from them
    private void startTurn() {
        if(gameState.getCurrentPlayer().isBot()){
            Thread thread = new Thread(() -> {
                Player currentPlayer = gameState.getCurrentPlayer();
                
                try {
                    Thread.sleep(1000);
                } 
                catch (InterruptedException ex) {
                    Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                aiMoveCard();
                
                if(currentPlayer == gameState.getCurrentPlayer()){
                    startTurn();
                }
            });
            
            thread.setDaemon(true);
            thread.start();
        }
    }
}
