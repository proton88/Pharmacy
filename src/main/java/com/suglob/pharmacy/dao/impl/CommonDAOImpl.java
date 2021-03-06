package com.suglob.pharmacy.dao.impl;

import com.suglob.pharmacy.constant.SqlConstant;
import com.suglob.pharmacy.dao.CommonDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.pool.ConnectionPool;
import com.suglob.pharmacy.pool.ConnectionPoolException;
import com.suglob.pharmacy.pool.ProxyConnection;
import com.suglob.pharmacy.entity.Doctor;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.entity.DrugCategory;
import com.suglob.pharmacy.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class contains methods to get, delete, add or modify data in the database.
 * All methods are associated with the all users.
 */
public class CommonDAOImpl implements CommonDAO {
    private int countRecords;
    /**
     * Returns user after log in.
     *
     * @param login unique user login
     * @param password unique user password
     * @return user if the authentication is successful
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public User logination(String login, String password) throws DAOException {
        User user=null;
        String sql = SqlConstant.SQL_LOGINATION;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
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
                user=new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
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
    /**
     * Get list of drug categories
     *
     * @return list of drug categories
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public ArrayList<DrugCategory> takeDrugCategories() throws DAOException {
        ArrayList<DrugCategory> drugCategoriesList = new ArrayList<>();
        String sql = SqlConstant.SQL_NAME_DRUG_CATEGORIES;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
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
    /**
     * Get list of drugs
     *
     * @param str sql string for different sets of drugs
     * @return list of drugs
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public ArrayList<Drug> takeDrugs(String str) throws DAOException {
        ArrayList<Drug> drugList = new ArrayList<>();

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try(PreparedStatement ps = con.prepareStatement(str);
            Statement statement=con.createStatement()){
            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                    drugList.add(new Drug(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getBigDecimal(5),
                            rs.getInt(6), rs.getString(7)));
            }
            rs = statement.executeQuery(SqlConstant.SQL_FOUND_ROWS);
            if(rs.next()) {
                countRecords = rs.getInt(1);
            }
        }catch (SQLException e){throw new DAOException("Wrong sql", e);
        }finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool",e);
            }
        }
        return drugList;
    }

    public int getCountRecords() {
        return countRecords;
    }
    /**
     * Get list of all doctors
     *
     * @return list of all doctors
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public ArrayList<Doctor> takeDoctors() throws DAOException {
        ArrayList<Doctor> doctorsList = new ArrayList<>();
        String sql = SqlConstant.SQL_TAKE_DOCTORS;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try(PreparedStatement ps = con.prepareStatement(sql)){
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                doctorsList.add(new Doctor(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5)));
            }

        }catch (SQLException e){throw new DAOException("Wrong sql", e);
        }finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool",e);
            }
        }
        return doctorsList;
    }

}
