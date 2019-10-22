package ru.otus.utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Sergei Viacheslaev
 */

@WebServlet("/testDbServlet")
public class TestDbServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // setup connection variables
        String user = "ss";
//        String user = "sa";
//        String pass = "sa";

        String jdbcUrl = "jdbc:h2:tcp://localhost/~/test";
//        String jdbcUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        String driver = "org.h2.Driver";

        // get connection to database
        try {
            PrintWriter out = response.getWriter();

            out.println("Connecting to database: " + jdbcUrl);

            Class.forName(driver);

            Connection myConn = DriverManager.getConnection(jdbcUrl, user, "");

            out.println("SUCCESS!!!");

            myConn.close();

        } catch (Exception exc) {
            exc.printStackTrace();
            throw new ServletException(exc);
        }


    }

}
