/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent.xmlHandler;


import generated.ObjectFactory;

import generated.Rummikub;
import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import mvcModelComponent.Player;

public class XmlHandler {
    
    mvcModelComponent.Game game = new mvcModelComponent.Game();
    
    public  void setGame(mvcModelComponent.Game game){
        this.game = game;
    }
    
    public mvcModelComponent.Game getGame(){
        return this.game;
    }
    
    public boolean saveGame(String saveFileNameAndPath, mvcModelComponent.Game game)
    {
        setGame(game);
        ObjectFactory obj = new ObjectFactory();
        Rummikub gameToSave = obj.createRummikub();
        
        saveCurrentPlayer(gameToSave);
        saveBoard(gameToSave);
        savePlayers(gameToSave);
        try{
            saveGameToXML(gameToSave, saveFileNameAndPath);
            return true;
        }catch (JAXBException e){
            return false;
        }
    }

    private void saveCurrentPlayer(generated.Rummikub gameToSave) {
        gameToSave.setCurrentPlayer(game.getCurrentPlayer().getName());     
    }

    private void saveBoard(generated.Rummikub gameToSave) {
        
       generated.Board boardToSave = new generated.Board();
       List<generated.Board.Sequence> sequneceToSave = boardToSave.getSequence();
       
        for ( mvcModelComponent.Sequence sequence: this.game.getBoard().getSequences()) {
            if(!sequence.isEmptySequence())
                sequneceToSave.add(convertGameSequenceToXMLSequence(sequence));       
        }
       gameToSave.setBoard(boardToSave);
    }

    private void savePlayers(generated.Rummikub gameToSave) {
        
        generated.Players convertedXmlPlayers = new generated.Players();
        
        for (mvcModelComponent.Player player : this.game.getPlayers()) {
            convertedXmlPlayers.getPlayer().add(convertGamePlayerToXMLPlayer(player));
        }
        gameToSave.setPlayers(convertedXmlPlayers);
    }

    private void saveGameToXML(generated.Rummikub gameToSave, String saveFileNameAndPath) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(generated.Rummikub.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(gameToSave, new File(saveFileNameAndPath));
    }

    private generated.Board.Sequence convertGameSequenceToXMLSequence(mvcModelComponent.Sequence sequence) {
   
        generated.Board.Sequence convertedXmlSequence = new generated.Board.Sequence();
        
        for (mvcModelComponent.Tile tile : sequence.getTiles()) {
            convertedXmlSequence.getTile().add(convertGametileToXMLtile(tile));
        }
        return convertedXmlSequence;
    }

    private generated.Tile convertGametileToXMLtile(mvcModelComponent.Tile tile) {
        generated.Tile convertedTile = new generated.Tile();
        
        convertedTile.setColor(convertGameColorToXMLColor(tile.getColor()));
        convertedTile.setValue(tile.getRank().rankValue());
        
        return convertedTile;
}

    private generated.Color convertGameColorToXMLColor(mvcModelComponent.Tile.Color color) {
        switch (color) {
            case RED :
                return generated.Color.RED;
                
            case BLACK :
               return generated.Color.BLACK;
             
            case BLUE :
                return generated.Color.BLUE;                          
       }
        return generated.Color.YELLOW;
    }

    private generated.Players.Player convertGamePlayerToXMLPlayer(Player player) {
        
        generated.Players.Player convertedPlayer = new generated.Players.Player();
        
        convertedPlayer.setName(player.getName());
        convertedPlayer.setType(player.isBot() ? generated.PlayerType.COMPUTER : generated.PlayerType.HUMAN );
        convertedPlayer.setTiles(convertGamePlayerTailsToXMLPlayerTails(player.getHand()));
        
        return convertedPlayer;
        
    }

    private generated.Players.Player.Tiles convertGamePlayerTailsToXMLPlayerTails(Iterable<mvcModelComponent.Tile> tails) {
        
        generated.Players.Player.Tiles convertedPlayerTails = new generated.Players.Player.Tiles();
        
        for (mvcModelComponent.Tile tail : tails) {
            convertedPlayerTails.getTile().add(convertGametileToXMLtile(tail));
        }
        return convertedPlayerTails;
    }
}
