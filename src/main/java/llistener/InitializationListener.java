package llistener;

import config.HibernateConfig;
import config.LiquibaseConfig;
import dao.ProductDAO;
import dao.UserDAO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import org.hibernate.SessionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import service.ProductService;
import service.RegistrationInputFieldService;
import service.UserService;
import service.validation.ValidationExecutor;
import service.validation.Validator;
import service.validation.impl.LoginValidator;
import service.validation.impl.PasswordValidator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Map;

@WebListener
public class InitializationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        initializeLiquibase();
        SessionFactory sessionFactory = new HibernateConfig().buildSessionFactory();



        UserDAO userDAO = new UserDAO(sessionFactory);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserService userService = new UserService(userDAO, passwordEncoder);

        LoginValidator loginValidator = new LoginValidator(userService);
        PasswordValidator passwordValidator = new PasswordValidator();

        Map<String, Validator> validatorMap = Map.ofEntries(
                Map.entry("login", loginValidator),
                Map.entry("password", passwordValidator)
        );

        ValidationExecutor validationExecutor = new ValidationExecutor(validatorMap);

        ProductDAO productDAO = new ProductDAO(sessionFactory);
        ProductService productService = new ProductService(productDAO);

        RegistrationInputFieldService registrationInputFieldService = new RegistrationInputFieldService();

        servletContext.setAttribute("sessionFactory", sessionFactory);

        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("passwordEncoder", passwordEncoder);

        servletContext.setAttribute("productService", productService);

        servletContext.setAttribute("validationExecutor", validationExecutor);

        servletContext.setAttribute("registrationInputFieldService", registrationInputFieldService);

    }

    @SneakyThrows
    private void initializeLiquibase() {
        LiquibaseConfig liquibaseConfig = new LiquibaseConfig();
        DataSource dataSource = liquibaseConfig.getDataSource();
        try (
                Connection connection = dataSource.getConnection();
                JdbcConnection jdbcConnection = new JdbcConnection(connection);
                Liquibase liquibase = new Liquibase(
                        LiquibaseConfig.CHANGELOG_FILE,
                        new ClassLoaderResourceAccessor(),
                        jdbcConnection)
        ) {
            liquibase.update(new Contexts(), new LabelExpression());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        SessionFactory sessionFactory = (SessionFactory) servletContextEvent
                .getServletContext()
                .getAttribute("sessionFactory");

        sessionFactory.close();
    }
}
