package com.suglob.pharmacy.dao.impl;

import com.suglob.pharmacy.dao.UserDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPool;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPoolException;
import com.suglob.pharmacy.domain.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public User registration(String login, String password, String passwordRepeat, String name, String surname, String patronymic, String adress, String passportId) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        ResultSet rs=null;
        int countUsers=0;


        String sql = "INSERT INTO users(users_id, login, password, type) VALUES(?,?,?,?)";
        String sql2 = "INSERT INTO clients(surname, name, patronymic, address, pasport_id, users_id) VALUES(?,?,?,?,?,?)";
        String sql3 = "SELECT count(*) FROM users;";
        try {
            con = ConnectionPool.getInstance().takeConnection();
            ps=con.prepareStatement(sql3);
            rs=ps.executeQuery(sql3);
            if (rs.next()){
                countUsers=rs.getInt(1);
                countUsers++;

            }
            ps = con.prepareStatement(sql);
            ps2 = con.prepareStatement(sql2);
            ps.setInt(1, countUsers);
            ps.setString(2, login);
            ps.setString(3, DigestUtils.md5Hex(password));
            ps.setString(4, "client");
            ps.executeUpdate();
            ps2.setString(1, surname);
            ps2.setString(2, name);
            ps2.setString(3, patronymic);
            ps2.setString(4, adress);
            ps2.setString(5, passportId);
            ps2.setInt(6, countUsers);
            ps2.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Wrong sql request",e);
        } catch (ConnectionPoolException e) {
            throw new DAOException("ConnectionPool error",e);
        }finally {
            if (ps != null || ps2 != null) {
                try {
                    ps.close();
                    ps2.close();
                } catch (SQLException e) {
                    throw new DAOException("Don't close prepare statement",e);
                }
            }
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Connection pool don't release connection",e);
            }
        }
        User user = new User(0, login, password, "client");
        return user;
    }
}
