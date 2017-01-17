package com.suglob.pharmacy.dao.impl;

import com.suglob.pharmacy.dao.PoolDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPool;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPoolException;
import com.suglob.pharmacy.dao.impl.pool.ProxyConnection;

public class PoolDAOImpl implements PoolDAO {
    @Override
    public void getPool(ConnectionPool<ProxyConnection> pool) throws DAOException {
        pool=ConnectionPool.getInstance();
        try {
            pool.initPoolData();
        } catch (ConnectionPoolException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void disposePool(ConnectionPool<ProxyConnection> pool) throws DAOException {
        try{
            pool.dispose();
        }catch (Exception e){
            throw new DAOException(e);
        }

    }
}
