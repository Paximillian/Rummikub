/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent;

import java.util.ArrayList;


public class Sequence {
    
    private final  ArrayList<Tile> tiles;
    private final int FIRST_SEQUENCE_SUM = 30;
    
    public boolean isSumOfFirstSequenceSufficient()
    {
        int sum = 0;
        
        for (Tile tile : tiles) {
            sum = tile.isJoker() ? 13 : tile.getRank().rankValue();         
        }
        return sum >= FIRST_SEQUENCE_SUM;
    }
    public Sequence(ArrayList<Tile> tiles)
    {
        this.tiles = tiles;
    }
    
    public Sequence(){
        this.tiles = new ArrayList<Tile>();
    }
    
    public void addTileToSequence(Tile tile, int index)
    {
        if(index < 0){
            throw new IndexOutOfBoundsException("Illegal index");
        }
        
        if(index < tiles.size()){
            this.tiles.add(index, tile);
        }
        else{
            this.tiles.add(tile);
        }
    }
    
    public void addTileToSequence(Tile tile)
    {
        this.tiles.add(tile);
    }
    
    public Tile removeTileFromSequence(int index){
        if(index < 0 || index >= tiles.size()){
            throw new IndexOutOfBoundsException("Illegal index");
        }
        
        return this.tiles.remove(index);
    }
    
    public Tile removeTileAt(int index){
        if(index < 0 || index >= tiles.size()){
            throw new IndexOutOfBoundsException("Illegal index");
        }
        
        return this.tiles.remove(index);
    }
    
    public Tile getTileAt(int index){
        if(index < 0 || index >= tiles.size()){
            throw new IndexOutOfBoundsException("Illegal index");
        }
        
        return this.tiles.get(index);
    }
    
    public boolean isEmptySequence(){
        return tiles.isEmpty();
    }
    
    public  boolean isGoodSequence(){
        return( tiles.size() >= 3 && (isFlush() || isStraight()));         
    }
    
    public boolean isFlush()
    {
        if(tiles.size() > 4)
            return false;
        
        Tile firstTile = null;
        int firstNonJokerIndex = 0;
        do{
            if(firstNonJokerIndex >= tiles.size()){
                return true;
            }
            
            firstTile = tiles.get(firstNonJokerIndex);
            ++firstNonJokerIndex;
        }while(firstTile.isJoker());
        
        ArrayList<Tile.Color> clolorsInSet = new ArrayList<Tile.Color>();

        for (Tile tile : tiles) {
            if(tile.getRank() == firstTile.getRank()){
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
    
    public int size(){
        return tiles.size();
    }
    
    public boolean isStraight()
    {
        int prvRank = 0;
        if(tiles.get(0).isJoker() && tiles.get(1).getRank().rankValue() == 1)
            return false;
        for (Tile tile : tiles) {
            if(tile.isJoker()){ 
                if(prvRank == 13)
                    return false;
                else if(prvRank != 0)
                    prvRank++;
            }
            else if(prvRank == 0 || prvRank + 1 == tile.getRank().rankValue())
                prvRank = tile.getRank().rankValue();
            else return false;
        }
        return true;
    }

    public Iterable<Tile> getTiles() {
        return tiles;
    }

    public void cloneSequence(Sequence sequenceToAdd) {
        this.tiles.clear();
        for(int i = 0; i < sequenceToAdd.size(); i++){
            this.addTileToSequence(sequenceToAdd.getTileAt(i));
        }
    }

    public void clear() {
        tiles.clear();
    }
}
