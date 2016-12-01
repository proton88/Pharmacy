package com.suglob.pharmacy.controller;

import com.suglob.pharmacy.command.ICommand;
import com.suglob.pharmacy.command.impl.Localization;
import com.suglob.pharmacy.command.impl.Logination;
import com.suglob.pharmacy.command.impl.Registration;

public enum CommandEnum {
    LOGINATION{
        {
            this.command=new Logination();
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
    };
    ICommand command;
    public ICommand getCurrentCommand() {
        return command;
    }
}
