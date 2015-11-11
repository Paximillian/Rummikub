
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author yafita870
 */
public class Card {

    private static boolean isFlash(List<Card> cards) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static boolean isSameCaind(List<Card> cards) {
        
        Card ferstCard =cards.get(0);
        
        if(cards.size() < 3)
            return false;
        
        ArrayList<Card> scandCards = new ArrayList<Card>();
        for(Card card : cards)
        {
           if( !(card.rank == ferstCard.rank) || scandCards.contains(card))
               return false;
           scandCards.add(card);
        }
        return true;
    }
    
    	public enum Rank {           
           ONE, TWO,THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE,THIRTEEN
          
          private int value;
          
          
           
               
           }
        }
        
        public enum Suit {
            CLUBS, DIAMONDS, HEARTS, SPADES
        }
        public static boolean isSeries(List<Card> cards)
        {
            return isFlash(cards) || isSameCaind(cards);
        }
        
        private final Rank rank;
	private final Suit suit;

	private Card(Rank rank, Suit suit) {
            this.rank = rank;
            this.suit = suit;
        }
        
            @Override
        public String toString() { return rank + " of " + suit; }

	private static final List<Card> deck =new ArrayList<>();

	// Initialize the static deck
        static {
            for (Suit suit : Suit.values()){
                for (Rank rank : Rank.values()){
                    deck.add(new Card(rank, suit));}}
        }

	public static ArrayList<Card> newDeck() {
            // Return copy of prototype deck
            return new ArrayList<Card>(deck);
        }
}

