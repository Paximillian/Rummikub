/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.gameScene;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import mvcControllerComponent.GameController;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

/**
 * FXML Controller class
 *
 * @author Mor
 */
public class GameSceneController implements Initializable {

    @FXML
    private List<HBox> paneSquences;
    
    @FXML
    private HBox paneHand;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
}
