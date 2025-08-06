<%@ page import="com.todo.dao.TaskDAO, com.todo.model.Task" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    Task task = new TaskDAO().getTask(id);
%>
<html>
<head><title>Edit Task</title></head>
<body>
    <h2>Edit Task</h2>
    <form action="UpdateTask" method="post">
        <input type="hidden" name="id" value="<%= task.getId() %>"/>
        Title: <input type="text" name="title" value="<%= task.getTitle() %>" /><br/>
        Description: <input type="text" name="description" value="<%= task.getDescription() %>" /><br/>
        <input type="submit" value="Update Task" />
    </form>
</body>
</html>