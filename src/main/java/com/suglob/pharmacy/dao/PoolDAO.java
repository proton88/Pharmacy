package com.suglob.pharmacy.dao;

import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPool;
import com.suglob.pharmacy.dao.impl.pool.ProxyConnection;

public interface PoolDAO {
    void getPool(ConnectionPool<ProxyConnection> pool) throws DAOException;
}
