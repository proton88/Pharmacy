package com.suglob.pharmacy.controller;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.impl.*;

public enum CommandEnum {
    LOGINATION(new Logination()),
    LOGOUT(new Logout()),
    LOCALE(new Localization()),
    REGISTRATION(new Registration()),
    ADD_ORDER(new AddOrder()),
    CANCEL_ORDER(new CancelOrder()),
    PAY_ORDER(new PayOrder()),
    ORDER_RECIPE(new OrderRecipe()),
    ORDER_EXTEND_RECIPE(new OrderExtendRecipe()),
    CHECK_RECIPE(new CheckRecipe()),
    CANCEL_RECIPE(new CancelRecipe()),
    ASSIGN_RECIPE(new AssignRecipe()),
    CANCEL_EXTEND_RECIPE(new CancelExtendRecipe()),
    EXTEND_RECIPE(new ExtendRecipe()),
    ADD_QUANTITY_DRUG(new AddQuantityDrug()),
    CHANGE_PRICE_DRUG(new ChangePriceDrug()),
    ADD_DRUG(new AddDrug()),
    DELETE_DRUG(new DeleteDrug()),
    ADD_DRUG_CATEGORY(new AddDrugCategory()),
    DELETE_DRUG_CATEGORY(new DeleteDrugCategory());

    ICommand command;
    CommandEnum(ICommand command) {
        this.command = command;
    }
    public ICommand getCurrentCommand() {
        return command;
    }
}
