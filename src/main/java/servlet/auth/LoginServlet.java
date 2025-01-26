package servlet.auth;

import entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servlet.auth.helper.CredentialsExtractor;
import servlet.auth.helper.dto.Credential;

import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends BaseAuthServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        Credential credential = CredentialsExtractor.extract(req);

        Optional<User> registeredUser = userService.getRegisteredUser(credential);

        if(registeredUser.isPresent()) {
            session.setAttribute("userId", registeredUser.get().getId());
            resp.sendRedirect("/secure/products");
        } else {
            resp.sendRedirect("login.jsp");
        }

    }
}
