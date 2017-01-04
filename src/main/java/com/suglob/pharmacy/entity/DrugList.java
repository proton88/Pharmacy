package com.suglob.pharmacy.entity;

import com.suglob.pharmacy.service.CommonService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.utils.ConstantClass;

import java.util.ArrayList;

public class DrugList extends Entity {
    private ArrayList<Drug> bookList = new ArrayList<>();

    private ArrayList<Drug> takeDrugs(String strSQL) {
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        CommonService service = factory.getCommonService();
        ///////////////////////////////////////////////////////////////////////////////
        ArrayList<Drug> drugList = new ArrayList<>();
        try {
            drugList=service.takeDrugs(strSQL);
        } catch (ServiceException e) {
            //logging
        }
        return drugList;
    }

    public ArrayList<Drug> takeDrugsByCategory(int id){
        return takeDrugs("SELECT drugs.drugs_id, drugs.name, drugs.dosage, drugs.country, drugs.price, drugs.quantity, drugs.is_recipe \n" +
                "FROM m2m_drugs_drugs_categories inner join drugs using(drugs_id)\n" +
                "inner join drugs_categories using(drugs_categories_id)\n" +
                "where drugs_categories_id="+id+" order by name;");
    }

    public ArrayList<Drug> takeDrugsBySearch(String textSearch){
        return takeDrugs("SELECT * \n" +
        "from drugs where match(name, dosage, country) against('"+textSearch+"') order by name;");
    }

    public ArrayList<Drug> takeAllDrugs(){
        return takeDrugs(ConstantClass.SQL_ALL_DRUGS);
    }
}
