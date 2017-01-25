package com.suglob.pharmacy.service;

import com.suglob.pharmacy.service.impl.*;

public class ServiceFactory {
    private static final ServiceFactory factory=new ServiceFactory();

    private final CommonService commonService = new CommonServiceImpl();
    private final ClientService clientService = new ClientServiceImpl();
    private final DoctorService doctorService = new DoctorServiceImpl();
    private final PharmacistService pharmacistService = new PharmacistServiceImpl();

    private ServiceFactory(){}


    public static ServiceFactory getInstance() {return factory;}

    public CommonService getCommonService() {
        return commonService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public DoctorService getDoctorService() {
        return doctorService;
    }

    public PharmacistService getPharmacistService() {
        return pharmacistService;
    }
}
