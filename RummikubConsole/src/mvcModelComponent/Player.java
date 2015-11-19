/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent;

import java.util.ArrayList;
import javax.smartcardio.Card;

/**
 *
 * @author Mor
 */
public class Player {
    private String playerName;
    private boolean isBot;
    private Sequence hand ;

    public Player(String newName, boolean isBotStatus, ArrayList<Tile> hand) {
        playerName = newName;
        isBot = isBotStatus;
        
        this.hand = new Sequence(hand);
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

    public Sequence getHand() {
        return hand;
    }
    
    public void addTileToHand(Tile tile)
    {
        this.hand.addTileToSequence(tile);
    }
    
    public Tile GetTileFromHand(int index)
    {
        if(this.hand.size() <= index)
            return null;
        else
            return this.hand.removeTileFromSequence(index);
    }
    
    @Override
    public Player clone(){
        ArrayList<Tile> clonedHand = new ArrayList<Tile>();
        
        for(Tile tile : this.getHand().getTiles()){
            //We cann add the tiles by reference because the tile itself never changes.
            clonedHand.add(tile);
        }
        
        return new Player(this.getName(), this.isBot, clonedHand);
    }
}
