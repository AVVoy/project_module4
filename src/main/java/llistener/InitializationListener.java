package llistener;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.ProductDAO;
import dao.UserDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.ProductService;
import service.UserService;
import service.validation.ValidationExecutor;
import service.validation.Validator;
import service.validation.impl.LoginValidator;
import service.validation.impl.PasswordValidator;

import java.io.File;
import java.util.Map;

@WebListener
public class InitializationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        ObjectMapper objectMapper = new ObjectMapper();
        File userFile = new File("E:\\IdeaProjects\\project_module3\\src\\main\\resources\\users.json");
        File productFile = new File("E:\\IdeaProjects\\project_module3\\src\\main\\resources\\products.json");

        UserDAO userDAO = new UserDAO(objectMapper, userFile);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserService userService = new UserService(userDAO, passwordEncoder);

        LoginValidator loginValidator = new LoginValidator(userService);
        PasswordValidator passwordValidator = new PasswordValidator();

        Map<String, Validator> validatorMap = Map.ofEntries(
                Map.entry("login", loginValidator),
                Map.entry("password", passwordValidator)
        );

        ValidationExecutor validationExecutor = new ValidationExecutor(validatorMap);

        ProductDAO productDao = new ProductDAO(objectMapper, productFile);
        ProductService productService = new ProductService(productDao);

        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("passwordEncoder", passwordEncoder);

        servletContext.setAttribute("productService", productService);

        servletContext.setAttribute("validationExecutor", validationExecutor);

    }
}
