<%@ page import="org.owasp.esapi.ESAPI" %>
<%--
  Created by IntelliJ IDEA.
  User: Edward Samuel
  Date: 02/09/14
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>

Value Box
<div id="valueBox"></div>

<script>
    var CURRENT_VALUE = '<%= ESAPI.encoder().encodeForJavaScript(request.getParameter("data")) %>';
    document.getElementById("valueBox").innerHTML = CURRENT_VALUE; // INSECURE CODE - DO NOT USE.
</script>

</body>
</html>
