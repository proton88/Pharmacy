package com.suglob.pharmacy.controller;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.constant.OtherConstant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@WebServlet(urlPatterns = "/Controller", loadOnStartup = 1)
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);

    public Controller() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter(OtherConstant.COMMAND);
        CommandEnum currentEnum;
        try {
            if (name != null) {
                currentEnum = CommandEnum.valueOf(name.toUpperCase());
            } else {
                throw new CommandException("Wrong command");
            }
            ICommand command = currentEnum.getCurrentCommand();

            command.execute(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, e);
            request.setAttribute(OtherConstant.ERROR_PAGE, e.toString());
            request.getRequestDispatcher(OtherConstant.ERROR_PAGE_PATH).forward(request, response);
        }
    }
}
