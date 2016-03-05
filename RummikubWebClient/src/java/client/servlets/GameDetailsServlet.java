/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.servlets;

import client.serverConnection.WebClient;
import static client.servlets.Cookie.CookieUtils.CookieMap;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ws.rummikub.GameDoesNotExists_Exception;
import ws.rummikub.InvalidParameters_Exception;

/**
 *
 * @author Mor
 */
@WebServlet("/Lobby/GameDetails")
//Expects: gameName - string
//Returns: gameDetails - GameDetails
public class GameDetailsServlet extends WebClient {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        String gameName = CookieMap(request.getCookies() ,"gameName" );
            
        if(gameName != null && !gameName.equals("")){
            try {
                out.write(json.toJson(webService.getGameDetails(gameName)));
            } 
            catch (GameDoesNotExists_Exception ex) {
                out.write(json.toJson(null));
            }
        }
        else{
            response.sendError(400, "Invalid game name supplied");
        }
    }
}
