<%@ page import="org.apache.commons.lang3.StringEscapeUtils" %>
<%@ page import="java.net.URLEncoder" %>
<%--
  User: Edward Samuel
  Date: 20/08/14
  Time: 15:30
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
    <h1>Login</h1>
    <p>Target URL: <a href="#"><%= request.getAttribute("action") %></a></p>
    <form method="post" action="<%= request.getAttribute("action") %>">
        <ul>
            <li>Username: <input type="text" name="username" /></li>
            <li>Password: <input type="password" name="password" /></li>
        </ul>
        <input type="submit" name="action" value="Login" />
    </form>
</body>
</html>
