/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent.mainMenuCommands;

import java.io.File;
import java.io.IOException;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mvcViewComponent.gui.messagingModule.MessageDisplayer;
import javax.xml.bind.JAXBException;
import mvcControllerComponent.GameController;
import mvcControllerComponent.MenuCommand;
import mvcModelComponent.xmlHandler.InvalidLoadFileException;
import mvcModelComponent.xmlHandler.XmlHandler;
import mvcViewComponent.gui.sceneController.ScreensController;
import org.xml.sax.SAXException;

/**
 *
 * @author Mor
 */
public class LoadGameCommand implements MenuCommand {

    @Override
    public void execute() throws SAXException, IOException {
        XmlHandler xmlHandler = new XmlHandler();
        
        FileChooser fileChooser = new FileChooser();       
        File file = fileChooser.showOpenDialog(null);
        String filePath = file.getAbsolutePath();      
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
