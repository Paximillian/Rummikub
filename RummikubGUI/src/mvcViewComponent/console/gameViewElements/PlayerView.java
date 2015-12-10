/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.console.gameViewElements;

/**
 *
 * @author Mor
 */
public class PlayerView implements ViewComponentPrinter{
    private final CardSetView hand;
    private String name;
    private boolean isBot;
    
    public PlayerView(String playerName, boolean isABot){
        hand = new CardSetView();
        name = playerName;
        isBot = isABot;
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
}
