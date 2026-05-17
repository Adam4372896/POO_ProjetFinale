package org.example.model;

public class Professeur {

    private String nom;
    private String prenom;
    private String identifiant;
    private double salaire;

    public Professeur(String nom, String prenom, String identifiant, double salaire) {
        this.nom = nom;
        this.prenom = prenom;
        this.identifiant = identifiant;
        this.salaire = salaire;
    }

    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getIdentifiant() { return identifiant; }
    public double getSalaire() { return salaire; }

    @Override
    public String toString() {
        return prenom + " " + nom;
    }
}
