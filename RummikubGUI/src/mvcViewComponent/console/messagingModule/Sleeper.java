/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcViewComponent.console.messagingModule;

/**
 *
 * @author Mor
 */
public class Sleeper {
    public static void Sleep(int msDuration){
        try {
            Thread.sleep(msDuration);
        } 
        catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
