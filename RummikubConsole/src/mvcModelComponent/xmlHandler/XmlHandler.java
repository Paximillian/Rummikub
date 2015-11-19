/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent.xmlHandler;


import generated.Color;
import generated.ObjectFactory;
import generated.Players;

import generated.Rummikub;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import mvcModelComponent.Player;
import mvcModelComponent.Tile;

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
        gameToSave.setName(this.game.getGameName());
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
        marshaller.marshal(gameToSave, new File(saveFileNameAndPath.endsWith(".xml") ? saveFileNameAndPath : saveFileNameAndPath + ".xml"));
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
        convertedPlayer.setTiles(convertGamePlayerTailsToXMLPlayerTails(player.getHand().getTiles()));
        
        return convertedPlayer;
        
    }

    private generated.Players.Player.Tiles convertGamePlayerTailsToXMLPlayerTails(Iterable<mvcModelComponent.Tile> tails) {
        
        generated.Players.Player.Tiles convertedPlayerTails = new generated.Players.Player.Tiles();
        
        for (mvcModelComponent.Tile tail : tails) {
            convertedPlayerTails.getTile().add(convertGametileToXMLtile(tail));
        }
        return convertedPlayerTails;
    }
    
        public mvcModelComponent.Game loadGame(String filePath) throws JAXBException, InvalidLoadFileException{
            
        File file = new File(filePath.endsWith(".xml") ? filePath : filePath + ".xml");                                  
        generated.Rummikub jaxBGame = createJaxBGame(file);
        loadPlayers(jaxBGame);
        loadBoard(jaxBGame);       
        loadCurrentPlayer(jaxBGame);
        this.game.setGameName(jaxBGame.getName());
        return game;
    }

    private generated.Rummikub createJaxBGame(File file) throws JAXBException {
        
        JAXBContext context = JAXBContext.newInstance(generated.Rummikub.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        
        return (generated.Rummikub)unmarshaller.unmarshal(file);
    }

    private void loadPlayers(generated.Rummikub jaxBGame) {
        
        for (generated.Players.Player loadedPlayer : jaxBGame.getPlayers().getPlayer()) {
            loadPlayer(loadedPlayer);
        }      
    }

    private void loadPlayer(generated.Players.Player loadedPlayer) {
        
        String playerName = loadedPlayer.getName();
        boolean isPlayerBot = loadedPlayer.getType() == generated.PlayerType.COMPUTER;
        ArrayList<mvcModelComponent.Tile> hand = loadTiles(loadedPlayer.getTiles().getTile());       
        
        this.game.addNewPlayer(new mvcModelComponent.Player(playerName, isPlayerBot, hand));
    }

    private ArrayList<mvcModelComponent.Tile> loadTiles(List<generated.Tile> loadedTiles) {
        
        ArrayList<mvcModelComponent.Tile> tiles = new ArrayList<mvcModelComponent.Tile>();
        for (generated.Tile loadedTile : loadedTiles) {
            tiles.add(loadTile(loadedTile));
        }
        return tiles;
    }

    private Tile loadTile(generated.Tile loadedTile) {
        
        mvcModelComponent.Tile.Rank rank =mvcModelComponent.Tile.Rank.fromValue(loadedTile.getValue());
        mvcModelComponent.Tile.Color color = loadColor(loadedTile.getColor());
        
        return new Tile(color, rank);
    }

    private mvcModelComponent.Tile.Color loadColor(generated.Color color) {
        
        switch (color) {
            
            case RED :
                return mvcModelComponent.Tile.Color.RED;
                
            case BLACK :
               return mvcModelComponent.Tile.Color.BLACK;
             
            case BLUE :
                return mvcModelComponent.Tile.Color.BLUE;                          
       }
        return mvcModelComponent.Tile.Color.YELLOW;
    }

    private void loadBoard(generated.Rummikub jaxBGame) {
        
        for (generated.Board.Sequence loadedSequence: jaxBGame.getBoard().getSequence()) {
            this.game.getBoard().getSequences().add(new mvcModelComponent.Sequence(loadTiles(loadedSequence.getTile())));
        }
    }

    private void loadCurrentPlayer(generated.Rummikub jaxBGame) {
        
        for (mvcModelComponent.Player player : this.game.getPlayers()) {
            if(player.getName() == jaxBGame.getCurrentPlayer())
            {
                this.game.setPlayerTurnTo(player);
                break;
            }
        }
    }
}
