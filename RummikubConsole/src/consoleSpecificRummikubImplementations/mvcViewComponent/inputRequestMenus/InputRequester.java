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
        System.out.print(requestMessage);
        return inputScanner.nextInt();
    }

    public static String RequestString(String requestMessage) {
        System.out.print(requestMessage);
        return inputScanner.nextLine();
    }
}
