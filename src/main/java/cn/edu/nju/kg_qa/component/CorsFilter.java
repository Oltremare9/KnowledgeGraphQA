package cn.edu.nju.kg_qa.component;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description: <br/>
 * date: 2020/12/22 14:47<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@WebFilter(filterName = "CorsFilter")
@Configuration
public class CorsFilter implements Filter {
    // Cross Origin
    // CORS Resource Sharing
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        chain.doFilter(req, res);
    }
}
