package com.stp.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCard")
    private Long idCard;

    private String headline;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "CardInstitution",
            joinColumns = { @JoinColumn(name = "cardId") },
            inverseJoinColumns = { @JoinColumn(name = "institutionId") }
    )
    private List<Institution> institutions;

    public List<Institution> getInstitutions() {
        return institutions;
    }

    public String getHeadline() {
        return headline;
    }

    public void setInstitutions(List<Institution> institutions) {
        this.institutions = institutions;
    }

    public void setIdCard(Long idCard) {
        this.idCard = idCard;
    }

    public Long getIdCard() {
        return idCard;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addNewInst(Institution institution){
        this.institutions.add(institution);
    }

    public void delInstFromList(Institution institution){
        this.institutions.remove(institution);
    }

    public void delInstByIndexFromList(Integer index){
        this.institutions.remove(index);
    }

    public void cleanInstList(){
        this.institutions.clear();
    }

    public boolean findInListByInstName(Card card, String name){
        for(Institution inst: card.getInstitutions()){
            if(inst.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public Integer getIndexInst(Card card, String name){
        Integer index = 0;
        for(Institution inst: card.getInstitutions()){
            if(inst.getName().equals(name)){
                return index;
            }
            index++;
        }
        return index;
    }

    public void setIdNyll() {
        this.idCard = null;
    }

    public String toString() {
        return "Id: " + this.idCard + "; headline: " + this.headline;
    }
}
