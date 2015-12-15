/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.gameViewElements.boardView;

import java.io.IOException;
import java.util.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import mvcViewComponent.gui.gameViewElements.cardSetView.CardSetView;
import mvcViewComponent.gui.gameViewElements.ViewComponentPrinter;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

/**
 *
 * @author Mor
 */
public class BoardView extends AnchorPane implements ViewComponentPrinter{

    private final List<CardSetView> publicCardSets;

    public BoardView() {
        this.publicCardSets = new ArrayList<CardSetView>(){};
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BoardView.fxml"));
        fxmlLoader.setRoot(this);
        
        try{
            fxmlLoader.load();
        }
        catch(IOException ex){
            ErrorDisplayer.showError(ex.getMessage());
        }
    }
    
    public void addCardSet(CardSetView cardSet){
        publicCardSets.add(cardSet);
    }
    
    List<CardSetView> getCardSets(){
        return publicCardSets;
    }
    
    @Override
    public void printComponent() {                
        for(int i = 1; i < publicCardSets.size(); ++i){
            CardSetView cardSet = publicCardSets.get(i);
            
            System.out.print(String.format("%d-[", i));
            cardSet.printComponent();
            System.out.print(String.format("]%s", System.lineSeparator()));
                            
            //If the the rest of the sequences from this point on are empty, we won't display them
            boolean allEmpty = true;
            for(int j = i + 1; j < publicCardSets.size(); ++j){
                if(publicCardSets.get(j).size() > 0){
                    allEmpty = false;
                    break;
                }
            }

            if(allEmpty){
                break;
            }
        }
    }
}
