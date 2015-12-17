/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.newGameScene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import mvcViewComponent.gui.sceneController.ControlledScreen;
import mvcViewComponent.gui.sceneController.ScreensController;

/**
 * FXML Controller class
 *
 * @author yafita870
 */
public class NewGameSceneController implements Initializable , ControlledScreen {

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void setScreenParent(ScreensController screenParent) {
        
    }
    
    @FXML private Pane player3canvas;
    @FXML private Pane player4canvas;
    @FXML
    private void onActionNumberOfPlayers2Button(ActionEvent event) throws IOException
    {
        player3canvas.setDisable(true);
        player4canvas.setDisable(true);
    }
    
    @FXML
    private void onActionNumberOfPlayers3Button(ActionEvent event) throws IOException
    {
        player3canvas.setDisable(false);
        player4canvas.setDisable(true);
    }
    
    @FXML
    private void onActionNumberOfPlayers4Button(ActionEvent event) throws IOException
    {
        player3canvas.setDisable(false);
        player4canvas.setDisable(false);
    }
    
    @FXML
    private void onActionBackButton(ActionEvent event) throws IOException  
    {
        ScreensController.getInstance().setScreen(ScreensController.MAIN_SCENE);
    }
    
    @FXML
    private void onActionRedyButton(ActionEvent event) throws IOException    
    {
        
    }
}
