package com.suglob.pharmacy.dao.impl.pool;

import com.suglob.pharmacy.utils.ConstantClass;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool<T extends Connection> {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static final ConnectionPool instance = new ConnectionPool();
    private BlockingQueue<T> connectionQueue;
    private BlockingQueue<T> givenAwayConQueue;

    private String url;
    private String user;
    private String password;
    private int poolSize;

    private ConnectionPool(){

        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        url = dbResourceManager.getValue(DBParameter.DB_URL);
        user = dbResourceManager.getValue(DBParameter.DB_USER);
        password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);

        try{
            poolSize=Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE));
        }catch(NumberFormatException e){
            poolSize= ConstantClass.POOLSIZE_DEF;
        }
    }

    public static ConnectionPool getInstance(){
        return instance;
    }

    public void initPoolData() throws ConnectionPoolException{
        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            givenAwayConQueue = new ArrayBlockingQueue<>(poolSize);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            for(int i=ConstantClass.ZERO; i<poolSize; i++){
                Connection con = DriverManager.getConnection(url, user, password);
                ProxyConnection proxyConnection=new ProxyConnection(con);
                connectionQueue.add((T) proxyConnection);
            }
        }catch(SQLException e){
            throw new ConnectionPoolException("SQLException in ConnectionPool",e);
        }
    }

    public void dispose(){
        clearConnectionQueue();
    }

    private void clearConnectionQueue(){
        closeConnectionQueue(givenAwayConQueue);
        closeConnectionQueue(connectionQueue);
    }

    private void closeConnectionQueue(BlockingQueue<T> queue){
        for (T con : queue) {
            try {
                con.close();
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, e);
            }
        }
    }

    public T takeConnection() throws ConnectionPoolException {
        T connection;
        try {
            connection = connectionQueue.take();
            givenAwayConQueue.add(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Error connecting to the data source.", e);
        }
        return connection;
    }

    public void releaseConnection(T connection) throws ConnectionPoolException {
        if (connection == null) {
            throw new ConnectionPoolException("Not connections");
        }

        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }

        givenAwayConQueue.remove(connection);
        connectionQueue.add(connection);

    }
}
