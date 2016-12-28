package com.suglob.pharmacy.listeners;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.impl.ConPool;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPool;
import com.suglob.pharmacy.dao.impl.pool.ProxyConnection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class ConnectionPoolInit implements ServletContextListener{
    private ConnectionPool<ProxyConnection> pool;
    public ConnectionPoolInit() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        ICommand command = new ConPool(pool);
        try {
            command.execute(null, null);
        } catch (CommandException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        pool.dispose();
    }

}
