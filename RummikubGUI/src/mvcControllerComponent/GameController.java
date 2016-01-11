/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Stack;
import mvcViewComponent.gui.gameViewElements.boardView.BoardView;
import mvcViewComponent.gui.gameViewElements.playerView.PlayerView;
import mvcViewComponent.gui.gameViewElements.cardView.CardView;
import mvcViewComponent.gui.gameViewElements.gameView.GameView;
import mvcViewComponent.gui.gameViewElements.cardSetView.CardSetView;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import mvcControllerComponent.client.ws.Event;
import mvcControllerComponent.client.ws.InvalidParameters_Exception;
import mvcModelComponent.*;
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
    
    private final Stack<MoveInfo> actionsPlayedThisTurn = new Stack<>();
    
    private int lastEventId = 0;
    
    private static GameController instance;
    
    private Game gameState;
    private Game gameStateBackup;
    
    private String lastSaveName = "";
    
    private VBox gameSceneView;
        
    public static GameController getInstance(){
        if(instance == null){
            instance = new GameController();
        }
        
        return instance;
    }
    
    private GameController(){
        gameState = new Game();
    }
    
    private GameView generateGameView() {
        GameView newGameView = new GameView();
        
        //Add the players
        for(Player player : gameState.getPlayers()){
            PlayerView playerView = new PlayerView();
            playerView.setName(player.getName());
            playerView.setIsBot(player.isBot());
            
            //Add the players' cards
            for(Tile card : player.getHand().getTiles()){
                CardView cardView = new CardView();
                cardView.setCardValue(card.toString());
                
                playerView.addCardToHand(cardView);
            }
            
            newGameView.addPlayer(playerView);
            
            //We're comparing references to tell if this is the player whose turn it is.
            if(gameState.getCurrentPlayer() == player){
                newGameView.setCurrentPlayer(playerView);
            }
        }
            
        BoardView boardView = new BoardView();
        
        int i = 1;
        for(Sequence sequence : gameState.getBoard().getSequences()){
            CardSetView cardSet = new CardSetView();
            cardSet.setCardSetIndex(i);
            
            for(Tile tile : sequence.getTiles()){
                CardView card = new CardView();
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
    public void endTurn() throws InvalidParameters_Exception{    
        while(!actionsPlayedThisTurn.empty()){
            MoveInfo moveInfo = actionsPlayedThisTurn.pop();
            webService.moveTile(gameState.getCurrentPlayer().getId(), moveInfo.fromSetID, moveInfo.fromCardID, moveInfo.toSetID, moveInfo.toPositionID);    
        }
        
        webService.finishTurn(gameState.getCurrentPlayer().getId());
    }
    
    public void moveCard(MoveInfo moveInfo) {
        actionsPlayedThisTurn.add(moveInfo);
    }

    public GameView pollServerStatus(int playerId) {
        GameView gameView = null;
        
        try {
            List<Event> events = webService.getEvents(playerId, lastEventId);
            gameView = generateGameView();
        } 
        catch (InvalidParameters_Exception ex) {
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return gameView;
    }

    public void clearLastPlay() throws InvalidParameters_Exception {
        actionsPlayedThisTurn.clear();
        //MoveInfo moveInfo = actionsPlayedThisTurn.pop();
        //webService.takeBackTile(gameState.getCurrentPlayer().getId(), moveInfo.toSetID, moveInfo.toPositionID);
    }
}
