package com.suglob.pharmacy.pool;

import com.suglob.pharmacy.util.ConstantClass;

import java.util.ResourceBundle;

class DBResourceManager {
    private static final DBResourceManager instance = new DBResourceManager();

    private ResourceBundle bundle;

    private DBResourceManager(){
        bundle= ResourceBundle.getBundle(ConstantClass.PROPERTIES_DB);
    }

    String getValue(String key){
        return bundle.getString(key);
    }

    public static DBResourceManager getInstance(){
        return instance;
    }
}
