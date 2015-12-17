/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.newGameScene;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import mvcViewComponent.gui.sceneController.ControlledScreen;
import mvcViewComponent.gui.sceneController.ScreensController;

/**
 * FXML Controller class
 *
 * @author yafita870
 */
public class NewGameSceneController implements Initializable , ControlledScreen {

    ScreensController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void setScreenParent(ScreensController screenParent) {
        myController = screenParent; 
    }
    
}
