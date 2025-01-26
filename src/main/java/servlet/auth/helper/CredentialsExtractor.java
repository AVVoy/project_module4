package servlet.auth.helper;

import jakarta.servlet.http.HttpServletRequest;
import servlet.auth.helper.dto.Credential;

public class CredentialsExtractor {
    public static Credential extract(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        return new Credential(login, password);
    }
}
