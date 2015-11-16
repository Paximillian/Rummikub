/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consoleSpecificRummikubImplementations.mvcViewComponent.gameViewElements;

/**
 *
 * @author Mor
 */
public class PlayerView implements ViewComponentPrinter{
    private final CardSetView hand;
    private String name;
    
    public PlayerView(String playerName){
        hand = new CardSetView();
        name = playerName;
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
    
}
