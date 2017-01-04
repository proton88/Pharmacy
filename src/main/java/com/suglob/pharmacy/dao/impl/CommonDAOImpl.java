package com.suglob.pharmacy.dao.impl;

import com.suglob.pharmacy.dao.CommonDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPool;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPoolException;
import com.suglob.pharmacy.dao.impl.pool.ProxyConnection;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.entity.DrugCategory;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.utils.ConstantClass;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CommonDAOImpl implements CommonDAO {

    @Override
    public User logination(String login, String password) throws DAOException {
        User user=null;
        String sql = "SELECT * FROM pharmacy.users WHERE login=? and password=?";

        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
        ProxyConnection con= null;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try(PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, login);
            ps.setString(2, DigestUtils.md5Hex(password));
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                user=new User(rs.getInt("block"),login,rs.getString("password"),rs.getString("type"));
            }

        }catch (SQLException e){throw new DAOException("Wrong sql in login", e);
        }finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool",e);
            }
        }
        return user;
    }

    @Override
    public ArrayList<DrugCategory> takeDrugCategories() throws DAOException {
        ArrayList<DrugCategory> drugCategoriesList = new ArrayList<>();
        String sql = ConstantClass.SQL_NAME_DRUG_CATEGORIES;

        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
        ProxyConnection con= null;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                drugCategoriesList.add(new DrugCategory(rs.getInt(1),rs.getString(2)));
            }

        }catch (SQLException e){throw new DAOException("Wrong sql in login", e);
        }finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool",e);
            }
        }
        return drugCategoriesList;
    }

    @Override
    public ArrayList<Drug> takeDrugs(String str) throws DAOException {
        ArrayList<Drug> drugList = new ArrayList<>();

        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
        ProxyConnection con= null;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try(PreparedStatement ps = con.prepareStatement(str)){
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                drugList.add(new Drug(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getBigDecimal(5),
                        rs.getInt(6),rs.getString(7)));
            }

        }catch (SQLException e){throw new DAOException("Wrong sql in login", e);
        }finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool",e);
            }
        }
        return drugList;
    }

}
