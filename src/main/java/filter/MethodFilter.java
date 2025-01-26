package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.IOException;
import java.util.List;

@WebFilter("/secure/products")
public class MethodFilter implements Filter {

    private final List<String> httpMethods = List.of("DELETE", "PUT", "PATCH");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String method = req.getParameter("_method");

        if (method != null && httpMethods.contains(method)) {
            HttpServletRequestWrapper requestWrapper = new HttpServletRequestWrapper(req) {
                @Override
                public String getMethod() {
                    return method;
                }
            };
            filterChain.doFilter(requestWrapper, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
