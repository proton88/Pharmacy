package com.suglob.pharmacy.dao.impl;

import com.suglob.pharmacy.dao.PoolDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.pool.ConnectionPool;

public class PoolDAOImpl implements PoolDAO {
    @Override
    public void getPool(ConnectionPool pool) throws DAOException {
        pool=ConnectionPool.getInstance();
        pool.initPoolData();
    }

    @Override
    public void disposePool(ConnectionPool pool) throws DAOException {
        try{
            pool.dispose();
        }catch (Exception e){
            throw new DAOException(e);
        }

    }
}
