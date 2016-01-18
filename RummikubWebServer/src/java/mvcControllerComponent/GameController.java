/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent;

import controller.LobbyManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
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
import ws.rummikub.GameDoesNotExists_Exception;
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
    private static final int TURN_TIMER = 120 * 1000;
        
    private int numberOfPlayers;
    private int numberOfComputerPlayers;
    
    private Game gameState;
    private Game gameStateBackup;
        
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private boolean gameReady = false;
    
    private String lastSaveName = "";
    
    private List<Event> eventList = new ArrayList<Event>();
    private List<Event> eventsSinceLastSync = new ArrayList<Event>();
            
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
            if(gameState.getCurrentPlayer().getCardsPlayedThisTurn().isSumOfFirstSequenceSufficient()){
                gameState.getCurrentPlayer().setPlacedFirstSequence(true);
            }
        
            while(eventsSinceLastSync.size() > 0){
                eventList.add(eventsSinceLastSync.get(0));
                eventsSinceLastSync.remove(0);
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
        moveEvent.setPlayerName(gameState.getCurrentPlayer().getName());
        moveEvent.setType(EventType.TILE_MOVED);
        moveEvent.setSourceSequenceIndex(moveInfo.fromSetID);
        moveEvent.setSourceSequencePosition(moveInfo.fromCardID);
        moveEvent.setTargetSequenceIndex(moveInfo.toSetID);
        moveEvent.setTargetSequencePosition(moveInfo.toPositionID);
        eventsSinceLastSync.add(moveEvent);
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

    public void clearLastPlay() throws InvalidParameters_Exception {
        eventsSinceLastSync.clear();
        
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
        newTurnEvent.setTiles(gameState.getCurrentPlayer().getHand().getTiles());
        eventList.add(newTurnEvent);
        
        while(gameState.getCurrentPlayer().isBot()){
            aiMoveCard();
        }
    }

    public List<Event> getEvents(int eventId) {
        if(eventId > eventList.size()){
            return new ArrayList<Event>();
        }
        else{
            return eventList.subList(eventId, eventList.size());
        }
    }

    public void startGame() {
        Event startGameEvent = new Event();
        startGameEvent.setType(EventType.GAME_START);
        eventList.add(startGameEvent);
        try{
            gameStateBackup = gameState.clone();
        }
        catch(CloneNotSupportedException ex){
            gameStateBackup = gameState;
        }
        
        startTurn();
    }

    public void resignPlayer(Integer playerId) throws InvalidParameters_Exception, GameDoesNotExists_Exception {
        Event resignEvent = new Event();
        resignEvent.setType(EventType.PLAYER_RESIGNED);
        resignEvent.setPlayerName(LobbyManager.getPlayerDetails(playerId).getName());
        
        eventList.add(resignEvent);
    }
}
