package com.suglob.pharmacy.dao.impl;

import com.suglob.pharmacy.dao.UserDAO;
import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPool;
import com.suglob.pharmacy.dao.impl.pool.ConnectionPoolException;
import com.suglob.pharmacy.dao.impl.pool.ProxyConnection;
import com.suglob.pharmacy.entity.Drug;
import com.suglob.pharmacy.entity.User;
import com.suglob.pharmacy.utils.ConstantClass;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public User registration(String login, String password, String passwordRepeat, String name, String surname, String patronymic, String adress, String passportId) throws DAOException {
        String sql = "INSERT INTO users(users_id, login, password, type) VALUES(?,?,?,?)";
        String sql2 = "INSERT INTO clients(surname, name, patronymic, address, pasport_id, users_id) VALUES(?,?,?,?,?,?)";
        String sql3 = "SELECT count(*) FROM users;";

        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
        ProxyConnection con= null;
        try {
            con = pool.takeConnection();
            con.setAutoCommit(false);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        } catch (SQLException e) {
            throw new DAOException("Autocommit error", e);
        }


        int countUsers=0;

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
            ps.setString(4, "client");
            ps.executeUpdate();
            ps2.setString(1, surname);
            ps2.setString(2, name);
            ps2.setString(3, patronymic);
            ps2.setString(4, adress);
            ps2.setString(5, passportId);
            ps2.setInt(6, countUsers);
            ps2.executeUpdate();
            con.commit();
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
        User user = new User(countUsers, login, password, "client", 0);
        return user;
    }

    @Override
    public String payOrder(List<Drug> orderList) throws DAOException {
        String result="";

        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
        ProxyConnection con= null;
        try {
            con = pool.takeConnection();
            con.setAutoCommit(false);
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        } catch (SQLException e) {
            throw new DAOException("Autocommit error", e);
        }

        try (PreparedStatement ps = con.prepareStatement(ConstantClass.SQL_PAY_ORDER);
             PreparedStatement ps2 = con.prepareStatement(ConstantClass.SQL2_PAY_ORDER)) {
            boolean isOk=true;
            for (Drug drug : orderList) {
                ps.setInt(1,drug.getId());
                ResultSet rs=ps.executeQuery();
                int quantityDrugs=0;
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
                result = "payment.ok";
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

    @Override
    public int addRecipe(String recipeCode, int count, int id) throws DAOException {
        int result=0;
        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
        ProxyConnection con= null;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }

        try(Statement st=con.createStatement(); Statement st2=con.createStatement()){
            ResultSet rs=st.executeQuery("SELECT date_finish, drugs_id, quantity, used, recipes_id from recipes\n" +
                    "join m2m_recipes_drugs using(recipes_id) where code='"+recipeCode+"'");
            if (rs.next()){
                if (rs.getDate(1).after(new Date()) && rs.getInt(2)==id && rs.getInt(3)>=count+rs.getInt(4)){
                    st2.executeUpdate("update m2m_recipes_drugs set used ="+(count+rs.getInt(4))+" where recipes_id="
                            +rs.getInt(5)+" and drugs_id="+id);
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

    @Override
    public void cancelOrder(int count, int id, int id_recipe) throws DAOException {
        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
        ProxyConnection con= null;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try(Statement st=con.createStatement(); Statement st2=con.createStatement()){
            ResultSet rs=st.executeQuery("SELECT used FROM m2m_recipes_drugs where recipes_id="+id_recipe+
                    " and drugs_id="+id);
            int usedRecipe=0;
            if (rs.next()) {
                usedRecipe = rs.getInt(1);
            }
            st2.executeUpdate("update m2m_recipes_drugs set used ="+(usedRecipe-count)+" where recipes_id="
                    +id_recipe+" and drugs_id="+id);
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

    @Override
    public String drugExists(String drugName) throws DAOException {
        String result="not exists";
        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
        ProxyConnection con= null;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(ConstantClass.SQL_DRUG_EXISTS)){
            ps.setString(1,drugName);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                if (rs.getString(7).equals("N")){
                    result="not need";
                }else{
                    result="ok";
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

    @Override
    public void orderRecipe(String drugName, String doctorSurname, int userId) throws DAOException {
        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
        ProxyConnection con= null;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(ConstantClass.SQL_ORDER_RECIPE);
             PreparedStatement ps2 = con.prepareStatement(ConstantClass.SQL2_ORDER_RECIPE);
             PreparedStatement ps3 = con.prepareStatement(ConstantClass.SQL3_ORDER_RECIPE)){
            ps.setInt(1,userId);
            ResultSet rs=ps.executeQuery();
            int clientsId=0;
            if (rs.next()) {
                clientsId=rs.getInt(1);
            }
            ps2.setString(1,doctorSurname);
            ResultSet rs2=ps2.executeQuery();
            int doctorId=0;
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

    @Override
    public String recipeExists(String codeDrug) throws DAOException {
        String result="not exists";
        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
        ProxyConnection con= null;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(ConstantClass.SQL_RECIPE_EXISTS)){
            ps.setString(1,codeDrug);
            ResultSet rs=ps.executeQuery();
            if (rs.next()) {
                result="ok";
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

    @Override
    public void extendRecipe(String codeDrug) throws DAOException {
        ConnectionPool<ProxyConnection> pool = ConnectionPool.getInstance();
        ProxyConnection con= null;
        try {
            con = pool.takeConnection();
        } catch (ConnectionPoolException e) {
            throw new DAOException("Don't take connection pool", e);
        }
        try (PreparedStatement ps = con.prepareStatement(ConstantClass.SQL_EXTEND_RECIPE)){
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
