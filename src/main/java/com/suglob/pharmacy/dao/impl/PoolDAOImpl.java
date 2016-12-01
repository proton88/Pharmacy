package com.suglob.pharmacy.dao.impl;

import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPool;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPoolException;

public class PoolDAOImpl implements PoolDAO {
    @Override
    public void getPool(ConnectionPool pool) throws DAOException {
        pool=ConnectionPool.getInstance();
        try {
            pool.initPoolData();
        } catch (ConnectionPoolException e) {
            new DAOException(e);
        }
    }
}
