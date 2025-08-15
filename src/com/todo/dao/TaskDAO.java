package com.todo.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.*;
import java.io.FileInputStream;
import java.io.IOException;

import com.todo.model.Task;

public class TaskDAO {

    private static final Logger logger = LogManager.getLogger(TaskDAO.class);
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public TaskDAO() {
        try {
            String configPath = System.getProperty("todoapp.config");
            if (configPath == null) {
                throw new IllegalStateException("System property 'todoapp.config' not set");
            }

            Properties props = new Properties();
            try (FileInputStream fis = new FileInputStream(configPath)) {
                props.load(fis);
            }

            this.dbUrl = props.getProperty("db.url");
            this.dbUser = props.getProperty("db.username");
            this.dbPassword = props.getProperty("db.password");

            logger.info("Database config loaded from {}", configPath);

        } catch (IOException e) {
            logger.error("Failed to load DB config", e);
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() throws SQLException {
        try {
            // Load the PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("PostgreSQL JDBC Driver not found", e);
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }

        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
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

            logger.info("Task added: " + task.getTitle());
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

            logger.info("Task fetched: " + id);
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

            logger.info("Task updated: " + task.getId());
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

            logger.info("Task deleted: " + id);
        } catch (Exception e) {
            logger.error("Error deleting task", e);
            e.printStackTrace();
        }
    }
}