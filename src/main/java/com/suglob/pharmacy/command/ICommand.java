package com.suglob.pharmacy.command;

import com.suglob.pharmacy.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ICommand {
    void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
