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
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import mvcControllerComponent.GameController;
import mvcModelComponent.MoveInfo;
import mvcViewComponent.gui.gameViewElements.cardView.CardView;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

/**
 *
 * @author Mor
 */
public class CardSetView extends HBox implements Initializable{

    private List<CardView> cardsInSet;
    private int cardSetIndex;

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
        cardToAdd.setCardSetRelevance(this);
        cardToAdd.setPrefSize(160, this.getHeight());
        cardsInSet.add(cardToAdd);
        this.getChildren().add(cardToAdd);
    }
    
    public void addCard(CardView cardToAdd, int index){
        cardToAdd.setCardSetRelevance(this);
        cardToAdd.setPrefSize(160, this.getHeight());
        cardsInSet.add(Math.min(cardsInSet.size(), index), cardToAdd);
        this.getChildren().add(Math.min(getChildren().size(), index), cardToAdd);
    }

    public void setCardSetIndex(int i){
        cardSetIndex = i;
    }
    
    public int getCardSetIndex(){
        return cardSetIndex;
    }
    
    public void removeCard(CardView cardToRemove) {
        cardsInSet.remove(cardToRemove);
        this.getChildren().remove(cardToRemove);
    }
    
    public int size() {
        return cardsInSet.size();
    }

    public List<CardView> getCards() {
        return cardsInSet;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpDropActions();
    }

    private void setUpDropActions() {
        this.setOnDragOver((event) -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            
            event.consume();
        });

        this.setOnDragEntered((event) -> {
            if (event.getDragboard().hasString()) {
                this.setStyle("-fx-border-color: green; -fx-border-width: 5");
            }
            
            event.consume();
        });

        this.setOnDragExited((event) -> {
            this.setStyle("-fx-border-color: black; -fx-border-width: 1");
            event.consume();
        });

        this.setOnDragDropped((event) -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            
            //We can move in any way we want except for from a set on the board to our hand
            if(!(cardSetIndex == 0 && CardView.getDraggedCard().getCardSetRelevance().getCardSetIndex() != 0)){            
                if (dragboard.hasString()) {
                    //Find out where we need to drop the card at;
                    double newCardX = event.getSceneX();
                    int index = 1;

                    for(Node child : getChildren()){
                        if(!(child instanceof Label)){
                            double thisCardX = child.localToScene(child.getBoundsInLocal()).getMinX();
                            double cardWidth = child.localToScene(child.getBoundsInLocal()).getMaxX() - thisCardX;
                            thisCardX += cardWidth / 2;

                            //If we dropped it left to the leftmost card that didn't already fail checking, we'll place it here
                            if(newCardX < thisCardX){
                                break;
                            }

                            ++index;
                        }
                    }

                    MoveInfo moveInfo = new MoveInfo();
                    moveInfo.fromCardID = CardView.getIndexOfDraggedCard() + 1;
                    moveInfo.fromSetID = CardView.getDraggedCard().getCardSetRelevance().getCardSetIndex();
                    moveInfo.toSetID = getCardSetIndex();
                    moveInfo.toPositionID = index;
                    GameController.getInstance().moveCard(moveInfo);
                    
                    CardView card = new CardView();

                    String val = dragboard.getString();
                    card.setCardValue(val);

                    this.addCard(card, index);
                    CardView.setDraggedCard(null);
                    
                    success = true;
                }
            }
            
            event.setDropCompleted(success);
            event.consume();
        });
    }

    public void reset() {
        while(cardsInSet.size() > 0){
            removeCard(cardsInSet.get(0));
        }
    }
}
