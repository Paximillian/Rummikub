package mvcViewComponent.gui.gameViewElements.gameView;

import mvcViewComponent.gui.gameViewElements.boardView.BoardView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;
import mvcViewComponent.gui.gameViewElements.playerView.PlayerView;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

public class GameView extends VBox implements Initializable {
    @FXML private PlayerView currentPlayerView;
    private List<PlayerView> players;
    
    @FXML private BoardView boardView;
        
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
