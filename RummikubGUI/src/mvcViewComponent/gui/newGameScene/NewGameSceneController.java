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
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import mvcControllerComponent.GameController;
import mvcViewComponent.gui.sceneController.ControlledScreen;
import mvcViewComponent.gui.sceneController.ScreensController;

/**
 * FXML Controller class
 *
 * @author yafita870
 */
public class NewGameSceneController implements Initializable , ControlledScreen {

    @FXML private TextField gameNameTextField;
    @FXML private TextField player1NameTextField;
    @FXML private TextField player2NameTextField;
    @FXML private TextField player3NameTextField;
    @FXML private TextField player4NameTextField;
    @FXML private CheckBox player1AiPlayerChoiceBox;
    @FXML private CheckBox player2AiPlayerChoiceBox;
    @FXML private CheckBox player3AiPlayerChoiceBox;
    @FXML private CheckBox player4AiPlayerChoiceBox;
    @FXML private Label errorLabel;
    private  String player1name;
    private  String player2name;
    private  String player3name;
    private  String player4name;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @Override
    public void setScreenParent(ScreensController screenParent) {
        
    }
    
    @FXML private Pane player3canvas;
    @FXML private Pane player4canvas;
    private int numberOfPlayers =2;
    @FXML
    private void onActionNumberOfPlayers2Button(ActionEvent event) throws IOException
    {
        player3canvas.setDisable(true);
        player4canvas.setDisable(true);
        numberOfPlayers=2;
        
    }
    
    @FXML
    private void onActionNumberOfPlayers3Button(ActionEvent event) throws IOException
    {
        player3canvas.setDisable(false);
        player4canvas.setDisable(true);
        numberOfPlayers=3;
    }
    
    @FXML
    private void onActionNumberOfPlayers4Button(ActionEvent event) throws IOException
    {
        player3canvas.setDisable(false);
        player4canvas.setDisable(false);
        numberOfPlayers=4;
    }
    
    @FXML
    private void onActionBackButton(ActionEvent event) throws IOException  
    {
        ScreensController.getInstance().setScreen(ScreensController.MAIN_SCENE);
    }
    
    @FXML
    private void onActionRedyButton(ActionEvent event) throws IOException    
    {
        if(isGameRedy())
        {
            GameController.getInstance().setGameName(fixName(gameNameTextField.getText(), "Rummikub " + date() ));
            GameController.getInstance().setNumberOfPlayers(numberOfPlayers);
            GameController.getInstance().addPlayer(player1name,player1AiPlayerChoiceBox.isSelected());
            GameController.getInstance().addPlayer(player2name,player2AiPlayerChoiceBox.isSelected());
            if(numberOfPlayers > 2)
            {
                GameController.getInstance().addPlayer(player3name,player3AiPlayerChoiceBox.isSelected());
                if(numberOfPlayers == 4)
                {
                    GameController.getInstance().addPlayer(player4name,player4AiPlayerChoiceBox.isSelected());
                }
            }
            GameController.getInstance().startGame();
        }
    }
    
    private String fixName(String str1,String str2)
    {
        return str1.trim().isEmpty() ? str2 : str1;
    }

    private boolean isGameRedy() {
        ArrayList<String> names = new ArrayList<String>();
        boolean allBots = true;
        boolean sameName = false;
        player1name = fixName(player1NameTextField.getText(), "Player1");
        player2name = fixName(player2NameTextField.getText(), "Player2");
        player3name = fixName(player3NameTextField.getText(), "Player3");
        player4name = fixName(player4NameTextField.getText(), "Player4");
        
        names.add(player1name);
        allBots = player1AiPlayerChoiceBox.isSelected() && player2AiPlayerChoiceBox.isSelected();
        if(names.contains(player2name))
                sameName = true;        
        names.add(player2name);
        if(numberOfPlayers > 2)
        {
            if(names.contains(player3name))
                sameName = true;
            names.add(player3name);
            allBots = allBots && player3AiPlayerChoiceBox.isSelected();
            if(numberOfPlayers == 4)
            {
                allBots = allBots && player4AiPlayerChoiceBox.isSelected();
                if(names.contains(player4name))
                    sameName = true;
            }
        }
        if(sameName || allBots )
        {
            if(sameName)
            {
                sendErrorMSG("Players must have different names!");
            }
            else
            {
                sendErrorMSG("The game requires atleast one human player!");
            }
            return false;
        }
        return true;
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
}
