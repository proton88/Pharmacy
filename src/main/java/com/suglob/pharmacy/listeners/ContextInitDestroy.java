package com.suglob.pharmacy.listeners;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.impl.ConPool;
import com.suglob.pharmacy.command.impl.ConPoolDispose;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPool;
import com.suglob.pharmacy.dao.impl.pool.ProxyConnection;
import com.suglob.pharmacy.utils.ConstantClass;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class ContextInitDestroy implements ServletContextListener{
    private static final Logger LOGGER= LogManager.getLogger(ContextInitDestroy.class);
    private ConnectionPool<ProxyConnection> pool;
    public ContextInitDestroy() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        System.setProperty(ConstantClass.LOG_DIR, sce.getServletContext().getRealPath(ConstantClass.EMPTY_STRING));

        ICommand command = new ConPool(pool);
        try {
            command.execute(null, null);
        } catch (CommandException e) {
            LOGGER.log(Level.ERROR,e);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ICommand command = new ConPoolDispose(pool);
        try {
            command.execute(null, null);
        } catch (CommandException e) {
            LOGGER.log(Level.ERROR,e);
        }
    }

}
