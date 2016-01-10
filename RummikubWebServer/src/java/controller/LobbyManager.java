/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import mvcControllerComponent.GameController;
import mvcModelComponent.Game;
import mvcModelComponent.Player;
import mvcModelComponent.xmlHandler.InvalidLoadFileException;
import mvcModelComponent.xmlHandler.XmlHandler;
import org.xml.sax.SAXException;
import ws.rummikub.DuplicateGameName;
import ws.rummikub.DuplicateGameName_Exception;
import ws.rummikub.GameDetails;
import ws.rummikub.GameDoesNotExists;
import ws.rummikub.GameDoesNotExists_Exception;
import ws.rummikub.GameStatus;
import ws.rummikub.InvalidParameters;
import ws.rummikub.InvalidParameters_Exception;
import ws.rummikub.InvalidXML;
import ws.rummikub.InvalidXML_Exception;

/**
 *
 * @author Mor
 */
public class LobbyManager {
    private static final Map<String, Game> waitingGames = new HashMap<>();
    private static final Map<String, GameDetails> waitingGameDetails = new HashMap<>();
    private static final Map<String, Game> playingGames = new HashMap<>();
    private static final List<String> playerNames = new ArrayList<>();
    
    private static final int MAX_NUMBER_OF_PLAYERS_PER_ROOM = 4;
    
    public static List<String> getWaitingGameNames(){
        List<String> gameNames = new ArrayList<String>();
        
        for(String gameName : waitingGames.keySet()){
            gameNames.add(gameName);
        }
        
        return gameNames;
    }

    public static void createGame(String name, int humanPlayers, int computerizedPlayers) throws InvalidParameters_Exception, DuplicateGameName_Exception {
        if(getWaitingGameNames().contains(name) || playingGames.containsKey(name)){
            throw new DuplicateGameName_Exception("A game of the same name already exists", new DuplicateGameName());
        }
        
        if(computerizedPlayers + humanPlayers > MAX_NUMBER_OF_PLAYERS_PER_ROOM){
            throw new InvalidParameters_Exception("Invalid number of players", new InvalidParameters());
        }
        
        Game game = new Game();
        game.setGameName(name);
        
        for(int i = 0; i < computerizedPlayers; ++i){
            game.addNewPlayer("bot"+i, true);
        }
        
        GameDetails gameDetails = new GameDetails();
        gameDetails.setComputerizedPlayers(computerizedPlayers);
        gameDetails.setHumanPlayers(humanPlayers);
        gameDetails.setJoinedHumanPlayers(0);
        gameDetails.setLoadedFromXML(false);
        gameDetails.setName(name);
        gameDetails.setStatus(GameStatus.WAITING);
                
        waitingGames.put(name, game);
        waitingGameDetails.put(name, gameDetails);
    }

    public static int addPlayerToGame(String gameName, String playerName) throws GameDoesNotExists_Exception, InvalidParameters_Exception  {
        Game game = waitingGames.get(gameName);
        
        if(game == null){
            throw new GameDoesNotExists_Exception("No game of the given name exists", new GameDoesNotExists());
        }
        
        GameDetails gameDetails = waitingGameDetails.get(gameName);
        
        int playerID = -1;
        
        if(gameDetails.isLoadedFromXML()){
            for(Player player : game.getHumanPlayers()){
                if(player.getName().equals(playerName)){
                    if(playerNames.contains(playerName)){
                        playerID = playerNames.indexOf(playerName);
                    }
                    else{
                        playerNames.add(playerName);
                        playerID = playerNames.size() - 1;
                    }
                    
                    break;
                }
            }
            
            if(playerID == -1){
                throw new InvalidParameters_Exception("Player of the given name does not exists in the game", new InvalidParameters());
            }
        }
        else{
            for(String player : playerNames){
                if(player.equals(playerName)){
                    throw new InvalidParameters_Exception("Player of the given name already exists", new InvalidParameters());
                }
            }

            game.addNewPlayer(playerName, false);
            playerNames.add(playerName);
            playerID = playerNames.size() - 1;
        }
        
        
        gameDetails.setJoinedHumanPlayers(gameDetails.getJoinedHumanPlayers() + 1);
        
        return playerID;
    }

    public static GameDetails getGameDetails(String gameName) throws GameDoesNotExists_Exception {
        if(!waitingGames.containsKey(gameName)){
            throw new GameDoesNotExists_Exception("No game of the given name was found", new GameDoesNotExists());
        }
        
        Game game = waitingGames.get(gameName);
        
        GameDetails gameDetails = waitingGameDetails.get(gameName);
        
        return gameDetails;
    }

    public static String createGameFromXML(String xmlData) throws InvalidParameters_Exception, DuplicateGameName_Exception, InvalidXML_Exception {
        XmlHandler xmlHandler = new XmlHandler();
        
        String gameName = "";
        
        try {
            Game game = xmlHandler.loadGameFromXml(xmlData);
            
            if(waitingGames.containsKey(game.getGameName()) || playingGames.containsKey(game.getGameName())){
                throw new DuplicateGameName_Exception("Game of this name already exists", new DuplicateGameName());
            }
            
            GameDetails gameDetails = new GameDetails();
            gameDetails.setComputerizedPlayers(game.getComputerPlayers().size());
            gameDetails.setHumanPlayers(game.getHumanPlayers().size());
            gameDetails.setJoinedHumanPlayers(0);
            gameDetails.setLoadedFromXML(true);
            gameDetails.setName(game.getGameName());
            gameDetails.setStatus(GameStatus.WAITING);

            waitingGames.put(game.getGameName(), game);
            waitingGameDetails.put(game.getGameName(), gameDetails);
        } 
        catch (JAXBException ex) {
            throw new InvalidXML_Exception(ex.getMessage(), new InvalidXML());
        } 
        catch (InvalidLoadFileException | SAXException ex) {
            throw new InvalidParameters_Exception(ex.getMessage(), new InvalidParameters());
        }
        
        return gameName;
    }
}
