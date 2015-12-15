/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.gameViewElements.cardSetView;

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
import javafx.scene.input.DragEvent;
import javafx.scene.layout.HBox;
import mvcControllerComponent.GameController;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

/**
 * FXML Controller class
 *
 * @author Mor
 */
public class CardSetController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    @FXML
    private void handleOnDragStart(DragEvent event) {
        System.out.println("Dropped");
    }    
    
    @FXML
    private void handleOnDragEnd(DragEvent event) {
        System.out.println("Left");
    }    
}
