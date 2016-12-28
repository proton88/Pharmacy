package com.suglob.pharmacy.dao;

import com.suglob.pharmacy.dao.impl.CommonDAOImpl;
import com.suglob.pharmacy.dao.impl.PoolDAOImpl;
import com.suglob.pharmacy.dao.impl.UserDAOImpl;

public class DAOFactory {
    private static final DAOFactory daoFactory = new DAOFactory();

    private final CommonDAO commonDAO = new CommonDAOImpl();
    private final PoolDAO poolDAO = new PoolDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();

    private DAOFactory(){}

    public static DAOFactory getInstance() {
        return daoFactory;
    }

    public CommonDAO getCommonDAO() {
        return commonDAO;
    }

    public PoolDAO getPoolDAO() {
        return poolDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }
}
