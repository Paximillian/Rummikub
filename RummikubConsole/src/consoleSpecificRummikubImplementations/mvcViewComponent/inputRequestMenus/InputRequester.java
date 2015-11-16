/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consoleSpecificRummikubImplementations.mvcViewComponent.inputRequestMenus;

import java.lang.reflect.Method;
import java.util.Scanner;

/**
 *
 * @author Mor
 */
public class InputRequester {
    private static final Scanner inputScanner = new Scanner(System.in);
    
    public static int RequestInt(String requestMessage){
        System.out.println(requestMessage);
        
        int inputNumber = 0;
        try{
            inputNumber = Integer.parseInt(inputScanner.nextLine());
        }
        catch(Exception ex){
            System.out.print(ex.getMessage());
            return -1;
        }
        
        return inputNumber;
    }

    public static String RequestString(String requestMessage) {
        System.out.println(requestMessage);
        return inputScanner.nextLine();
    }
}
