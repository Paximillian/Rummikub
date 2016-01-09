/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.game;

import java.util.ArrayList;
import javax.jws.WebService;

/**
 *
 * @author Mor
 */
@WebService(serviceName = "RummikubWebServiceService", portName = "RummikubWebServicePort", endpointInterface = "ws.rummikub.RummikubWebService", targetNamespace = "http://rummikub.ws/", wsdlLocation = "WEB-INF/wsdl/RummiWS/RummikubWebServiceService.wsdl")
public class RummiWS {

    public java.util.List<ws.rummikub.Event> getEvents(int playerId, int eventId) throws ws.rummikub.InvalidParameters_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.lang.String createGameFromXML(java.lang.String xmlData) throws ws.rummikub.InvalidXML_Exception, ws.rummikub.DuplicateGameName_Exception, ws.rummikub.InvalidParameters_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.util.List<ws.rummikub.PlayerDetails> getPlayersDetails(java.lang.String gameName) throws ws.rummikub.GameDoesNotExists_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void createGame(java.lang.String name, int humanPlayers, int computerizedPlayers) throws ws.rummikub.InvalidParameters_Exception, ws.rummikub.DuplicateGameName_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public ws.rummikub.GameDetails getGameDetails(java.lang.String gameName) throws ws.rummikub.GameDoesNotExists_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public java.util.List<java.lang.String> getWaitingGames() {
        //TODO implement this method
        return new ArrayList<String>(){{add("test");}};
    }

    public int joinGame(java.lang.String gameName, java.lang.String playerName) throws ws.rummikub.InvalidParameters_Exception, ws.rummikub.GameDoesNotExists_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public ws.rummikub.PlayerDetails getPlayerDetails(int playerId) throws ws.rummikub.GameDoesNotExists_Exception, ws.rummikub.InvalidParameters_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void createSequence(int playerId, java.util.List<ws.rummikub.Tile> tiles) throws ws.rummikub.InvalidParameters_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void addTile(int playerId, ws.rummikub.Tile tile, int sequenceIndex, int sequencePosition) throws ws.rummikub.InvalidParameters_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void takeBackTile(int playerId, int sequenceIndex, int sequencePosition) throws ws.rummikub.InvalidParameters_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void moveTile(int playerId, int sourceSequenceIndex, int sourceSequencePosition, int targetSequenceIndex, int targetSequencePosition) throws ws.rummikub.InvalidParameters_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void finishTurn(int playerId) throws ws.rummikub.InvalidParameters_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void resign(int playerId) throws ws.rummikub.InvalidParameters_Exception {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
