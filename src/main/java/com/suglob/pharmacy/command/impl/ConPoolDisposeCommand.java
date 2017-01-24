package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.pool.ConnectionPool;
import com.suglob.pharmacy.service.PoolService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConPoolDisposeCommand implements ICommand {
    private ConnectionPool pool;
    public ConPoolDisposeCommand(ConnectionPool pool) {
        this.pool=pool;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        PoolService service = factory.getPoolService();
        ///////////////////////////////////////////////////////////////////////////////
        try {
            service.disposePool(pool);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
