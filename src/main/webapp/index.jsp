<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="user" class="com.suglob.pharmacy.entity.User"/>
<!DOCTYPE html>


<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Онлайн Аптека::Вход</title>
    <link href="css/index.css" rel="stylesheet" type="text/css">
</head>

<body>
${pageContext.request.session.setAttribute("url",pageContext.request.getRequestURL())}
${pageContext.request.session.setAttribute("user",user)}
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="properties.localization" var="loc"/>
<fmt:message bundle="${loc}" key="localization.ru_button" var="ru_button"/>
<fmt:message bundle="${loc}" key="localization.en_button" var="en_button"/>
<fmt:message bundle="${loc}" key="index.p1" var="p1"/>
<fmt:message bundle="${loc}" key="index.p2" var="p2"/>
<fmt:message bundle="${loc}" key="index.p3" var="p3"/>
<fmt:message bundle="${loc}" key="index.p4" var="p4"/>
<fmt:message bundle="${loc}" key="index.login" var="login"/>
<fmt:message bundle="${loc}" key="index.password" var="password"/>
<fmt:message bundle="${loc}" key="index.but_login" var="but_login"/>
<fmt:message bundle="${loc}" key="reg.input_login" var="login_reg"/>
<fmt:message bundle="${loc}" key="reg.input_password" var="password_reg"/>
<fmt:message bundle="${loc}" key="reg.input_password2" var="password2"/>
<fmt:message bundle="${loc}" key="reg.input_name" var="name"/>
<fmt:message bundle="${loc}" key="reg.input_surname" var="surname"/>
<fmt:message bundle="${loc}" key="reg.input_patronymic" var="patronymic"/>
<fmt:message bundle="${loc}" key="reg.input_adress" var="adress"/>
<fmt:message bundle="${loc}" key="reg.input_passport" var="passport"/>
<fmt:message bundle="${loc}" key="reg.button_reg" var="but_reg"/>
<c:if test="${error!=null}">
    <fmt:message bundle="${loc}" key="${error}" var="error_message"/>
</c:if>
<div class="main">
    ${error_message}
    <form action="Controller" method="post">
        <input type="hidden" name="command" value="locale">
        <input type="hidden" name="locale" value="ru">
        <input type="submit" class="sendsubmitru" value="${ru_button}">
    </form>
    <form action="Controller" method="post">
        <input type="hidden" name="command" value="locale">
        <input type="hidden" name="locale" value="en">
        <input type="submit" class="sendsubmiten" value="${en_button}">
    </form>

    <div class="content">
        <p class="title"><span class="text"><img src="images/pharmacy-logo.png" width="77" height="77"></span></p>
        <p class="title">${p1}</p>
        <p class="text">${p2}</p>
        <p class="text">${p3}</p>
        <p class="text">${p4}<a href="mailto:andreysuglob@gmail.com">andreysuglob@gmail.com</a></p>

    </div>

    <div class="login_div">
        <p class="title">Для входа введите свои данные:</p>
        <form class="login_form" action="Controller" method="POST">
            <input type="hidden" name="command" value="logination">
            ${login}
            <input type="text" name="login" value=""><br/>
            ${password}
            <input type="password" name="password" value=""><br/>
            <a href="#" id="link">регистрация(только для клиентов)</a><br>
            <input type="submit" value="${but_login}" class="btn"/>
        </form>

    </div>
    <div class="hide show" id="block">
        <form action="Controller" method="post">
            <input type="hidden" name="command" value="registration">
            ${login_reg}<br/>
            <input type="text" name="login_reg"><br/>
            ${password_reg}<br/>
            <input type="password" name="password_reg"><br/>
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
            Email*:<br/>
            <input type="text" name="email"><br/>
            <input type="submit" value="${but_reg}" class="btn"/>
        </form>
    </div>
    <div class="footer">
        Разработчик: Суглоб Андрей Александрович, 2016 г
    </div>
</div>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="bouncebox_plugin/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="bouncebox_plugin/jquery.bouncebox.1.0.js"></script>
<script type="text/javascript" src="js\script.js"></script>
</body>
</html>
