package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.entity.Drug;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddOrder implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        int count= Integer.parseInt(request.getParameter("count"));
        int id= Integer.parseInt(request.getParameter("drug_id"));
        List<Drug> drugList= (List<Drug>) request.getSession().getAttribute("listDrugs");
        List<Drug> orderList;
        if ((List<Drug>) request.getSession().getAttribute("orderList")==null){
            orderList=new ArrayList<>();
        }else{
            orderList=(List<Drug>) request.getSession().getAttribute("orderList");
        }
        for (Drug drug:drugList){
            if (drug.getId()==id){
                if (count<=drug.getCount()) {
                    drug.setCount(count);
                    orderList.add(drug);
                    request.getSession().setAttribute("orderList", orderList);
                }else {
                    request.setAttribute("error", "main.not_enough_drugs");
                }
                break;
            }
        }

        try {
            request.getRequestDispatcher("main").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*StringBuffer buf= (StringBuffer) request.getSession().getAttribute("url");
        String url=buf.toString();
        if (request.getSession().getAttribute("param")!=null) {
            url = url + "?" + request.getSession().getAttribute("paramName") + "=" + request.getSession().getAttribute("param");
            if (request.getSession().getAttribute("param2") != null){
                url = url + "&" + request.getSession().getAttribute("paramName2") + "=" + request.getSession().getAttribute("param2");
            }
        }
        System.out.println(url);

        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new CommandException("Don't execute url: "+url,e);
        }*/
    }
}
