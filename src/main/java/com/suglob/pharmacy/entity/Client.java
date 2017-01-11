package com.suglob.pharmacy.entity;

public class Client extends Entity {
    private int clientsId;
    private String surname;
    private String name;
    private String patronymic;
    private String address;
    private String pasportId;
    private String email;

    public Client() {
    }

    public Client(int clientsId, String surname, String name, String patronymic, String address, String pasportId, String email) {
        this.clientsId = clientsId;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.address = address;
        this.pasportId = pasportId;
        this.email = email;
    }

    public int getClientsId() {
        return clientsId;
    }

    public void setClientsId(int clientsId) {
        this.clientsId = clientsId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPasportId() {
        return pasportId;
    }

    public void setPasportId(String pasportId) {
        this.pasportId = pasportId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
