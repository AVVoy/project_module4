package servlet.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.auth.helper.CredentialsExtractor;
import servlet.auth.helper.dto.Credential;

import java.io.IOException;

@WebServlet(urlPatterns = "/registration")
public class RegistrationServlet extends BaseAuthServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Credential credential = CredentialsExtractor.extract(req);

        userService.saveUser(credential);

        resp.sendRedirect("login.jsp");
    }
}
