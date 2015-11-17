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
public class BoardView implements ViewComponentPrinter{

    private final List<CardSetView> publicCardSets;

    public BoardView() {
        this.publicCardSets = new ArrayList<CardSetView>(){};
    }
    
    public void addCardSet(CardSetView cardSet){
        publicCardSets.add(cardSet);
    }
    
    public List<CardSetView> getCardSets(){
        return publicCardSets;
    }
    
    @Override
    public void printComponent() {
        int currentSetID = 1;
                
        for(CardSetView cardSet : publicCardSets){
            System.out.print(String.format("%d-[", currentSetID));
            cardSet.printComponent();
            System.out.print(String.format("]%s", System.lineSeparator()));
            
            ++currentSetID;
        }
    }
}
