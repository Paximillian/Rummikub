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
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import mvcModelComponent.Tile;
import mvcViewComponent.gui.gameViewElements.cardSetView.CardSetView;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

/**
 *
 * @author Mor
 */
public class CardView extends AnchorPane implements Initializable{
    @FXML private Label labelCardValue;
    private String cardValue;
    
    private CardSetView cardSetRelevance = null;
    
    private static CardView draggedCard = null;
    
        
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
    }
        
    public void setCardValue(String value){
        Tile tile = new Tile(value);
        cardValue = value;
        this.getStyleClass().add(tile.getColor().name().toLowerCase());
        labelCardValue.setText(value.substring(3));
    }
    
    public void setCardSetRelevance(CardSetView cardSet){
        cardSetRelevance = cardSet;
    }
    
    public CardSetView getCardSetRelevance(){
        return cardSetRelevance;
    }
    
    public static CardView getDraggedCard(){
        return draggedCard;
    }
    
    public static void setDraggedCard(CardView card){
        draggedCard = card;
    }
    
    public String getCardValue(){
        return cardValue;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpDragActions();
    }

    private void setUpDragActions() {
        this.setOnDragDetected((event) -> {
            WritableImage snapshot = this.snapshot(new SnapshotParameters(), null);
            Dragboard dragboard = this.startDragAndDrop(TransferMode.ANY);

            ClipboardContent cpContent = new ClipboardContent();
            cpContent.putString(getCardValue());
            dragboard.setContent(cpContent);
            dragboard.setDragView(snapshot, snapshot.getWidth() / 2, snapshot.getHeight() / 2);
            
            cardSetRelevance.removeCard(this);
            draggedCard = this;

            event.consume();
        });

        this.setOnDragDone((event) -> {
            //If the card was dropped outside of a legal range, we'll put it back in its' original set
            if(draggedCard != null){
                Dragboard dragboard = event.getDragboard();

                CardView card = new CardView();
                String val = dragboard.getString();
                card.setCardValue(val);

                this.getCardSetRelevance().addCard(card);
                CardView.setDraggedCard(null);
            }
            
            event.consume();
        });
    }
}
