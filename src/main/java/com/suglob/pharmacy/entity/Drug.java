package com.suglob.pharmacy.entity;

import java.math.BigDecimal;

public class Drug extends Entity {
    private int id;
    private String name;
    private String dosage;
    private String country;
    private BigDecimal price;
    private int count;
    private String isRecipe;

    public Drug() {
    }

    public Drug(int id, String name, String dosage, String country, BigDecimal price, int count, String isRecipe) {
        this.id=id;
        this.name = name;
        this.dosage = dosage;
        this.country = country;
        this.price = price;
        this.count=count;
        this.isRecipe = isRecipe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getIsRecipe() {
        return isRecipe;
    }

    public void setIsRecipe(String isRecipe) {
        this.isRecipe = isRecipe;
    }
}
