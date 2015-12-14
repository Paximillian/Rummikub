package mvcViewComponent.gui.gameViewElements;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.SplitPane;

public class GameView extends SplitPane implements ViewComponentPrinter {
    private PlayerView currentPlayerView;
    private final List<PlayerView> players = new ArrayList<PlayerView>();
    
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

    public void addPlayer(PlayerView playerView) {
        players.add(playerView);
    }
    
    public List<PlayerView> getPlayers(){
        return players;
    }
    
    public BoardView getBoard(){
        return boardView;
    }
    
    public void setBoard(BoardView board){
        boardView = board;
    }

    public void setCurrentPlayer(PlayerView playerView) {
        currentPlayerView = playerView;
    }
}
