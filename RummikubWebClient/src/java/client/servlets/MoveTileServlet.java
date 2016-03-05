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
import ws.rummikub.DuplicateGameName_Exception;
import ws.rummikub.InvalidParameters_Exception;

/**
 *
 * @author Mor
 */
@WebServlet("/Game/MoveTile")
//Expects: playerId - int
//          sourceSequenceIndex - int 
//          sourceSequencePosition - int 
//          targetSequenceIndex - int 
//          targetSequencePosition - int
//Returns: 
public class MoveTileServlet extends WebClient {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        int playerId;
        try{
            playerId = Integer.parseInt(CookieMap(request.getCookies() ,"playerId"));

            int sourceSequenceId, sourceSequencePos, targetSequenceId, targetSequencePos;

            try{
                sourceSequenceId = Integer.parseInt(CookieMap(request.getCookies() ,"sourceSequenceIndex"));
                sourceSequencePos = Integer.parseInt(CookieMap(request.getCookies() ,"sourceSequencePosition"));
                targetSequenceId = Integer.parseInt(CookieMap(request.getCookies() ,"targetSequenceIndex"));
                targetSequencePos = Integer.parseInt(CookieMap(request.getCookies() ,"targetSequencePosition"));
                
                webService.moveTile(playerId, sourceSequenceId, sourceSequencePos, targetSequenceId, targetSequencePos);
            }
            catch (NumberFormatException ex) {
                response.sendError(400, "Invalid move data supplied");
            }
            catch(InvalidParameters_Exception ex){
                response.sendError(400, ex.getMessage());
            } 
        }
        catch(NumberFormatException ex){
                response.sendError(400, "Invalid player ID supplied");
        }
    }
}
