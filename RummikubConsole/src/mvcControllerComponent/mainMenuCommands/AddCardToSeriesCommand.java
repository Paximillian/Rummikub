/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent.mainMenuCommands;

import consoleSpecificRummikubImplementations.mvcViewComponent.inputRequestMenus.InputRequester;

/**
 *
 * @author Mor
 */
public class AddCardToSeriesCommand implements MenuCommand {

    @Override
    public void execute() throws Exception {
        int cardId = InputRequester.RequestInt("ID of card in your hand that you want to move:");
        
    }
    
}
