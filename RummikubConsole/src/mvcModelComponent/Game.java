
import java.util.ArrayList;
import mvcModelComponent.Deck;
import mvcModelComponent.Player;
import mvcModelComponent.Tile;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yafita870
 */
public class Game {
    
    private final Deck deck = new Deck();
    private final ArrayList<Player> players = new ArrayList<Player>();
    
    
    public Player newPlayer(String newName, boolean isBotStatus)
    {
        Player newPlayer = new Player(newName, isBotStatus, newHand());
        this.players.add(newPlayer);
        return newPlayer;
    }
    
    private ArrayList<Tile> newHand()
    {
        ArrayList<Tile> newHand = new ArrayList<Tile>();
        for(int i = 0; i < 14; ++i){
            newHand.add(this.deck.getTile());
        }
        return newHand;
    }
}
