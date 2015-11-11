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
class BoardView implements ViewComponentPrinter{

    private final List<CardSetView> publicCardSets;

    public BoardView() {
        this.publicCardSets = new ArrayList<CardSetView>(){};
    }
    
    public void addCardSet(CardSetView cardSet){
        publicCardSets.add(cardSet);
    }
    
    @Override
    public void printComponent() {
        int currentSetID = 1;
        
        //Printing all cards sets on the board and their ID for IO purpose.
        System.out.print(String.format("Sets:%s", System.lineSeparator()));
        
        for(CardSetView cardSet : publicCardSets){
            System.out.print(String.format("%d-[", currentSetID));
            cardSet.printComponent();
            System.out.print(String.format("]%s", System.lineSeparator()));
            
            ++currentSetID;
        }
    }
    
}
