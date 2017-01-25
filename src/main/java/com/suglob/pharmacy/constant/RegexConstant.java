package com.suglob.pharmacy.constant;

public class RegexConstant {

    public static final String REGEX_PASSPORT="[A-Z]{2}[0-9]{7}";
    public static final String REGEX_LOGIN="^[a-zA-Z][a-zA-Z0-9_]{4,}$";
    public static final String REGEX_PASSWORD="(?=^.{6,}$)^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$";
    public static final String REGEX_EMAIL="^[-\\w.]+@([A-z0-9][-A-z0-9]+\\.)+[A-z]{2,4}$";
    public static final String REGEX_DRUG_CATEGORY = "[а-яА-Я-\\s,]";
}
