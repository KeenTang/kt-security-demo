package com.k.security.demo.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-02
 * Time: 22:34
 */
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("TimeFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        long st = System.currentTimeMillis();
        System.out.println("TimeFilter doFilter start");
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("TimeFilter doFilter end,总耗时:" + (System.currentTimeMillis() - st));
    }

    @Override
    public void destroy() {

    }
}
