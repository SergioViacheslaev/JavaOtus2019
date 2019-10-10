package ru.otus.webserver.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * @author Sergei Viacheslaev
 */
public class AdminServlet extends HttpServlet {
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private final TemplateProcessor templateProcessor;

    public AdminServlet() throws IOException {
        this.templateProcessor = new TemplateProcessor();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //First check if we have session, if no - then go to "login page".
        if (isSessionAlive(request)) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(templateProcessor.getPage(ADMIN_PAGE_TEMPLATE, Collections.emptyMap()));
            response.setStatus(HttpServletResponse.SC_OK);

        } else {
            response.sendRedirect("/login");
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private boolean isSessionAlive(HttpServletRequest request) {
        return request.getSession().getId() != null;
    }
}
