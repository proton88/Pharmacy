package com.suglob.pharmacy.dao.impl;

import com.suglob.pharmacy.constant.NumberConstant;
import com.suglob.pharmacy.constant.SqlConstant;
import com.suglob.pharmacy.dao.PharmacistDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.pool.ConnectionPool;
import com.suglob.pharmacy.pool.ConnectionPoolException;
import com.suglob.pharmacy.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * This class contains methods to get, delete, add or modify data in the database.
 * All methods are associated with the user: 'doctor'.
 */
public class PharmacistDAOImpl implements PharmacistDAO {
    /**
     * Method add count drugs for drugs table.
     *
     * @param drugId drug id which must be added the amount
     * @param newQuantity the quantity to be set
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public void addQuantityDrug(int drugId, int newQuantity) throws DAOException {
        String sql = SqlConstant.SQL_ADD_QUANTITY_DRUG;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, newQuantity);
            ps.setInt(2, drugId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Wrong sql", e);
        } finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool", e);
            }
        }
    }
    /**
     * Method checks to existing drug.
     *
     * @param drugIdInt drug id
     * @return {@code true} if exists the drug
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public boolean checkDrug(int drugIdInt) throws DAOException {
        boolean result = false;
        String sql = SqlConstant.SQL_CHECK_DRUG_ID;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, drugIdInt);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Wrong sql", e);
        } finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool", e);
            }
        }
        return result;
    }
    /**
     * Method change price for drug in the drugs table.
     *
     * @param drugIdInt drug id which must be changed the price
     * @param priceDrugDouble the price to be set
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public void changePriceDrug(int drugIdInt, double priceDrugDouble) throws DAOException {
        String sql = SqlConstant.SQL_CHANGE_PRICE_DRUG;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, priceDrugDouble);
            ps.setInt(2, drugIdInt);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Wrong sql", e);
        } finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool", e);
            }
        }
    }
    /**
     * Method create new drug in the database
     *
     * @param drugName name of drug
     * @param dosage dosage of drug
     * @param country manufacturer country of drug
     * @param priceDrugDouble the price of drug
     * @param quantityInt the quantity of drug
     * @param recipe the code of drug recipe
     * @param categories the categories of drug
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public void addDrug(String drugName, String dosage, String country, double priceDrugDouble, int quantityInt,
                        String recipe, String[] categories) throws DAOException {
        String sql = SqlConstant.SQL_ADD_DRUG;
        String sql2 = SqlConstant.SQL_DRUG_ID;
        String sql3 = SqlConstant.SQL_DRUG_CATEGORY_ID;
        String sql4 = SqlConstant.SQL_ADD_DRUG_DRUG_CATEGORY;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
            con.setAutoCommit(false);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        } catch (SQLException e) {
            throw new DAOException("Error autocommit", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql);
             Statement st = con.createStatement();
             PreparedStatement ps3 = con.prepareStatement(sql3);
             PreparedStatement ps4 = con.prepareStatement(sql4)) {
            ResultSet rs=st.executeQuery(sql2);
            int drugId= NumberConstant.ZERO;
            if (rs.next()){
                drugId=rs.getInt(1);
                drugId++;
            }
            ps.setInt(1, drugId);
            ps.setString(2,drugName);
            ps.setString(3,dosage);
            ps.setString(4,country);
            ps.setDouble(5, priceDrugDouble);
            ps.setInt(6, quantityInt);
            ps.setString(7,recipe);
            ps.executeUpdate();
            for (String category:categories){
                ps3.setString(1,category);
                ResultSet rs2=ps3.executeQuery();
                int drugCategoryId= NumberConstant.ZERO;
                if (rs2.next()){
                    drugCategoryId=rs2.getInt(1);
                }
                ps4.setInt(1,drugId);
                ps4.setInt(2,drugCategoryId);
                ps4.executeUpdate();
            }
            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new DAOException("Wrong rollback", e);
            }
            throw new DAOException("Wrong sql", e);
        } finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool", e);
            }
        }
    }
    /**
     * Method sets the value of the not_sale
     *
     * @param drugIdInt id of drug
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public void deleteDrug(int drugIdInt) throws DAOException {
        String sql = SqlConstant.SQL_DRUG_NOT_SALE;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, drugIdInt);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Wrong sql", e);
        } finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool", e);
            }
        }
    }
    /**
     * Method checks to existing drug category.
     *
     * @param drugCategory drug category
     * @return {@code true} if exists the drug category
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public boolean checkDrugCategory(String drugCategory) throws DAOException {
        boolean result = false;
        String sql = SqlConstant.SQL_CHECK_DRUG_CATEGORY;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, drugCategory);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Wrong sql", e);
        } finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool", e);
            }
        }
        return result;
    }
    /**
     * Method add drug category in the database
     *
     * @param drugCategory name of drug category
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public void addDrugCategory(String drugCategory) throws DAOException {
        String sql = SqlConstant.SQL_ADD_DRUG_CATEGORY;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1,drugCategory);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Wrong sql", e);
        } finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool", e);
            }
        }
    }
    /**
     * Method delete drug category in the database
     *
     * @param drugCategory name of drug category
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public void deleteDrugCategory(String drugCategory) throws DAOException {
        String sql = SqlConstant.SQL_DELETE_DRUG_CATEGORY;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1,drugCategory);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Wrong sql", e);
        } finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool", e);
            }
        }
    }
    /**
     * Method checks whether the drug category is empty.
     *
     * @param drugCategory drug category
     * @return {@code true} if the category of drug is not empty
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public boolean checkDrugCategoryNotEmpty(String drugCategory) throws DAOException {
        boolean result = false;
        String sql = SqlConstant.SQL_CHECK_DRUG_CATEGORY_NOT_EMPTY;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, drugCategory);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOException("Wrong sql", e);
        } finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool", e);
            }
        }
        return result;
    }
}
