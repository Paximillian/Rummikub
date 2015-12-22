/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent.mainMenuCommands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public void execute() throws IOException, SAXException {
             
        FileChooser fileChooser = new FileChooser();       
        File file = fileChooser.showOpenDialog(null);
        
        if(file != null){
            String filePath = file.getAbsolutePath();  
            new Thread (() ->{
                try {
                    loadGame(filePath);
                } catch (IOException | SAXException ex) {
                    MessageDisplayer.showMessage("Error game not loaded");
                }
            }).start();   
        }
    }

    private void loadGame(String filePath) throws IOException, SAXException {
        XmlHandler xmlHandler = new XmlHandler();
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
