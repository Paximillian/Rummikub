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
public class CardView implements ViewComponentPrinter{
    private String cardValue;
    
    public CardView(String cardValue){
        this.cardValue = cardValue;
    }
    
    @Override
    public void printComponent() {
        System.out.print(String.format("%s%s%s", "|", cardValue, "|"));
    }
}
