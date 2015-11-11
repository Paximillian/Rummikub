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
class PlayerView implements ViewComponentPrinter{
    private final CardSetView hand;
    
    public PlayerView(){
        hand = new CardSetView();
    }
    
    public void addCardToHand(CardView card){
        hand.addCard(card);
    }
    
    @Override
    public void printComponent() {
        System.out.print(String.format("Hand:%s", System.lineSeparator()));
        
        hand.printComponent();
    }
    
}
