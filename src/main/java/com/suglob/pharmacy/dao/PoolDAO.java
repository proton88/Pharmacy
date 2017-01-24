package com.suglob.pharmacy.dao;

import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.pool.ConnectionPool;

public interface PoolDAO {
    void getPool(ConnectionPool pool) throws DAOException;

    void disposePool(ConnectionPool pool) throws DAOException;
}
