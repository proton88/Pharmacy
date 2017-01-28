package com.suglob.pharmacy.dao.impl;

import com.suglob.pharmacy.constant.MessageConstant;
import com.suglob.pharmacy.constant.NumberConstant;
import com.suglob.pharmacy.constant.OtherConstant;
import com.suglob.pharmacy.constant.SqlConstant;
import com.suglob.pharmacy.dao.UserDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.pool.ConnectionPool;
import com.suglob.pharmacy.pool.ConnectionPoolException;
import com.suglob.pharmacy.pool.ProxyConnection;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
/**
 * This class contains methods to get, delete, add or modify data in the database.
 * All methods are associated with the user: 'client'.
 */
public class UserDAOImpl implements UserDAO {
    /**
     * Returns user after registration
     *
     * @param login unique user login
     * @param password unique user password
     * @param passwordRepeat repeat of unique user password
     * @param name name of user
     * @param surname surname of user
     * @param patronymic patronymic of user
     * @param adress user adress
     * @param passportId passport number of user
     * @param email unique user email
     * @return user if the registration is successful
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public User registration(String login, String password, String passwordRepeat, String name, String surname,
                             String patronymic, String adress, String passportId, String email) throws DAOException {
        String sql = SqlConstant.SQL_ADD_USER;
        String sql2 = SqlConstant.SQL_ADD_CLIENT;
        String sql3 = SqlConstant.SQL_COUNT_USERS;

        User user;

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


        int countUsers= NumberConstant.ZERO;
        try (PreparedStatement ps = con.prepareStatement(sql);
             PreparedStatement ps2 = con.prepareStatement(sql2);
             PreparedStatement ps3 = con.prepareStatement(sql3)){

            ResultSet rs=ps3.executeQuery(sql3);
            if (rs.next()){
                countUsers=rs.getInt(1);
                countUsers++;
            }
            ps.setInt(1, countUsers);
            ps.setString(2, login);
            ps.setString(3, DigestUtils.md5Hex(password));
            ps.setString(4, OtherConstant.CLIENT);
            ps.executeUpdate();
            ps2.setString(1, surname);
            ps2.setString(2, name);
            ps2.setString(3, patronymic);
            ps2.setString(4, adress);
            ps2.setString(5, passportId);
            ps2.setInt(6, countUsers);
            ps2.setString(7,email);
            ps2.executeUpdate();
            con.commit();
            user = new User(countUsers, login, password, OtherConstant.CLIENT, NumberConstant.ZERO);
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new DAOException("Rollback error",e);
            }
            throw new DAOException("Wrong sql request",e);
        }finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Connection pool don't release connection",e);
            }
        }

        return user;
    }
    /**
     * Returns result after paying order
     *
     * @param orderList list ordered drugs
     * @return result operation
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public String payOrder(List<Drug> orderList) throws DAOException {
        String result= OtherConstant.EMPTY_STRING;

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

        try (PreparedStatement ps = con.prepareStatement(SqlConstant.SQL_GET_QUANTITY_DRUGS);
             PreparedStatement ps2 = con.prepareStatement(SqlConstant.SQL_UPDATE_QUANTITY_DRUGS)) {
            boolean isOk=true;
            for (Drug drug : orderList) {
                ps.setInt(1,drug.getId());
                ResultSet rs=ps.executeQuery();
                int quantityDrugs= NumberConstant.ZERO;
                if (rs.next()){
                    quantityDrugs=rs.getInt(1);
                }
                if (quantityDrugs>=drug.getCount()){
                    ps2.setInt(1,quantityDrugs-drug.getCount());
                    ps2.setInt(2,drug.getId());
                    ps2.executeUpdate();
                }else {
                    result="Don't drug: "+drug.getName()+", left: "+quantityDrugs;
                    isOk=false;
                    break;

                }
            }
            if (isOk) {
                result = MessageConstant.PAYMENT_OK;
                con.commit();
            }else {
                con.rollback();
            }
            return result;

        }catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                throw new DAOException("Rollback error",e);
            }
            throw new DAOException(e);
        }finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Connection pool don't release connection",e);
            }
        }

    }
    /**
     * Returns recipe id after adding order
     *
     * @param recipeCode unique recipe code
     * @param count the amount of drugs in the recipe
     * @param id unique drug id
     * @return recipe id, which was added
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public int addOrder(String recipeCode, int count, int id) throws DAOException {
        int result= NumberConstant.ZERO;
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }

        try(PreparedStatement ps=con.prepareStatement(SqlConstant.SQL_GET_RECIPE);
            PreparedStatement ps2=con.prepareStatement(SqlConstant.SQL_ADD_USED_DRUGS)){
            ps.setString(1,recipeCode);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                if (rs.getDate(1).after(new Date()) && rs.getInt(2)==id && rs.getInt(3)>=count+rs.getInt(4)){
                    ps2.setInt(1,count+rs.getInt(4));
                    ps2.setInt(2,rs.getInt(5));
                    ps2.setInt(3,id);
                    ps2.executeUpdate();
                    result=rs.getInt(5);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Connection pool don't release connection",e);
            }
        }
        return result;
    }
    /**
     * Cancel order
     *
     * @param count the number of drugs that are canceled
     * @param id unique drug id
     * @param recipeId unique recipe id
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public void cancelOrder(int count, int id, int recipeId) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try(PreparedStatement ps=con.prepareStatement(SqlConstant.SQL_GET_COUNT_USED_DRUGS);
            PreparedStatement ps2=con.prepareStatement(SqlConstant.SQL_CANCEL_USED_DRUGS)){
            ps.setInt(1,recipeId);
            ps.setInt(2,id);
            ResultSet rs=ps.executeQuery();
            int usedRecipe= NumberConstant.ZERO;
            if (rs.next()) {
                usedRecipe = rs.getInt(1);
            }
            ps2.setInt(1,usedRecipe-count);
            ps2.setInt(2,recipeId);
            ps2.setInt(3,id);
            ps2.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Connection pool don't release connection",e);
            }
        }
    }
    /**
     * Method checks to existing drug
     *
     * @param drugName name of drug
     * @return result of checking
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public String drugExists(String drugName) throws DAOException {
        String result= MessageConstant.NOT_EXIST;
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(SqlConstant.SQL_DRUG_EXISTS)){
            ps.setString(1,drugName);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                if (OtherConstant.N.equals(rs.getString(7))){
                    result= MessageConstant.NOT_NEED;
                }else{
                    result= OtherConstant.OK;
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Connection pool don't release connection",e);
            }
        }
        return result;
    }
    /**
     * Method add recipe in order_recipes table.
     *
     * @param drugName name of drug
     * @param doctorSurname surname of doctor who want to order recipe
     * @param userId unique id user who wants to order recipe
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public void orderRecipe(String drugName, String doctorSurname, int userId) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(SqlConstant.SQL_SELECT_CLIENT_ID);
             PreparedStatement ps2 = con.prepareStatement(SqlConstant.SQL_SELECT_DOCTOR_ID);
             PreparedStatement ps3 = con.prepareStatement(SqlConstant.SQL_ADD_ORDER_RECIPE)){
            ps.setInt(1,userId);
            ResultSet rs=ps.executeQuery();
            int clientsId= NumberConstant.ZERO;
            if (rs.next()) {
                clientsId=rs.getInt(1);
            }
            ps2.setString(1,doctorSurname);
            ResultSet rs2=ps2.executeQuery();
            int doctorId= NumberConstant.ZERO;
            if (rs2.next()) {
                doctorId=rs2.getInt(1);
            }
            drugName=drugName.toLowerCase();
            drugName=drugName.substring(0, 1).toUpperCase() + drugName.substring(1);
            ps3.setString(1,drugName);
            ps3.setInt(2,clientsId);
            ps3.setInt(3,doctorId);
            ps3.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Connection pool don't release connection",e);
            }
        }

    }
    /**
     * Method checks to existing recipe.
     *
     * @param codeDrug code of drug
     * @return result of checking
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public String recipeExists(String codeDrug) throws DAOException {
        String result= MessageConstant.NOT_EXIST;
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(SqlConstant.SQL_RECIPE_EXISTS)){
            ps.setString(1,codeDrug);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                result= OtherConstant.OK;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Connection pool don't release connection",e);
            }
        }
        return result;
    }
    /**
     * Method return result after adding order for extend recipe
     *
     * @param codeDrug unique drug code
     * @throws DAOException if ConnectionPoolException or SQLException arise.
     */
    @Override
    public void orderExtendRecipe(String codeDrug) throws DAOException {
        ConnectionPool pool = ConnectionPool.getInstance();
        ProxyConnection con;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(SqlConstant.SQL_ADD_EXTEND_RECIPE)){
            ps.setString(1,codeDrug);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }finally {
            try {
                ConnectionPool.getInstance().releaseConnection(con);
            } catch (ConnectionPoolException e) {
                throw new DAOException("Connection pool don't release connection",e);
            }
        }
    }
}
