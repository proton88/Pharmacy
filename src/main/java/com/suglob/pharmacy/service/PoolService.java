package com.suglob.pharmacy.service;

import com.suglob.pharmacy.dao.impl.pool.ConnectionPool;
import com.suglob.pharmacy.service.exception.ServiceException;

public interface PoolService {
    void getPool(ConnectionPool pool) throws ServiceException;
}
