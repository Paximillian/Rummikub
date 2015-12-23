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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
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
public class MainMenuController implements Initializable, ControlledScreen{
    @FXML private Label errorLabel;
    
    @FXML
    private void handleMenuButtonLoadAction(ActionEvent event) throws SAXException, IOException {
        new LoadGameCommand().run();
    }
    
    @FXML
    private void handleMenuButtonPlayAction(ActionEvent event) throws IOException
    {
        ScreensController.getInstance().setScreen(ScreensController.NEW_GAME_SCENE);      
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    } 
    
    
    public void sendErrorMSG(String errorMSG)
    {
        errorLabel.setText("error:  " + errorMSG);
        Timeline timeline = new Timeline(new KeyFrame(
        Duration.millis(5666),ae -> errorLabel.setText("")));
        timeline.play();
    }

    @Override
     public void setScreenParent(ScreensController screenParent){ 
     } 
    
}
