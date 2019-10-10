package ru.otus.webserver.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.webserver.api.services.UserAthorizeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergei Viacheslaev
 */
public class LoginServlet extends HttpServlet {
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";
    private Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private UserAthorizeService userAthorizeService;
    private TemplateProcessor templateProcessor;


    public LoginServlet(UserAthorizeService userAthorizeService) throws IOException {
        this.userAthorizeService = userAthorizeService;
        this.templateProcessor = new TemplateProcessor();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("resultMssg", "");

        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, pageVariables));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("request params: {}", request.getQueryString());

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (userAthorizeService.authorizeUser(username, password)) {
            authorizationSuccessful(response);
            response.getWriter().printf("Welcome to server %s", username);
        } else {
            authorizationFailed(response);
        }

    }


    private void authorizationSuccessful(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void authorizationFailed(HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("resultMssg", "Authorization failed ! Check your login/password.");

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
