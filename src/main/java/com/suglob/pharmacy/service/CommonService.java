package com.suglob.pharmacy.service;

import com.suglob.pharmacy.domain.User;
import com.suglob.pharmacy.service.exception.ServiceException;

public interface CommonService {
    User logination(String login, String password) throws ServiceException;
}
