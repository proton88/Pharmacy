package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.entity.Drug;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AddOrder implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int count = Integer.parseInt(request.getParameter("count"));
        int id = Integer.parseInt(request.getParameter("drug_id"));
        String recipeCode=request.getParameter("code");
        List<Drug> drugList = (List<Drug>) request.getSession().getAttribute("listDrugs");
        BigDecimal price = (BigDecimal) request.getSession().getAttribute("orderPrice");
        if (price == null) {
            price = new BigDecimal(0.0);
        }
        List<Drug> orderList = (List<Drug>) request.getSession().getAttribute("orderList");
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        for (Drug drug : drugList) {
            if (drug.getId() == id) {
                if (count <= drug.getCount()) {
                    if (drug.getIsRecipe().equals("Y")) {

                    }
                    price = price.add(drug.getPrice().multiply(new BigDecimal(count)));
                    if (!orderList.isEmpty()) {
                        for (Drug orderDrug : orderList) {
                            if (orderDrug.getId() == id) {
                                orderDrug.setCount(orderDrug.getCount() + count);
                            } else {
                                Drug newDrug = new Drug(drug);
                                newDrug.setCount(count);
                                orderList.add(newDrug);
                            }
                        }
                    } else {
                        Drug newDrug = new Drug(drug);
                        newDrug.setCount(count);
                        orderList.add(newDrug);
                    }


                    request.getSession().setAttribute("orderList", orderList);
                    request.getSession().setAttribute("orderPrice", price);
                } else {
                    request.getSession().setAttribute("error", "main.not_enough_drugs");
                }
                break;
            }
        }

        StringBuffer buf = (StringBuffer) request.getSession().getAttribute("url");
        String url = buf.toString();
        String urlParams = (String) request.getSession().getAttribute("urlParams");

        try {
            response.sendRedirect(url + "?" + urlParams);
        } catch (IOException e) {
            throw new CommandException("Don't execute url: " + url, e);
        }
    }
}
