/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent.mainMenuCommands;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mvcViewComponent.gui.messagingModule.MessageDisplayer;
import javax.xml.bind.JAXBException;
import mvcControllerComponent.GameController;
import mvcControllerComponent.GameLobbyManager;
import mvcControllerComponent.MenuCommand;
import mvcControllerComponent.client.ws.DuplicateGameName_Exception;
import mvcControllerComponent.client.ws.GameDoesNotExists_Exception;
import mvcControllerComponent.client.ws.InvalidParameters_Exception;
import mvcControllerComponent.client.ws.InvalidXML_Exception;
import mvcModelComponent.xmlHandler.InvalidLoadFileException;
import mvcModelComponent.xmlHandler.XmlHandler;
import mvcViewComponent.gui.mainMenu.MainMenuController;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;
import mvcViewComponent.gui.newGameScene.NewGameSceneController;
import mvcViewComponent.gui.sceneController.ScreensController;
import org.xml.sax.SAXException;

/**
 *
 * @author Mor
 */
public class LoadGameCommand implements Runnable {
     
    public static String errorMSG ="Error game not loaded";
    @Override
    public void run() {
             
        FileChooser fileChooser = new FileChooser();       
        File file = fileChooser.showOpenDialog(null);
        
        if(file != null){
            String filePath = file.getAbsolutePath();  
            
            Thread thread = new Thread(() -> {
                try {
                    byte[] encodedXml = Files.readAllBytes(Paths.get(filePath));
                    String xmlData = new String(encodedXml, Charset.defaultCharset());
                    String gameName = GameLobbyManager.createGameFromXml(xmlData);
                    
                    Platform.runLater(() -> {
                        MessageDisplayer.showMessage(String.format("The game named %s has been loaded, you may now join it from the join games menu", gameName));   
                    });
                } 
                catch (IOException | DuplicateGameName_Exception | InvalidParameters_Exception | InvalidXML_Exception ex) {
                    Platform.runLater(() -> {
                       ErrorDisplayer.showError(ex.getMessage());   
                    });
                }
            });
            
            thread.setDaemon(true);
            thread.start();
        }
    }
}
