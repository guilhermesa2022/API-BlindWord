package com.hotTable.API_BlindWord.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Base64;
import java.util.UUID;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.VERSION_7)
    @Column(nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int classicPoints = 0;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int duelPoints = 0;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private int chainPoints = 0;

    public UUID getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getClassicPoints() {
        return classicPoints;
    }

    public void setClassicPoints(int classicPoints){
        this.classicPoints = classicPoints;
    }

    public int getDuelPoints() {
        return duelPoints;
    }

    public void setDuelPoints(int duelPoints) {
        this.duelPoints = duelPoints;
    }

    public int getChainPoints() {
        return chainPoints;
    }

    public void setChainPoints(int chainPoints) {
        this.chainPoints = chainPoints;
    }
}