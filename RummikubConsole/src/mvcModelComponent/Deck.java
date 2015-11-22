/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author yafita870
 */
public class Deck {
    
    private final ArrayList<Tile> tiles = new ArrayList<Tile>();
    
    public Deck(){
        for (Tile.Color color : Tile.Color.values()) {
            for (Tile.Rank rank : Tile.Rank.values()) {
                if( rank == Tile.Rank.JOKER && Tile.Color.RED != color)
                    continue;
                Tile tile = new Tile(color, rank);
                tiles.add(tile);
                tiles.add(tile);
            }
        }    
        Collections.shuffle(tiles);
    }
    
    private Deck(ArrayList<Tile> cardsRemaining){
        for(Tile tile : cardsRemaining){
            tiles.add(tile);
        }
    }
    
    public Tile getNextTile(){
        return tiles.remove(0);
    }
    
    public boolean isDeckEmpty(){
        return tiles.isEmpty();
    }
    public boolean removeTileFromDeck(Tile tilee)
    {
        Tile tileToRemove = null;
        {
            for (Tile tile : tiles) {
                if(tile.getRank() == tilee.getRank() && tile.getColor() == tilee.getColor())
                {
                    tileToRemove = tile;
                    break;
                }
            }
            if(tileToRemove == null)
                return false;
            this.tiles.remove(tileToRemove);
            return true;
        }
    }
    @Override
    public Deck clone(){
        Deck clonedDeck = new Deck(tiles);
        
        return clonedDeck;
    }
}
