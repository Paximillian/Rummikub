package mvcViewComponent;

import mvcViewComponent.ViewPrinter;


public class GameView implements ViewPrinter {
    private PlayerView currentPlayerView;
    
    private BoardView boardView;

    @Override
    public void Print() {
        boardView.Print();
        currentPlayerView.Print();
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
