/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent;

import java.util.ArrayList;


public class Board {
    
    private final ArrayList<Sequence> sequences = new ArrayList<Sequence>();
    
    public Board()
    {
        for(int i = 0; i < 30; ++i)
            sequences.add(new Sequence(new ArrayList<Tile>()));
    }
    
    public boolean isBoardLegal()
    {
        for (Sequence sequence : sequences) {
            if(!sequence.isGoodSequence() || !sequence.isEmptySequence())
                return false;
        }
        return true;
    }
    
    public boolean addTileToSequence(int sequenceIndex, Tile tile)
    {
        if(sequenceIndex > 30)
            return false;
        
        this.sequences.get(sequenceIndex).addTileToSequence(tile);
        return true;
    }
    
    public Tile removeTileFromSequence(int sequenceIndex,int tileIndex){
        
        if(sequenceIndex > 30)               
            return null;
        
        Sequence sequence = this.sequences.get(sequenceIndex);
        
        return sequence.removeTailFromSequence(tileIndex);          
    }
    
    public ArrayList<Sequence> getSequences(){
        return sequences;
    }
}
