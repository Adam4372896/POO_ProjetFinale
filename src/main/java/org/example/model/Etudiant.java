package org.example.model;

public class Etudiant {

    private String nom;
    private String prenom;
    private String identifiant;
    private double coteRDeLaSession;

    public Etudiant(String nom, String prenom, String identifiant, double coteRDeLaSession) {
        this.nom = nom;
        this.prenom = prenom;
        this.identifiant = identifiant;
        this.coteRDeLaSession = coteRDeLaSession;
    }

    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getIdentifiant() { return identifiant; }
    public double getCoteRDeLaSession() { return coteRDeLaSession; }

}
