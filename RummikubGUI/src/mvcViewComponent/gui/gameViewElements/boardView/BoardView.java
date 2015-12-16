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
import javafx.scene.layout.AnchorPane;
import mvcViewComponent.gui.gameViewElements.cardSetView.CardSetView;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

/**
 *
 * @author Mor
 */
public class BoardView extends AnchorPane implements Initializable{

    private List<CardSetView> publicCardSets;

    public BoardView() {
        
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
    
    public List<CardSetView> getCardSets(){
        return publicCardSets;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
