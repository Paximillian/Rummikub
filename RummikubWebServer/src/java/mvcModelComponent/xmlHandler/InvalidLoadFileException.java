/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcModelComponent.xmlHandler;

/**
 *
 * @author yafita870
 */
public class InvalidLoadFileException extends Exception {
    
    public InvalidLoadFileException(){         
    }
     
    public InvalidLoadFileException(String message) {
        super(message);
    }
}
