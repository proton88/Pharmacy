package com.suglob.pharmacy.command.impl;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPool;
import com.suglob.pharmacy.dao.impl.pool.ProxyConnection;
import com.suglob.pharmacy.service.PoolService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConPool implements ICommand {
    private ConnectionPool<ProxyConnection> pool;
    public ConPool(ConnectionPool<ProxyConnection> pool) {
        this.pool=pool;
    }


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        PoolService service = factory.getPoolService();
        ///////////////////////////////////////////////////////////////////////////////
        try {
            service.getPool(pool);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
    }
}
