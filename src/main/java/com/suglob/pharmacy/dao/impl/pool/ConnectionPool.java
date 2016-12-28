package com.suglob.pharmacy.dao.impl.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool<T extends Connection> {
    private static final ConnectionPool instance = new ConnectionPool();
    private BlockingQueue<T> connectionQueue;
    private BlockingQueue<T> givenAwayConQueue;

    private String driverName;
    private String url;
    private String user;
    private String password;
    private int poolSize;

    private ConnectionPool(){

        DBResourceManager dbResourceManager = DBResourceManager.getInstance();
        driverName = dbResourceManager.getValue(DBParameter.DB_DRIVER);
        url = dbResourceManager.getValue(DBParameter.DB_URL);
        user = dbResourceManager.getValue(DBParameter.DB_USER);
        password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);

        try{
            poolSize=Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE));
        }catch(NumberFormatException e){
            poolSize=5;
        }
    }

    public static ConnectionPool getInstance(){
        return instance;
    }

    public void initPoolData() throws ConnectionPoolException{
        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            givenAwayConQueue = new ArrayBlockingQueue<T>(poolSize);
            connectionQueue = new ArrayBlockingQueue<T>(poolSize);
            for(int i=0; i<poolSize; i++){
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
                // logging
            }
        }
    }

    public T takeConnection() throws ConnectionPoolException {
        T connection = null;
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
            throw new ConnectionPoolException("User, i haven't connections");
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
