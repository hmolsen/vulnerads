package de.cqrity.vulnerapp.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CspFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Content-Security-Policy", "default-src 'self'");
        //res.setHeader("Content-Security-Policy", "default-src 'self'; report-uri https://attacat.de:8666/logger/log.jsp");
        //res.setHeader("Content-Security-Policy-Report-Only", "default-src 'self'; report-uri https://attacat.de:8666/logger/log.jsp");
        chain.doFilter(request, res);
    }

    @Override
    public void destroy() {

    }
}
