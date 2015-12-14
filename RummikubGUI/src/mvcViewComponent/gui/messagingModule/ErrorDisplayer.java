/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.gui.messagingModule;

import javafx.scene.control.Alert;

/**
 *
 * @author Mor
 */
public class ErrorDisplayer {
    public static void showError(String errorMessage){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(String.format(errorMessage));
        alert.showAndWait();
    }
}
