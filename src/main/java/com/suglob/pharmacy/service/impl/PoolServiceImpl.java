package com.suglob.pharmacy.service.impl;

import com.suglob.pharmacy.dao.DAOFactory;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.dao.PoolDAO;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPool;
import com.suglob.pharmacy.dao.impl.pool.ProxyConnection;
import com.suglob.pharmacy.service.PoolService;
import com.suglob.pharmacy.service.exception.ServiceException;

public class PoolServiceImpl implements PoolService {
    @Override
    public void getPool(ConnectionPool<ProxyConnection> pool) throws ServiceException {
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        PoolDAO poolDAO = factory.getPoolDAO();
        ///////////////////////////////////////////////////
        try {
            poolDAO.getPool(pool);
        } catch (DAOException e) {
            throw new ServiceException();
        }
    }

    @Override
    public void disposePool(ConnectionPool<ProxyConnection> pool) throws ServiceException {
        ////////////////////////////////////////////////////
        DAOFactory factory = DAOFactory.getInstance();
        PoolDAO poolDAO = factory.getPoolDAO();
        ///////////////////////////////////////////////////
        try {
            poolDAO.disposePool(pool);
        } catch (DAOException e) {
            throw new ServiceException();
        }
    }
}
