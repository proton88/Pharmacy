package com.suglob.pharmacy.filters;

import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.utils.ConstantClass;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
            request.setAttribute(ConstantClass.ERROR, ConstantClass.END_SESSION);
            request.getRequestDispatcher(ConstantClass.INDEX).forward(request, resp);
        } else {
            User user = (User) session.getAttribute(ConstantClass.USER);
            if (user == null) {
                request.setAttribute(ConstantClass.ERROR, ConstantClass.NOT_SESSION_USER);
                request.getRequestDispatcher(ConstantClass.INDEX).forward(request, resp);

            }else {
                chain.doFilter(req, resp);
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
