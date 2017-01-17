package com.suglob.pharmacy.dao.impl;

import com.suglob.pharmacy.dao.PharmacistDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPool;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPoolException;
import com.suglob.pharmacy.dao.impl.pool.ProxyConnection;
import com.suglob.pharmacy.utils.ConstantClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PharmacistDAOImpl implements PharmacistDAO {
    @Override
    public void addQuantityDrug(int drugId, int newQuantity) throws DAOException {
        String sql = ConstantClass.SQL_ADD_QUANTITY_DRUG;

        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
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

    @Override
    public boolean checkDrug(int drugIdInt) throws DAOException {
        boolean result = false;
        String sql = ConstantClass.SQL_CHECK_DRUG_ID;

        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
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

    @Override
    public void changePriceDrug(int drugIdInt, double priceDrugDouble) throws DAOException {
        String sql = ConstantClass.SQL_CHANGE_PRICE_DRUG;

        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
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

    @Override
    public void addDrug(String drugName, String dosage, String country, double priceDrugDouble, int quantityInt,
                        String recipe, String[] categories) throws DAOException {
        String sql = ConstantClass.SQL_ADD_DRUG;
        String sql2 = ConstantClass.SQL_DRUG_ID;
        String sql3 = ConstantClass.SQL_DRUG_CATEGORY_ID;
        String sql4 = ConstantClass.SQL_ADD_DRUG_DRUG_CATEGORY;

        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
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
            int drugId=ConstantClass.ZERO;
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
                int drugCategoryId=ConstantClass.ZERO;
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

    @Override
    public void deleteDrug(int drugIdInt) throws DAOException {
        String sql = ConstantClass.SQL_DELETE_DRUG;
        String sql2 = ConstantClass.SQL2_DELETE_DRUG;

        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
            con.setAutoCommit(false);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }catch (SQLException e) {
            throw new DAOException("Error autocommit", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql);
             PreparedStatement ps2 = con.prepareStatement(sql2)) {
            ps.setInt(1, drugIdInt);
            ps.executeUpdate();
            ps2.setInt(1,drugIdInt);
            ps2.executeUpdate();
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

    @Override
    public boolean checkDrugCategory(String drugCategory) throws DAOException {
        boolean result = false;
        String sql = ConstantClass.SQL_CHECK_DRUG_CATEGORY;

        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
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

    @Override
    public void addDrugCategory(String drugCategory) throws DAOException {
        String sql = ConstantClass.SQL_ADD_DRUG_CATEGORY;

        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
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

    @Override
    public void deleteDrugCategory(String drugCategory) throws DAOException {
        String sql = ConstantClass.SQL_DELETE_DRUG_CATEGORY;

        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
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

    @Override
    public boolean checkDrugCategoryNotEmpty(String drugCategory) throws DAOException {
        boolean result = false;
        String sql = ConstantClass.SQL_CHECK_DRUG_CATEGORY_NOT_EMPTY;

        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
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
