/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.gameViewElements.boardView;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import mvcViewComponent.gui.gameViewElements.cardSetView.CardSetView;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

/**
 *
 * @author Mor
 */
public class BoardView extends ScrollPane implements Initializable{

    @FXML private VBox paneBoard;
    private final List<CardSetView> publicCardSets;

    public BoardView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("BoardView.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } 
        catch (IOException ex) {
            ErrorDisplayer.showError(ex.getMessage());
        }
        
        publicCardSets = new ArrayList<CardSetView>();
    }
    
    public void addCardSet(CardSetView cardSet){
        publicCardSets.add(cardSet);
        paneBoard.getChildren().add(cardSet);
    }
    
    public List<CardSetView> getCardSets(){
        return publicCardSets;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
