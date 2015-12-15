package mvcViewComponent.gui.gameViewElements.gameView;

import mvcViewComponent.gui.gameViewElements.boardView.BoardView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import mvcViewComponent.gui.gameViewElements.playerView.PlayerView;
import mvcViewComponent.gui.gameViewElements.ViewComponentPrinter;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

public class GameView extends VBox implements ViewComponentPrinter {
    @FXML private PlayerView currentPlayerView;
    private final List<PlayerView> players = new ArrayList<PlayerView>();
    
    private BoardView boardView;
        
    public GameView(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameView.fxml"));
        fxmlLoader.setRoot(this);
        
        try{
            fxmlLoader.load();
        }
        catch(IOException ex){
            ErrorDisplayer.showError(ex.getMessage());
        }
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
