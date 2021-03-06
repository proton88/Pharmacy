package com.suglob.pharmacy.listener;

import com.suglob.pharmacy.constant.NumberConstant;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener()
public class HttpSessionListenerImpl implements HttpSessionListener {

    // Public constructor is required by servlet spec
    public HttpSessionListenerImpl() {
    }

    public void sessionCreated(HttpSessionEvent se) {
      se.getSession().setMaxInactiveInterval(NumberConstant.SESSION_TIME);
    }

    public void sessionDestroyed(HttpSessionEvent se) {
      /* Session is destroyed. */
    }


}
