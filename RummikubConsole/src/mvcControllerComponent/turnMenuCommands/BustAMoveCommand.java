/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent.turnMenuCommands;

import consoleSpecificRummikubImplementations.mvcViewComponent.inputRequestMenus.InputRequester;
import mvcControllerComponent.GameController;
import mvcControllerComponent.MenuCommand;

/**
 *
 * @author Mor
 */
public class BustAMoveCommand implements MenuCommand {

    @Override
    public void execute() throws Exception {
        //Request input from the view component
        int fromSetID = InputRequester.RequestInt("ID of set you want to move a card from:");
        int fromCardID = InputRequester.RequestInt("ID of card in the set that you want to move:");
        int toSetID = InputRequester.RequestInt("ID of set you want to move a card to:");
        int toPositionID = InputRequester.RequestInt("ID of position in the set you want to move to:");

        //Move the cards around according to input
        GameController.getInstance().moveCard(fromSetID, fromCardID, toSetID, toPositionID);
    }
    
}
