/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.newGameScene;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import mvcControllerComponent.GameController;
import mvcControllerComponent.GameLobbyManager;
import mvcControllerComponent.client.ws.DuplicateGameName_Exception;
import mvcControllerComponent.client.ws.GameDetails;
import mvcControllerComponent.client.ws.GameDoesNotExists_Exception;
import mvcControllerComponent.client.ws.GameStatus;
import mvcControllerComponent.client.ws.InvalidParameters_Exception;
import mvcControllerComponent.client.ws.PlayerDetails;
import mvcControllerComponent.client.ws.PlayerType;
import mvcViewComponent.gui.messagingModule.ErrorDisplayer;
import mvcViewComponent.gui.sceneController.ControlledScreen;
import mvcViewComponent.gui.sceneController.ScreensController;

/**
 * FXML Controller class
 *
 * @author yafita870
 */
public class NewGameSceneController implements Initializable , ControlledScreen {
    private static String loadedGameName = "";

    private final Background SELECTED_BACKGROUND = new Background(new BackgroundFill(Paint.valueOf("0x000000"), CornerRadii.EMPTY, Insets.EMPTY));
    private final Background UNSELECTED_BACKGROUND = new Background(new BackgroundFill(Paint.valueOf("0xffffff"), CornerRadii.EMPTY, Insets.EMPTY));
        
    @FXML private Label waitingForPlayersLabel;
    @FXML private TextField gameNameTextField;
    @FXML private TextField player1NameTextField;
    @FXML private TextField player2NameTextField;
    @FXML private TextField player3NameTextField;
    @FXML private TextField player4NameTextField;
    @FXML private Button playerAmount2Button;
    @FXML private Button playerAmount3Button;
    @FXML private Button playerAmount4Button;
    @FXML private Button aiAmount0Button;
    @FXML private Button aiAmount1Button;
    @FXML private Button aiAmount2Button;
    @FXML private Button aiAmount3Button;
    @FXML private Label errorLabel;
    @FXML private Button createGameButton;
    private String player1name;
    private String player2name;
    private String player3name;
    private String player4name;
    @FXML private Pane player3canvas;
    @FXML private Pane player4canvas;
    private int numberOfPlayers = 2;
    private int numberOfComputerPlayers = 0;
    private boolean keepUpdating;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        aiAmount0Button.setBackground(SELECTED_BACKGROUND);
        aiAmount1Button.setBackground(UNSELECTED_BACKGROUND);
        aiAmount2Button.setBackground(UNSELECTED_BACKGROUND);
        aiAmount3Button.setBackground(UNSELECTED_BACKGROUND);
        
        playerAmount2Button.setBackground(SELECTED_BACKGROUND);
        playerAmount3Button.setBackground(UNSELECTED_BACKGROUND);
        playerAmount4Button.setBackground(UNSELECTED_BACKGROUND);
        
        aiAmount2Button.setVisible(false);
        aiAmount3Button.setVisible(false);
        
        player3canvas.setVisible(false);
        player4canvas.setVisible(false);
        
        getRoomUpdatesFromServer();
    }    

    @Override
    public void setScreenParent(ScreensController screenParent) {
        
    }
    
    public static void setLoadedGame(String gameName){
        loadedGameName = gameName;
    }
    
    public void getRoomUpdatesFromServer(){
        Thread checkThread = new Thread(() -> {
            boolean keepChecking = true;
            while(keepChecking){
                try{
                    if(!loadedGameName.equals("")){
                        disableControls();
                        keepChecking = false;
                    }

                    try {
                        Thread.sleep(100);
                    } 
                    catch (InterruptedException ex) {
                        Logger.getLogger(NewGameSceneController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                catch(Exception e){
                }
            }
            
            Thread thread = new Thread(() -> {
                keepUpdating = true;
                while(keepUpdating){
                    try{
                        GameDetails gameDetails = GameLobbyManager.getGameDetails(loadedGameName);
                        List<PlayerDetails> playerDetails = GameLobbyManager.getPlayerDetails(gameDetails.getName());

                        Platform.runLater(() -> {
                            keepUpdating = updateRoomFrom(gameDetails, playerDetails);
                        });
                    }   
                    catch (GameDoesNotExists_Exception ex) {
                        keepUpdating = false;
                        loadedGameName = "";

                        Platform.runLater(() ->{
                            ErrorDisplayer.showError("Game room was closed");
                            ScreensController.getInstance().setScreen(ScreensController.MAIN_SCENE);
                        });
                    }

                    try {
                        Thread.sleep(100);
                    } 
                    catch (InterruptedException ex) {
                        Logger.getLogger(NewGameSceneController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            thread.setDaemon(true);
            thread.start();
        });
        
        checkThread.setDaemon(true);
        checkThread.start();
    }
    
    @FXML
    private void onActionNumberOfPlayers2Button(ActionEvent event) throws IOException
    {
        playerAmount2Button.setBackground(SELECTED_BACKGROUND);
        playerAmount3Button.setBackground(UNSELECTED_BACKGROUND);
        playerAmount4Button.setBackground(UNSELECTED_BACKGROUND);
        
        player3canvas.setVisible(false);
        player4canvas.setVisible(false);
        aiAmount2Button.setVisible(false);
        aiAmount3Button.setVisible(false);
        numberOfPlayers = 2;
        
        if(numberOfComputerPlayers > 1){
            numberOfComputerPlayers = 1;
            
            aiAmount0Button.setBackground(UNSELECTED_BACKGROUND);
            aiAmount1Button.setBackground(SELECTED_BACKGROUND);
            aiAmount2Button.setBackground(UNSELECTED_BACKGROUND);
            aiAmount3Button.setBackground(UNSELECTED_BACKGROUND);
        }
    }
    
    @FXML
    private void onActionNumberOfPlayers3Button(ActionEvent event) throws IOException
    {
        playerAmount2Button.setBackground(UNSELECTED_BACKGROUND);
        playerAmount3Button.setBackground(SELECTED_BACKGROUND);
        playerAmount4Button.setBackground(UNSELECTED_BACKGROUND);
        
        player3canvas.setVisible(true);
        player4canvas.setVisible(false);
        aiAmount2Button.setVisible(true);
        aiAmount3Button.setVisible(false);
        numberOfPlayers = 3;
        
        if(numberOfComputerPlayers > 2){
            numberOfComputerPlayers = 2;
            
            aiAmount0Button.setBackground(UNSELECTED_BACKGROUND);
            aiAmount1Button.setBackground(UNSELECTED_BACKGROUND);
            aiAmount2Button.setBackground(SELECTED_BACKGROUND);
            aiAmount3Button.setBackground(UNSELECTED_BACKGROUND);
        }
    }
    
    @FXML
    private void onActionNumberOfPlayers4Button(ActionEvent event) throws IOException
    {
        playerAmount2Button.setBackground(UNSELECTED_BACKGROUND);
        playerAmount3Button.setBackground(UNSELECTED_BACKGROUND);
        playerAmount4Button.setBackground(SELECTED_BACKGROUND);
        
        player3canvas.setVisible(true);
        player4canvas.setVisible(true);
        aiAmount2Button.setVisible(true);
        aiAmount3Button.setVisible(true);
        numberOfPlayers = 4;
        
        if(numberOfComputerPlayers > 3){
            numberOfComputerPlayers = 3;
            
            aiAmount0Button.setBackground(UNSELECTED_BACKGROUND);
            aiAmount1Button.setBackground(UNSELECTED_BACKGROUND);
            aiAmount2Button.setBackground(UNSELECTED_BACKGROUND);
            aiAmount3Button.setBackground(SELECTED_BACKGROUND);
        }
    }
    
    @FXML
    private void onActionNumberOfAIPlayers0Button(ActionEvent event) throws IOException
    {
        aiAmount0Button.setBackground(SELECTED_BACKGROUND);
        aiAmount1Button.setBackground(UNSELECTED_BACKGROUND);
        aiAmount2Button.setBackground(UNSELECTED_BACKGROUND);
        aiAmount3Button.setBackground(UNSELECTED_BACKGROUND);
        
        numberOfComputerPlayers = 0;
    }
    
    @FXML
    private void onActionNumberOfAIPlayers1Button(ActionEvent event) throws IOException
    {
        aiAmount0Button.setBackground(UNSELECTED_BACKGROUND);
        aiAmount1Button.setBackground(SELECTED_BACKGROUND);
        aiAmount2Button.setBackground(UNSELECTED_BACKGROUND);
        aiAmount3Button.setBackground(UNSELECTED_BACKGROUND);
        
        numberOfComputerPlayers = 1;
    }
    
    @FXML
    private void onActionNumberOfAIPlayers2Button(ActionEvent event) throws IOException
    {
        aiAmount0Button.setBackground(UNSELECTED_BACKGROUND);
        aiAmount1Button.setBackground(UNSELECTED_BACKGROUND);
        aiAmount2Button.setBackground(SELECTED_BACKGROUND);
        aiAmount3Button.setBackground(UNSELECTED_BACKGROUND);
        
        numberOfComputerPlayers = 2;
    }
    
    @FXML
    private void onActionNumberOfAIPlayers3Button(ActionEvent event) throws IOException
    {
        aiAmount0Button.setBackground(UNSELECTED_BACKGROUND);
        aiAmount1Button.setBackground(UNSELECTED_BACKGROUND);
        aiAmount2Button.setBackground(UNSELECTED_BACKGROUND);
        aiAmount3Button.setBackground(SELECTED_BACKGROUND);
        
        numberOfComputerPlayers = 3;
    }
    
    @FXML
    private void onActionBackButton(ActionEvent event) throws IOException  
    {
        ScreensController.getInstance().setScreen(ScreensController.MAIN_SCENE);
        setLoadedGame("");
    }
    
    @FXML
    private void onActionRedyButton(ActionEvent event) throws IOException    
    {
        try {
            GameLobbyManager.createGame(player1NameTextField.getText(), gameNameTextField.getText(), numberOfPlayers, numberOfComputerPlayers);
            GameController.getInstance().setPlayerName(player1NameTextField.getText());
            
            setLoadedGame(gameNameTextField.getText());
        } 
        catch (GameDoesNotExists_Exception | DuplicateGameName_Exception | InvalidParameters_Exception ex) {
            ErrorDisplayer.showError(ex.getMessage());
        }
    }
    
    private String fixName(String str1,String str2)
    {
        return str1.trim().isEmpty() ? str2 : str1;
    }
    
    private void sendErrorMSG(String errorMSG)
    {
        errorLabel.setText("error:  " + errorMSG);
        Timeline timeline = new Timeline(new KeyFrame(
        Duration.millis(5666),ae -> errorLabel.setText("")));
        timeline.play();
    }
    
    private String date()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private boolean updateRoomFrom(GameDetails gameDetails, List<PlayerDetails> playerDetails) {
        boolean keepUpdating = true;
        
        if(gameDetails.getStatus() == GameStatus.ACTIVE){
            GameController.getInstance().setGameName(loadedGameName);
            if(GameController.getInstance().getPlayerName().equals("")){
                GameController.getInstance().setPlayerName(player1NameTextField.getText());
            }
            ScreensController.getInstance().setScreen(ScreensController.GAME_SCENE);
            keepUpdating = false;
        }
        else{
            aiAmount0Button.setBackground(UNSELECTED_BACKGROUND);
            aiAmount1Button.setBackground(UNSELECTED_BACKGROUND);
            aiAmount2Button.setBackground(UNSELECTED_BACKGROUND);
            aiAmount3Button.setBackground(UNSELECTED_BACKGROUND);
            switch(gameDetails.getComputerizedPlayers()){
                case 0:
                    aiAmount0Button.setBackground(SELECTED_BACKGROUND);
                    break;
                case 1:
                    aiAmount1Button.setBackground(SELECTED_BACKGROUND);
                    break;
                case 2:
                    aiAmount2Button.setBackground(SELECTED_BACKGROUND);
                    break;
                case 3:
                    aiAmount3Button.setBackground(SELECTED_BACKGROUND);
                    break;
            }

            playerAmount2Button.setBackground(UNSELECTED_BACKGROUND);
            playerAmount3Button.setBackground(UNSELECTED_BACKGROUND);
            playerAmount4Button.setBackground(UNSELECTED_BACKGROUND);
            switch(gameDetails.getHumanPlayers() + gameDetails.getComputerizedPlayers()){
                case 2:
                    playerAmount2Button.setBackground(SELECTED_BACKGROUND);
                    break;
                case 3:
                    playerAmount3Button.setBackground(SELECTED_BACKGROUND);
                    player3canvas.setVisible(true);
                    break;
                case 4:
                    playerAmount4Button.setBackground(SELECTED_BACKGROUND);
                    player3canvas.setVisible(true);
                    player4canvas.setVisible(true);
                    break;
            }

            gameNameTextField.setText(gameDetails.getName());
            
            int playerPos = 0;
            for(int i = 0; i < playerDetails.size(); ++i){
                if(playerPos == 0){
                    player1NameTextField.setText(playerDetails.get(i).getName());
                }
                else if(playerPos == 1){
                    player2NameTextField.setText(playerDetails.get(i).getName());
                }
                else if(playerPos == 2){
                    player3NameTextField.setText(playerDetails.get(i).getName());
                }
                else if(playerPos == 3){
                    player4NameTextField.setText(playerDetails.get(i).getName());
                }

                ++playerPos;
            }

            waitingForPlayersLabel.setDisable(false);
            waitingForPlayersLabel.setText(String.format("Waiting for %d players", gameDetails.getHumanPlayers() - gameDetails.getJoinedHumanPlayers()));
        }
        
        return keepUpdating;
    }

    private void disableControls() {
            createGameButton.setDisable(true);
            aiAmount0Button.setDisable(true);
            aiAmount1Button.setDisable(true);
            aiAmount2Button.setDisable(true);
            aiAmount3Button.setDisable(true);
            playerAmount2Button.setDisable(true);
            playerAmount3Button.setDisable(true);
            playerAmount4Button.setDisable(true);
            gameNameTextField.setDisable(true);
            player1NameTextField.setDisable(true);
            
            waitingForPlayersLabel.setVisible(true);
    }
}
