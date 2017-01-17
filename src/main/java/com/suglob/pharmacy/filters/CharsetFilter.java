package com.suglob.pharmacy.filters;

import com.suglob.pharmacy.utils.ConstantClass;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "CharsetFilter")
public class CharsetFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding(ConstantClass.UTF8);
        resp.setCharacterEncoding(ConstantClass.UTF8);
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
