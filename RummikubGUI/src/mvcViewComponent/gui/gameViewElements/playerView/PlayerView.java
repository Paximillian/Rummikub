/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.gameViewElements.playerView;

import mvcViewComponent.gui.gameViewElements.cardView.CardView;
import mvcViewComponent.gui.gameViewElements.cardSetView.CardSetView;
import java.io.IOException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import mvcViewComponent.gui.gameViewElements.ViewComponentPrinter;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

/**
 *
 * @author Mor
 */
public class PlayerView extends VBox implements ViewComponentPrinter{
    
    @FXML private final CardSetView paneHand = new CardSetView();
    
    @FXML private Label labelName;
    @FXML private CheckBox cbIsBot;
    
    public PlayerView(){
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlayerView.fxml"));
        fxmlLoader.setRoot(this);
        
        try{
            fxmlLoader.load();
        }
        catch(IOException ex){
            ErrorDisplayer.showError(ex.getMessage());
        }
    }
    
    public void addCardToHand(CardView card){
        paneHand.addCard(card);
    }
    
    @Override
    public void printComponent() {
        System.out.print(getName());
        System.out.print(String.format("Hand:%s", System.lineSeparator()));
        
        paneHand.printComponent();
    }

    public CardSetView getHand() {
        return paneHand;
    }
    
    public String getName(){
        return nameProperty().get();
    }

    public StringProperty nameProperty(){
        return labelName.textProperty();
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
}
