package com.suglob.pharmacy.command.utils;

import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.entity.Client;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CommandHelp {

    public static void sendResponse(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        StringBuffer buf= (StringBuffer) request.getSession().getAttribute("url");
        String url=buf.toString();
        String urlParams = (String) request.getSession().getAttribute("urlParams");

        try {
            response.sendRedirect(url+"?"+urlParams);
        } catch (IOException e) {
            throw new CommandException("Don't execute url: "+url,e);
        }
    }

    public static void clearOrderRecipe(HttpServletRequest request, String drugName, int clientId) {
        List<String> drugsNameOrderRecipe = (List<String>) request.getSession().getAttribute("drugsNameOrderRecipe");
        List<Client> clientsOrderRecipe = (List<Client>) request.getSession().getAttribute("clientsOrderRecipe");
        Client clientDelete=null;
        for (Client client:clientsOrderRecipe){
            if (client.getClientsId()==clientId){
                clientDelete=client;
            }
        }
        if (drugsNameOrderRecipe.contains(drugName) && clientDelete!=null){
            drugsNameOrderRecipe.remove(drugName);
            clientsOrderRecipe.remove(clientDelete);
            request.getSession().setAttribute("drugsNameOrderRecipe",drugsNameOrderRecipe);
            request.getSession().setAttribute("clientsOrderRecipe",clientsOrderRecipe);
        }
    }
}
