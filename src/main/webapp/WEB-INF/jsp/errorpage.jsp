<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Ошибки</title>
</head>
<body>
	<fmt:setLocale value="${sessionScope.locale }"/>
	<fmt:setBundle basename="resources.localization" var="loc"/>
	<c:if test="${errorpage!=null}">
		<fmt:message bundle="${loc}" key="${errorpage}" var="message"/>
		${message}
	</c:if>
	<c:if test="${errorpage==null}">
	<br> Request from ${pageContext.errorData.requestURI} is failed
	<br /> Servlet name or type: ${pageContext.errorData.servletName}
	<br /> Status code: ${pageContext.errorData.statusCode}
	<br /> Exception: ${pageContext.errorData.throwable} Message from
	exception: ${pageContext.exception.message}
	</c:if>
	<br>
	<a href="index.jsp">Go to login page</a>
</body>
</html>