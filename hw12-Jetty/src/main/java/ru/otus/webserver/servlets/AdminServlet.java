package ru.otus.webserver.servlets;

import ru.otus.webserver.api.model.User;
import ru.otus.webserver.api.services.DBServiceCachedUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Sergei Viacheslaev
 */
public class AdminServlet extends HttpServlet {
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private final TemplateProcessor templateProcessor;
    private DBServiceCachedUser dbServiceCachedUser;

    public AdminServlet(DBServiceCachedUser dbServiceCachedUser) throws IOException {
        this.dbServiceCachedUser = dbServiceCachedUser;
        this.templateProcessor = new TemplateProcessor();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<User> loadedUser = dbServiceCachedUser.getUser(1L);
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("loadedUser", loadedUser.toString());

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        int userAge = Integer.parseInt(request.getParameter("userAge"));

        dbServiceCachedUser.saveUser(new User(userName, userAge));
    }


}
