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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import mvcControllerComponent.GameController;
import mvcViewComponent.gui.gameViewElements.boardView.BoardView;
import mvcViewComponent.gui.gameViewElements.gameView.GameView;
import mvcViewComponent.gui.gameViewElements.playerView.PlayerView;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

/**
 *
 * @author Mor
 */
public class GameSceneView extends VBox implements Initializable {
    
    @FXML private GameView gameView;
    
    public GameSceneView(){
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameSceneView.fxml"));
        fxmlLoader.setRoot(this);
        
        try{
            fxmlLoader.load();
        }
        catch(IOException ex){
            ErrorDisplayer.showError(ex.getMessage());
        }
    }
    
    public void setGameView(GameView targetGameView) {
        gameView = targetGameView;
    }
    
    public GameView getGameView(){
        return gameView;
    }
    
    @FXML
    private void handleButtonActionEndTurn(ActionEvent event) {
        try {
            GameController.getInstance().endTurn();
        } 
        catch (CloneNotSupportedException ex) {
            ErrorDisplayer.showError(String.format("A cloning error has occured, please contact the developers and tell them to: 'Fix it, you lazy fucks!'%sThank you!", System.lineSeparator()));
        }
    }
    
    @FXML
    private void handleButtonActionClearLastPlay(ActionEvent event) {
        try {
            GameController.getInstance().clearLastPlay();
        } 
        catch (CloneNotSupportedException ex) {
            ErrorDisplayer.showError(String.format("A cloning error has occured, please contact the developers and tell them to: 'Fix it, you lazy fucks!'%sThank you!", System.lineSeparator()));
        }
    }
    
    @FXML
    private void handleButtonActionSaveGame(ActionEvent event) {
        GameController.getInstance().saveGame();
    }
    
    @FXML
    private void handleButtonActionSaveGameAs(ActionEvent event) {
        GameController.getInstance().saveGameAs();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
