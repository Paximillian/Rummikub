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
import mvcModelComponent.Game;
import mvcModelComponent.Player;
import ws.rummikub.DuplicateGameName;
import ws.rummikub.DuplicateGameName_Exception;
import ws.rummikub.GameDetails;
import ws.rummikub.GameDoesNotExists;
import ws.rummikub.GameDoesNotExists_Exception;
import ws.rummikub.GameStatus;
import ws.rummikub.InvalidParameters;
import ws.rummikub.InvalidParameters_Exception;

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
        if(getWaitingGameNames().contains(name)){
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
        
        for(String player : playerNames){
            if(player.equals(playerName)){
                throw new InvalidParameters_Exception("Player of the given name already exists", new InvalidParameters());
            }
        }
        
        game.addNewPlayer(playerName, false);
        playerNames.add(playerName);
        
        GameDetails gameDeatils = waitingGameDetails.get(gameName);
        gameDeatils.setJoinedHumanPlayers(gameDeatils.getJoinedHumanPlayers() + 1);
        
        return playerNames.size() - 1;
    }

    public static GameDetails getGameDetails(String gameName) throws GameDoesNotExists_Exception {
        if(!waitingGames.containsKey(gameName)){
            throw new GameDoesNotExists_Exception("No game of the given name was found", new GameDoesNotExists());
        }
        
        Game game = waitingGames.get(gameName);
        
        GameDetails gameDetails = waitingGameDetails.get(gameName);
        
        return gameDetails;
    }
}
