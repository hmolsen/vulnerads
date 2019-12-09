package de.cqrity.vulnerapp.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsDemoFilter implements Filter {

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        //response.setHeader("Access-Control-Allow-Origin", "https://attacat.de:8666");
        //response.setHeader("Access-Control-Allow-Headers", "X-My-Own-Header");
        chain.doFilter(req, res);
    }

}
