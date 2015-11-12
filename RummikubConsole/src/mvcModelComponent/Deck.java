/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent;

import java.util.ArrayList;

/**
 *
 * @author yafita870
 */
public class Deck {
    
    private final ArrayList<Tile> tiles =new ArrayList<Tile>();
    
    public Deck(){
        for (Tile.Color color : Tile.Color.values()) {
            for (Tile.Rank rank : Tile.Rank.values()) {
                Tile tile = new Tile(color, rank);
                tiles.add(tile);
                tiles.add(tile);                
            }
        }      
    }
}
