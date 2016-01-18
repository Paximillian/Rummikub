/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.gameScene;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javax.jws.WebService;
import mvcControllerComponent.GameController;
import mvcControllerComponent.GameLobbyManager;
import mvcControllerComponent.client.ws.GameDoesNotExists_Exception;
import mvcControllerComponent.client.ws.InvalidParameters_Exception;
import mvcViewComponent.gui.gameViewElements.boardView.BoardView;
import mvcViewComponent.gui.gameViewElements.gameView.GameView;
import mvcViewComponent.gui.gameViewElements.playerView.PlayerView;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;
import mvcViewComponent.gui.messagingModule.MessageDisplayer;
import mvcViewComponent.gui.sceneController.ControlledScreen;
import mvcViewComponent.gui.sceneController.ScreensController;

/**
 *
 * @author Mor
 */
public class GameSceneView extends AnchorPane implements Initializable, ControlledScreen {
  
    @FXML private GameView gameView;
    @FXML private Button btnClearLastPlay;
    @FXML private Button btnEndTurn;
    @FXML private Label labelPleaseWait;
    private static int playerId = -1;
    
    public GameSceneView(){
        pollServerStatus();
    }
    
    public static void setPlayerId(int id) {
        playerId = id;
    }
    
    public static void resignPlayer(){
        if(playerId >= 0){
            try {
                GameLobbyManager.resignGame(playerId);
            } 
            catch (InvalidParameters_Exception ex) {
                Logger.getLogger(GameSceneView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void pollServerStatus(){
        Thread thread = new Thread(() -> {
            while(playerId == -1 || GameController.getInstance().getPlayerName().equals("") || GameController.getInstance().getGameName().equals("")){try { Thread.sleep(0);} catch(InterruptedException e){ System.out.println(e.getMessage()); } }
            
            boolean activeGame = true;
            
            while(activeGame){
                if(playerId == -1 || GameController.getInstance().getPlayerName().equals("") || GameController.getInstance().getGameName().equals("")){
                    Platform.runLater(() -> ErrorDisplayer.showError("Invalid player Id"));
                    activeGame = false;
                } 
                  
                try{
                    if(GameController.getInstance().newEventsFound(playerId)){
                        GameView gameStateView = GameController.getInstance().pollServerStatus(playerId);
                        Platform.runLater(() -> {
                            setGameView(gameStateView);
                            
                            btnClearLastPlay.setDisable(gameStateView.disabledProperty().get());
                            btnEndTurn.setDisable(gameStateView.disabledProperty().get());
                            labelPleaseWait.setVisible(gameStateView.disabledProperty().get());
                        });
                    }
                    
                    Thread.sleep(10);
                }
                catch(IllegalArgumentException | InvalidParameters_Exception | GameDoesNotExists_Exception | CloneNotSupportedException | InterruptedException ex){
                    Platform.runLater(() -> ErrorDisplayer.showError(ex.getMessage()));
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
    
    public void setGameView(GameView targetGameView) {
        gameView.reset();
        
        gameView.setBoard(targetGameView.getBoard());
        gameView.setCurrentPlayer(targetGameView.getCurrentPlayer());
        
        for(PlayerView player : targetGameView.getPlayers()){
            gameView.addPlayer(player);
        }
    }
    
    public GameView getGameView(){
        return gameView;
    }
    
    @FXML
    private void handleButtonActionEndTurn(ActionEvent event) {
        try {
            GameController.getInstance().endTurn(playerId);
        } 
        catch (InvalidParameters_Exception | CloneNotSupportedException ex) {
            ErrorDisplayer.showError(ex.getMessage());
        }
    }
    
    @FXML
    private void handleButtonActionClearLastPlay(ActionEvent event) {
        try {
            setGameView(GameController.getInstance().clearLastPlay());
        } 
        catch (InvalidParameters_Exception | CloneNotSupportedException ex) {
            ErrorDisplayer.showError(ex.getMessage());
        }
    }
    
    @FXML
    private void handleButtonActionSaveGame(ActionEvent event) {
        //GameController.getInstance().saveGame();
    }
    
    @FXML
    private void handleButtonActionSaveGameAs(ActionEvent event) {
        //GameController.getInstance().saveGameAs();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnClearLastPlay.setDisable(true);
        btnEndTurn.setDisable(true);
    }
    
    @Override
     public void setScreenParent(ScreensController screenParent){ 
     } 
}
