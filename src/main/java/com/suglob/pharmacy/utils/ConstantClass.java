package com.suglob.pharmacy.utils;

public class ConstantClass {
    public static final String PASSWORD="password";
    public static final String LOGIN="login";
    public static final String PASSWORD_REG = "password_reg";
    public static final String LOGIN_REG = "login_reg";
    public static final String PASSWORDREPEAT="passwordRepeat";
    public static final String NAME="name";
    public static final String SURNAME="surname";
    public static final String ADRESS="adress";
    public static final String PASSPORTID="passportId";
    public static final String PATRONYMIC="patronymic";
    public static final String LOCALE="locale";
    public static final String USER="user";
    public static final String DRUG_NAME ="drugName";
    public static final String DOCTOR_SURNAME ="doctorSurname";
    public static final String CODE_DRUG ="codeDrug";
    public static final String POSITION = "position";
    public static final String USER_ID = "userId";
    public static final String DRUG_ID = "drugId";
    public static final String QUANTITY = "quantity";
    public static final String PERIOD = "period";
    public static final String CLIENT_ID = "clientId";
    public static final String CODE = "code";
    public static final String EMAIL = "email";



    public static final String SQL_NAME_DRUG_CATEGORIES="SELECT * FROM pharmacy.drugs_categories order by name;";
    public static final String SQL_PAY_ORDER="select quantity from pharmacy.drugs where drugs_id=?;";
    public static final String SQL2_PAY_ORDER="UPDATE `pharmacy`.`drugs` SET `quantity`=? WHERE `drugs_id`=?;";
    public static final String SQL_TAKE_DOCTORS = "SELECT * FROM pharmacy.doctors order by surname;";
    public static final String SQL_DRUG_EXISTS ="SELECT * FROM pharmacy.drugs where name=?";
    public static final String SQL_ORDER_RECIPE ="SELECT clients_id FROM clients where users_id=?" ;
    public static final String SQL2_ORDER_RECIPE ="SELECT doctors_id FROM doctors where surname=?" ;
    public static final String SQL3_ORDER_RECIPE ="insert into order_recipes(drug_name, clients_id, doctors_id) values(?,?,?)";
    public static final String SQL_RECIPE_EXISTS = "SELECT * FROM pharmacy.recipes where code=?";
    public static final String SQL_ORDER_EXTEND_RECIPE = "insert into extend_recipe(drug_code) values(?)";
    public static final String SQL_CHECK_RECIPE = "SELECT drug_name, clients_id, surname, name, patronymic, address, " +
            "pasport_id, email from clients join order_recipes using(clients_id) where doctors_id=? order by clients_id";
    public static final String SQL2_CHECK_RECIPE = "SELECT recipes.code, drugs.name, clients_id, surname, clients.name, patronymic, " +
            "address, pasport_id, email from recipes join clients using(clients_id)\n" +
            "join m2m_recipes_drugs using(recipes_id) join drugs using(drugs_id)\n" +
            "join extend_recipe on extend_recipe.drug_code=recipes.code where doctors_id=? order by clients_id;";
    public static final String SQL3_CHECK_RECIPE = "SELECT doctors_id FROM pharmacy.doctors where users_id=?";
    public static final String SQL_CANCEL_RECIPE = "DELETE FROM order_recipes WHERE drug_name=? and clients_id=? and doctors_id=?";
    public static final String SQL_CHECK_DRUG = "SELECT * FROM pharmacy.drugs where drugs_id=?";
    public static final String SQL_CHECK_CLIENT = "SELECT * FROM pharmacy.clients where clients_id=?";
    public static final String SQL_ASSIGN_RECIPE = "insert into recipes(date_start, date_finish, doctors_id, " +
            "clients_id, code) values(?,?,?,?,?)";
    public static final String SQL_ASSIGN2_RECIPE = "SELECT count(*) from recipes";
    public static final String SQL_ASSIGN3_RECIPE = "insert into m2m_recipes_drugs(recipes_id, drugs_id, quantity) " +
            "values(?,?,?)";
    public static final String SQL_ASSIGN4_RECIPE = "SELECT name FROM drugs where drugs_id=?";
    public static final String SQL_ASSIGN5_RECIPE = "DELETE FROM order_recipes WHERE drug_name=? and clients_id=? and doctors_id=?";
    public static final String SQL_CHECK_DRUG_CODE = "SELECT * FROM pharmacy.recipes where code=?";
    public static final String SQL_CANCEL_EXTEND_RECIPE = "DELETE FROM extend_recipe WHERE drug_code=?";
    public static final String SQL_EXTEND_RECIPE = "update recipes set date_start=?, date_finish=? where code=?";


    public static final String REGEX_PASSPORT="[A-Z]{2}[0-9]{7}";
    public static final String REGEX_LOGIN="^[a-zA-Z][a-zA-Z0-9_]{4,}$";
    public static final String REGEX_PASSWORD="(?=^.{6,}$)^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
    public static final String REGEX_EMAIL="^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";


    public static final String CURRENT_QUANTITY = "currentQuantity";
    public static final String SQL_ADD_QUANTITY_DRUG = "update drugs set quantity=? where drugs_id=?";
    public static final String PRICE_DRUG = "priceDrug";
    public static final String SQL_CHECK_DRUG_ID = "SELECT * FROM drugs where drugs_id=?";
    public static final String SQL_CHANGE_PRICE_DRUG = "update drugs set price=? where drugs_id=?";
    public static final String DOSAGE = "dosage";
    public static final String COUNTRY = "country";
    public static final String RECIPE = "recipe";
    public static final String SQL_ADD_DRUG = "insert into drugs(drugs_id, name, dosage, country, price, quantity, is_recipe) values(?,?,?,?,?,?,?)";
    public static final String DRUG_CATEGORIES = "drugCategories";
    public static final String SQL_DRUG_ID = "SELECT count(*) FROM drugs";
    public static final String SQL_DRUG_CATEGORY_ID = "SELECT drugs_categories_id FROM drugs_categories where name=?";
    public static final String SQL_ADD_DRUG_DRUG_CATEGORY = "insert into m2m_drugs_drugs_categories values(?, ?)";
    public static final String SQL_DELETE_DRUG = "delete from m2m_drugs_drugs_categories where drugs_id=?";
    public static final String SQL2_DELETE_DRUG = "delete from drugs where drugs_id=?";
    public static final String DRUG_CATEGORY = "drugCategory";
    public static final String SQL_CHECK_DRUG_CATEGORY = "SELECT * FROM drugs_categories where name=?";
    public static final String SQL_ADD_DRUG_CATEGORY = "insert into drugs_categories(name) values(?)";
    public static final String SQL_CHECK_DRUG_CATEGORY_NOT_EMPTY = "SELECT * FROM m2m_drugs_drugs_categories where drugs_categories_id=\n" +
            "(SELECT drugs_categories_id FROM pharmacy.drugs_categories where name=?)";
    public static final String SQL_DELETE_DRUG_CATEGORY = "delete from drugs_categories where name=?";
    public static final String COUNT = "count";
    public static final String LIST_DRUGS = "listDrugs";
    public static final String ORDER_PRICE = "orderPrice";
    public static final String ORDER_LIST = "orderList";
    public static final String Y = "Y";
    public static final String DRUGS_CODE_EXTEND_RECIPE = "drugsCodeExtendRecipe";
    public static final String N = "N";
    public static final String DRUGS_NAME_ORDER_RECIPE = "drugsNameOrderRecipe";
    public static final String CLIENTS_ORDER_RECIPE = "clientsOrderRecipe";
    public static final String DRUGS_NAME_EXTEND_RECIPE = "drugsNameExtendRecipe";
    public static final String CLIENTS_EXTEND_RECIPE = "clientsExtendRecipe";
    public static final String INDEX = "index.jsp";
    public static final String URL = "url";
    public static final String MAIN = "main";
    public static final String URL_PARAMS = "urlParams";
    public static final String COMMAND = "command";
    public static final String ERROR_PAGE = "errorpage";
    public static final String ERROR_PAGE_PATH = "WEB-INF/jsp/errorpage.jsp";
    public static final String PROPERTIES_DB = "properties.db";
    public static final String SQL_LOGINATION = "SELECT * FROM pharmacy.users WHERE login=? and password=?";
    public static final String SQL_FOUND_ROWS = "SELECT FOUND_ROWS()";
    public static final String SQL_REGISTRATION = "INSERT INTO users(users_id, login, password, type) VALUES(?,?,?,?)";
    public static final String SQL2_REGISTRATION = "INSERT INTO clients(surname, name, patronymic, address, pasport_id, users_id, email) VALUES(?,?,?,?,?,?,?)";
    public static final String SQL3_REGISTRATION = "SELECT count(*) FROM users;";
    public static final String CLIENT = "client";
    public static final String NOT_EXIST = "not exist";
    public static final String OK = "ok";
    public static final String NOT_NEED = "not need";
    public static final String SQL_CANCEL_ORDER = "SELECT used FROM m2m_recipes_drugs where recipes_id=? and drugs_id=?";
    public static final String SQL2_CANCEL_ORDER = "update m2m_recipes_drugs set used =? where recipes_id=? and drugs_id=?";
    public static final String PAYMENT_OK = "payment.ok";
    public static final String SQL_ADD_RECIPE = "SELECT date_finish, drugs_id, quantity, used, recipes_id from recipes\n" +
            "join m2m_recipes_drugs using(recipes_id) where code=?";
    public static final String SQL2_ADD_RECIPE = "update m2m_recipes_drugs set used =? where recipes_id=? and drugs_id=?";
    public static final String UTF8 = "utf-8";
    public static final String ERROR = "error";
    public static final String EMPTY_STRING = "";
    public static final String LOG_DIR = "logDir";
    public static final String MSG = "msg";
    public static final String MSG_ADD_DRUG_OK = "add_drug.ok";
    public static final String MSG_ADD_DRUG_CATEGORY_OK = "add_drug_category.ok";
    public static final double DOUBLE_ZERO = 0.0;
    public static final int ZERO = 0;
    public static final int RECIPE_CODE_LENGTH = 6;
    public static final String BAD_RECIPE = "main.badRecipe";
    public static final String MSG_NOT_ENOUGH_DRUGS = "main.not_enough_drugs";
    public static final String MSG_RECIPE_PARAMETER_WRONG = "recipeParameter.wrong";
    public static final String MSG_CLIENT_ID_WRONG = "clientId.wrong";
    public static final String MSG_CHANGE_PRICE_DRUG_OK = "change_price_drug.ok";
    public static final String MSG_DELETE_DRUG_OK = "delete_drug.ok";
    public static final String MSG_DELETE_DRUG_CATEGORY_OK = "delete_drug_category.ok";
    public static final String WRONG_FORMAT = "wrong_format_parameters";
    public static final String MSG_EXTEND_RECIPE = "extend_recipe.ok";
    public static final String BLOCK_USER = "index.error_block";
    public static final String NOT_USER = "index.error_not_user";
    public static final String PAYMENT_MSG = "payment_msg";
    public static final String REG_ELSE = "reg.else";
    public static final String QUESTION = "?";
    public static final int POOLSIZE_DEF = 5;
    public static final double DOUBLE_CHANGE = 1.0;
    public static final String END_SESSION = "index.error_end_session";
    public static final String NOT_SESSION_USER = "index.error_not_session_user";
    public static final String ORDER_RECIPE_OK = "orderRecipe.ok";
    public static final String EXTEND_RECIPE_OK = "extendRecipe.ok";
    public static final String ORDER_RECIPE_EMPTY_FIELD = "orderRecipe.empty_field";
    public static final String DRUG_NOT_EXIST = "orderRecipe.drug_not_exists";
    public static final String DRUG_NOT_NEED = "orderRecipe.drug_not_need";
    public static final String EXTEND_RECIPE_EMPTY_FIELD = "extendRecipe.empty_field";
    public static final String EXTEND_RECIPE_NOT_EXIST = "extendRecipe.recipe_not_exists";
    public static final String NOT_DRUG_CLIENT = "assignRecipe.notDrugClient";
    public static final int MAX_QUANTITY = 9;
    public static final int MAX_PERIOD = 60;
    public static final String WRONG_QUANTITY = "assignRecipe.wrongQuantity";
    public static final String WRONG_PERIOD = "assignRecipe.wrongPeriod";
    public static final String WRONG_CODE = "assignRecipe.wrongCode";
    public static final String REPEAT_CODE = "assignRecipe.repeatCode";
    public static final String REG_EMPTY_FIELD = "reg.empty_field";
    public static final String REG_BAD_LOGIN = "reg.bad_login";
    public static final String REG_BAD_PASSWORD = "reg.bad_password";
    public static final String REG_PASSWORD = "reg.password";
    public static final String REG_PASSPORT = "reg.passport";
    public static final String REG_BAD_EMAIL = "reg.bad_email";
    public static final String REG_USER = "reg.user";
    public static final String DRUG_NOT_EXISTS = "drug_not_exist";
    public static final String WRONG_RECIPE_PARAM = "wrong_recipe_param";
    public static final String EMPTY_CATEGORY = "empty_category";
    public static final String DRUG_CATEGORY_EXIST = "drug_category_exist";
    public static final String DRUG_CATEGORY_NOT_EXIST = "drug_category_not_exist";
    public static final String DRUG_CATEGORY_NOT_EMPTY = "drug_category_not_empty";
    public static final String REGEX_DRUG_CATEGORY = "[а-яА-Я-\\s,]";
    public static final String REG_BAD_DRUG_CATEGORY = "bad_drug_category";
}
