/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent;

import mvcViewComponent.console.gameMenus.Menu;
import java.util.*;
import mvcControllerComponent.mainMenuCommands.*;

/**
 *
 * @author Mor
 */
public class GameLobbyManager {
    public static boolean exitGame = false;
        
    public static void main(String[] args) {
        Menu menu = new Menu();
        
        Map<String, MenuCommand> menuCommands = new HashMap<String, MenuCommand>();
        menuCommands.put("New Game", new NewGameCommand());
        menuCommands.put("Load Game", new LoadGameCommand());
        menuCommands.put("Exit", () -> exitGame());
        
        do{
            try{
                menu.showMenu(menuCommands);
            }
            catch(InputMismatchException e){
                System.out.println(e.getMessage());
            }
            
            //If all the details have been validated, we can start a new game.
            if(GameController.getInstance().getGameReady()){
                GameController.getInstance().startGame();
            }
        }
        while(!exitGame);
    }

    private static void exitGame() {
        exitGame = true;
    }
}
