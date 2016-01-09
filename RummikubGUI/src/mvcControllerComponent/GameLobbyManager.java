/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcControllerComponent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvcControllerComponent.client.ws.RummikubWebService;
import mvcControllerComponent.client.ws.RummikubWebServiceService;
/**
 *
 * @author Mor
 */
public class GameLobbyManager {
    
    private static final String SERVICE_URL = "http://localthost";
    private static final String SERVICE_PORT = "8080";
    private static final String SERVICE_NAME = "api/RummikubWebServiceService?wsdl";
    private static URL getServiceURL() throws MalformedURLException{ return new URL(String.format("%s:%s/%s", SERVICE_URL, SERVICE_PORT, SERVICE_NAME)); }
    
    private static RummikubWebService webService;
    
    static{
        try {
            RummikubWebServiceService service = new RummikubWebServiceService(getServiceURL());
            webService = service.getRummikubWebServicePort();
        } 
        catch (MalformedURLException ex) {
            Logger.getLogger(GameLobbyManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
