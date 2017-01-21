package com.suglob.pharmacy.listeners;

import com.suglob.pharmacy.utils.ConstantClass;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

@WebListener()
public class SessionListener implements HttpSessionListener {

    // Public constructor is required by servlet spec
    public SessionListener() {
    }

    public void sessionCreated(HttpSessionEvent se) {
      se.getSession().setMaxInactiveInterval(ConstantClass.SESSION_TIME);
    }

    public void sessionDestroyed(HttpSessionEvent se) {
      /* Session is destroyed. */
    }


}
