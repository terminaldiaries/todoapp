package com.todo.servlet;

import com.todo.dao.TaskDAO;
import com.todo.model.Task;
import javax.servlet.http.*;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddTaskServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(AddTaskServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String title = request.getParameter("title");
        String desc = request.getParameter("description");

        Task task = new Task();
        task.setTitle(title);
        task.setDescription(desc);

        new TaskDAO().addTask(task);
        logger.info("New task added by user: {}", title);
        response.sendRedirect("index.jsp");
    }
}