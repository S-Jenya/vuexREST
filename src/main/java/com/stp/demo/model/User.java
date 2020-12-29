package com.stp.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private Long idUser;
    @Column(
            name = "name",
            unique = true
    )
    private String name;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Card> cards;

    @Column(name = "role")
    private String role;

    public Long getIdUser() {
        return idUser;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String toString() {
        return "Id: " + this.idUser +
                "; name: " + this.name +
                "; role: " + this.role +
                "; password: " + this.password;
    }
}
