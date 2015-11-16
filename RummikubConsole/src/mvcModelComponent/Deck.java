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
    
    private final ArrayList<Tile> tiles =new ArrayList<Tile>();
    
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
    
    public Tile getTile(){
        return tiles.remove(0);
    }
    
    public boolean isDeckEmptey(){
        return tiles.isEmpty();
    }
    
}
