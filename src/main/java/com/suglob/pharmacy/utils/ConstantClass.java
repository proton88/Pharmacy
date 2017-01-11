package com.suglob.pharmacy.utils;

public class ConstantClass {
    public static final String PASSWORD="password";
    public static final String LOGIN="login";
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


    public static final String SQL_NAME_DRUG_CATEGORIES="SELECT * FROM pharmacy.drugs_categories order by name;";
    public static final String SQL_PAY_ORDER="select quantity from pharmacy.drugs where drugs_id=?;";
    public static final String SQL2_PAY_ORDER="UPDATE `pharmacy`.`drugs` SET `quantity`=? WHERE `drugs_id`=?;";
    public static final String SQL_TAKE_DOCTORS = "SELECT * FROM pharmacy.doctors order by surname;";

    public static final java.lang.String SQL_DRUG_EXISTS ="SELECT * FROM pharmacy.drugs where name=?";
    public static final java.lang.String SQL_ORDER_RECIPE ="SELECT clients_id FROM clients where users_id=?" ;
    public static final java.lang.String SQL2_ORDER_RECIPE ="SELECT doctors_id FROM doctors where surname=?" ;
    public static final java.lang.String SQL3_ORDER_RECIPE ="insert into order_recipes(drug_name, clients_id, doctors_id) values(?,?,?)";
    public static final java.lang.String SQL_RECIPE_EXISTS = "SELECT * FROM pharmacy.recipes where code=?";
    public static final java.lang.String SQL_EXTEND_RECIPE = "insert into extend_recipe(drug_code) values(?)";
    public static final String SQL_CHECK_RECIPE = "SELECT drug_name, clients_id, surname, name, patronymic, address, " +
            "pasport_id, email from clients join order_recipes using(clients_id) where doctors_id=? order by clients_id";
    public static final String SQL2_CHECK_RECIPE = "SELECT drugs.name, clients_id, surname, clients.name, patronymic, " +
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
}
