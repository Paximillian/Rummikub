/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent.turnMenuCommands;

import mvcControllerComponent.GameController;
import mvcControllerComponent.MenuCommand;

/**
 *
 * @author Mor
 */
public class EndTurnCommand implements MenuCommand {

    @Override
    public void execute() throws Exception {
        GameController.getInstance().endTurn();
    }
    
}
