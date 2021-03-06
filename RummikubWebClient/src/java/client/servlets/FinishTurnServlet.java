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
import ws.rummikub.InvalidParameters_Exception;

/**
 *
 * @author Mor
 */
@WebServlet("/Game/FinishTurn")
//Expects: playerId - int
//Returns: 
public class FinishTurnServlet extends WebClient {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        int playerId;
        try{
            playerId = Integer.parseInt(CookieMap(request.getCookies() ,"playerId"));

            webService.finishTurn(playerId);
        }
        catch(NumberFormatException ex){
                response.sendError(400, "Invalid player ID supplied");
        } 
        catch (InvalidParameters_Exception ex) {
                response.sendError(400, ex.getMessage());
        }
    }
}