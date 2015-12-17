/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.gameViewElements.playerView;

import mvcViewComponent.gui.gameViewElements.cardView.CardView;
import mvcViewComponent.gui.gameViewElements.cardSetView.CardSetView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

/**
 *
 * @author Mor
 */
public class PlayerView extends VBox implements Initializable{
    
    @FXML private CardSetView paneHand;
    
    @FXML private Label labelName;
    @FXML private CheckBox cbIsBot;
    
    private StringProperty name;
    
    public PlayerView(){
        name = new SimpleStringProperty();
        //labelName.textProperty().bindBidirectional(name);
    }
    
    public void addCardToHand(CardView card){
        paneHand.addCard(card);
    }

    public CardSetView getHand() {
        return paneHand;
    }
    
    public String getName(){
        return nameProperty().get();
    }

    public StringProperty nameProperty(){
        return name;
    }
    
    public BooleanProperty isBotProperty(){
        return cbIsBot.selectedProperty();
    }
    
    public boolean getIsBot() {
        return isBotProperty().get();
    }

    public void setName(String name) {
        nameProperty().set(name);
    }

    public void setIsBot(boolean isABot) {
        isBotProperty().set(getIsBot());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
