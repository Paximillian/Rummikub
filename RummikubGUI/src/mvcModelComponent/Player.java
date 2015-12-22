/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 *
 * @author Mor
 */
public class Player {
    private String playerName;
    private boolean isBot;
    private final Sequence hand ;    
    private boolean placedFirstSequence ;
    
    private final Sequence cardsPlayedThisTurn = new Sequence();
    
    public boolean isPlacedFirstSequence() {
        return placedFirstSequence;
    }

    public void setPlacedFirstSequence(boolean PlacedFirstSequence) {
        this.placedFirstSequence = PlacedFirstSequence;
    }

    public Player(String newName, boolean isBotStatus, ArrayList<Tile> hand) {
        playerName = newName;
        isBot = isBotStatus;
        placedFirstSequence = false;
        
        this.hand = new Sequence(hand);
    }
    
        public Player(String newName, boolean isBotStatus, ArrayList<Tile> hand, boolean placedFirstSequence) {
        playerName = newName;
        isBot = isBotStatus;
        this.placedFirstSequence = placedFirstSequence;
        
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

    public boolean isTurnLegalForPlayer() {
        return placedFirstSequence = isPlacedFirstSequence() || cardsPlayedThisTurn.size() == 0 || cardsPlayedThisTurn.isSumOfFirstSequenceSufficient();
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

    public MoveInfo requestMove(Game gameState) throws Exception {
        MoveInfo moveInfo = new MoveInfo();
        if(isBot){
            //AI code
            Collection<Tile> tiles = (Collection)hand.getTiles();
            
            //Start by trying to match a sequence on the board.
            for(int j = 0; j < gameState.getBoard().getSequences().size(); ++j){
                Sequence sequence = gameState.getBoard().getSequences().get(j);
                        
                if(!sequence.isEmptySequence()){
                    Tile firstTile = ((List<Tile>)sequence.getTiles()).get(0);
                    Tile lastTile = ((List<Tile>)sequence.getTiles()).get(((List<Tile>)sequence.getTiles()).size() - 1);
                    
                    for(int i = 0; i < hand.size(); ++i){
                        Tile tile = hand.getTileAt(i);
                        
                        //If we can place a tile from the hand left to the first tile of a sequence.
                        if(tile.isJoker() ||
                            ((sequence.isStraight() || ((List<Tile>)sequence.getTiles()).size() < 2) && tile.getColor().equals(firstTile.getColor()) && tile.getValue() == firstTile.getValue() - 1) ||
                            ((sequence.isFlush() || ((List<Tile>)sequence.getTiles()).size() < 2) && !((Collection<Tile>)sequence.getTiles()).stream().anyMatch(tile2 -> tile.getColor().equals(tile2.getColor())) && tile.getValue() == firstTile.getValue())){
                            moveInfo.fromSetID = 0;
                            moveInfo.fromCardID = i + 1;
                            moveInfo.toSetID = j + 1;
                            moveInfo.toPositionID = 1;
                            
                            return moveInfo;
                        }
                        
                        //If we can place a tile from the hand left to the last tile of a sequence.
                        if(tile.isJoker() ||
                            ((sequence.isStraight()|| ((List<Tile>)sequence.getTiles()).size() < 2) && tile.getColor().equals(lastTile.getColor()) && tile.getValue() == lastTile.getValue() - 1) ||
                            ((sequence.isFlush() || ((List<Tile>)sequence.getTiles()).size() < 2) && !((Collection<Tile>)sequence.getTiles()).stream().anyMatch(tile2 -> tile.getColor().equals(tile2.getColor())) && tile.getValue() == firstTile.getValue())){
                            moveInfo.fromSetID = 0;
                            moveInfo.fromCardID = i + 1;
                            moveInfo.toSetID = j + 1;
                            moveInfo.toPositionID = sequence.size();
                            
                            return moveInfo;
                        }
                    }
                }
            }
            
            //Store the hand position of the card.
            Map<Tile, Integer> tileToHandPos = new TreeMap<>();
            for(int i = 0; i < ((List)hand.getTiles()).size(); ++i){
                tileToHandPos.put(hand.getTileAt(i), i + 1);
            }
            
            //Sort by color -> value
            List<Tile> redTiles = tiles.stream().filter(tile -> tile.getColor() == Tile.Color.RED).sorted((tile1, tile2) -> Integer.compare(tile1.getValue(), tile2.getValue())).collect(Collectors.toList());
            List<Tile> blueTiles = tiles.stream().filter(tile -> tile.getColor() == Tile.Color.BLUE).sorted((tile1, tile2) -> Integer.compare(tile1.getValue(), tile2.getValue())).collect(Collectors.toList());
            List<Tile> blackTiles = tiles.stream().filter(tile -> tile.getColor() == Tile.Color.BLACK).sorted((tile1, tile2) -> Integer.compare(tile1.getValue(), tile2.getValue())).collect(Collectors.toList());
            List<Tile> yellowTiles = tiles.stream().filter(tile -> tile.getColor() == Tile.Color.YELLOW).sorted((tile1, tile2) -> Integer.compare(tile1.getValue(), tile2.getValue())).collect(Collectors.toList());
            
            //Check for red straight sequences.
            for(int i = 0; i < redTiles.size() - 3; ++i){
                if(redTiles.get(i).getValue() == redTiles.get(i + 1).getValue() - 1 &&
                    redTiles.get(i + 1).getValue() == redTiles.get(i + 2).getValue() - 1){
                    moveInfo.fromSetID = 0;
                    moveInfo.fromCardID = tileToHandPos.get(redTiles.get(i));
                    
                    moveInfo.toSetID = 0;
                    while(!gameState.getBoard().getSequences().get(moveInfo.toSetID).isEmptySequence()){
                        ++moveInfo.toSetID;
                    }
                    ++moveInfo.toSetID;
                    
                    moveInfo.toPositionID = 1;
                    
                    return moveInfo;
                }
            }
            
            //Check for blue straight sequences.
            for(int i = 0; i < blueTiles.size() - 3; ++i){
                if(blueTiles.get(i).getValue() == blueTiles.get(i + 1).getValue() - 1 &&
                    blueTiles.get(i + 1).getValue() == blueTiles.get(i + 2).getValue() - 1){
                    moveInfo.fromSetID = 0;
                    moveInfo.fromCardID = tileToHandPos.get(blueTiles.get(i));
                    
                    moveInfo.toSetID = 0;
                    while(!gameState.getBoard().getSequences().get(moveInfo.toSetID).isEmptySequence()){
                        ++moveInfo.toSetID;
                    }
                    ++moveInfo.toSetID;
                    
                    moveInfo.toPositionID = 1;
                    
                    return moveInfo;
                }
            }
            //Check for black straight sequences.
            for(int i = 0; i < blackTiles.size() - 3; ++i){
                if(blackTiles.get(i).getValue() == blackTiles.get(i + 1).getValue() - 1  &&
                    blackTiles.get(i + 1).getValue() == blackTiles.get(i + 2).getValue() - 1){
                    moveInfo.fromSetID = 0;
                    moveInfo.fromCardID = tileToHandPos.get(blackTiles.get(i));
                    
                    moveInfo.toSetID = 0;
                    while(!gameState.getBoard().getSequences().get(moveInfo.toSetID).isEmptySequence()){
                        ++moveInfo.toSetID;
                    }
                    ++moveInfo.toSetID;
                    
                    moveInfo.toPositionID = 1;
                    
                    return moveInfo;
                }
            }
            //Check for yellow straight sequences.
            for(int i = 0; i < yellowTiles.size() - 3; ++i){
                if(yellowTiles.get(i).getValue() == yellowTiles.get(i + 1).getValue() - 1  &&
                    yellowTiles.get(i + 1).getValue() == yellowTiles.get(i + 2).getValue() - 1){
                    moveInfo.fromSetID = 0;
                    moveInfo.fromCardID = tileToHandPos.get(yellowTiles.get(i));
                    
                    moveInfo.toSetID = 0;
                    while(!gameState.getBoard().getSequences().get(moveInfo.toSetID).isEmptySequence()){
                        ++moveInfo.toSetID;
                    }
                    ++moveInfo.toSetID;
                    
                    moveInfo.toPositionID = 1;
                    
                    return moveInfo;
                }
            }
            
            return null;
        }
        
        return moveInfo;
    }

    public void addToCardsPlayedThisTurn(Tile movedCard) {
        cardsPlayedThisTurn.addTileToSequence(movedCard);
    }
    
    public void clearCardsPlayedThisTurn(){
        cardsPlayedThisTurn.clear();
    }
}
