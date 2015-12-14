/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.gameViewElements;

import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Mor
 */
public class PlayerView extends AnchorPane implements ViewComponentPrinter{
    private final CardSetView hand;
    private String name;
    private boolean isBot;
    
    public PlayerView(){
        hand = new CardSetView();
    }
    
    public void addCardToHand(CardView card){
        hand.addCard(card);
    }
    
    @Override
    public void printComponent() {
        System.out.print(name);
        System.out.print(String.format("Hand:%s", System.lineSeparator()));
        
        hand.printComponent();
    }

    public CardSetView getHand() {
        return hand;
    }
    
    public String getName(){
        return name;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsBot(boolean isABot) {
        this.isBot = isABot;
    }
}
