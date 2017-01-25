package com.suglob.pharmacy.filter;

import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "CheckSessionFilter")
public class CheckSessionFilter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);

        if (session == null || session.isNew()) {
            request.setAttribute(OtherConstant.ERROR, MessageConstant.END_SESSION);
            request.getRequestDispatcher(OtherConstant.INDEX).forward(request, resp);
        } else {
            User user = (User) session.getAttribute(OtherConstant.USER);
            if (user == null) {
                request.setAttribute(OtherConstant.ERROR, MessageConstant.NOT_SESSION_USER);
                request.getRequestDispatcher(OtherConstant.INDEX).forward(request, resp);

            }else {
                chain.doFilter(req, resp);
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
