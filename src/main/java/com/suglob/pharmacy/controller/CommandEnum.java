package com.suglob.pharmacy.controller;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.impl.*;

public enum CommandEnum {
    LOGINATION{
        {
            this.command=new Logination();
        }
    },
    LOGOUT{
        {
            this.command=new Logout();
        }
    },
    LOCALE{
        {
            this.command=new Localization();
        }
    },
    REGISTRATION{
        {
            this.command=new Registration();
        }
    },
    ADD_ORDER{
        {
            this.command=new AddOrder();
        }
    },
    CANCEL_ORDER{
        {
            this.command=new CancelOrder();
        }
    },
    PAY_ORDER{
        {
            this.command=new PayOrder();
        }
    },
    ORDER_RECIPE{
        {
            this.command=new OrderRecipe();
        }
    },
    ORDER_EXTEND_RECIPE{
        {
            this.command=new OrderExtendRecipe();
        }
    },
    CHECK_RECIPE{
        {
            this.command=new CheckRecipe();
        }
    },
    CANCEL_RECIPE{
        {
            this.command=new CancelRecipe();
        }
    },
    ASSIGN_RECIPE{
        {
            this.command=new AssignRecipe();
        }
    },
    CANCEL_EXTEND_RECIPE{
        {
            this.command=new CancelExtendRecipe();
        }
    },
    EXTEND_RECIPE{
        {
            this.command=new ExtendRecipe();
        }
    },
    ADD_QUANTITY_DRUG{
        {
            this.command=new AddQuantityDrug();
        }
    },
    CHANGE_PRICE_DRUG{
        {
            this.command=new ChangePriceDrug();
        }
    },
    ADD_DRUG{
        {
            this.command=new AddDrug();
        }
    },
    DELETE_DRUG{
        {
            this.command=new DeleteDrug();
        }
    },
    ADD_DRUG_CATEGORY{
        {
            this.command=new AddDrugCategory();
        }
    },
    DELETE_DRUG_CATEGORY{
        {
            this.command=new DeleteDrugCategory();
        }
    };
    ICommand command;
    public ICommand getCurrentCommand() {
        return command;
    }
}
