package com.suglob.pharmacy.dao;

import com.suglob.pharmacy.dao.impl.*;

public class DAOFactory {
    private static final DAOFactory daoFactory = new DAOFactory();

    private final CommonDAO commonDAO = new CommonDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();
    private final DoctorDAO doctorDAO = new DoctorDAOImpl();
    private final PharmacistDAO pharmacistDAO = new PharmacistDAOImpl();

    private DAOFactory(){}

    public static DAOFactory getInstance() {
        return daoFactory;
    }

    public CommonDAO getCommonDAO() {
        return commonDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public DoctorDAO getDoctorDAO() {
        return doctorDAO;
    }

    public PharmacistDAO getPharmacistDAO() {
        return pharmacistDAO;
    }
}
