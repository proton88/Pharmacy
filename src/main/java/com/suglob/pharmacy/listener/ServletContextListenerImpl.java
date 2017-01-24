package com.suglob.pharmacy.listener;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.exception.CommandException;
import com.suglob.pharmacy.command.impl.CreateConnectionPoolCommand;
import com.suglob.pharmacy.command.impl.ConPoolDisposeCommand;
import com.suglob.pharmacy.pool.ConnectionPool;
import com.suglob.pharmacy.util.ConstantClass;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class ServletContextListenerImpl implements ServletContextListener{
    private static final Logger LOGGER= LogManager.getLogger(ServletContextListenerImpl.class);
    private ConnectionPool pool;
    public ServletContextListenerImpl() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        System.setProperty(ConstantClass.LOG_DIR, sce.getServletContext().getRealPath(ConstantClass.EMPTY_STRING));

        ICommand command = new CreateConnectionPoolCommand(pool);
        try {
            command.execute(null, null);
        } catch (CommandException e) {
            LOGGER.log(Level.ERROR,e);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ICommand command = new ConPoolDisposeCommand(pool);
        try {
            command.execute(null, null);
        } catch (CommandException e) {
            LOGGER.log(Level.ERROR,e);
        }
    }

}
