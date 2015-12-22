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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import mvcViewComponent.gui.gameViewElements.cardSetView.CardSetView;
import mvcViewComponent.gui.gameViewElements.playerView.PlayerView;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;

public class GameView extends AnchorPane implements Initializable {
    @FXML private PlayerView currentPlayerView;
    private List<PlayerView> players;
    
    @FXML private BoardView boardView;
        
    public GameView(){   
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GameView.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } 
        catch (IOException ex) {
            ErrorDisplayer.showError(ex.getMessage());
        }  
        
        players = new ArrayList<PlayerView>();
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
        for(CardSetView cardSet : board.getCardSets()){
            boardView.addCardSet(cardSet);
        }
    }

    public void setCurrentPlayer(PlayerView playerView) {
        currentPlayerView.setName(playerView.getName());
        currentPlayerView.setIsBot(playerView.getIsBot());
        currentPlayerView.setHand(playerView.getHand());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public PlayerView getCurrentPlayer() {
        return currentPlayerView;
    }
}