<%@ page import="java.util.*, com.todo.dao.TaskDAO, com.todo.model.Task" %>
<html>
<head><title>To-Do List</title></head>
<body>
    <h2>To-Do List</h2>
    <form action="AddTask" method="post">
        Title: <input type="text" name="title" />
        Description: <input type="text" name="description" />
        <input type="submit" value="Add Task" />
    </form>
    <hr/>
    <table border="1">
        <tr><th>Title</th><th>Description</th><th>Actions</th></tr>
        <%
            TaskDAO dao = new TaskDAO();
            for (Task task : dao.getAllTasks()) {
        %>
        <tr>
            <td><%= task.getTitle() %></td>
            <td><%= task.getDescription() %></td>
            <td>
                <a href="edit.jsp?id=<%= task.getId() %>">Edit</a> |
                <a href="DeleteTask?id=<%= task.getId() %>">Delete</a>
            </td>
        </tr>
        <% } %>
    </table>
</body>
</html>