package ru.otus.webserver.servlets;

import ru.otus.webserver.api.services.UserAthorizeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Sergei Viacheslaev
 */
public class LoginServlet extends HttpServlet {
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";
    private static final String RESULT_VARIABLE_NAME = "resultMssg";
    private static final String RESULT_VARIABLE_VALUE = "Authorization failed ! Check your login/password.";


    private UserAthorizeService userAthorizeService;
    private TemplateProcessor templateProcessor;


    public LoginServlet(UserAthorizeService userAthorizeService) throws IOException {
        this.userAthorizeService = userAthorizeService;
        this.templateProcessor = new TemplateProcessor();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("resultMssg", "");

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //if loging/password correct then open session(if not exist) and redirect to Admin page
        if (userAthorizeService.authorizeUser(username, password)) {
            authorizationSuccessful(request, response);
        } else {
            authorizationFailed(response);
        }

    }


    private void authorizationSuccessful(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession();
        response.sendRedirect("/admin");
    }

    private void authorizationFailed(HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(RESULT_VARIABLE_NAME, RESULT_VARIABLE_VALUE);

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, pageVariables));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
