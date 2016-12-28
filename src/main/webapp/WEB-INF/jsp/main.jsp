<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@include file="../jspf/header.jspf" %>
<%@include file="../jspf/left_menu.jspf" %>
<jsp:useBean id="drugList" class="com.suglob.pharmacy.entity.DrugList"/>
<section>
    <h2>${param.name}</h2>
    <c:if test="${not empty param.name}">
        <c:set var="drugListCurrent" value="${drugList.takeDrugsByCategory(param.category_id)}"/>
        <c:if test="${not empty drugListCurrent}">
            <table>
                <tr>
                    <th>Наименование</th>
                    <th>Дозировка</th>
                    <th>Производитель</th>
                    <th>Цена</th>
                    <th>Рецепт</th>
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
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <c:if test="${empty drugListCurrent}">
            <h3>Извините, таких лекарств нет!</h3>
        </c:if>
    </c:if>
    <c:if test="${empty param.name}">
        <h3>Выберите лекарства</h3>
    </c:if>
</section>
<%@include file="../jspf/footer.jspf" %>
