package fr.ali.amanzegouarene.tennisstatsapi.entity;

import fr.ali.amanzegouarene.tennisstatsapi.model.Country;
import fr.ali.amanzegouarene.tennisstatsapi.model.Data;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="player")
public class PlayerEntity implements Serializable {
    @Id
    @GeneratedValue()
    private Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    public boolean isOfficialPlayer() {
        return isOfficialPlayer;
    }

    public void setOfficialPlayer(boolean officialPlayer) {
        isOfficialPlayer = officialPlayer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private boolean isOfficialPlayer;

    public Long getId() {
        return id;
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

}
