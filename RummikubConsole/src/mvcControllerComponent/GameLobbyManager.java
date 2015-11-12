/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent;

import consoleSpecificRummikubImplementations.mvcViewComponent.gameMenus.MainMenu;
import java.util.*;
import mvcControllerComponent.mainMenuCommands.*;

/**
 *
 * @author Mor
 */
public class GameLobbyManager {
    public static void main(String[] args) {
        MainMenu menu = new MainMenu();
        
        Map menuCommands = new HashMap();
        menuCommands.put("New Game", new NewGameCommand());
        menuCommands.put("Load Game", new LoadGameCommand());
        menuCommands.put("Exit", new ExitGameCommand());
        
        do{
            menu.showMenu(menuCommands);
            
            //If all the details have been validated, we can start a new game.
            if(GameController.getInstance().getGameReady()){
                
            }
        }
        while(!GameController.getInstance().hasGameEnded());
    }
}
