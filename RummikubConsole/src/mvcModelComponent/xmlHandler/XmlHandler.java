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
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import mvcModelComponent.Player;
import mvcModelComponent.Tile;
import org.xml.sax.SAXException;

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
        File file;
            file = new File(saveFileNameAndPath.endsWith(".xml") ? saveFileNameAndPath : saveFileNameAndPath + ".xml");
            marshaller.marshal(gameToSave,file );              
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
        convertedPlayer.setPlacedFirstSequence(player.isPlacedFirstSequence());
        
        return convertedPlayer;
        
    }

    private generated.Players.Player.Tiles convertGamePlayerTailsToXMLPlayerTails(Iterable<mvcModelComponent.Tile> tails) {
        
        generated.Players.Player.Tiles convertedPlayerTails = new generated.Players.Player.Tiles();
        
        for (mvcModelComponent.Tile tail : tails) {
            convertedPlayerTails.getTile().add(convertGametileToXMLtile(tail));
        }
        return convertedPlayerTails;
    }
    
        public mvcModelComponent.Game loadGame(String filePath) throws JAXBException, InvalidLoadFileException, SAXException{
        
        File file = new File(filePath.endsWith(".xml") ? filePath : filePath + ".xml"); 
        if(file == null || !file.exists())
        {
            throw new InvalidLoadFileException("fill is corapted or at das not exisit");
        }
        generated.Rummikub jaxBGame = createJaxBGame(file);
        loadPlayers(jaxBGame);
        loadBoard(jaxBGame);       
        loadCurrentPlayer(jaxBGame);
        this.game.setGameName(jaxBGame.getName());
        return game;
    }

    private generated.Rummikub createJaxBGame(File file) throws JAXBException, SAXException, InvalidLoadFileException {
    
    generated.Rummikub loadedGame = null;
    File schemaFile = new File("rummikub.xsd");
    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = schemaFactory.newSchema(schemaFile);
    try{
        JAXBContext context = JAXBContext.newInstance(generated.Rummikub.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema(schema);
        loadedGame = (generated.Rummikub)unmarshaller.unmarshal(file);
    }
    catch (JAXBException exception){
        throw new InvalidLoadFileException("fail data did not mach the recyayerd schema");
    }
        return loadedGame;
    }

    private void loadPlayers(generated.Rummikub jaxBGame) throws InvalidLoadFileException {
        
        for (generated.Players.Player loadedPlayer : jaxBGame.getPlayers().getPlayer()) {
            loadPlayer(loadedPlayer);
        }      
    }

    private void loadPlayer(generated.Players.Player loadedPlayer) throws InvalidLoadFileException {
        
        String playerName = loadedPlayer.getName();
        boolean isPlayerBot = loadedPlayer.getType() == generated.PlayerType.COMPUTER;
        ArrayList<mvcModelComponent.Tile> hand = loadTiles(loadedPlayer.getTiles().getTile());       
        boolean PlacedFirstSequence = loadedPlayer.isPlacedFirstSequence();
        this.game.addNewPlayer(new mvcModelComponent.Player(playerName, isPlayerBot, hand,PlacedFirstSequence));
    }

    private ArrayList<mvcModelComponent.Tile> loadTiles(List<generated.Tile> loadedTiles) throws InvalidLoadFileException {
        
        ArrayList<mvcModelComponent.Tile> tiles = new ArrayList<mvcModelComponent.Tile>();
        for (generated.Tile loadedTile : loadedTiles) {
            tiles.add(loadTile(loadedTile));
        }
        return tiles;
    }

    private Tile loadTile(generated.Tile loadedTile) throws InvalidLoadFileException {
        
        mvcModelComponent.Tile.Rank rank =mvcModelComponent.Tile.Rank.fromValue(loadedTile.getValue());
        mvcModelComponent.Tile.Color color = loadColor(loadedTile.getColor());
        
        if(rank.rankValue() == 0)
        {
            color = mvcModelComponent.Tile.Color.RED;
        }      
        
        Tile tile = new Tile(color, rank);
        if(!this.game.removeTileFromDeck(tile))
        {
            throw new InvalidLoadFileException("eligal tile illegal");
        }
                return tile;
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

    private void loadBoard(generated.Rummikub jaxBGame) throws InvalidLoadFileException {
        
        for (generated.Board.Sequence loadedSequence: jaxBGame.getBoard().getSequence()) {
            this.game.getBoard().addSequence(new mvcModelComponent.Sequence(loadTiles(loadedSequence.getTile())));                 
        }
        if(!this.game.getBoard().isBoardLegal()){
            throw new InvalidLoadFileException("the Board givin has elegal sequence");
        }
    }

    private void loadCurrentPlayer(generated.Rummikub jaxBGame) throws InvalidLoadFileException {
        
        boolean playerNameSet = false;
        for (mvcModelComponent.Player player : this.game.getPlayers()) {
            if(player.getName().equals(jaxBGame.getCurrentPlayer()))
            {
                this.game.setPlayerTurnTo(player);
                playerNameSet = true;
                break;
            }
        }
        if(!playerNameSet)
        {
            throw new InvalidLoadFileException("invaled carant player givin");
        }
    }
}
