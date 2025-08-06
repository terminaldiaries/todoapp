package com.todo.servlet;

import com.todo.dao.TaskDAO;
import com.todo.model.Task;
import javax.servlet.http.*;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UpdateTaskServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UpdateTaskServlet.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String desc = request.getParameter("description");

        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setDescription(desc);

        new TaskDAO().updateTask(task);

        logger.info("Task updated by user: {}", id);
        response.sendRedirect("index.jsp");
    }
}