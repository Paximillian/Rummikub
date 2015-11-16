/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent;

import java.util.ArrayList;

/**
 *
 * @author Mor
 */
public class Player {
    private String playerName;
    private boolean isBot;
    private  ArrayList<Tile> hand ;

    public Player(String newName, boolean isBotStatus,ArrayList<Tile> hand) {
        playerName = newName;
        isBot = isBotStatus;
        this.hand = hand;
    }
    
    public void setName(String newName){
        playerName = newName;
    }
    
    public String getName(){
        return playerName;
    }
    
    public boolean isBot(){
        return isBot;
    }
    
    public void setIsBot(boolean isBot){
        this.isBot = isBot;
    }
    
    public void addTileToHand(Tile tile)
    {
        this.hand.add(tile);
    }
    
    public Tile GetTileFromHand(int index)
    {
        if(this.hand.size() <= index)
            return null;
        else
            return this.hand.remove(index);
    }
    
}
