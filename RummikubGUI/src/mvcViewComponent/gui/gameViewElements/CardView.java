/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.gameViewElements;

import javafx.scene.control.Label;

/**
 *
 * @author Mor
 */
public class CardView extends Label implements ViewComponentPrinter{
    private String cardValue;
    
    public CardView(String cardValue){
        this.cardValue = cardValue;
    }
    
    @Override
    public void printComponent() {
        String color = cardValue.substring(0, 3);
        String value = cardValue.substring(3);
                
        this.setText(String.format("%s%s%s", "|", color + value, "|"));
    }
}
