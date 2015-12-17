/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.mainMenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mvcViewComponent.gui.sceneController.ScreensController;

/**
 *
 * @author Mor
 */
public class MainMenu extends Application {
         
    @Override
    public void start(Stage stage) throws Exception {
        
       ScreensController  mainContainer = new ScreensController(); 
       mainContainer.loadScreen(ScreensController.MAIN_SCENE, ScreensController.MAIN_SCENE_FXML); 
       mainContainer.loadScreen(ScreensController.NEW_GAME_SCENE, ScreensController.NEW_GAME_SCENE_FXML); 
       Group root = new Group(); 
       root.getChildren().addAll(mainContainer); 
       Scene scene = new Scene(root); 
       stage.setScene(scene); 
       mainContainer.setScreen(ScreensController.MAIN_SCENE);
       stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
