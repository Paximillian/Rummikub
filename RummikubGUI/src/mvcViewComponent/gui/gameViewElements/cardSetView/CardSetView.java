/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.gameViewElements.cardSetView;

import java.io.IOException;
import java.util.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import mvcViewComponent.gui.gameViewElements.cardView.CardView;
import mvcViewComponent.gui.gameViewElements.ViewComponentPrinter;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

/**
 *
 * @author Mor
 */
public class CardSetView extends HBox implements ViewComponentPrinter{

    private final List<CardView> cardsInSet;

    public CardSetView() {
        this.cardsInSet = new ArrayList<CardView>(){};
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CardSetView.fxml"));
        fxmlLoader.setRoot(this);
        
        try{
            fxmlLoader.load();
        }
        catch(IOException ex){
            ErrorDisplayer.showError(ex.getMessage());
        }
    }
        
    public void addCard(CardView cardToAdd){
        cardsInSet.add(cardToAdd);
    }
    
    @Override
    public void printComponent() {
        int currentCardId = 1;
        
        //Printing all cards in the set and their ID for IO purpose.
        for(CardView card : cardsInSet){
            System.out.print(String.format(" %d%s", currentCardId, "-"));
            card.printComponent();
            
            ++currentCardId;
        }
        
        System.out.print(String.format(" %d%s", currentCardId, "-[]"));
        ++currentCardId;
    }

    public int size() {
        return cardsInSet.size();
    }

    public Iterable<CardView> getCards() {
        return cardsInSet;
    }
}
