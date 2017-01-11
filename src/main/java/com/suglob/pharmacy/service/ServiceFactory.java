package com.suglob.pharmacy.service;

import com.suglob.pharmacy.service.impl.ClientServiceImpl;
import com.suglob.pharmacy.service.impl.CommonServiceImpl;
import com.suglob.pharmacy.service.impl.DoctorServiceImpl;
import com.suglob.pharmacy.service.impl.PoolServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory factory=new ServiceFactory();

    private final CommonService commonService = new CommonServiceImpl();
    private final PoolService poolService = new PoolServiceImpl();
    private final ClientService clientService = new ClientServiceImpl();
    private final DoctorService doctorService = new DoctorServiceImpl();

    private ServiceFactory(){}


    public static ServiceFactory getInstance() {return factory;}

    public CommonService getCommonService() {
        return commonService;
    }

    public PoolService getPoolService() {
        return poolService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public DoctorService getDoctorService() {
        return doctorService;
    }
}
