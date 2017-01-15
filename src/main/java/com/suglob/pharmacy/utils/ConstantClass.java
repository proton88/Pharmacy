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
    public static final java.lang.String SQL_DRUG_EXISTS ="SELECT * FROM pharmacy.drugs where name=?";
    public static final java.lang.String SQL_ORDER_RECIPE ="SELECT clients_id FROM clients where users_id=?" ;
    public static final java.lang.String SQL2_ORDER_RECIPE ="SELECT doctors_id FROM doctors where surname=?" ;
    public static final java.lang.String SQL3_ORDER_RECIPE ="insert into order_recipes(drug_name, clients_id, doctors_id) values(?,?,?)";
    public static final java.lang.String SQL_RECIPE_EXISTS = "SELECT * FROM pharmacy.recipes where code=?";
    public static final java.lang.String SQL_ORDER_EXTEND_RECIPE = "insert into extend_recipe(drug_code) values(?)";
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
}
