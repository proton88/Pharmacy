package com.suglob.pharmacy.controller;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.utils.ConstantClass;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@WebServlet("/Controller")
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    public Controller() {}
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name=request.getParameter(ConstantClass.COMMAND);
        CommandEnum currentEnum = CommandEnum.valueOf(name.toUpperCase());
        ICommand command = currentEnum.getCurrentCommand();
        try {
            command.execute(request, response);
        } catch (CommandException e) {
            LOGGER.log(Level.ERROR, "Exception: ", e);
            request.setAttribute(ConstantClass.ERROR_PAGE, e.toString());
            request.getRequestDispatcher(ConstantClass.ERROR_PAGE_PATH).forward(request, response);
        }
    }
}
