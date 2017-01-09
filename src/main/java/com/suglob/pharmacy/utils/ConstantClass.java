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
}
