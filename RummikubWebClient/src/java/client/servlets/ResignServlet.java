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
import ws.rummikub.InvalidParameters_Exception;

/**
 *
 * @author Mor
 */
@WebServlet("/Game/Resign")
//Expects: playerId - int
//Returns: 
public class ResignServlet extends WebClient {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        int playerId;
        try{
            playerId = Integer.parseInt(request.getParameter("playerId"));

            webService.resign(playerId);
        }
        catch(NumberFormatException ex){
                response.sendError(404, "Invalid player ID supplied");
        } 
        catch (InvalidParameters_Exception ex) {
                response.sendError(404, ex.getMessage());
        }
    }
}
