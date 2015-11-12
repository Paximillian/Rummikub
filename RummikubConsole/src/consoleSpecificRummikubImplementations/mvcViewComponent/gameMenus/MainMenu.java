/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consoleSpecificRummikubImplementations.mvcViewComponent.gameMenus;

import mvcControllerComponent.mainMenuCommands.*;
import java.util.*;

/**
 *
 * @author Mor
 */
public class MainMenu {
    public void showMenu(Map<String, MenuCommand> menuItems){
        System.out.print("Choose an action:");
        System.out.print(System.lineSeparator());
        
        List<MenuCommand> menuCommands = new ArrayList<MenuCommand>();
        
        int currentCommandID = 1;
        for(String menuItem : menuItems.keySet()){
            menuCommands.add(menuItems.get(menuItem));
            
            System.out.print(String.format("%d - %s%s", currentCommandID, menuItem, System.lineSeparator()));
            
            ++currentCommandID;
        }
        
        Scanner inputScanner = new Scanner(System.in);
        int menuChoice = inputScanner.nextInt();
        
        try{
            if(menuChoice < 1 || menuChoice > menuCommands.size()){
                throw new IndexOutOfBoundsException("Invalid menu choice");
            }
            
            menuCommands.get(menuChoice - 1).execute();
        }
        catch(Exception ex){
            System.out.print(ex.getMessage());
        }
    }
}
