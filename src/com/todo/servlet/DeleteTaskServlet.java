package com.todo.servlet;

import com.todo.dao.TaskDAO;
import javax.servlet.http.*;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteTaskServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DeleteTaskServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        new TaskDAO().deleteTask(id);

        logger.info("Task deleted by user: {}", id);
        response.sendRedirect("index.jsp");
    }
}