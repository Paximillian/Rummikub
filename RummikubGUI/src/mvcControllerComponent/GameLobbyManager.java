/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvcControllerComponent.client.ws.DuplicateGameName_Exception;
import mvcControllerComponent.client.ws.GameDetails;
import mvcControllerComponent.client.ws.GameDoesNotExists_Exception;
import mvcControllerComponent.client.ws.InvalidParameters_Exception;
import mvcControllerComponent.client.ws.RummikubWebService;
import mvcControllerComponent.client.ws.RummikubWebServiceService;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;
/**
 *
 * @author Mor
 */
public class GameLobbyManager extends WebClient{
    
    public static List<String> getWaitingGameNames(){
        return webService.getWaitingGames();
    }

    public static void createGame(String creatingPlayerName, String gameName, int numberOfPlayers, int numberOfComputerPlayers) throws GameDoesNotExists_Exception, DuplicateGameName_Exception, InvalidParameters_Exception {
       webService.createGame(gameName, numberOfPlayers, numberOfComputerPlayers);
       webService.joinGame(gameName, creatingPlayerName);
    }
    
    public static int joinGame(String gameName, String playerName) throws GameDoesNotExists_Exception, InvalidParameters_Exception{
        return webService.joinGame(gameName, playerName);
    }

    public static GameDetails getGameDetails(String gameName) throws GameDoesNotExists_Exception {
        return webService.getGameDetails(gameName);
    }
}
