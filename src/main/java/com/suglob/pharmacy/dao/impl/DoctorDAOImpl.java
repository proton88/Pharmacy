package com.suglob.pharmacy.dao.impl;

import com.suglob.pharmacy.dao.DoctorDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.pool.ConnectionPool;
import com.suglob.pharmacy.pool.ConnectionPoolException;
import com.suglob.pharmacy.pool.ProxyConnection;
import com.suglob.pharmacy.entity.Client;
import com.suglob.pharmacy.util.ConstantClass;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DoctorDAOImpl implements DoctorDAO {
    @Override
    public Map<String, List> checkRecipe(int user_id) throws DAOException {
        Map<String, List> result = new HashMap<>();
        ArrayList<Client> clientsListOrder = new ArrayList<>();
        ArrayList<String> drugsNameOrder = new ArrayList<>();
        ArrayList<Client> clientsListExtend = new ArrayList<>();
        ArrayList<String> drugsNameExtend = new ArrayList<>();
        ArrayList<String> drugsCodeExtend = new ArrayList<>();
        String sql = ConstantClass.SQL_CHECK_RECIPE;
        String sql2 = ConstantClass.SQL2_CHECK_RECIPE;
        String sql3 = ConstantClass.SQL3_CHECK_RECIPE;
        int doctorsId =ConstantClass.ZERO;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps3 = con.prepareStatement(sql3);
             PreparedStatement ps = con.prepareStatement(sql);
             PreparedStatement ps2 = con.prepareStatement(sql2)) {
            ps3.setInt(1, user_id);
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()) {
                doctorsId = rs3.getInt(1);
            }
            ps.setInt(1, doctorsId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                drugsNameOrder.add(rs.getString(1));
                clientsListOrder.add(new Client(rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7), rs.getString(8)));
            }
            result.put(ConstantClass.DRUGS_NAME_ORDER_RECIPE, drugsNameOrder);
            result.put(ConstantClass.CLIENTS_ORDER_RECIPE, clientsListOrder);

            ps2.setInt(1, doctorsId);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                drugsCodeExtend.add(rs2.getString(1));
                drugsNameExtend.add(rs2.getString(2));
                clientsListExtend.add(new Client(rs2.getInt(3), rs2.getString(4), rs2.getString(5), rs2.getString(6),
                        rs2.getString(7), rs2.getString(8), rs2.getString(9)));
            }
            result.put(ConstantClass.DRUGS_CODE_EXTEND_RECIPE, drugsCodeExtend);
            result.put(ConstantClass.DRUGS_NAME_EXTEND_RECIPE, drugsNameExtend);
            result.put(ConstantClass.CLIENTS_EXTEND_RECIPE, clientsListExtend);
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
    public void cancelRecipe(int userId, String drugName, int clientId) throws DAOException {
        String sql = ConstantClass.SQL3_CHECK_RECIPE;
        String sql2 = ConstantClass.SQL_CANCEL_RECIPE;
        int doctorsId = ConstantClass.ZERO;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql);
             PreparedStatement ps2 = con.prepareStatement(sql2)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                doctorsId = rs.getInt(1);
            }
            ps2.setString(1, drugName);
            ps2.setInt(2, clientId);
            ps2.setInt(3, doctorsId);
            ps2.executeUpdate();
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
    public boolean checkDrugAndClient(int drugId, int clientId) throws DAOException {
        boolean result = true;
        String sql = ConstantClass.SQL_CHECK_DRUG;
        String sql2 = ConstantClass.SQL_CHECK_CLIENT;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql);
             PreparedStatement ps2 = con.prepareStatement(sql2)) {
            ps.setInt(1, drugId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                result = false;
                return result;
            }
            ps2.setInt(1, clientId);
            ResultSet rs2 = ps2.executeQuery();
            if (!rs2.next()) {
                result = false;
                return result;
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
    public String assignRecipe(int userId, int drugId, int quantity, int period, int clientId, String code) throws DAOException {
        String drugName=ConstantClass.EMPTY_STRING;
        String sql = ConstantClass.SQL3_CHECK_RECIPE;
        String sql2 = ConstantClass.SQL_ASSIGN_RECIPE;
        String sql3 = ConstantClass.SQL_ASSIGN2_RECIPE;
        String sql4 = ConstantClass.SQL_ASSIGN3_RECIPE;
        String sql5 = ConstantClass.SQL_ASSIGN4_RECIPE;
        String sql6 = ConstantClass.SQL_ASSIGN5_RECIPE;
        int doctorsId = ConstantClass.ZERO;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
            con.setAutoCommit(false);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        } catch (SQLException e) {
            throw new DAOException("Autocommit error", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql);
             PreparedStatement ps2 = con.prepareStatement(sql2);
             Statement st = con.createStatement();
             PreparedStatement ps3 = con.prepareStatement(sql4);
             PreparedStatement ps4 = con.prepareStatement(sql5);
             PreparedStatement ps5 = con.prepareStatement(sql6)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                doctorsId = rs.getInt(1);
            }

            ps2.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
            ps2.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(period)));
            ps2.setInt(3, doctorsId);
            ps2.setInt(4, clientId);
            ps2.setString(5, code);
            ps2.executeUpdate();

            ResultSet rs2 = st.executeQuery(sql3);
            int recipesId = ConstantClass.ZERO;
            if (rs2.next()) {
                recipesId = rs2.getInt(1);
            }

            ps3.setInt(1, recipesId);
            ps3.setInt(2, drugId);
            ps3.setInt(3, quantity);
            ps3.executeUpdate();

            ps4.setInt(1, drugId);
            ResultSet rs3 = ps4.executeQuery();
            if (rs3.next()) {
                drugName = rs3.getString(1);
            }

            ps5.setString(1, drugName);
            ps5.setInt(2, clientId);
            ps5.setInt(3, doctorsId);
            ps5.executeUpdate();

            con.commit();
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new DAOException("Rollback error", e);
            }
            throw new DAOException("Wrong sql request", e);
        } finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Don't release connection pool", e);
            }
        }
        return drugName;
    }

    @Override
    public boolean checkDrugCode(String code) throws DAOException {
        boolean result = true;
        String sql = ConstantClass.SQL_CHECK_DRUG_CODE;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = false;
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
    public void cancelExtendRecipe(String codeRecipe) throws DAOException {
        String sql = ConstantClass.SQL_CANCEL_EXTEND_RECIPE;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, codeRecipe);
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
    public void extendRecipe(int period, String codeRecipe) throws DAOException {
        String sql = ConstantClass.SQL_EXTEND_RECIPE;

        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(java.time.LocalDate.now()));
            ps.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now().plusDays(period)));
            ps.setString(3, codeRecipe);
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
}
