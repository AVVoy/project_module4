package servlet.auth;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import service.UserService;

public abstract class BaseAuthServlet extends HttpServlet {
    protected UserService userService;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext servletContext = getServletContext();

        userService = (UserService) servletContext.getAttribute("userService");
    }
}
