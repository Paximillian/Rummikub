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
@WebServlet("/Game/Events")
//Expects: playerId - int
//          eventId - int
//Returns: events - List<Event>
public class EventsServlet extends WebClient {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        int playerId;
        try{
            playerId = Integer.parseInt(request.getParameter("playerId"));

            int eventId;

            try{
                eventId = Integer.parseInt(request.getParameter("eventId"));
                out.write(json.toJson(webService.getEvents(playerId, eventId)));
            }
            catch (InvalidParameters_Exception | NumberFormatException ex) {
                response.sendError(404, "Invalid event ID supplied");
            }
        }
        catch(NumberFormatException ex){
                response.sendError(404, "Invalid player ID supplied");
        }
    }
}
