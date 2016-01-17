/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.joinGame;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import mvcControllerComponent.GameController;
import mvcControllerComponent.GameLobbyManager;
import mvcControllerComponent.client.ws.GameDoesNotExists_Exception;
import mvcControllerComponent.client.ws.InvalidParameters_Exception;
import mvcViewComponent.gui.gameScene.GameSceneView;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;
import mvcViewComponent.gui.newGameScene.NewGameSceneController;
import mvcViewComponent.gui.sceneController.ControlledScreen;
import mvcViewComponent.gui.sceneController.ScreensController;

/**
 * FXML Controller class
 *
 * @author Mor
 */
public class JoinGameMenuController implements Initializable, ControlledScreen {

    @FXML private VBox gameList;
    @FXML private TextField playerNameTextBox;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshGameList();
    }    
    
    @FXML
    public void Button_RefreshGameList(ActionEvent event){
        refreshGameList();
    }
    
    @FXML
    public void Button_BackToMainMenu(ActionEvent event){
        ScreensController.getInstance().setScreen(ScreensController.MAIN_SCENE); 
    }

    private void refreshGameList() {
        gameList.getChildren().clear();
        
        for(String gameName : GameLobbyManager.getWaitingGameNames()){
            Button joinGameButton = new Button("Join Room: " + gameName);
            joinGameButton.setMaxWidth(1200);
            joinGameButton.setPrefWidth(1200);
            joinGameButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(playerNameTextBox.getText().isEmpty()){
                        ErrorDisplayer.showError("Please enter a username");
                    }
                    else{
                        try {
                            int id = GameLobbyManager.joinGame(gameName, playerNameTextBox.getText());
                            GameSceneView.setPlayerId(id);
                            GameController.getInstance().setPlayerName(playerNameTextBox.getText());
                            NewGameSceneController.setLoadedGame(gameName);
                            ScreensController.getInstance().setScreen(ScreensController.NEW_GAME_SCENE);
                        } 
                        catch (GameDoesNotExists_Exception | InvalidParameters_Exception ex) {
                            ErrorDisplayer.showError(ex.getMessage());
                        }
                    }
                }
            });
            gameList.getChildren().add(joinGameButton);
        }
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
    }
}
