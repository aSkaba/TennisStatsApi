package fr.ali.amanzegouarene.tennisstatsapi.model;

public class Player {
    private Integer id;
    private String firstname;
    private String lastname;
    private String shortname;
    private String sex;
    private Country country;
    private String picture;
    private Data data;

    /**
     * No args constructor for use in serialization
     *
     */
    public Player() {
    }

    /**
     *
     * @param country
     * @param firstname
     * @param data
     * @param sex
     * @param id
     * @param shortname
     * @param picture
     * @param lastname
     */
    public Player(Integer id, String firstname, String lastname, String shortname, String sex, Country country, String picture, Data data) {
        super();
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.shortname = shortname;
        this.sex = sex;
        this.country = country;
        this.picture = picture;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
