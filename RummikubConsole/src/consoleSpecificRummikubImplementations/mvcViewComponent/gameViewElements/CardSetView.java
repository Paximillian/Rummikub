/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consoleSpecificRummikubImplementations.mvcViewComponent.gameViewElements;

import java.util.*;

/**
 *
 * @author Mor
 */
public class CardSetView implements ViewComponentPrinter{

    private final List<CardView> cardsInSet;

    public CardSetView() {
        this.cardsInSet = new ArrayList<CardView>(){};
    }
        
    public void addCard(CardView cardToAdd){
        cardsInSet.add(cardToAdd);
    }
    
    @Override
    public void printComponent() {
        int currentCardId = 0;
        
        System.out.print(String.format(" %d%s", currentCardId, "-[]"));
        ++currentCardId;
        
        //Printing all cards in the set and their ID for IO purpose.
        for(CardView card : cardsInSet){
            System.out.print(String.format(" %d%s", currentCardId, "-"));
            card.printComponent();
            
            ++currentCardId;
        }
        
        System.out.print(String.format(" %d%s", currentCardId, "-[]"));
    }

    public int size() {
        return cardsInSet.size();
    }

    public Iterable<CardView> getCards() {
        return cardsInSet;
    }
}
