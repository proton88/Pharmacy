package com.suglob.pharmacy.dao.impl;

import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPool;

public interface PoolDAO {
    void getPool(ConnectionPool pool) throws DAOException;
}
