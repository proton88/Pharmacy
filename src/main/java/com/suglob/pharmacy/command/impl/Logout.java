package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.utils.ConstantClass;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Logout implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        request.getSession().invalidate();
        try {
            request.getRequestDispatcher(ConstantClass.INDEX).forward(request,response);
        } catch (IOException | ServletException e) {
            throw new CommandException("Don't execute index.jsp",e);
        }

    }
}
