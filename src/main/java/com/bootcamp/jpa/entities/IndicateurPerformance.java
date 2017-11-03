/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.jpa.entities;

import com.bootcamp.jpa.enums.TypeIndicateur;
import java.io.Serializable; 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id; 
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Administrateur
 */
@Entity
@Table(name = "tp_indicateur_performance") 
public class IndicateurPerformance implements Serializable {
    /*
    *Debut de la 
    *declaration des variables de la classe
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Ce champs ne doit pas etre vide")
    private String libelle,nature,nom,propriete;
    @NotNull(message = "Ce champs ne doit pas etre vide")
    private String valeur;
    @NotNull(message = "Ce champs ne doit pas etre vide")
    private TypeIndicateur typeIndicateur;
    
    
    /*
    *fin de la
    *declaration des variables de la classe
    */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPropriete() {
        return propriete;
    }

    public void setPropriete(String propriete) {
        this.propriete = propriete;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public TypeIndicateur getTypeIndicateur() {
        return typeIndicateur;
    }

    public void setTypeIndicateur(TypeIndicateur typeIndicateur) {
        this.typeIndicateur = typeIndicateur;
    }
       
    
}
