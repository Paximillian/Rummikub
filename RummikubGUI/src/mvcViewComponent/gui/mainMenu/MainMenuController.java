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
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import mvcControllerComponent.GameController;
import mvcControllerComponent.mainMenuCommands.LoadGameCommand;
import mvcControllerComponent.mainMenuCommands.NewGameCommand;
import mvcViewComponent.gui.sceneController.ControlledScreen;
import mvcViewComponent.gui.sceneController.ScreensController;
import org.xml.sax.SAXException;

/**
 *
 * @author Mor
 */
public class MainMenuController implements Initializable, ControlledScreen {
        
    @FXML
    private Label label;
    
    @FXML
    private void handleMenuButtonLoadAction(ActionEvent event) throws SAXException, IOException {
        GameController.getInstance().setGameName("a");
        GameController.getInstance().setNumberOfPlayers(2);
        GameController.getInstance().setNumberOfComputerPlayers(1);
        GameController.getInstance().addPlayer("1", true);
        GameController.getInstance().addPlayer("2", false);
        GameController.getInstance().startGame();
    }
    
    @FXML
    private void handleMenuButtonPlayAction(ActionEvent event) throws IOException
    {
        ScreensController.getInstance().setScreen(ScreensController.NEW_GAME_SCENE);
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
     public void setScreenParent(ScreensController screenParent){ 
     } 
    
}
