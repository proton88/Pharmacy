package com.suglob.pharmacy.entity;

import com.suglob.pharmacy.service.CommonService;
import com.suglob.pharmacy.service.ServiceFactory;
import com.suglob.pharmacy.service.exception.ServiceException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Doctor extends Entity {
    private static final Logger LOGGER= LogManager.getLogger(Doctor.class);
    private int doctorId;
    private String surname;
    private String name;
    private String patronymic;
    private String clinicName;

    public Doctor() {
    }

    public Doctor(int doctorId, String surname, String name, String patronymic, String clinicName) {
        this.doctorId = doctorId;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.clinicName = clinicName;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public ArrayList<Doctor> createDoctorsList(){
        ///////////////////////////////////////////////////////////////////////////////
        ServiceFactory factory = ServiceFactory.getInstance();
        CommonService service = factory.getCommonService();
        ///////////////////////////////////////////////////////////////////////////////
        ArrayList<Doctor> doctorsList = new ArrayList<>();
        try {
            doctorsList=service.takeDoctors();
        } catch (ServiceException e) {
            LOGGER.log(Level.ERROR,e);
        }
        return doctorsList;
    }
}