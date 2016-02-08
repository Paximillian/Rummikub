/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.servlets;

import client.serverConnection.WebClient;
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

@WebServlet("/Lobby/PlayersDetails")
//Expects: gameName - string
//Returns: playerDetails - List<PlayerDetails>
public class PlayersDetailsServlet extends WebClient {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        String gameName = request.getParameter("gameName");
        
        if(gameName != null && !gameName.equals("")){
            try {
                out.write(json.toJson(webService.getPlayersDetails(gameName)));
            } 
            catch (GameDoesNotExists_Exception ex) {
                response.sendError(404, ex.getMessage());
            }
        }
        else{
            response.sendError(404, "Invalid game name supplied");
        }
    }
}
