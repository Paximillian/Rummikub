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
     
    private static final String MAIN_SCREEN = "main";
    private static final String MAIN_SCREEN_FXML = "MainMenuFXML.fxml";
    
    @Override
    public void start(Stage stage) throws Exception {
        
       ScreensController  mainContainer = new ScreensController(); 
       mainContainer.loadScreen(MainMenu.MAIN_SCREEN, MainMenu.MAIN_SCREEN_FXML); 
        
       Group root = new Group(); 
       root.getChildren().addAll(mainContainer); 
       Scene scene = new Scene(root); 
       stage.setScene(scene); 
       stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
