package com.todo.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.*;
import com.todo.model.Task;

public class TaskDAO {
    private static final Logger logger = LogManager.getLogger(TaskDAO.class);

    private String jdbcURL = "jdbc:postgresql://localhost:5432/tododb";
    private String jdbcUsername = "todo_user";
    private String jdbcPassword = "lougrace";

    private Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public List<Task> getAllTasks() {
        List<Task> list = new ArrayList<>();
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tasks");
            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                list.add(task);
            }
            logger.info("Fetched {} tasks from database.", list.size());
        } catch (Exception e) {
            logger.error("Error adding task", e);
            e.printStackTrace();
        }
        return list;
    }

    public void addTask(Task task) {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO tasks (title, description) VALUES (?, ?)");
            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.executeUpdate();
        } catch (Exception e) {
            logger.error("Error adding task", e);
            e.printStackTrace();
        }
    }

    public Task getTask(int id) {
        Task task = new Task();
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM tasks WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                task.setId(rs.getInt("id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }

    public void updateTask(Task task) {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("UPDATE tasks SET title=?, description=? WHERE id=?");
            ps.setString(1, task.getTitle());
            ps.setString(2, task.getDescription());
            ps.setInt(3, task.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            logger.error("Error updating task", e);
            e.printStackTrace();
        }
    }

    public void deleteTask(int id) {
        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM tasks WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.error("Error deleting task", e);
            e.printStackTrace();
        }
    }
}