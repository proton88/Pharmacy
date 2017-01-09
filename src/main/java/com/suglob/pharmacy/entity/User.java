package com.suglob.pharmacy.entity;


public class User extends Entity {
    private int id;
    private int block;
    private String login;
    private String password;
    private String type;

    public User() {
    }

    public User(int id, String login, String password, String type, int block) {
        this.id=id;
        this.block = block;
        this.login = login;
        this.password = password;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
