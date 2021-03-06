/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.server;

import controller.LobbyManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import mvcModelComponent.MoveInfo;
import ws.rummikub.DuplicateGameName_Exception;
import ws.rummikub.GameDoesNotExists_Exception;
import ws.rummikub.InvalidParameters;
import ws.rummikub.InvalidParameters_Exception;
import ws.rummikub.InvalidXML_Exception;
import ws.rummikub.PlayerDetails;
import ws.rummikub.PlayerStatus;
import ws.rummikub.PlayerType;

/**
 *
 * @author Mor
 */
@WebService(serviceName = "RummikubWebServiceService", portName = "RummikubWebServicePort", endpointInterface = "ws.rummikub.RummikubWebService", targetNamespace = "http://rummikub.ws/", wsdlLocation = "WEB-INF/wsdl/RummiWS/RummikubWebServiceService.wsdl")
public class RummiWS {

    public java.util.List<ws.rummikub.Event> getEvents(int playerId, int eventId) throws InvalidParameters_Exception {
        return LobbyManager.getGameControllerForPlayer(playerId).getEvents(eventId);
    }

    public java.lang.String createGameFromXML(java.lang.String xmlData) throws InvalidParameters_Exception, DuplicateGameName_Exception, InvalidXML_Exception {
        return LobbyManager.createGameFromXML(xmlData);
    }

    public java.util.List<ws.rummikub.PlayerDetails> getPlayersDetails(java.lang.String gameName) throws GameDoesNotExists_Exception {
        return LobbyManager.getPlayerDetails(gameName);
    }

    public void createGame(java.lang.String name, int humanPlayers, int computerizedPlayers) throws InvalidParameters_Exception, DuplicateGameName_Exception {
        LobbyManager.createGame(name, humanPlayers, computerizedPlayers);
    }

    public ws.rummikub.GameDetails getGameDetails(java.lang.String gameName) throws GameDoesNotExists_Exception {
        return LobbyManager.getGameDetails(gameName);
    }

    public java.util.List<java.lang.String> getWaitingGames() {
        return LobbyManager.getWaitingGameNames();
    }

    public int joinGame(java.lang.String gameName, java.lang.String playerName) throws GameDoesNotExists_Exception, InvalidParameters_Exception {
        return LobbyManager.addPlayerToGame(gameName, playerName);
    }

    public ws.rummikub.PlayerDetails getPlayerDetails(int playerId) throws GameDoesNotExists_Exception, InvalidParameters_Exception {
        return LobbyManager.getPlayerDetails(playerId);
    }

    //Not relevant to our project structure.
    public void createSequence(int playerId, java.util.List<ws.rummikub.Tile> tiles) throws InvalidParameters_Exception {
        LobbyManager.unsupportedOperation();
    }

    //Not relevant to our project structure.
    public void addTile(int playerId, ws.rummikub.Tile tile, int sequenceIndex, int sequencePosition) throws InvalidParameters_Exception {
        LobbyManager.unsupportedOperation();
    }

    //Not relevant to our project structure.
    public void takeBackTile(int playerId, int sequenceIndex, int sequencePosition) throws InvalidParameters_Exception {
        LobbyManager.unsupportedOperation();
    }

    public void moveTile(int playerId, int sourceSequenceIndex, int sourceSequencePosition, int targetSequenceIndex, int targetSequencePosition) throws InvalidParameters_Exception {
        MoveInfo moveInfo = new MoveInfo();
        moveInfo.fromSetID = sourceSequenceIndex; 
        moveInfo.fromCardID = sourceSequencePosition; 
        moveInfo.toSetID = targetSequenceIndex; 
        moveInfo.toPositionID = targetSequencePosition;
        LobbyManager.getGameControllerForPlayer(playerId).moveCard(moveInfo);
    }

    public void finishTurn(int playerId) throws InvalidParameters_Exception {
        LobbyManager.getGameControllerForPlayer(playerId).endTurn();
    }

    public void resign(int playerId) throws InvalidParameters_Exception {
        LobbyManager.resignPlayer(playerId);
    }
    
}
