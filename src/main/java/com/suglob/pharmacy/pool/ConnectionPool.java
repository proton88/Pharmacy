package com.suglob.pharmacy.pool;

import com.suglob.pharmacy.constant.NumberConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private static AtomicBoolean poolExists = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private BlockingQueue<ProxyConnection> connectionQueue;

    private String url;
    private String user;
    private String password;
    private int poolSize;

    private ConnectionPool() {

        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        url = dbResourceManager.getValue(DBParameter.DB_URL);
        user = dbResourceManager.getValue(DBParameter.DB_USER);
        password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);

        try {
            poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE));
        } catch (NumberFormatException e) {
            LOGGER.log(Level.ERROR, "Connection pool size is wrong", e);
            poolSize = NumberConstant.POOLSIZE_DEF;
        }
    }

    public static ConnectionPool getInstance() {
        if (!poolExists.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    poolExists.getAndSet(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public void initPoolData() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            LOGGER.log(Level.FATAL, "Wrong JDBC Driver", e);
            throw new RuntimeException("Wrong JDBC Driver", e);
        }
        connectionQueue = new ArrayBlockingQueue<>(poolSize);
        int tryConnection = NumberConstant.ZERO;
        while (connectionQueue.size() < poolSize) {
            try {
                Connection con = DriverManager.getConnection(url, user, password);
                ProxyConnection proxyConnection = new ProxyConnection(con);
                connectionQueue.add(proxyConnection);

            } catch (SQLException e) {
                if (tryConnection++ < poolSize) {
                    LOGGER.log(Level.ERROR, "Impossible to get new connection", e);
                } else {
                    LOGGER.log(Level.FATAL, "Wrong initialization connection pool", e);
                    throw new RuntimeException("Wrong initialization connection pool", e);
                }
            }
        }
    }

    public void dispose() {
        clearConnectionQueue();
    }

    private void clearConnectionQueue() {
        closeConnectionQueue(connectionQueue);
    }

    private void closeConnectionQueue(BlockingQueue<ProxyConnection> queue) {
        for (int i = 0; i < queue.size(); i++) {
            try {
                queue.take().close();
            } catch (SQLException | InterruptedException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
    }

    public ProxyConnection takeConnection() throws ConnectionPoolException {
        ProxyConnection connection;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Error connecting to the data source.", e);
        }
        return connection;
    }

    public void releaseConnection(ProxyConnection connection) throws ConnectionPoolException {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                connectionQueue.put(connection);
            } catch (SQLException | InterruptedException e) {
                throw new ConnectionPoolException(e);
            }
        }
    }

}
