/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent.mainMenuCommands;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mvcControllerComponent.MenuCommand;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;
import mvcControllerComponent.GameController;

/**
 *
 * @author Mor
 */
public class NewGameCommand implements MenuCommand {

    @Override
    public void execute() throws IOException {
        
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("newGameScene.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        
        //----------------------------------------shmok------------------------
        /*
        int playerCount = 0;
        int computerPlayerCount = 0;
        
        try{
            try{
                //Player count
                try{
                    //playerCount = InputRequester.RequestInt("How many players are in the game?");
                }
                catch(Exception e){
                    ErrorDisplayer.showError("Invalid input " + e.getMessage());
                }
                
                GameController.getInstance().setNumberOfPlayers(playerCount);

                //Computer player count
                try{
                    //computerPlayerCount = InputRequester.RequestInt("How many computer controlled players are in the game?");
                }
                catch(Exception e){
                    ErrorDisplayer.showError("Invalid input " + e.getMessage());
                }
                GameController.getInstance().setNumberOfComputerPlayers(computerPlayerCount);
            }
            catch(IllegalArgumentException ex){
                ErrorDisplayer.showError(ex.getMessage());
                return;
            }

            //Game name
            String nameOfGame = "";
            try{
                //nameOfGame = InputRequester.RequestString("What will be the name of the game room?");
            }
            catch(Exception e){
                ErrorDisplayer.showError("Invalid input " + e.getMessage());
                return;
            }
            
            GameController.getInstance().setGameName(nameOfGame);

            //Get the name of each player
            for(int i = 1; i <= playerCount - computerPlayerCount; ++i){
                try{
                    //String playerName = InputRequester.RequestString(String.format("Name of player %d?", i));
                    //GameController.getInstance().addPlayer(playerName, false);
                }
                catch(IllegalArgumentException e){
                    ErrorDisplayer.showError(e.getMessage());
                    --i;
                }
            }
            
            //Add bots
            for(int i = 0; i < computerPlayerCount; ++i){
                GameController.getInstance().addPlayer(String.format("Bot%d", i + 1), true);
            }
        }
        catch(IllegalStateException ex){
            ErrorDisplayer.showError(ex.getMessage());
        }
                */
    }
    
}
