<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="user" class="com.suglob.pharmacy.entity.User"/>

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
<fmt:message bundle="${loc}" key="fill_field" var="fill_field"/>
<fmt:message bundle="${loc}" key="bad_login" var="bad_login"/>
<fmt:message bundle="${loc}" key="password_not_equals" var="password_not_equals"/>
<fmt:message bundle="${loc}" key="bad_password" var="bad_password"/>
<fmt:message bundle="${loc}" key="bad_mail" var="bad_mail"/>
<fmt:message bundle="${loc}" key="bad_passport" var="bad_passport"/>
<fmt:message bundle="${loc}" key="fill_all_fields" var="fill_all_fields"/>
<fmt:message bundle="${loc}" key="index.title" var="title"/>
<fmt:message bundle="${loc}" key="index.form_title" var="form_title"/>
<fmt:message bundle="${loc}" key="index.reg" var="reg"/>
<fmt:message bundle="${loc}" key="index.author" var="author"/>
<!DOCTYPE html>


<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${title}</title>
    <link href="css/index.css" rel="stylesheet" type="text/css">
</head>

<body>
${pageContext.request.session.setAttribute("url",pageContext.request.getRequestURL())}
${pageContext.request.session.setAttribute("user",user)}

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
        <p class="title">${form_title}</p>
        <form class="login_form" action="Controller" method="POST" onsubmit="return validateLoginForm()"
              name="loginForm">
            <input type="hidden" name="command" value="logination">
            ${login}
            <input type="text" name="login" value="${cookie.login.value}"><br/>
            ${password}
            <input type="password" name="password" value="${cookie.password.value}"><br/>
            <a href="#" id="link">${reg}</a><br>
            <input type="submit" value="${but_login}" class="btn"/>
        </form>
        <span class="err" id="err_fields">${fill_all_fields} </span>
        <span class="err" id="err_login">${bad_login}</span>
        <span class="err" id="err_password">${bad_password}</span>
    </div>
    <div class="hide show" id="block">
        <form action="Controller" method="post" onsubmit="return validateRegisterForm()" name="registerForm">
            <input type="hidden" name="command" value="registration">

            ${login_reg}<br/>
            <input type="text" name="login_reg">
            <br><span class="err" id="err_uname_field">${fill_field}</span>
            <span class="err" id="err_uname_login">${bad_login}</span><br>

            ${password_reg}<br/>
            <input type="password" name="password_reg">
            <br><span class="err" id="err_psw_empty">${fill_field}</span>
            <span class="err" id="err_psw_bad">${bad_password}</span>
            <span class="err" id="err_psw_not_equals">${password_not_equals}</span><br>

            ${password2}<br/>
            <input type="password" name="passwordRepeat"><br/>
            <span class="err" id="err_psw2_empty">${fill_field}</span>
            <span class="err" id="err_psw2_not_equals">${password_not_equals}</span><br>

            ${name}<br/>
            <input type="text" name="name"><br/>
            <span class="err" id="err_name_empty">${fill_field}</span><br>

            ${surname}<br/>
            <input type="text" name="surname"><br/>
            <span class="err" id="err_surname_empty">${fill_field}</span><br>

            ${patronymic}<br/>
            <input type="text" name="patronymic"><br/><br>

            ${adress}<br/>
            <input type="text" name="adress"><br/>
            <span class="err" id="err_adress_empty">${fill_field}</span><br>

            ${passport}<br/>
            <input type="text" name="passportId"><br/>
            <span class="err" id="err_passport_empty">${fill_field}</span>
            <span class="err" id="err_passport_bad">${bad_passport}</span><br>

            Email*:<br/>
            <input type="text" name="email"><br/>
            <span class="err" id="err_mail_empty">${fill_field}</span>
            <span class="err" id="err_mail_bad">${bad_mail}</span><br>

            <input type="submit" value="${but_reg}" class="btn"/>
        </form>
    </div>
    <div class="footer">
        ${author}
    </div>
</div>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script type="text/javascript" src="bouncebox_plugin/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="bouncebox_plugin/jquery.bouncebox.1.0.js"></script>
<script type="text/javascript" src="js\script.js"></script>
<script type="text/javascript" src="js\valid.js"></script>
</body>
</html>
