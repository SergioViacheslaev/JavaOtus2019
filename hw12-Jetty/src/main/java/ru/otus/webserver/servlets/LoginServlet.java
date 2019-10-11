package ru.otus.webserver.servlets;

import ru.otus.webserver.api.services.UserAuthenticationService;

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
    private static final String RESULT_VARIABLE_NAME = "resultMssg";
    private static final String RESULT_VARIABLE_VALUE = "Authorization failed ! Check your login/password.";


    private UserAuthenticationService userService;
    private TemplateProcessor templateProcessor;


    public LoginServlet(UserAuthenticationService userAuthenticationService) throws IOException {
        this.userService = userAuthenticationService;
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
        if (userService.isUserLoginPasswordCorrect(username, password)) {
            request.getSession();
            response.sendRedirect("/admin");
        } else {
            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put(RESULT_VARIABLE_NAME, RESULT_VARIABLE_VALUE);

            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, pageVariables));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }


}
