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
import ws.rummikub.DuplicateGameName_Exception;
import ws.rummikub.GameDoesNotExists_Exception;
import ws.rummikub.InvalidParameters_Exception;

/**
 *
 * @author Mor
 */
@WebServlet("/Lobby/CreateGame")
//Expects: gameName - string
//          humanPlayers - int
//          computerPlayers - int
//Returns: 
public class CreateGameServlet extends WebClient {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        String gameName = request.getParameter("gameName");
        
        if(gameName != null && !gameName.equals("")){
            int humanPlayers;
            try{
                humanPlayers = Integer.parseInt(request.getParameter("humanPlayers"));

                int computerPlayers;

                try{
                    computerPlayers = Integer.parseInt(request.getParameter("computerPlayers"));
                    webService.createGame(gameName, humanPlayers, computerPlayers);
                }
                catch (NumberFormatException ex) {
                    response.sendError(404, "Invalid number of computer players supplied");
                }
                catch(InvalidParameters_Exception | DuplicateGameName_Exception ex){
                    response.sendError(404, ex.getMessage());
                } 
            }
            catch(NumberFormatException ex){
                    response.sendError(404, "Invalid number of players supplied");
            }
        }
        else{
            response.sendError(404, "Invalid game name supplied");
        }
    }
}