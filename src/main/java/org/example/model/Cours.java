package org.example.model;

public class Cours {

    private String nom;
    private String code;
    private String description;
    private Professeur professeur;

    public Cours(String nom, String code, String description, Professeur professeur) {
        this.nom = nom;
        this.code = code;
        this.description = description;
        this.professeur = professeur;
    }

    public String getNom() { return nom; }
    public String getCode() { return code; }
    public String getDescription() { return description; }
    public Professeur getProfesseur() { return professeur; }
}
