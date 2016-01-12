/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import ws.rummikub.Event;
import ws.rummikub.EventType;
import ws.rummikub.InvalidParameters;
import ws.rummikub.InvalidParameters_Exception;

/**
 *
 * @author Mor
 */

//Game controller is a singleton that manages the current game.
public class GameController {
    private static final int MAX_PLAYER_COUNT = 4;
    private static final int MIN_PLAYER_COUNT = 2;
        
    private int numberOfPlayers;
    private int numberOfComputerPlayers;
    
    private Game gameState;
    private Game gameStateBackup;
        
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private boolean gameReady = false;
    
    private String lastSaveName = "";
    
    private List<Event> eventList = new ArrayList<Event>();
            
    public GameController(Game game, int humanPlayers, int aiPlayers){
        gameState = game;
        numberOfComputerPlayers = aiPlayers;
        numberOfPlayers = humanPlayers + aiPlayers;
    }

    public void endGame() {
        gameEnded = true;
        
        Event overEvent = new Event();
        overEvent.setType(EventType.GAME_OVER);
        eventList.add(overEvent);
        
        Event winnerEvent = new Event();
        winnerEvent.setType(EventType.GAME_WINNER);
        int max = 0;
        for(Player player : gameState.getPlayers()){
            if(player.getScore() > max){
                winnerEvent.setPlayerName(player.getName());
                max = player.getScore();
            }
        }
        eventList.add(winnerEvent);
    }
    
    public boolean hasGameEnded(){
        return gameEnded = gameState.checkGameEnded();
    }
    
    boolean getGameReady() {
        return gameReady;
    }
    
    //When a turn ends we check the validity of all the changes, if they're legal, we'll set the new state.
    public void endTurn() throws InvalidParameters_Exception{        
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
        
        try {
            gameStateBackup = gameState.clone();
        } 
        catch (CloneNotSupportedException ex) {
            throw new InvalidParameters_Exception("Can't clone game", new InvalidParameters());
        }
        
        Event endTurnEvent = new Event();
        endTurnEvent.setType(EventType.PLAYER_FINISHED_TURN);
        eventList.add(endTurnEvent);
        
        //If the game ended, move to the main menu
        if(!hasGameEnded()){
            startTurn();
        }
    }

    public void moveCard(MoveInfo moveInfo){
        gameState.moveCard(moveInfo.fromSetID, moveInfo.fromCardID, moveInfo.toSetID, moveInfo.toPositionID);
        
        Event moveEvent = new Event();
        moveEvent.setType(EventType.TILE_MOVED);
        moveEvent.setSourceSequenceIndex(moveInfo.fromSetID);
        moveEvent.setSourceSequencePosition(moveInfo.fromCardID);
        moveEvent.setTargetSequenceIndex(moveInfo.toSetID);
        moveEvent.setTargetSequencePosition(moveInfo.toPositionID);
        eventList.add(moveEvent);
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

    public void clearLastPlay() throws InvalidParameters_Exception {
        try {
            gameState = gameStateBackup.clone();
        } 
        catch (CloneNotSupportedException ex) {
            throw new InvalidParameters_Exception("Can't clone game", new InvalidParameters());
        }
    }

    //If this is a turn of a bot, we'll requeset a move from them
    private void startTurn() {
        Event newTurnEvent = new Event();
        newTurnEvent.setType(EventType.PLAYER_TURN);
        newTurnEvent.setPlayerName(gameState.getCurrentPlayer().getName());
        eventList.add(newTurnEvent);
        
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

    public List<Event> getEvents(int eventId) {
        return eventList.subList(eventId, eventList.size());
    }
}
