package com.suglob.pharmacy.dao.impl;

import com.suglob.pharmacy.constant.NumberConstant;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.constant.SqlConstant;
import com.suglob.pharmacy.dao.DoctorDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.pool.ConnectionPool;
import com.suglob.pharmacy.pool.ConnectionPoolException;
import com.suglob.pharmacy.pool.ProxyConnection;
import com.suglob.pharmacy.entity.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
/**
 * This class contains methods to get, delete, add or modify data in the database.
 * All methods are associated with the user: 'doctor'.
 */
public class DoctorDAOImpl implements DoctorDAO {
    /**
     * Method get a map containing the orders for the extension or getting of recipe
     *
     * @param userId unique id user who wants to check orders for recipe
     * @return map containing the orders for the extension or getting of recipe
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public Map<String, List> checkRecipe(int userId) throws DAOException {
        Map<String, List> result = new HashMap<>();
        ArrayList<Client> clientsListOrder = new ArrayList<>();
        ArrayList<String> drugsNameOrder = new ArrayList<>();
        ArrayList<Client> clientsListExtend = new ArrayList<>();
        ArrayList<String> drugsNameExtend = new ArrayList<>();
        ArrayList<String> drugsCodeExtend = new ArrayList<>();
        String sql = SqlConstant.SQL_SELECT_ORDER_RECIPE_INFO;
        String sql2 = SqlConstant.SQL_SELECT_EXTEND_RECIPE_INFO;
        String sql3 = SqlConstant.SQL_GET_DOCTOR_ID;
        int doctorsId = NumberConstant.ZERO;

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
            ps3.setInt(1, userId);
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
            result.put(OtherConstant.DRUGS_NAME_ORDER_RECIPE, drugsNameOrder);
            result.put(OtherConstant.CLIENTS_ORDER_RECIPE, clientsListOrder);

            ps2.setInt(1, doctorsId);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                drugsCodeExtend.add(rs2.getString(1));
                drugsNameExtend.add(rs2.getString(2));
                clientsListExtend.add(new Client(rs2.getInt(3), rs2.getString(4), rs2.getString(5), rs2.getString(6),
                        rs2.getString(7), rs2.getString(8), rs2.getString(9)));
            }
            result.put(OtherConstant.DRUGS_CODE_EXTEND_RECIPE, drugsCodeExtend);
            result.put(OtherConstant.DRUGS_NAME_EXTEND_RECIPE, drugsNameExtend);
            result.put(OtherConstant.CLIENTS_EXTEND_RECIPE, clientsListExtend);
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
     * Method cancel order recipe and delete record from order_recipe table
     *
     * @param userId unique id user who wants to check orders for recipe
     * @param drugName the name of the drug, a recipe for which it is necessary to cancel
     * @param clientId unique id client whose order should be canceled
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public void cancelRecipe(int userId, String drugName, int clientId) throws DAOException {
        String sql = SqlConstant.SQL_GET_DOCTOR_ID;
        String sql2 = SqlConstant.SQL_CANCEL_RECIPE;
        int doctorsId = NumberConstant.ZERO;

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
    /**
     * Method checks to existing client and the drug
     *
     * @param drugId drug id
     * @param clientId client id
     * @return {@code true} if exists client and the drug
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public boolean checkDrugAndClient(int drugId, int clientId) throws DAOException {
        boolean result = true;
        String sql = SqlConstant.SQL_CHECK_DRUG;
        String sql2 = SqlConstant.SQL_CHECK_CLIENT;

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
    /**
     * Method is for assign recipe. It insert record into recipes and m2m_recipes_drugs tables and delete from order_recipes.
     *
     * @param userId unique id user who wants to assign the recipe
     * @param drugId id drug that it is necessary assign
     * @param quantity drug quantity that it is necessary assign
     * @param period period of appointment drug
     * @param clientId unique id client whose recipe should be assigned
     * @param code code of drug that it is necessary assign
     * @return the name of the drug, which is assigned to the recipe
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public String assignRecipe(int userId, int drugId, int quantity, int period, int clientId, String code) throws DAOException {
        String drugName= OtherConstant.EMPTY_STRING;

        String sql = SqlConstant.SQL_GET_DOCTOR_ID;
        String sql2 = SqlConstant.SQL_ASSIGN_RECIPE;
        String sql3 = SqlConstant.SQL_COUNT_RECIPES;
        String sql4 = SqlConstant.SQL_SET_QUANTITY_DRUGS;
        String sql5 = SqlConstant.SQL_SELECT_DRUG_NAME;
        String sql6 = SqlConstant.SQL_DELETE_ORDER_RECIPE;
        int doctorsId = NumberConstant.ZERO;

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
            int recipesId = NumberConstant.ZERO;
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
    /**
     * Method checks to existing recipe with the given code
     *
     * @param code unique recipe code
     * @return {@code true} if not exists recipe
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public boolean checkDrugCode(String code) throws DAOException {
        boolean result = true;
        String sql = SqlConstant.SQL_CHECK_DRUG_CODE;

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
    /**
     * Method cancel extend recipe. It delete record from extend_recipe table.
     *
     * @param codeRecipe recipe code, which must not extend
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public void cancelExtendRecipe(String codeRecipe) throws DAOException {
        String sql = SqlConstant.SQL_CANCEL_EXTEND_RECIPE;

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
    /**
     * Method extend recipe. It changes dates for recipes table.
     *
     * @param codeRecipe recipe code, which must extend
     * @param period period of extending recipe
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public void extendRecipe(int period, String codeRecipe) throws DAOException {
        String sql = SqlConstant.SQL_EXTEND_RECIPE;

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
