package com.suglob.pharmacy.entity;

import com.suglob.pharmacy.service.CommonService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;

import java.util.ArrayList;

public class DrugCategory extends Entity {
    private int id;
    private String name;

    public DrugCategory() {
    }

    public DrugCategory(int id, String name) {
        this.id=id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<DrugCategory> createDrugCategoriesList(){
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        CommonService service = factory.getCommonService();
        ///////////////////////////////////////////////////////////////////////////////
        ArrayList<DrugCategory> drugCategoriesList = new ArrayList<>();
        try {
            drugCategoriesList=service.takeDrugCategories();
        } catch (ServiceException e) {
            //logging
        }
        return drugCategoriesList;
    }
}
