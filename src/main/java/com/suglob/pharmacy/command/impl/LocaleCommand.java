package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.util.CommandHelp;
import com.suglob.pharmacy.constant.OtherConstant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * This class is for set locale.
 */
public class LocaleCommand implements ICommand {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String locale;
        locale=request.getParameter(OtherConstant.LOCALE);
        request.getSession().setAttribute(OtherConstant.LOCALE, locale);
        CommandHelp.sendResponse(request, response);

    }
}
