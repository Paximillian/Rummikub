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
import org.xml.sax.SAXException;

/**
 *
 * @author Mor
 */
public class LoadGameCommand implements MenuCommand {

    @Override
    public void execute() throws SAXException {
        XmlHandler xmlHandler = new XmlHandler();
        
        String filePath = InputRequester.RequestString("Please enter a file path to load the game");
       try{
            GameController.getInstance().loadGame(xmlHandler.loadGame(filePath));
            MessageDisplayer.showMessage("Game loaded...");
            GameController.getInstance().startGame();
       }
       catch(JAXBException  e)
       {
           MessageDisplayer.showMessage("Error game not loaded");
       }
       catch(InvalidLoadFileException e)
       {
           MessageDisplayer.showMessage("Error game not loaded" + System.lineSeparator() + e.getMessage());
       }
       
    }
    
}
