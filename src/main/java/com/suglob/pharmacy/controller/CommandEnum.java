package com.suglob.pharmacy.controller;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.impl.*;

public enum CommandEnum {
    LOGINATION(new LogInCommand()),
    LOGOUT(new LogOutCommand()),
    LOCALE(new LocaleCommand()),
    REGISTRATION(new RegisterCommand()),
    ADD_ORDER(new AddOrderCommand()),
    CANCEL_ORDER(new CancelOrderCommand()),
    PAY_ORDER(new PayOrderCommand()),
    ORDER_RECIPE(new OrderRecipeCommand()),
    ORDER_EXTEND_RECIPE(new OrderExtendRecipeCommand()),
    CHECK_RECIPE(new CheckRecipeCommand()),
    CANCEL_RECIPE(new CancelRecipeCommand()),
    ASSIGN_RECIPE(new AssignRecipeCommand()),
    CANCEL_EXTEND_RECIPE(new CancelExtendRecipeCommand()),
    EXTEND_RECIPE(new ExtendRecipeCommand()),
    ADD_QUANTITY_DRUG(new AddQuantityDrugCommand()),
    CHANGE_PRICE_DRUG(new ChangePriceDrugCommand()),
    ADD_DRUG(new AddDrugCommand()),
    DELETE_DRUG(new DeleteDrugCommand()),
    ADD_DRUG_CATEGORY(new AddDrugCategoryCommand()),
    DELETE_DRUG_CATEGORY(new DeleteDrugCategoryCommand());

    ICommand command;
    CommandEnum(ICommand command) {
        this.command = command;
    }
    public ICommand getCurrentCommand() {
        return command;
    }
}
