/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent;

import java.util.ArrayList;
import java.util.Collections;


public class Sequence {
    
    private  ArrayList<Tile> sequence;
    
    Sequence(ArrayList<Tile> sequence)
    {
        this.sequence = sequence;
    }
    
    public void addTileToSequence(Tile tile)
    {
        this.sequence.add(tile);
    }
    
    public Tile removeTailFromSequence(int index){
        return this.sequence.remove(index);
    }
    
    public boolean isEmptySequence(){
        return sequence.isEmpty();
    }
    
    public  boolean isGoodSequence(){
        return( sequence.size() >= 3 && (isFlush() || isStraight()));         
    }
    
    private boolean isFlush()
    {
        Tile ferstTile = sequence.get(0);       
        ArrayList<Tile.Color> clolorsInSet = new ArrayList<Tile.Color>();

        for (Tile tile : sequence) {
            if(tile.getRank() == ferstTile.getRank()){
                if(!clolorsInSet.contains(tile.getColor())){
                    clolorsInSet.add(tile.getColor());
                    continue;
                }
            }
            if(! tile.isJoker())
                return false;
        }
        return true;
    }
    
    private boolean isStraight()
    {      
        Collections.sort(sequence);
        int prvRankVal = 0;
        int jokerNeeded = 0;
        for (Tile tile : sequence) {
            if(tile.isJoker())
            {
                jokerNeeded -= 1;
                continue;
            }
            if(prvRankVal == 0){
                prvRankVal = tile.getRank().rankValue();
            }
            else{
                if(prvRankVal == tile.getRank().rankValue())
                   return false;
                
                jokerNeeded += tile.getRank().rankValue() -prvRankVal + 1;
                prvRankVal = tile.getRank().rankValue();
            }                
        }
        return jokerNeeded <= 0;
    }
}
