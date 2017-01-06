package com.suglob.pharmacy.entity;

import com.suglob.pharmacy.service.CommonService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import com.suglob.pharmacy.utils.ConstantClass;

import java.util.ArrayList;

public class DrugList extends Entity {
    private ArrayList<Drug> bookList = new ArrayList<>();
    private int countPages;
    private int countAllRecords;

    private ArrayList<Drug> takeDrugs(String strSQL, int countRecords) {
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
        countAllRecords=service.getCountRecords();
        countPages=(int) Math.ceil(countAllRecords * 1.0 / countRecords);
        return drugList;
    }

    public ArrayList<Drug> takeDrugsByCategory(int id, int start, int countRecords){
        return takeDrugs("SELECT drugs.drugs_id, drugs.name, drugs.dosage, drugs.country, drugs.price, drugs.quantity, drugs.is_recipe \n" +
                "FROM m2m_drugs_drugs_categories inner join drugs using(drugs_id)\n" +
                "inner join drugs_categories using(drugs_categories_id)\n" +
                "where drugs_categories_id="+id+" order by name limit " + start + ", " + countRecords, countRecords);
    }

    public ArrayList<Drug> takeDrugsBySearch(String textSearch, int start, int countRecords){
        return takeDrugs("SELECT * from drugs where match(name, dosage, country) " +
                "against('"+textSearch+"') order by name limit " + start + ", " + countRecords,countRecords);
    }

    public ArrayList<Drug> takeAllDrugs(int start, int countRecords){
        return takeDrugs("SELECT SQL_CALC_FOUND_ROWS * from drugs order by name limit " + start + ", " + countRecords, countRecords);
    }

    public int getCountPages() {
        return countPages;
    }

    public int getCountAllRecords() {
        return countAllRecords;
    }
}
