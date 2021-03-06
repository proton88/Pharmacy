﻿<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@include file="../jspf/header.jspf" %>
<%@include file="../jspf/left_menu.jspf" %>
<jsp:useBean id="drugList" class="com.suglob.pharmacy.entity.DrugList"/>
<jsp:useBean id="doctor" class="com.suglob.pharmacy.entity.Doctor"/>
<div class="hide show" id="block">
    <c:if test="${empty orderList}">
        <h2>${empty_basket}</h2>
    </c:if>
    <c:if test="${not empty orderList}">
        <p>${your_basket}</p>
        <table>
            <tr>
                <th>${name}</th>
                <th>${dosage}</th>
                <th>${country}</th>
                <th>${price}</th>
                <th>${recipe}</th>
                <th>${table_count}</th>
            </tr>
            <c:forEach var="drug" items="${orderList}">
                <tr>
                    <td>${drug.name}</td>
                    <td>${drug.dosage}</td>
                    <td>${drug.country}</td>
                    <td>${drug.price}</td>
                    <td>${drug.isRecipe}</td>
                    <td>${drug.count}</td>
                </tr>
            </c:forEach>
        </table>
        <h4>${total_price}${orderPrice}</h4>
        <form action="Controller" method="post">
            <input type="hidden" name="command" value="pay_order"/>
            <input type="submit" value="${pay}" class="btn">
        </form>
        <form action="Controller" method="post">
            <input type="hidden" name="command" value="cancel_order"/>
            <input type="submit" value="${cancel}" class="btn">
        </form>
    </c:if>
</div>

</div>
<section>
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
    <c:if test="${error!=null}">
        <fmt:message bundle="${loc}" key="${error}" var="error_message"/>
        <h3>${error_message}</h3>
        <c:set var="error" value="${null}"/>
    </c:if>
    <c:if test="${pageContext.request.session==null or pageContext.request.session.isNew() or
    empty user.type}">
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
            <h3>${drugs_find} ${drugList.countAllRecords}</h3>
            <table>
                <tr>
                    <c:if test="${user.type!='client'}">
                        <th>id</th>
                    </c:if>
                    <th>${name}</th>
                    <th>${dosage}</th>
                    <th>${country}</th>
                    <th>${price}</th>
                    <th>${recipe}</th>
                    <th>${table_count}</th>
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
                    <td>${drug.name}</td>
                    <td>${drug.dosage}</td>
                    <td>${drug.country}</td>
                    <td>${drug.price}</td>
                    <c:if test="${drug.isRecipe=='Y'}">
                        <td><input type="text" name="code" placeholder="${code}" size="4" form="order"></td>
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
            <ctg:pagination page="${page}" countPages="${countPages}" urlParamsPagination="${urlParamsPagination}"
                            locale="${locale}"/>
        </c:if>


        <c:if test="${empty drugListCurrent}">
            <h3>${no_drugs}</h3>
        </c:if>
    </c:if>
    <c:if test="${empty param.category_id and empty param.textSearch}">
        <h3>${select_drugs}</h3>
    </c:if>
    <c:if test="${user.type=='client'}">
        <c:set var="doctorList" value="${doctor.createDoctorsList()}"/>
        <br>
        <div>
            <form action="Controller" method="post" class="buttons">
                <input type="hidden" name="command" value="order_recipe"/>
                <input type="text" name="drugName" placeholder="${drug_name}" size="15" required>
                <select name="doctorSurname" required>
                    <option disabled>${select_doctor}</option>
                    <c:forEach var="doc" items="${doctorList}">
                        <option>${doc.surname}</option>
                    </c:forEach>
                </select>
                <input type="submit" value="${ask_recipe}" class="btn">
            </form>
            <form action="Controller" method="post" class="buttons">
                <input type="hidden" name="command" value="order_extend_recipe"/>
                <input type="text" name="codeDrug" placeholder="${code}" size="8" required>
                <input type="submit" value="${extend_recipe}" class="btn">
            </form>
        </div>
    </c:if>
    <br>
    <c:if test="${user.type=='doctor'}">
        <form action="Controller" method="post" class="buttons">
            <input type="hidden" name="command" value="check_recipe"/>
            <input type="hidden" name="userId" value="${user.id}"/>
            <input type="submit" value="${check}" class="btn">
        </form>
        <c:if test="${not empty drugsNameOrderRecipe.listDrugs}">
            <h3>${requests_recipe}</h3>
            <div class="blockRecipe">
                ${drugsNameOrderRecipe.iter()}
                <ctg:table-drug rows="${drugsNameOrderRecipe.size}" locale="${locale}">
                    ${drugsNameOrderRecipe.drug}
                </ctg:table-drug>
                <table class="recipe">
                    <tr>
                        <th>${id_client}</th>
                        <th>${surname}</th>
                        <th>${name_client}</th>
                        <th>${patronymic}</th>
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
                <input type="text" placeholder="${drug_name}" name="drugName">
                <input type="text" placeholder="${id_client}" name="clientId">
                <input type="submit" value="${cancel}" class="btn">
            </form>
            <form action="Controller" method="post" class="buttons" onsubmit="return validateAssignRecipeForm()"
                  name="assignRecipeForm">
                <input type="hidden" name="command" value="assign_recipe"/>
                <input type="hidden" name="userId" value="${user.id}"/>
                <input type="text" placeholder="${id_drug}" name="drugId" size="8">
                <input type="text" placeholder="${table_count}" name="quantity" size="8">
                <input type="text" placeholder="${period}" name="period" size="8">
                <input type="text" placeholder="${id_client}" name="clientId" size="8">
                <input type="text" placeholder="${code}" name="code" size="7">
                <input type="submit" value="${assign}" class="btn">
            </form>
            <span class="err" id="err_fields">${fill_all_fields} </span>
            <span class="err" id="err_format_id_client">${id_client_bad}</span>
            <span class="err" id="err_format_id_drug">${id_drug_bad}</span>
            <span class="err" id="err_quantity_bad">${quantity_bad}</span>
            <span class="err" id="err_period_bad">${period_bad}</span>
            <span class="err" id="err_code_bad">${code_bad}</span>

        </c:if>

        <c:if test="${not empty clientsExtendRecipe}">
            <h3>${requests_extend_recipe}</h3>
            ${drugsNameExtendRecipe.iter()}
            <ctg:table-drug rows="${drugsNameExtendRecipe.size}" locale="${locale}">
                ${drugsNameExtendRecipe.drug}
            </ctg:table-drug>
            <table class="recipe">
                <tr>
                    <th>${surname}</th>
                    <th>${name}</th>
                    <th>${patronymic}</th>
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
                                <input type="text" placeholder="${period}" name="period" size="3">
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
            <input type="text" placeholder="${name}*" name="drugName" size="7">
            <input type="text" placeholder="${dosage}" name="dosage" size="10">
            <input type="text" placeholder="${country}*" name="country" size="10">
            <input type="text" placeholder="${price}*" name="priceDrug" size="4">
            <input type="text" placeholder="${table_count}*" name="quantity" size="3">
            <select name="recipe">
                <option>Y</option>
                <option>N</option>
            </select>
            <select name="drugCategories" multiple>
                <option disabled>${form_category}</option>
                <c:forEach var="category" items="${drugCategories}">
                    <option>${category.name}</option>
                </c:forEach>
            </select>
            <input type="submit" value="${add_drug}" class="btn">
        </form>
        <form action="Controller" method="post" class="buttons" onsubmit="return validateChangePriceDrugForm()"
              name="changePriceDrugForm">
            <input type="hidden" name="command" value="change_price_drug"/>
            <input type="text" placeholder="${id_drug}" name="drugId" size="9">
            <input type="text" placeholder="${new_price}" name="priceDrug" size="7">
            <input type="submit" value="${change_price}" class="btn">
        </form>
        <form action="Controller" method="post" class="buttons" onsubmit="return validateDeleteDrugForm()"
              name="deleteDrugForm">
            <input type="hidden" name="command" value="delete_drug"/>
            <input type="text" placeholder="${id_drug}" name="drugId" size="9">
            <input type="submit" value="${delete_drug}" class="btn">
        </form>
        <form action="Controller" method="post" class="buttons" onsubmit="return validateAddDrugCategoryForm()"
              name="addDrugCategoryForm">
            <input type="hidden" name="command" value="add_drug_category"/>
            <input type="text" placeholder="${name_cattegory}" name="drugCategory" size="14">
            <input type="submit" value="${add_category}" class="btn">
        </form>
        <form action="Controller" method="post" class="buttons" onsubmit="return validateDeleteDrugCategoryForm()"
              name="deleteDrugCategoryForm">
            <input type="hidden" name="command" value="delete_drug_category"/>
            <input type="text" placeholder="${name_cattegory}" name="drugCategory" size="14">
            <input type="submit" value="${delete_category}" class="btn">
        </form>
        <span class="err" id="err_fields">${fill_all_fields} </span>
        <span class="err" id="err_bad_country">${country_bad}</span>
        <span class="err" id="err_bad_price">${price_bad}</span>
        <span class="err" id="err_bad_quantity">${quantity_wrong}</span>
        <span class="err" id="err_drug_id">${id_drug_bad}</span>
        <span class="err" id="err_drug_category">${bad_drug_category}</span>
    </c:if>

</section>
<%@include file="../jspf/footer.jspf" %>
