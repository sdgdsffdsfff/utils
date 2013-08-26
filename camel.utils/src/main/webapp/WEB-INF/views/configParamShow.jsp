<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>configParam Show</title>
</head>
<body>	
		${model.test}
		<table border="1px solid">
			<tr>
				<td>jdbc.driverClassName</td>
				<td><c:out value='${conn.driverClassName}'/></td>
				<td>jdbc.url</td>
				<td><c:out value='${conn.url}'/></td>
			</tr>
			<tr>
				<td>jdbc.username</td>
				<td><c:out value='${conn.username}'/></td>
				<td>jdbc.password</td>
				<td><c:out value='${conn.password}'/></td>
			</tr>
		</table>
		<%
		com.utils.mvc.controller.JDBCConnection jdbcConnection = (com.utils.mvc.controller.JDBCConnection)request.getAttribute("conn");
		out.println(jdbcConnection.getDriverClassName());
		out.println(jdbcConnection.getPassword());
		%>
</body>
</html>