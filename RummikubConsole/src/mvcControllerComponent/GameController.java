/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent;

import java.util.ArrayList;
import java.util.List;
import mvcModelComponent.Player;

/**
 *
 * @author Mor
 */

//Game controller is a singleton that manages the current game.
public class GameController {
    private static final int MAX_PLAYER_COUNT = 4;
    private static final int MIN_PLAYER_COUNT = 2;
    
    private static GameController instance;
        
    private int numberOfPlayers;
    private int numberOfComputerPlayers;
    private String gameName;
    private final List<Player> activePlayers = new ArrayList<Player>();
    
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private boolean gameReady = false;
    
    public static GameController getInstance(){
        if(instance == null){
            instance = new GameController();
        }
        
        return instance;
    }
    
    private GameController(){
        
    }

    public void endGame() {
        gameEnded = true;
    }
    
    public boolean hasGameEnded(){
        return gameEnded;
    }
    
    boolean getGameReady() {
        return gameReady;
    }
    
    public void setNumberOfPlayers(int playerCount) throws IllegalArgumentException, IllegalStateException{
        if(gameStarted){
            throw new IllegalStateException("Can't set the number of players of a game that already started");
        }
        
        if(playerCount > MAX_PLAYER_COUNT || playerCount < MIN_PLAYER_COUNT){
            throw new IllegalArgumentException(String.format("Illegal number of players(Legal range is %d-%d)", MIN_PLAYER_COUNT, MAX_PLAYER_COUNT));
        }
        
        numberOfPlayers = playerCount;
    }

    public void setNumberOfComputerPlayers(int computerPlayerCount) throws IllegalArgumentException, IllegalStateException{
        if(gameStarted){
            throw new IllegalStateException("Can't set the number of players of a game that already started");
        }
        
        if(computerPlayerCount < 0 || computerPlayerCount > numberOfPlayers){
            throw new IllegalArgumentException(String.format("Illegal number of players(Legal range is %d-%d)", MIN_PLAYER_COUNT, MAX_PLAYER_COUNT));
        }
        
        numberOfComputerPlayers = computerPlayerCount;
    }

    public void setGameName(String nameOfGame) throws IllegalStateException {
        if(gameStarted){
            throw new IllegalStateException("Can't set the name of a game that already started");
        }
        
        gameName = nameOfGame;
    }

    public void addPlayer(String playerName, boolean isBot) throws IllegalStateException, IllegalArgumentException {
        if(gameStarted){
            throw new IllegalStateException("Can't add a player to a game that already started");
        }
        
        //Name can't already exist.
        if(activePlayers.stream().noneMatch(player -> player.getName().equals(playerName))){
            //Name can't be empty
            if(playerName.length() == 0){
                throw new IllegalArgumentException("Player name can't be empty");
            }
            else{
                //activePlayers.add(new Player(playerName, isBot));
            }
        }
        else{
            if(isBot){
                addPlayer(String.format("B%s", playerName), isBot);
            }
            else{
                throw new IllegalArgumentException("This player name already exists.");
            }
        }
                
        //If all players have been added.
        if(activePlayers.size() == numberOfPlayers){
            gameReady = true;
        }
    }
    
    public void resetGame(){
        instance = null;
    }
}
