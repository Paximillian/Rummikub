/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent.mainMenuCommands;

import consoleSpecificRummikubImplementations.mvcViewComponent.inputRequestMenus.InputRequester;
import consoleSpecificRummikubImplementations.mvcViewComponent.messagingModule.MessageDisplayer;
import javax.xml.bind.JAXBException;
import mvcControllerComponent.GameController;
import mvcControllerComponent.MenuCommand;
import mvcModelComponent.xmlHandler.InvalidLoadFileException;
import mvcModelComponent.xmlHandler.XmlHandler;

/**
 *
 * @author Mor
 */
public class LoadGameCommand implements MenuCommand {

    @Override
    public void execute() {
        XmlHandler xmlHandler = new XmlHandler();
        
        String filePath = InputRequester.RequestString("plase enter afail-path to load the game");
       try{
            GameController.getInstance().loadGame(xmlHandler.loadGame(filePath));
            MessageDisplayer.showMessage("Game loaded...");
            GameController.getInstance().startGame();
       }
       catch(JAXBException | InvalidLoadFileException e)
       {
           MessageDisplayer.showMessage("erro game not loaded");
       }
       
    }
    
}
