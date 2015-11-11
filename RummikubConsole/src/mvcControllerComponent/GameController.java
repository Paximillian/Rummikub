/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mor
 */

//Game controller is a singleton that manages the current game.
public class GameController {
    private static GameController instance;
    
    public static GameController getInstance(){
        if(instance == null){
            instance = new GameController();
        }
        
        return instance;
    }
    
    private GameController(){
        
    }
    
    public void resetGame(){
        instance = null;
    }
}
