<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Онлайн Аптека::Вход</title>
        <link href="css/style_index.css" rel="stylesheet" type="text/css">
        <link href="css/style_common.css" rel="stylesheet" type="text/css">
    </head>

    <body>
    ${pageContext.request.session.setAttribute("url",pageContext.request.getRequestURL())}
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
                <p>&nbsp;</p>

            </div>

            <div class="login_div">
                <p class="title">Для входа введите свои данные:</p>
                <form class="login_form" action="Controller" method="POST">
                    <input type="hidden" name="command" value="logination">
                    ${login}
                    <input type="text" name="login" value=""><br/>
                    ${password}
                    <input type="password" name="password" value=""><br/>
                    <a href="reg">регистрация</a>(только для клиентов)<br>
                    <input type="submit" value="${but_login}" class="btn"/>
                </form>

            </div>

            <div class="footer">
                Разработчик: Суглоб Андрей Александрович, 2016 г
            </div>
        </div>


    </body>
</html>
