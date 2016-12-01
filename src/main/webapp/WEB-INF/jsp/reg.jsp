<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Register</title>
</head>
<body>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.localization" var="loc"/>
<fmt:message bundle="${loc}" key="reg.input_login" var="login"/>
<fmt:message bundle="${loc}" key="reg.input_password" var="password"/>
<fmt:message bundle="${loc}" key="reg.input_password2" var="password2"/>
<fmt:message bundle="${loc}" key="reg.input_name" var="name"/>
<fmt:message bundle="${loc}" key="reg.input_surname" var="surname"/>
<fmt:message bundle="${loc}" key="reg.input_patronymic" var="patronymic"/>
<fmt:message bundle="${loc}" key="reg.input_adress" var="adress"/>
<fmt:message bundle="${loc}" key="reg.input_passport" var="passport"/>
<fmt:message bundle="${loc}" key="reg.button_reg" var="but_reg"/>
<c:if test="${error!=null}">
    <fmt:message bundle="${loc}" key="${error}" var="error_loc"/>
    <c:out value="${error_loc}"/>
</c:if>
<form action="Controller" method="post">
    <input type="hidden" name="command" value="registration">
    ${login}<br/>
    <input type="text" name="login"><br/>
    ${password}<br/>
    <input type="password" name="password"><br/>
    ${password2}<br/>
    <input type="password" name="passwordRepeat"><br/>
    ${name}<br/>
    <input type="text" name="name"><br/>
    ${surname}<br/>
    <input type="text" name="surname"><br/>
    ${patronymic}<br/>
    <input type="text" name="patronymic"><br/>
    ${adress}<br/>
    <input type="text" name="adress"><br/>
    ${passport}<br/>
    <input type="text" name="passportId"><br/>
    <input type="submit" value="${but_reg}"/>
</form>
</body>
</html>