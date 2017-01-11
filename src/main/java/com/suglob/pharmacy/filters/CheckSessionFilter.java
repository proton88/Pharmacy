package com.suglob.pharmacy.filters;

import com.suglob.pharmacy.entity.User;

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
            System.out.println("Session invalidate");
            request.setAttribute("error", "index.error_end_session");
            request.getRequestDispatcher("index.jsp").forward(request, resp);
        } else {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                request.setAttribute("error", "index.error_not_session_user");
                request.getRequestDispatcher("index.jsp").forward(request, resp);

            }else {
                chain.doFilter(req, resp);
            }
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
