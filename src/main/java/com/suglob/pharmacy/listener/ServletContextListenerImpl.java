package com.suglob.pharmacy.listener;

import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.pool.ConnectionPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener()
public class ServletContextListenerImpl implements ServletContextListener{
    public ServletContextListenerImpl() {
    }

    public void contextInitialized(ServletContextEvent sce) {
        System.setProperty(OtherConstant.LOG_DIR, sce.getServletContext().getRealPath(OtherConstant.EMPTY_STRING));

        ConnectionPool.getInstance().initPoolData();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().dispose();
    }

}
