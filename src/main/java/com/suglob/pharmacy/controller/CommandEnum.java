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
    };
    ICommand command;
    public ICommand getCurrentCommand() {
        return command;
    }
}
