package com.suglob.pharmacy.controller;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/Controller")
public class Controller extends HttpServlet {
    private static final String COMMAND="command";

    public Controller() {}
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = null;
        session=request.getSession(false);
        if(session==null){

            System.out.println("Session wasn't construct");
        }else {
            System.out.println("Session id: " + session.getId());
        }
        String name=request.getParameter(COMMAND);
        CommandEnum currentEnum = CommandEnum.valueOf(name.toUpperCase());
        ICommand command = currentEnum.getCurrentCommand();
        try {
            command.execute(request, response);
        } catch (CommandException e) {
            request.setAttribute("errorpage", e.toString());
            request.getRequestDispatcher("WEB-INF/jsp/errorpage.jsp").forward(request, response);
        }
    }
}
