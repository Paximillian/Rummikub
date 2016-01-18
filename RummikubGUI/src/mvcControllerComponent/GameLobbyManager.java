/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent;

import com.sun.javafx.application.PlatformImpl;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import mvcControllerComponent.client.ws.DuplicateGameName_Exception;
import mvcControllerComponent.client.ws.GameDetails;
import mvcControllerComponent.client.ws.GameDoesNotExists_Exception;
import mvcControllerComponent.client.ws.InvalidParameters_Exception;
import mvcControllerComponent.client.ws.InvalidXML_Exception;
import mvcControllerComponent.client.ws.PlayerDetails;
import mvcControllerComponent.client.ws.RummikubWebService;
import mvcControllerComponent.client.ws.RummikubWebServiceService;
import mvcViewComponent.gui.gameScene.GameSceneView;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;
import mvcViewComponent.gui.sceneController.ScreensController;
/**
 *
 * @author Mor
 */
public class GameLobbyManager extends WebClient{
    
    public static List<String> getWaitingGameNames(){
        List<String> gameNames = null;
        
        try{
            gameNames = webService.getWaitingGames();   
        }
        catch(Exception e){
            try{
                ErrorDisplayer.showError(e.getMessage());
                Platform.exit();
            }
            catch(Exception ex){
            }
        }
        
        return gameNames;
    }

    public static void createGame(String creatingPlayerName, String gameName, int numberOfPlayers, int numberOfComputerPlayers) throws GameDoesNotExists_Exception, DuplicateGameName_Exception, InvalidParameters_Exception {
       webService.createGame(gameName, numberOfPlayers - numberOfComputerPlayers, numberOfComputerPlayers);
       int id = webService.joinGame(gameName, creatingPlayerName);
       GameSceneView.setPlayerId(id);
    }
    
    public static int joinGame(String gameName, String playerName) throws GameDoesNotExists_Exception, InvalidParameters_Exception{
        return webService.joinGame(gameName, playerName);
    }

    public static GameDetails getGameDetails(String gameName) throws GameDoesNotExists_Exception {
        return webService.getGameDetails(gameName);
    }
    
    public static String createGameFromXml(String xmlData) throws DuplicateGameName_Exception, InvalidParameters_Exception, InvalidXML_Exception{
        return webService.createGameFromXML(xmlData);
    }

    public static List<PlayerDetails> getPlayerDetails(String name) throws GameDoesNotExists_Exception {
        return webService.getPlayersDetails(name);
    }
    
    public static PlayerDetails getPlayerDetails(int playerID) throws GameDoesNotExists_Exception, InvalidParameters_Exception {
        return webService.getPlayerDetails(playerID);
    }

    public static void resignGame(int playerId) throws InvalidParameters_Exception {
        webService.resign(playerId);
    }
}
