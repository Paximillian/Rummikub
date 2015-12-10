/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.console.gameMenus;

import mvcViewComponent.console.inputRequestMenus.InputRequester;
import mvcControllerComponent.MenuCommand;
import java.util.*;

/**
 *
 * @author Mor
 */
public class Menu{
    public void showMenu(Map<String, MenuCommand> menuItems){
        List<MenuCommand> menuCommands = new ArrayList<MenuCommand>();
        
        int currentCommandID = 1;
        for(String menuItem : menuItems.keySet()){
            menuCommands.add(menuItems.get(menuItem));
            
            System.out.print(String.format("%d - %s%s", currentCommandID, menuItem, System.lineSeparator()));
            
            ++currentCommandID;
        }
                
        try{
            int menuChoice = InputRequester.RequestInt("Choose an action:");
            
            if(menuChoice < 1 || menuChoice > menuCommands.size()){
                throw new IndexOutOfBoundsException("Invalid menu choice");
            }
            
            menuCommands.get(menuChoice - 1).execute();
        }
        catch(Exception ex){
            System.out.println("Invalid input " + ex.getMessage());
        }
    }
}
