/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.mainMenu;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mvcControllerComponent.GameController;
import mvcControllerComponent.mainMenuCommands.LoadGameCommand;
import org.xml.sax.SAXException;

/**
 *
 * @author Mor
 */
public class MainMenuController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleMenuButtonLoadAction(ActionEvent event) throws SAXException, IOException {
        try{
            new LoadGameCommand().execute((Stage)((Button)event.getSource()).getScene().getWindow());
        }
        catch (SAXException ex) {
            label.setText(ex.getMessage());
        }
    }
    
    @FXML
    private void handleMenuButtonPlayAction(ActionEvent event) throws IOException
    {        
        GameController.getInstance().setNumberOfPlayers(2);
        GameController.getInstance().setNumberOfComputerPlayers(0);
        GameController.getInstance().setGameName("a");
        GameController.getInstance().addPlayer("1", false);
        GameController.getInstance().addPlayer("2", false);
        GameController.getInstance().startGame((Stage)((Button)event.getSource()).getScene().getWindow());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
