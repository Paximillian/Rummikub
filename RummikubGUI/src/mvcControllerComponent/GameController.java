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
import java.util.Queue;
import java.util.Stack;
import mvcViewComponent.gui.gameViewElements.boardView.BoardView;
import mvcViewComponent.gui.gameViewElements.playerView.PlayerView;
import mvcViewComponent.gui.gameViewElements.cardView.CardView;
import mvcViewComponent.gui.gameViewElements.gameView.GameView;
import mvcViewComponent.gui.gameViewElements.cardSetView.CardSetView;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import mvcControllerComponent.client.ws.Event;
import mvcControllerComponent.client.ws.EventType;
import mvcControllerComponent.client.ws.GameDetails;
import mvcControllerComponent.client.ws.GameDoesNotExists_Exception;
import mvcControllerComponent.client.ws.InvalidParameters_Exception;
import mvcControllerComponent.client.ws.PlayerDetails;
import mvcControllerComponent.client.ws.PlayerType;
import mvcModelComponent.*;
import mvcModelComponent.Tile.Color;
import mvcModelComponent.Tile.Rank;
import mvcModelComponent.xmlHandler.*;
import mvcViewComponent.gui.sceneController.ScreensController;

/**
 *
 * @author Mor
 */

//Game controller is a singleton that manages the current game.
public class GameController extends WebClient {
    private static final int MAX_PLAYER_COUNT = 4;
    private static final int MIN_PLAYER_COUNT = 2;
    
    private final Queue<MoveInfo> actionsPlayedThisTurn = new LinkedBlockingQueue<>(10000);
    
    private int lastEventId = 0;
    
    private static GameController instance;
    
    private Game gameState;
    private Game gameStateBackup;
    
    private String lastSaveName = "";
    
    private VBox gameSceneView;
    
    private String gameName = "";
    
    private boolean isPlayerTurn;
    
    private String playerName = "";
        
    public static GameController getInstance(){
        if(instance == null){
            instance = new GameController();
        }
        
        return instance;
    }
    
    private GameController(){
        gameState = new Game();
    }
    
    public void setPlayerName(String name){
        playerName = name;
    }
    
    public void setGameName(String name){
        gameName = name;
    }
    
    private GameView generateGameView() {
        GameView newGameView = new GameView();
        newGameView.setDisable(!isPlayerTurn);
        
        //Add the players
        for(Player player : gameState.getPlayers()){
            PlayerView playerView = new PlayerView();
            playerView.setDisable(!isPlayerTurn);
            playerView.setName(player.getName());
            playerView.setIsBot(player.isBot());
            
            
            //Add the players' cards
            for(Tile card : player.getHand().getTiles()){
                CardView cardView = new CardView();
                cardView.setDisable(!isPlayerTurn);
                if(playerName.equals(player.getName())){
                    cardView.setCardValue(card.toString());
                }
                else{
                    cardView.setCardValue("");
                }

                playerView.addCardToHand(cardView);
            }
            
            newGameView.addPlayer(playerView);
            
            //We're comparing references to tell if this is the player whose turn it is.
            if(gameState.getCurrentPlayer() == player){
                newGameView.setCurrentPlayer(playerView);
            }
        }
            
        BoardView boardView = new BoardView();
        boardView.setDisable(!isPlayerTurn);
        
        int i = 1;
        for(Sequence sequence : gameState.getBoard().getSequences()){
            CardSetView cardSet = new CardSetView();
            cardSet.setDisable(!isPlayerTurn);
            cardSet.setCardSetIndex(i);
            
            for(Tile tile : sequence.getTiles()){
                CardView card = new CardView();
                card.setDisable(!isPlayerTurn);
                card.setCardValue(tile.toString());
                
                cardSet.addCard(card);
            }
            
            boardView.addCardSet(cardSet);
            
            ++i;
        }
        
        newGameView.setBoard(boardView);
        
        return newGameView;
    }

    //When a turn ends we check the validity of all the changes, if they're legal, we'll set the new state.
    public void endTurn(int playerId) throws InvalidParameters_Exception, CloneNotSupportedException{    
        while(actionsPlayedThisTurn.size() > 0){
            MoveInfo moveInfo = actionsPlayedThisTurn.poll();
            webService.moveTile(playerId, moveInfo.fromSetID, moveInfo.fromCardID, moveInfo.toSetID, moveInfo.toPositionID);    
        }
        
        webService.finishTurn(playerId);
        
        if(gameStateBackup == null){
            gameStateBackup = gameState;
        }
        
        gameState = gameStateBackup.clone();
        
        isPlayerTurn = false;
    }
    
    public void moveCard(MoveInfo moveInfo) {
        actionsPlayedThisTurn.offer(moveInfo);
    }

    public GameView pollServerStatus(int playerId) throws InvalidParameters_Exception, GameDoesNotExists_Exception, IllegalArgumentException, CloneNotSupportedException {
        GameView gameView = null;
        
        List<Event> events = webService.getEvents(playerId, lastEventId);
        resolveEvents(events);
        gameView = generateGameView();
        
        return gameView;
    }

    public GameView clearLastPlay() throws InvalidParameters_Exception, CloneNotSupportedException {
        actionsPlayedThisTurn.clear();
        gameState = gameStateBackup.clone();
        
        return generateGameView();
        //MoveInfo moveInfo = actionsPlayedThisTurn.pop();
        //webService.takeBackTile(gameState.getCurrentPlayer().getId(), moveInfo.toSetID, moveInfo.toPositionID);
    }

    private void resolveEvents(List<Event> events) throws GameDoesNotExists_Exception, IllegalArgumentException, CloneNotSupportedException {
        if(events.size() > 0){
            Event event = events.get(0);
            events.remove(0);
            
            switch(event.getType()){
                case GAME_START:
                    gameStart();
                    break;
                case PLAYER_TURN:
                    setPlayerTurn(event);
                    break;
                case TILE_MOVED:
                    gameState.moveCard(event.getPlayerName(), event.getSourceSequenceIndex(), event.getSourceSequencePosition(), event.getTargetSequenceIndex(), event.getTargetSequencePosition());
                    break;
                case PLAYER_FINISHED_TURN:
                    actionsPlayedThisTurn.clear();
                    gameStateBackup = gameState.clone();
                    break;
                case PLAYER_RESIGNED:
                    playerResigned(event);
                    break;
            }
            
            lastEventId++;
        }
    }

    public Player getPlayerByName(String playerName) throws IllegalArgumentException{
        Player player = null;
        
        for(Player p : gameState.getPlayers()){
            if(p.getName().equals(playerName)){
                player = p;
            }
        }
        
        if(player == null){
            throw new IllegalArgumentException("Player not found in game");
        }
        
        return player;
    }

    private void gameStart() throws GameDoesNotExists_Exception{
        gameState.setGameName(gameName);

        List<PlayerDetails> players = webService.getPlayersDetails(gameName);

        for(PlayerDetails playerDetails : players){
            Player player = new Player(playerDetails.getName(), playerDetails.getType() == PlayerType.COMPUTER, new ArrayList<>(), playerDetails.isPlayedFirstSequence());
            
            for(mvcControllerComponent.client.ws.Tile tile : playerDetails.getTiles()){
                Color color = null;
                switch(tile.getColor()){
                    case BLACK:
                        color = Color.BLACK;
                        break;
                    case RED:
                        color = Color.RED;
                        break;
                    case BLUE:
                        color = Color.BLUE;
                        break;
                    case YELLOW:
                        color = Color.YELLOW;
                        break;
                }

                Tile newTile = new Tile(color, Rank.fromValue(tile.getValue()));

                player.addTileToHand(newTile);
            }

            gameState.addNewPlayer(player);
        }
    }

    private void setPlayerTurn(Event event) throws IllegalArgumentException, CloneNotSupportedException {
        Player player = getPlayerByName(event.getPlayerName());
        gameState.setPlayerTurnTo(player);
        
        if(playerName.equals(player.getName())){
            isPlayerTurn = true;
            java.awt.Toolkit.getDefaultToolkit().beep();
            gameState.getCurrentPlayer().setHand(event.getTiles());
        }
        
        gameStateBackup = gameState.clone();
    }

    public Object getGameName() {
        return gameName;
    }

    public boolean newEventsFound(int playerId) throws InvalidParameters_Exception {
        return webService.getEvents(playerId, lastEventId).size() > 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    private void playerResigned(Event event) {
        if(event.getPlayerName().equals(playerName)){
            Platform.exit();
        }
    }
}
