package com.suglob.pharmacy.service;

import com.suglob.pharmacy.pool.ConnectionPool;
import com.suglob.pharmacy.service.exception.ServiceException;

public interface PoolService {
    void getPool(ConnectionPool pool) throws ServiceException;

    void disposePool(ConnectionPool pool) throws ServiceException;
}
