package ru.otus.webserver.servlets;

import ru.otus.webserver.api.model.User;
import ru.otus.webserver.api.services.DBServiceCachedUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author Sergei Viacheslaev
 */
public class AdminServlet extends HttpServlet {
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private static final String CREATED_USER_VAR_NAME = "createdUser";
    private static final String USERS_LIST_VAR_NAME = "usersList";

    private final TemplateProcessor templateProcessor;
    private DBServiceCachedUser serviceUser;


    public AdminServlet(DBServiceCachedUser serviceUser) throws IOException {
        this.serviceUser = serviceUser;
        this.templateProcessor = new TemplateProcessor();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(USERS_LIST_VAR_NAME, Collections.emptyList());
        pageVariables.put(CREATED_USER_VAR_NAME, Collections.emptyList());


        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Если в запросе была нажата кнопка "Создать нового пользователя"
        if (request.getParameter("createUser") != null) {
            String userName = request.getParameter("userName");
            int userAge = Integer.parseInt(request.getParameter("userAge"));

            User savedUser = new User(userName, userAge);
            serviceUser.saveUser(savedUser);

            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put(CREATED_USER_VAR_NAME, Collections.singletonList(savedUser));
            pageVariables.put(USERS_LIST_VAR_NAME, Collections.emptyList());

            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, pageVariables));
            response.setStatus(HttpServletResponse.SC_OK);

        } else if (request.getParameter("getUsersList") != null) {

            Map<String, Object> pageVariables = new HashMap<>();
            pageVariables.put(CREATED_USER_VAR_NAME, Collections.emptyList());
            pageVariables.put(USERS_LIST_VAR_NAME, serviceUser.getUsersList());

            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, pageVariables));
            response.setStatus(HttpServletResponse.SC_OK);
        } else if (request.getParameter("exitAdminPanel") != null) {
            request.getSession().invalidate();
            response.sendRedirect("/login");
        }

    }
}
