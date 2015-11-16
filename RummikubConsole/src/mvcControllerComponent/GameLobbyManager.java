/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent;

import consoleSpecificRummikubImplementations.mvcViewComponent.gameMenus.Menu;
import java.util.*;
import mvcControllerComponent.mainMenuCommands.*;

/**
 *
 * @author Mor
 */
public class GameLobbyManager {
    public static void main(String[] args) {
        Menu menu = new Menu();
        
        Map<String, MenuCommand> menuCommands = new HashMap<String, MenuCommand>();
        menuCommands.put("New Game", new NewGameCommand());
        menuCommands.put("Load Game", new LoadGameCommand());
        menuCommands.put("Exit", new ExitGameCommand());
        
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
        while(!GameController.getInstance().hasGameEnded());
    }
}
