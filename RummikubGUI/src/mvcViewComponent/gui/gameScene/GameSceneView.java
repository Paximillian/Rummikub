/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.gameScene;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import mvcViewComponent.gui.gameViewElements.boardView.BoardView;
import mvcViewComponent.gui.gameViewElements.gameView.GameView;
import mvcViewComponent.gui.gameViewElements.playerView.PlayerView;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

/**
 *
 * @author Mor
 */
public class GameSceneView extends VBox {
    
    @FXML private GameView gameView;
    
    public GameSceneView(){
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameSceneView.fxml"));
        fxmlLoader.setRoot(this);
        
        try{
            fxmlLoader.load();
        }
        catch(IOException ex){
            ErrorDisplayer.showError(ex.getMessage());
        }
    }
    
    public void setGameView(GameView targetGameView) {
        gameView = targetGameView;
    }
    
    public GameView getGameView(){
        return gameView;
    }
}
