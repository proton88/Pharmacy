package com.suglob.pharmacy.command.utils;

import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.entity.Client;
import com.suglob.pharmacy.utils.ConstantClass;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CommandHelp {

    public static void sendResponse(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        StringBuffer buf= (StringBuffer) request.getSession().getAttribute(ConstantClass.URL);
        String url=buf.toString();
        String urlParams = (String) request.getSession().getAttribute(ConstantClass.URL_PARAMS);
        if (urlParams!=null){
            url=url+ConstantClass.QUESTION+urlParams;
        }

        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new CommandException("Don't execute url: "+url,e);
        }
    }

    public static void clearOrderRecipe(HttpServletRequest request, String drugName, int clientId) {
        List<String> drugsNameOrderRecipe = (List<String>) request.getSession().getAttribute(ConstantClass.DRUGS_NAME_ORDER_RECIPE);
        List<Client> clientsOrderRecipe = (List<Client>) request.getSession().getAttribute(ConstantClass.CLIENTS_ORDER_RECIPE);
        Client clientDelete=null;
        for (Client client:clientsOrderRecipe){
            if (client.getClientsId()==clientId){
                clientDelete=client;
            }
        }
        if (drugsNameOrderRecipe.contains(drugName) && clientDelete!=null){
            drugsNameOrderRecipe.remove(drugName);
            clientsOrderRecipe.remove(clientDelete);
            request.getSession().setAttribute(ConstantClass.DRUGS_NAME_ORDER_RECIPE,drugsNameOrderRecipe);
            request.getSession().setAttribute(ConstantClass.CLIENTS_ORDER_RECIPE,clientsOrderRecipe);
        }
    }

    public static void clearExtendRecipe(HttpServletRequest request, int positionRecipe) {
        List<String> drugsNameExtendRecipe = (List<String>) request.getSession().getAttribute(ConstantClass.DRUGS_NAME_EXTEND_RECIPE);
        List<String> drugsCodeExtendRecipe = (List<String>) request.getSession().getAttribute(ConstantClass.DRUGS_CODE_EXTEND_RECIPE);
        List<Client> clientsExtendRecipe = (List<Client>) request.getSession().getAttribute(ConstantClass.CLIENTS_EXTEND_RECIPE);
        drugsNameExtendRecipe.remove(positionRecipe-1);
        drugsCodeExtendRecipe.remove(positionRecipe-1);
        clientsExtendRecipe.remove(positionRecipe-1);
    }
}
