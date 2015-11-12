/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent.mainMenuCommands;

import consoleSpecificRummikubImplementations.mvcViewComponent.inputRequestMenus.InputRequester;
import mvcControllerComponent.GameController;

/**
 *
 * @author Mor
 */
public class NewGameCommand implements MenuCommand {

    @Override
    public void execute() {
        int playerCount = 0;
        int computerPlayerCount = 0;
        
        try{
            try{
                //Player count
                playerCount = InputRequester.RequestInt("How many players are in the game?");
                GameController.getInstance().setNumberOfPlayers(playerCount);

                //Computer player count
                computerPlayerCount = InputRequester.RequestInt("How many computer controlled players are in the game?");
                GameController.getInstance().setNumberOfComputerPlayers(computerPlayerCount);
            }
            catch(IllegalArgumentException ex){
                System.out.print(ex.getMessage());
                return;
            }

            //Game name
            String nameOfGame = InputRequester.RequestString("What will be the name of the game room?");
            GameController.getInstance().setGameName(nameOfGame);

            //Get the name of each player
            for(int i = 1; i <= playerCount - computerPlayerCount; ++i){
                String playerName = InputRequester.RequestString(String.format("Name of player %d?", i));
                GameController.getInstance().addPlayer(playerName, false);
            }
            
            //Add bots
            for(int i = 0; i < computerPlayerCount; ++i){
                GameController.getInstance().addPlayer(String.format("Bot%d", i + 1), true);
            }
        }
        catch(IllegalStateException ex){
            System.out.print(ex.getMessage());
        }
    }
    
}
