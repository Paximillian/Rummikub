/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.servlets;

import client.serverConnection.WebClient;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ws.rummikub.GameDoesNotExists_Exception;
import ws.rummikub.InvalidParameters_Exception;

/**
 *
 * @author Mor
 */
@WebServlet("/Lobby/PlayerDetails")
//Expects: playerId - int
//Returns: cplayerDetails - PlayerDetails
public class PlayerDetailsServlet extends WebClient {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            int playerId = Integer.parseInt(request.getParameter("playerId"));
            out.write(json.toJson(webService.getPlayerDetails(playerId)));
        } 
        catch (GameDoesNotExists_Exception | InvalidParameters_Exception | NumberFormatException ex) {
            response.sendError(400, "Invalid player ID supplied");
        }
    }
}
