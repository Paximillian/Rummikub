/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.gameViewElements.cardView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

/**
 *
 * @author Mor
 */
public class CardView extends Label implements Initializable{
    @FXML private Label labelCardValue;
    
    private StringProperty cardValue;
    
    public CardView(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CardView.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } 
        catch (IOException ex) {
            ErrorDisplayer.showError(ex.getMessage());
        }
        
        cardValue = new SimpleStringProperty();
        labelCardValue.textProperty().bindBidirectional(cardValue);
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
    public void initialize(URL location, ResourceBundle resources) {
    
    }
}
