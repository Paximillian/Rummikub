/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent;

/**
 *
 * @author Mor
 */
public class Player {
    private String playerName;
    private boolean isBot;

    public Player(String newName, boolean isBotStatus) {
        playerName = newName;
        isBot = isBotStatus;
    }
    
    public void setName(String newName){
        playerName = newName;
    }
    
    public String getName(){
        return playerName;
    }
}
