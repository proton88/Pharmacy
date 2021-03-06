package com.suglob.pharmacy.constant;

public class SqlConstant {

    public static final String SQL_NAME_DRUG_CATEGORIES="SELECT * FROM pharmacy.drugs_categories order by name;";
    public static final String SQL_GET_QUANTITY_DRUGS ="select quantity from pharmacy.drugs where drugs_id=?;";
    public static final String SQL_UPDATE_QUANTITY_DRUGS ="UPDATE `pharmacy`.`drugs` SET `quantity`=? WHERE `drugs_id`=?;";
    public static final String SQL_TAKE_DOCTORS = "SELECT * FROM pharmacy.doctors order by surname;";
    public static final String SQL_DRUG_EXISTS ="SELECT * FROM pharmacy.drugs where name=?";
    public static final String SQL_SELECT_CLIENT_ID ="SELECT clients_id FROM clients where users_id=?" ;
    public static final String SQL_SELECT_DOCTOR_ID ="SELECT doctors_id FROM doctors where surname=?" ;
    public static final String SQL_ADD_ORDER_RECIPE ="insert into order_recipes(drug_name, clients_id, doctors_id) values(?,?,?)";
    public static final String SQL_RECIPE_EXISTS = "SELECT * FROM pharmacy.recipes where code=?";
    public static final String SQL_ADD_EXTEND_RECIPE = "insert into extend_recipe(drug_code) values(?)";
    public static final String SQL_SELECT_ORDER_RECIPE_INFO = "SELECT drug_name, clients_id, surname, name, patronymic, address, " +
            "pasport_id, email from clients join order_recipes using(clients_id) where doctors_id=? order by clients_id";
    public static final String SQL_SELECT_EXTEND_RECIPE_INFO = "SELECT recipes.code, drugs.name, clients_id, surname, clients.name, patronymic, " +
            "address, pasport_id, email from recipes join clients using(clients_id)\n" +
            "join m2m_recipes_drugs using(recipes_id) join drugs using(drugs_id)\n" +
            "join extend_recipe on extend_recipe.drug_code=recipes.code where doctors_id=? order by clients_id;";
    public static final String SQL_GET_DOCTOR_ID = "SELECT doctors_id FROM pharmacy.doctors where users_id=?";
    public static final String SQL_CANCEL_RECIPE = "DELETE FROM order_recipes WHERE drug_name=? and clients_id=? and doctors_id=?";
    public static final String SQL_CHECK_DRUG = "SELECT * FROM pharmacy.drugs where drugs_id=?";
    public static final String SQL_CHECK_CLIENT = "SELECT * FROM pharmacy.clients where clients_id=?";
    public static final String SQL_ASSIGN_RECIPE = "insert into recipes(date_start, date_finish, doctors_id, " +
            "clients_id, code) values(?,?,?,?,?)";
    public static final String SQL_COUNT_RECIPES = "SELECT count(*) from recipes";
    public static final String SQL_SET_QUANTITY_DRUGS = "insert into m2m_recipes_drugs(recipes_id, drugs_id, quantity) " +
            "values(?,?,?)";
    public static final String SQL_SELECT_DRUG_NAME = "SELECT name FROM drugs where drugs_id=?";
    public static final String SQL_DELETE_ORDER_RECIPE = "DELETE FROM order_recipes WHERE drug_name=? and clients_id=? and doctors_id=?";
    public static final String SQL_CHECK_DRUG_CODE = "SELECT * FROM pharmacy.recipes where code=?";
    public static final String SQL_CANCEL_EXTEND_RECIPE = "DELETE FROM extend_recipe WHERE drug_code=?";
    public static final String SQL_EXTEND_RECIPE = "update recipes set date_start=?, date_finish=? where code=?";
    public static final String SQL_ADD_QUANTITY_DRUG = "update drugs set quantity=? where drugs_id=?";
    public static final String SQL_CHECK_DRUG_ID = "SELECT * FROM drugs where drugs_id=?";
    public static final String SQL_CHANGE_PRICE_DRUG = "update drugs set price=? where drugs_id=?";
    public static final String SQL_ADD_DRUG = "insert into drugs(drugs_id, name, dosage, country, price, quantity, is_recipe) values(?,?,?,?,?,?,?)";
    public static final String SQL_DRUG_ID = "SELECT count(*) FROM drugs";
    public static final String SQL_DRUG_CATEGORY_ID = "SELECT drugs_categories_id FROM drugs_categories where name=?";
    public static final String SQL_ADD_DRUG_DRUG_CATEGORY = "insert into m2m_drugs_drugs_categories values(?, ?)";
    public static final String SQL_CHECK_DRUG_CATEGORY = "SELECT * FROM drugs_categories where name=?";
    public static final String SQL_ADD_DRUG_CATEGORY = "insert into drugs_categories(name) values(?)";
    public static final String SQL_CHECK_DRUG_CATEGORY_NOT_EMPTY = "SELECT * FROM m2m_drugs_drugs_categories where drugs_categories_id=\n" +
            "(SELECT drugs_categories_id FROM pharmacy.drugs_categories where name=?)";
    public static final String SQL_DELETE_DRUG_CATEGORY = "delete from drugs_categories where name=?";
    public static final String SQL_LOGINATION = "SELECT * FROM pharmacy.users WHERE login=? and password=?";
    public static final String SQL_FOUND_ROWS = "SELECT FOUND_ROWS()";
    public static final String SQL_ADD_USER = "INSERT INTO users(users_id, login, password, type) VALUES(?,?,?,?)";
    public static final String SQL_ADD_CLIENT = "INSERT INTO clients(surname, name, patronymic, address, pasport_id, users_id, email) VALUES(?,?,?,?,?,?,?)";
    public static final String SQL_COUNT_USERS = "SELECT count(*) FROM users;";
    public static final String SQL_GET_COUNT_USED_DRUGS = "SELECT used FROM m2m_recipes_drugs where recipes_id=? and drugs_id=?";
    public static final String SQL_CANCEL_USED_DRUGS = "update m2m_recipes_drugs set used =? where recipes_id=? and drugs_id=?";
    public static final String SQL_GET_RECIPE = "SELECT date_finish, drugs_id, quantity, used, recipes_id from recipes\n" +
            "join m2m_recipes_drugs using(recipes_id) where code=?";
    public static final String SQL_ADD_USED_DRUGS = "update m2m_recipes_drugs set used =? where recipes_id=? and drugs_id=?";
    public static final String SQL_DRUG_NOT_SALE = "update drugs set not_sale=1 where drugs_id=?";
}
