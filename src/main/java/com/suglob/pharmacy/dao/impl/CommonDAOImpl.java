package com.suglob.pharmacy.dao.impl;

import com.suglob.pharmacy.dao.CommonDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPool;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPoolException;
import com.suglob.pharmacy.domain.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CommonDAOImpl implements CommonDAO {

    @Override
    public User logination(String login, String password) throws DAOException {
        User user=null;
        int userBlock;

        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            con= ConnectionPool.getInstance().takeConnection();

            String sql = "SELECT * FROM pharmacy.users";
            ps=con.prepareStatement(sql);


            rs=ps.executeQuery(sql);
            while (rs.next()){
                if (rs.getString("login").equals(login)){
                    user=new User(rs.getInt("block"),login,rs.getString("password"),rs.getString("type"));
                }
            }
        }catch (ConnectionPoolException e){throw new DAOException("Don't take connection pool", e);
        }catch (SQLException e){throw new DAOException("Wrong sql", e);
        }finally {
            if (ps!=null){
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException("Don't close prepared statement", e);
                }
            }
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool",e);
            }
        }
        return user;
    }

}
