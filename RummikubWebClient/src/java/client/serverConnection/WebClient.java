/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.serverConnection;

import com.google.gson.Gson;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServlet;
import ws.rummikub.RummikubWebService;
import ws.rummikub.RummikubWebServiceService;

/**
 *
 * @author Mor
 */
public class WebClient extends HttpServlet {
    private static final String SERVICE_URL = "http://mobedu3.mtacloud.co.il";
    private static final String SERVICE_PORT = "8080";
    private static final String SERVICE_NAME = "RummikubWebServer/RummikubWebServiceService?wsdl";
    //private static final String SERVICE_URL = "http://localhost";
    //private static final String SERVICE_PORT = "8080";
    //private static final String SERVICE_NAME = "api/RummikubWebServiceService?wsdl";
    
    private static URL getServiceURL() throws MalformedURLException{ return new URL(String.format("%s:%s/%s", SERVICE_URL, SERVICE_PORT, SERVICE_NAME)); }
    
    protected static RummikubWebService webService;
    protected static Gson json = new Gson();
    
    static{
        try {
            URL url = getServiceURL();
            RummikubWebServiceService service = new RummikubWebServiceService(url);
            webService = service.getRummikubWebServicePort();
        } 
        catch (MalformedURLException ex) {
            Logger.getLogger(WebClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
