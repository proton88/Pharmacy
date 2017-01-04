<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@include file="../jspf/header.jspf" %>
<%@include file="../jspf/left_menu.jspf" %>
<jsp:useBean id="drugList" class="com.suglob.pharmacy.entity.DrugList"/>

<section>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="properties.localization" var="loc"/>
    <c:if test="${error!=null}">
        <fmt:message bundle="${loc}" key="${error}" var="error_message"/>
        <h3>${error_message}</h3>
    </c:if>
    <c:if test="${pageContext.request.session==null or pageContext.request.session.isNew() or
    pageContext.request.session.getAttribute('user')==null}">
        ${pageContext.request.setAttribute("error", "index.error_end_session")}
        <jsp:forward page="index.jsp"/>
    </c:if>
    ${pageContext.request.session.setAttribute("url",pageContext.request.getRequestURL())}
    <c:if test="${not empty param.category_id or not empty param.textSearch}">
        <c:if test="${not empty param.textSearch}">
            ${pageContext.request.session.setAttribute("param",param.textSearch)}
            ${pageContext.request.session.setAttribute("paramName","textSearch")}
            ${pageContext.request.session.removeAttribute("param2")}
            ${pageContext.request.session.removeAttribute("paramName2")}
            <c:set var="drugListCurrent" value="${drugList.takeDrugsBySearch(param.textSearch)}"/>
        </c:if>
        <c:if test="${not empty param.category_id}">
            <c:if test="${param.category_id=='all'}">
                ${pageContext.request.session.setAttribute("param",param.category_id)}
                ${pageContext.request.session.setAttribute("paramName","category_id")}
                ${pageContext.request.session.removeAttribute("param2")}
                ${pageContext.request.session.removeAttribute("paramName2")}
                <c:set var="drugListCurrent" value="${drugList.takeAllDrugs()}"/>
            </c:if>
            <c:if test="${param.category_id!='all'}">
                ${pageContext.request.session.setAttribute("param",param.category_id)}
                ${pageContext.request.session.setAttribute("paramName","category_id")}
                ${pageContext.request.session.setAttribute("param2",param.name)}
                ${pageContext.request.session.setAttribute("paramName2","name")}
                <c:set var="drugListCurrent" value="${drugList.takeDrugsByCategory(param.category_id)}"/>
            </c:if>
        </c:if>
        <c:if test="${not empty drugListCurrent}">
            ${pageContext.request.session.setAttribute("listDrugs",drugListCurrent)}
            <h3>Найдено лекарств: ${drugListCurrent.size()}</h3>
            <table>
                <tr>
                    <th>Наименование</th>
                    <th>Дозировка</th>
                    <th>Производитель</th>
                    <th>Цена</th>
                    <th>Рецепт</th>
                    <th>Кол-во</th>
                </tr>
                <c:forEach var="drug" items="${drugListCurrent}" varStatus="count">
                    <c:if test="${count.count%2==1}">
                        <tr>
                    </c:if>
                    <c:if test="${count.count%2==0}">
                        <tr class="even">
                    </c:if>
                    <td><a href="#">${drug.name}</a></td>
                    <td><a href="#">${drug.dosage}</a></td>
                    <td><a href="#">${drug.country}</a></td>
                    <td>${drug.price}</td>
                    <td>${drug.isRecipe}</td>
                    <td>${drug.count}</td>
                    <td>
                        <form action="Controller" method="post">
                        <input type="hidden" name="command" value="add_order"/>
                        <input type="hidden" name="drug_id" value="${drug.id}"/>
                        <input type="number" name="count" min="1" max="9" value="1">
                        <input type="submit" value="Купить" class="btn">
                        </form>
                    </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty drugListCurrent}">
            <h3>Извините, таких лекарств нет!</h3>
        </c:if>
    </c:if>
    <c:if test="${empty param.category_id and empty param.textSearch}">
        <c:set var="list" value="${listDrugs}"/>
        <c:if test="${not empty list}">
            <h3>Найдено лекарств: ${list.size()}</h3>
            <table>
                <tr>
                    <th>Наименование</th>
                    <th>Дозировка</th>
                    <th>Производитель</th>
                    <th>Цена</th>
                    <th>Рецепт</th>
                    <th>Кол-во</th>
                </tr>
                <c:forEach var="drug" items="${list}" varStatus="count">
                    <c:if test="${count.count%2==1}">
                        <tr>
                    </c:if>
                    <c:if test="${count.count%2==0}">
                        <tr class="even">
                    </c:if>
                    <td><a href="#">${drug.name}</a></td>
                    <td><a href="#">${drug.dosage}</a></td>
                    <td><a href="#">${drug.country}</a></td>
                    <td>${drug.price}</td>
                    <td>${drug.isRecipe}</td>
                    <td>${drug.count}</td>
                    <td>
                        <form action="Controller" method="post">
                            <input type="hidden" name="command" value="add_order"/>
                            <input type="hidden" name="drug_id" value="${drug.id}"/>
                            <input type="number" name="count" min="1" max="9" value="1">
                            <input type="submit" value="Купить" class="btn">
                        </form>
                    </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty list}">
            <h3>Выберите лекарства</h3>
        </c:if>
    </c:if>
</section>
<%@include file="../jspf/footer.jspf" %>
