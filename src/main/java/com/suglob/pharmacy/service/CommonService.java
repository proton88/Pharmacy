package com.suglob.pharmacy.service;

import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.entity.DrugCategory;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.service.exception.ServiceException;

import java.util.ArrayList;

public interface CommonService {
    User logination(String login, String password) throws ServiceException;
    ArrayList<DrugCategory> takeDrugCategories() throws ServiceException;

    ArrayList<Drug> takeDrugs(String str) throws ServiceException;
}
