package fr.ali.amanzegouarene.tennisstatsapi.model;

public class Country {

    private String picture;
    private String code;

    /**
     * No args constructor for use in serialization
     *
     */
    public Country() {
    }

    /**
     *
     * @param code
     * @param picture
     */
    public Country(String picture, String code) {
        super();
        this.picture = picture;
        this.code = code;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
