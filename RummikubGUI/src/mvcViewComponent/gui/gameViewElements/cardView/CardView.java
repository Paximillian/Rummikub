/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.gameViewElements.cardView;

import java.io.IOException;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import mvcViewComponent.gui.gameViewElements.ViewComponentPrinter;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

/**
 *
 * @author Mor
 */
public class CardView extends Label implements ViewComponentPrinter{
    @FXML
    private Label labelCardValue;
    
    public CardView(String cardValue){
        setCardValue(cardValue);
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CardView.fxml"));
        fxmlLoader.setRoot(this);
        
        try{
            fxmlLoader.load();
        }
        catch(IOException ex){
            ErrorDisplayer.showError(ex.getMessage());
        }
    }
    
    public StringProperty cardValueProperty(){
        return labelCardValue.textProperty();
    }
    
    public void setCardValue(String value){
        cardValueProperty().set(value);
    }
    
    public String getCardValue(){
        return cardValueProperty().get();
    }
    
    @Override
    public void printComponent() {
        String color = getCardValue().substring(0, 3);
        String value = getCardValue().substring(3);
                
        this.setText(String.format("%s%s%s", "|", color + value, "|"));
    }
}
