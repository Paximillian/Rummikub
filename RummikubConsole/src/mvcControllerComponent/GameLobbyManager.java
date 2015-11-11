/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent;

import consoleSpecificRummikubImplementations.mvcViewComponent.gameMenus.MainMenu;
import java.util.*;
import mvcControllerComponent.mainMenuCommands.LoadGameCommand;
import mvcControllerComponent.mainMenuCommands.MenuCommand;
import mvcControllerComponent.mainMenuCommands.NewGameCommand;

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
        menu.showMenu(menuCommands);
    }
}
