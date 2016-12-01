package com.suglob.pharmacy.dao.impl.pool;

import java.util.ResourceBundle;

public class DBResourceManager {
    private static final DBResourceManager instance = new DBResourceManager();

    private ResourceBundle bundle;

    private DBResourceManager(){
        bundle= ResourceBundle.getBundle("properties.db");
    }

    public String getValue(String key){
        return bundle.getString(key);
    }

    public static DBResourceManager getInstance(){
        return instance;
    }
}
