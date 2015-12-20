/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.gameViewElements.cardSetView;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.HBox;
import mvcViewComponent.gui.gameViewElements.cardView.CardView;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

/**
 *
 * @author Mor
 */
public class CardSetView extends HBox implements Initializable{

    private List<CardView> cardsInSet;

    public CardSetView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CardSetView.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } 
        catch (IOException ex) {
            ErrorDisplayer.showError(ex.getMessage());
        }
        
        cardsInSet = new ArrayList<CardView>();
    }
        
    public void addCard(CardView cardToAdd){
        cardsInSet.add(cardToAdd);
    }
    
    public int size() {
        return cardsInSet.size();
    }

    public Iterable<CardView> getCards() {
        return cardsInSet;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
   
    }
    
    @FXML
    private void handleOnDragStart(DragEvent event) {
        System.out.println("Dropped");
    }    
    
    @FXML
    private void handleOnDragEnd(DragEvent event) {
        System.out.println("Left");
    }  
}
