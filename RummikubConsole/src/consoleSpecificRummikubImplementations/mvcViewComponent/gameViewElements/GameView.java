package consoleSpecificRummikubImplementations.mvcViewComponent.gameViewElements;

import java.util.ArrayList;
import java.util.List;
import mvcControllerComponent.GameController;

public class GameView implements ViewComponentPrinter {
    private PlayerView currentPlayerView;
    
    private BoardView boardView;
        
    public GameView(){
    }

    @Override
    public void printComponent() {
        //Print the board
        boardView.printComponent();
        System.out.print(System.lineSeparator());
        
        //Print the player's hand
        System.out.print("0-");
        currentPlayerView.printComponent();
        System.out.print(System.lineSeparator());
        System.out.print(System.lineSeparator());
    }

    public void moveCard(int fromSetID, int fromCardID, int toSetID, int toPositionID) {
        
    }
}
