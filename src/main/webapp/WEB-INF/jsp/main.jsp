﻿<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@include file="../jspf/header.jspf" %>
<%@include file="../jspf/left_menu.jspf" %>
<jsp:useBean id="drugList" class="com.suglob.pharmacy.entity.DrugList"/>
<jsp:useBean id="doctor" class="com.suglob.pharmacy.entity.Doctor"/>
<div class="hide show" id="block">
    <c:if test="${empty orderList}">
        <h2>Корзина пуста</h2>
    </c:if>
    <c:if test="${not empty orderList}">
        <p>Ваша корзина:</p>
        <table>
            <tr>
                <th>Наименование</th>
                <th>Дозировка</th>
                <th>Производитель</th>
                <th>Цена</th>
                <th>Рецепт</th>
                <th>Кол-во</th>
            </tr>
            <c:forEach var="drug" items="${orderList}">
                <tr>
                    <td><a href="#">${drug.name}</a></td>
                    <td><a href="#">${drug.dosage}</a></td>
                    <td><a href="#">${drug.country}</a></td>
                    <td>${drug.price}</td>
                    <td>${drug.isRecipe}</td>
                    <td>${drug.count}</td>
                </tr>
            </c:forEach>
        </table>
        <h4>Итоговая цена:${orderPrice}</h4>
        <form action="Controller" method="post">
            <input type="hidden" name="command" value="pay_order"/>
            <input type="submit" value="Оплатить" class="btn">
        </form>
        <form action="Controller" method="post">
            <input type="hidden" name="command" value="cancel_order"/>
            <input type="submit" value="Отменить" class="btn">
        </form>
    </c:if>
</div>

</div>
<section>
    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="properties.localization" var="loc"/>
    <c:if test="${payment_msg=='payment.ok'}">
        <fmt:message bundle="${loc}" key="${payment_msg}" var="payment_message"/>
        <h3>${payment_message}</h3>
        <c:set var="payment_msg" value="${null}"/>
    </c:if>
    <c:if test="${payment_msg!='payment.ok'}">
        <h3>${payment_msg}</h3>
    </c:if>
    <c:if test="${orderRecipe_msg!=null}">
        <fmt:message bundle="${loc}" key="${orderRecipe_msg}" var="order_recipe_message"/>
        <h3>${order_recipe_message}</h3>
        <c:set var="orderRecipe_msg" value="${null}"/>
    </c:if>
    <c:if test="${extendRecipe_msg!=null}">
        <fmt:message bundle="${loc}" key="${extendRecipe_msg}" var="extend_recipe_message"/>
        <h3>${extend_recipe_message}</h3>
        <c:set var="extendRecipe_msg" value="${null}"/>
    </c:if>
    <c:if test="${error!=null}">
        <fmt:message bundle="${loc}" key="${error}" var="error_message"/>
        <h3>${error_message}</h3>
        <c:set var="error" value="${null}"/>
    </c:if>
    <c:if test="${pageContext.request.session==null or pageContext.request.session.isNew() or
    pageContext.request.session.getAttribute('user')==null}">
        ${pageContext.request.setAttribute("error", "index.error_end_session")}
        <jsp:forward page="index.jsp"/>
    </c:if>
    <c:set var="page" value="1"/>
    <c:set var="recordsPerPage" value="5"/>
    <c:if test="${param.page!=null}">
        <c:set var="page" value="${param.page}"/>
    </c:if>
    <c:if test="${param.page==null}">
        ${pageContext.request.session.setAttribute("urlParamsPagination",pageContext.request.getQueryString())}
    </c:if>
    ${pageContext.request.session.setAttribute("url",pageContext.request.getRequestURL())}
    ${pageContext.request.session.setAttribute("urlParams",pageContext.request.getQueryString())}
    <c:if test="${not empty param.category_id or not empty param.textSearch}">
        <c:if test="${not empty param.textSearch}">
            <c:set var="drugListCurrent"
                   value="${drugList.takeDrugsBySearch(param.textSearch,(page-1)*recordsPerPage,recordsPerPage)}"/>
        </c:if>
        <c:if test="${not empty param.category_id}">
            <c:if test="${param.category_id=='all'}">
                <c:set var="drugListCurrent" value="${drugList.takeAllDrugs((page-1)*recordsPerPage,recordsPerPage)}"/>
            </c:if>
            <c:if test="${param.category_id!='all'}">
                <c:set var="drugListCurrent"
                       value="${drugList.takeDrugsByCategory(param.category_id,(page-1)*recordsPerPage,recordsPerPage)}"/>
            </c:if>
        </c:if>
        <c:set var="countPages" value="${drugList.countPages}"/>
        <c:if test="${not empty drugListCurrent}">
            ${pageContext.request.session.setAttribute("listDrugs",drugListCurrent)}
            <h3>Найдено лекарств: ${drugList.countAllRecords}</h3>
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
                    <c:forEach var="drugOrder" items="${orderList}">
                        <c:if test="${drugOrder.id==drug.id}">
                            <c:set var="drugCount" value="${drug.count-drugOrder.count}"/>
                        </c:if>
                    </c:forEach>
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
                    <c:if test="${drug.isRecipe=='Y'}">
                        <td><input type="text" name="code" placeholder="код:" size="4" form="order"></td>
                    </c:if>
                    <c:if test="${drug.isRecipe!='Y'}">
                        <td>${drug.isRecipe}</td>
                    </c:if>
                    <c:if test="${drugCount!=null}">
                        <td>${drugCount}</td>
                    </c:if>
                    <c:if test="${drugCount==null}">
                        <td>${drug.count}</td>
                    </c:if>
                    <c:set var="drugCount" value="${null}"/>
                    <td>
                        <form id="order" action="Controller" method="post">
                            <input type="hidden" name="command" value="add_order"/>
                            <input type="hidden" name="drug_id" value="${drug.id}"/>
                            <input type="number" name="count" min="1" max="9" value="1">
                            <input type="submit" value="+" class="btn">
                        </form>
                    </td>
                    </tr>

                </c:forEach>
            </table>

            <c:if test="${countPages!=1}">
                <c:if test="${page != 1}">
                    <td><a href="main?page=${page - 1}&${urlParamsPagination}">Previous</a></td>
                </c:if>


                <c:forEach begin="1" end="${countPages}" var="i">
                    <c:choose>
                        <c:when test="${page == i}">
                            ${i}
                        </c:when>
                        <c:otherwise>
                            <a href="main?page=${i}&${urlParamsPagination}">${i}</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>


                <c:if test="${page < countPages}">
                    <td><a href="main?page=${page + 1}&${urlParamsPagination}">Next</a></td>
                </c:if>
            </c:if>
        </c:if>


        <c:if test="${empty drugListCurrent}">
            <h3>Извините, таких лекарств нет!</h3>
        </c:if>
    </c:if>
    <c:if test="${empty param.category_id and empty param.textSearch}">
        <h3>Выберите лекарства</h3>
        <p>&nbsp</p>
        <p>&nbsp</p>
        <p>&nbsp</p>

    </c:if>
    <c:set var="doctorList" value="${doctor.createDoctorsList()}"/>
    <br>
    <div>
    <form action="Controller" method="post" class="buttons">
        <input type="hidden" name="command" value="order_recipe"/>
        <input type="text" name="drugName" placeholder="название лекарства:" size="15">
        <select name="doctorSurname">
            <option disabled selected>Выберите врача</option>
            <c:forEach var="doc" items="${doctorList}">
                <option>${doc.surname}</option>
            </c:forEach>
        </select>
        <input type="submit" value="Запросить рецепт" class="btn">
    </form>
    <form action="Controller" method="post" class="buttons">
        <input type="hidden" name="command" value="extend_recipe"/>
        <input type="text" name="codeDrug" placeholder="код рецепта:" size="8">
        <input type="submit" value="Продлить рецепт" class="btn">
    </form>
    </div>
</section>
<%@include file="../jspf/footer.jspf" %>
