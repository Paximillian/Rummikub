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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ws.rummikub.DuplicateGameName_Exception;
import ws.rummikub.GameDoesNotExists_Exception;
import ws.rummikub.InvalidParameters_Exception;

/**
 *
 * @author Mor
 */
@WebServlet("/Lobby/JoinGame")
//Expects: gameName - string
//          playerName - string
//Returns: playerId - int
public class JoinGameServlet extends WebClient {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        String gameName = CookieMap(request.getCookies() ,"gameName" );
        String playerName = CookieMap(request.getCookies() ,"playerName");
        
        if(gameName != null && !gameName.equals("")){
            if(playerName != null && !playerName.equals("")){
                try{
                    out.write(json.toJson(webService.joinGame(gameName, playerName)));
                }
                catch(InvalidParameters_Exception | GameDoesNotExists_Exception ex){
                    response.sendError(404, ex.getMessage());
                }
            }
            else{
                response.sendError(404, "Invalid player name supplied");
            }
        }
        else{
            response.sendError(404, "Invalid game name supplied");
        }
    }
}
