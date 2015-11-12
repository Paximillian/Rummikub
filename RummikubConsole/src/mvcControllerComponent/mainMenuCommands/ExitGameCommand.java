/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent.mainMenuCommands;

import mvcControllerComponent.GameController;
import mvcControllerComponent.mainMenuCommands.MenuCommand;

/**
 *
 * @author Mor
 */
public class ExitGameCommand implements MenuCommand{

    public ExitGameCommand() {
    }

    @Override
    public void execute() throws Exception {
        GameController.getInstance().endGame();
    }   
}
