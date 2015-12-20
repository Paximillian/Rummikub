/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.sceneController;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

/**
 *
 * @author yafita870
 */
public class ScreensController extends StackPane {
    
    public static final String MAIN_SCENE = "main";
    public static final String MAIN_SCENE_FXML = "/mvcViewComponent/gui/mainMenu/MainMenuFXML.fxml";
    public static final String NEW_GAME_SCENE = "newGameScene";
    public static final String NEW_GAME_SCENE_FXML = "/mvcViewComponent/gui/newGameScene/newGameScene.fxml";
    public static final String GAME_SCENE = "GameScene";
    public static final String GAME_SCENE_FXML = "/mvcViewComponent/gui/gameScene/GameSceneView.fxml";
    
    private static ScreensController instance;
    public static ScreensController getInstance(){
        if(instance == null){
            instance = new ScreensController();
        }
        
        return instance;
    }
    
    private HashMap<String, Node> screens = new HashMap<>();

    private ScreensController(){

    }

    public void addScreen(String name, Node screen) { 
        screens.put(name, screen); 
    }

    public boolean loadScreen(String name, String resource) {
      try { 
         URL url = getClass().getResource(resource);
         FXMLLoader myLoader = new FXMLLoader(url);
         Parent loadScreen = (Parent) myLoader.load(); 
         ControlledScreen myScreenControler = ((ControlledScreen) myLoader.getController());
         myScreenControler.setScreenParent(this); 
         addScreen(name, loadScreen); 
         return true; 
      }
      catch(Exception e) { 
         System.out.println(e.getMessage()); 
         return false; 
      } 
    } 

    public Node getScreen(final String name){
        return screens.get(name);
    }
    
    public boolean setScreen(final String name) { 

        if(screens.get(name) != null) { //screen loaded 
            final DoubleProperty opacity = opacityProperty(); 

            //Is there is more than one screen 
            if(!getChildren().isEmpty()){                
                //remove displayed screen 
                getChildren().remove(0); 
                //add new screen 
                getChildren().add(0, screens.get(name)); 
            } 
            else { 
               //no one else been displayed, then just show          
               getChildren().add(screens.get(name));         
            }

            return true; 
        } 
        else { 
            System.out.println("screen hasn't been loaded!\n");
            return false; 
        } 
    }



    public boolean unloadScreen(String name) { 
        if(screens.remove(name) == null) { 
            System.out.println("Screen didn't exist"); 
            return false; 
        } 
        else { 
            return true; 
        } 
    } 
   
   
}
