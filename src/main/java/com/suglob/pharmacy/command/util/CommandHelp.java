package com.suglob.pharmacy.command.util;

import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.constant.NumberConstant;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.entity.Client;
import com.suglob.pharmacy.entity.ListDragTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class CommandHelp {

    public static void sendResponse(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        StringBuffer buf = (StringBuffer) request.getSession().getAttribute(OtherConstant.URL);
        String url = buf.toString();
        String urlParams = (String) request.getSession().getAttribute(OtherConstant.URL_PARAMS);
        if (urlParams != null) {
            url = url + OtherConstant.QUESTION + urlParams;
        }

        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new CommandException("Don't execute url: " + url, e);
        }
    }

    public static void clearOrderRecipe(HttpServletRequest request, String drugName, int clientId) {
        drugName = drugName.toLowerCase();
        drugName = drugName.substring(0, 1).toUpperCase() + drugName.substring(1);
        List<String> drugsNameOrderRecipe = ((ListDragTag) request.getSession().getAttribute(OtherConstant.DRUGS_NAME_ORDER_RECIPE)).getListDrugs();
        List<Client> clientsOrderRecipe = (List<Client>) request.getSession().getAttribute(OtherConstant.CLIENTS_ORDER_RECIPE);
        int index = NumberConstant.ZERO;
        Iterator<Client> it = clientsOrderRecipe.iterator();
        while (it.hasNext()) {
            if (it.next().getClientsId() == clientId) {
                if (drugsNameOrderRecipe.get(index).contains(drugName)) {
                    drugsNameOrderRecipe.remove(drugName);
                    it.remove();
                    index--;
                    ListDragTag tag = new ListDragTag(drugsNameOrderRecipe);
                    request.getSession().setAttribute(OtherConstant.DRUGS_NAME_ORDER_RECIPE, tag);
                    request.getSession().setAttribute(OtherConstant.CLIENTS_ORDER_RECIPE, clientsOrderRecipe);
                }
            }
            index++;
        }
    }

    public static void clearExtendRecipe(HttpServletRequest request, int positionRecipe) {
        List<String> drugsNameExtendRecipe = ((ListDragTag) request.getSession().getAttribute(OtherConstant.DRUGS_NAME_EXTEND_RECIPE)).getListDrugs();
        List<String> drugsCodeExtendRecipe = (List<String>) request.getSession().getAttribute(OtherConstant.DRUGS_CODE_EXTEND_RECIPE);
        List<Client> clientsExtendRecipe = (List<Client>) request.getSession().getAttribute(OtherConstant.CLIENTS_EXTEND_RECIPE);
        drugsNameExtendRecipe.remove(positionRecipe - 1);
        drugsCodeExtendRecipe.remove(positionRecipe - 1);
        clientsExtendRecipe.remove(positionRecipe - 1);
    }
}
