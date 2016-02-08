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
import ws.rummikub.InvalidXML_Exception;

/**
 *
 * @author Mor
 */
@WebServlet("/Lobby/CreateFromXML")
//Expects: xmlData - string
//Returns: createdGameName - string
public class CreateGameFromXmlServlet extends WebClient {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        String xmlData = request.getParameter("xmlData");
        if(xmlData != null && !xmlData.equals("")) {
            try {
                out.write(json.toJson(webService.createGameFromXML(xmlData)));
            } 
            catch (DuplicateGameName_Exception | InvalidParameters_Exception | InvalidXML_Exception ex) {
                response.sendError(404, ex.getMessage());
            }
        } 
        else{
            response.sendError(404, "Invalid xml data supplied");
        }
    }
}
