<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <c:if test="${msg!=null}">
        <fmt:message bundle="${loc}" key="${msg}" var="message"/>
        <h3>${message}</h3>
        <c:set var="msg" value="${null}"/>
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
                    <c:if test="${user.type!='client'}">
                        <th>id</th>
                    </c:if>
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
                    <c:if test="${user.type!='client'}">
                        <td>${drug.id}</td>
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
                    <c:if test="${user.type=='client'}">
                        <td>
                            <form id="order" action="Controller" method="post">
                                <input type="hidden" name="command" value="add_order"/>
                                <input type="hidden" name="drugId" value="${drug.id}"/>
                                <input type="number" name="count" min="1" max="9" value="1" required>
                                <input type="submit" value="+" class="btn">
                            </form>
                        </td>
                    </c:if>
                    <c:if test="${user.type=='pharmacist'}">
                        <td>
                            <form action="Controller" method="post">
                                <input type="hidden" name="command" value="add_quantity_drug"/>
                                <input type="hidden" name="drugId" value="${drug.id}"/>
                                <input type="hidden" name="currentQuantity" value="${drug.count}"/>
                                <input type="number" name="quantity" min="-999" max="999" required id="quantity">
                                <input type="submit" value="+" class="btn">
                            </form>
                        </td>
                    </c:if>
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
    </c:if>
    <c:if test="${user.type=='client'}">
        <c:set var="doctorList" value="${doctor.createDoctorsList()}"/>
        <br>
        <div>
            <form action="Controller" method="post" class="buttons">
                <input type="hidden" name="command" value="order_recipe"/>
                <input type="text" name="drugName" placeholder="название лекарства:" size="15" required>
                <select name="doctorSurname" required>
                    <option disabled>Выберите врача</option>
                    <c:forEach var="doc" items="${doctorList}">
                        <option>${doc.surname}</option>
                    </c:forEach>
                </select>
                <input type="submit" value="Запросить рецепт" class="btn">
            </form>
            <form action="Controller" method="post" class="buttons">
                <input type="hidden" name="command" value="order_extend_recipe"/>
                <input type="text" name="codeDrug" placeholder="код рецепта:" size="8" required>
                <input type="submit" value="Продлить рецепт" class="btn">
            </form>
        </div>
    </c:if>
    <br>
    <c:if test="${user.type=='doctor'}">
        <form action="Controller" method="post" class="buttons">
            <input type="hidden" name="command" value="check_recipe"/>
            <input type="hidden" name="userId" value="${user.id}"/>
            <input type="submit" value="Проверить запросы на рецепты" class="btn">
        </form>
        <c:if test="${drugsNameOrderRecipe!=null and not empty drugsNameOrderRecipe}">
            <h3>Запросы на заказ рецепта</h3>
            <div class="blockRecipe">
                <table class="recipe">
                    <tr>
                        <th>Препарат</th>
                    </tr>
                    <c:forEach var="orderRecipe" items="${drugsNameOrderRecipe}">
                        <tr>
                            <td>${orderRecipe}</td>
                        </tr>
                    </c:forEach>
                </table>
                <table class="recipe">
                    <tr>
                        <th>id клиента</th>
                        <th>Фамилия</th>
                        <th>Имя</th>
                        <th>Отчество</th>
                        <th>Email</th>
                    </tr>
                    <c:forEach var="clientRecipe" items="${clientsOrderRecipe}">
                        <tr>
                            <td>${clientRecipe.clientsId}</td>
                            <td>${clientRecipe.surname}</td>
                            <td>${clientRecipe.name}</td>
                            <td>${clientRecipe.patronymic}</td>
                            <td>${clientRecipe.email}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <br>
            <form action="Controller" method="post" class="buttons" onsubmit="return validateCancelRecipeForm()"
                  name="cancelRecipeForm">
                <input type="hidden" name="command" value="cancel_recipe"/>
                <input type="hidden" name="userId" value="${user.id}"/>
                <input type="text" placeholder="название лекарства" name="drugName">
                <input type="text" placeholder="id клиента" name="clientId">
                <input type="submit" value="Отклонить" class="btn">
            </form>
            <form action="Controller" method="post" class="buttons" onsubmit="return validateAssignRecipeForm()"
                  name="assignRecipeForm">
                <input type="hidden" name="command" value="assign_recipe"/>
                <input type="hidden" name="userId" value="${user.id}"/>
                <input type="text" placeholder="id лекарства" name="drugId" size="8">
                <input type="text" placeholder="количество" name="quantity" size="8">
                <input type="text" placeholder="срок(дней)" name="period" size="8">
                <input type="text" placeholder="id клиента" name="clientId" size="8">
                <input type="text" placeholder="код(6)" name="code" size="7">
                <input type="submit" value="Назначить" class="btn">
            </form>
            <span class="err" id="err_fields">${fill_all_fields} </span>
            <span class="err" id="err_format_id_client">${id_client_bad}</span>
            <span class="err" id="err_format_id_drug">${id_drug_bad}</span>
            <span class="err" id="err_quantity_bad">${quantity_bad}</span>
            <span class="err" id="err_period_bad">${period_bad}</span>
            <span class="err" id="err_code_bad">${code_bad}</span>

        </c:if>

        <c:if test="${drugsNameExtendRecipe!=null and not empty drugsNameExtendRecipe}">
            <h3>Запросы на продление рецепта</h3>
            <table class="recipe">
                <tr>
                    <th>Препарат</th>
                </tr>
                <c:forEach var="extendRecipe" items="${drugsNameExtendRecipe}">
                    <tr>
                        <td>${extendRecipe}</td>
                    </tr>
                </c:forEach>
            </table>
            <table class="recipe">
                <tr>
                    <th>Фамилия</th>
                    <th>Имя</th>
                    <th>Отчество</th>
                    <th>Email</th>
                </tr>
                <c:forEach var="clientRecipe" items="${clientsExtendRecipe}" varStatus="number">
                    <tr>
                        <td>${clientRecipe.surname}</td>
                        <td>${clientRecipe.name}</td>
                        <td>${clientRecipe.patronymic}</td>
                        <td>${clientRecipe.email}</td>
                        <td>
                            <form action="Controller" method="post">
                                <input type="hidden" name="command" value="cancel_extend_recipe"/>
                                <input type="hidden" name="position" value="${number.count}"/>
                                <input type="submit" value="-" class="btn">
                            </form>
                        </td>
                        <td>
                            <form action="Controller" method="post" onsubmit="return validateExtendRecipeForm()"
                                  name="extendRecipeForm">
                                <input type="hidden" name="command" value="extend_recipe"/>
                                <input type="hidden" name="position" value="${number.count}"/>
                                <input type="text" placeholder="дней" name="period" size="3">
                                <input type="submit" value="+" class="btn">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <br>
            <span class="err" id="err_period_field">${fill_field}</span>
            <span class="err" id="err_period_bad">${period_bad}</span>
        </c:if>
    </c:if>

    <c:if test="${user.type=='pharmacist'}">
        <form action="Controller" method="post" class="buttons" onsubmit="return validateAddDrugForm()"
              name="addDrugForm">
            <input type="hidden" name="command" value="add_drug"/>
            <input type="text" placeholder="название*" name="drugName" size="7">
            <input type="text" placeholder="дозировка" name="dosage" size="10">
            <input type="text" placeholder="страна*" name="country" size="10">
            <input type="text" placeholder="цена*" name="priceDrug" size="4">
            <input type="text" placeholder="кол-во*" name="quantity" size="3">
            <select name="recipe">
                <option>Y</option>
                <option>N</option>
            </select>
            <select name="drugCategories" multiple>
                <option disabled>Категория</option>
                <c:forEach var="category" items="${drugCategories}">
                    <option>${category.name}</option>
                </c:forEach>
            </select>
            <input type="submit" value="Добавить лекарство" class="btn">
        </form>
        <form action="Controller" method="post" class="buttons">
            <input type="hidden" name="command" value="change_price_drug"/>
            <input type="text" placeholder="id лекарства" name="drugId" size="9">
            <input type="text" placeholder="новая цена" name="priceDrug" size="7">
            <input type="submit" value="Изменить цену" class="btn">
        </form>
        <form action="Controller" method="post" class="buttons">
            <input type="hidden" name="command" value="delete_drug"/>
            <input type="text" placeholder="id лекарства" name="drugId" size="9">
            <input type="submit" value="Удалить лекарство" class="btn">
        </form>
        <form action="Controller" method="post" class="buttons">
            <input type="hidden" name="command" value="add_drug_category"/>
            <input type="text" placeholder="название категории" name="drugCategory" size="14">
            <input type="submit" value="Добавить категорию" class="btn">
        </form>
        <form action="Controller" method="post" class="buttons">
            <input type="hidden" name="command" value="delete_drug_category"/>
            <input type="text" placeholder="название категории" name="drugCategory" size="14">
            <input type="submit" value="Удалить категорию" class="btn">
        </form>
        <span class="err" id="err_fields">${fill_all_fields} </span>
        <span class="err" id="err_bad_country">${country_bad}</span>
        <span class="err" id="err_bad_price">${price_bad}</span>
        <span class="err" id="err_bad_quantity">${quantity_wrong}</span>
    </c:if>

</section>
<%@include file="../jspf/footer.jspf" %>
