package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import service.RegistrationInputFieldService;
import service.validation.ValidationExecutor;
import servlet.auth.helper.CredentialsExtractor;
import servlet.auth.helper.dto.Credential;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebFilter("/registration")
public class RegistrationInputFieldsFilter implements Filter {

    private ValidationExecutor validationExecutor;
    private RegistrationInputFieldService registrationInputFieldService;

    @Override
    public void init(FilterConfig filterConfig) {
        validationExecutor =
                (ValidationExecutor) filterConfig.getServletContext().getAttribute("validationExecutor");
        registrationInputFieldService =
                (RegistrationInputFieldService) filterConfig.getServletContext().getAttribute("registrationInputFieldService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;

        if (req.getMethod().equals("POST")) {
            Credential credential = CredentialsExtractor.extract(req);
            Map<String, List<String>> stringListMap = validationExecutor.executeValidation(credential);


            if (registrationInputFieldService.isEmptyErrorMessage(stringListMap)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                for(var validationErrorMessages:stringListMap.entrySet()) {
                   if (!validationErrorMessages.getValue().isEmpty()) {
                       req.setAttribute(validationErrorMessages.getKey()+"ValidationErrorMessages",
                               validationErrorMessages.getValue()
                       );
                   }
                }

                req.getRequestDispatcher("/registration.jsp").forward(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
