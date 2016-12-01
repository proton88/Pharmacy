package com.suglob.pharmacy.dao;

import com.suglob.pharmacy.dao.exception.DAOException;
import com.suglob.pharmacy.domain.User;

public interface CommonDAO {
    User logination(String login, String password) throws DAOException;
}
