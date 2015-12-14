/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.mainMenu;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import mvcControllerComponent.mainMenuCommands.LoadGameCommand;
import org.xml.sax.SAXException;

/**
 *
 * @author Mor
 */
public class MainMenuController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleMenuButtonLoadAction(ActionEvent event) throws SAXException {
        
        try{
        new LoadGameCommand().execute();
        }
        catch (SAXException ex) {
            label.setText(ex.getMessage());
        }
                

        
    }
    
    @FXML
    private void handleMenuButtonPlayAction(ActionEvent event)
    {
        System.out.println("You clicked MenuButtonPlay!");
        label.setText("You clicked MenuButtonPlay");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
