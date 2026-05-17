package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.model.Professeur;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AjoutProfController {

    @FXML private TextField champNom;
    @FXML private TextField champPrenom;
    @FXML private TextField champId;
    @FXML private TextField champSalaire;

    private Professeur professeurCree = null;

    @FXML
    private void handleSauvegarder() {
        String nom = champNom.getText().trim();
        String prenom = champPrenom.getText().trim();
        String id = champId.getText().trim();
        String salaireTexte = champSalaire.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || id.isEmpty() || salaireTexte.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs");
            return;
        }

        double salaire = Double.parseDouble(salaireTexte);
        professeurCree = new Professeur(nom, prenom, id, salaire);

        // Sauvegarder dans profs.json
        sauvegarderJson();

        // Fermer le popup
        Stage stage = (Stage) champNom.getScene().getWindow();
        stage.close();
    }

    private void sauvegarderJson() {
        Gson gson = new Gson();
        List<Professeur> liste = new ArrayList<>();

        // Lire le fichier existant
        File fichier = new File("profs.json");
        if (fichier.exists()) {
            try (Reader reader = new FileReader(fichier)) {
                Type type = new TypeToken<List<Professeur>>(){}.getType();
                liste = gson.fromJson(reader, type);
                if (liste == null) liste = new ArrayList<>();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Ajouter le nouveau prof
        liste.add(professeurCree);

        // Écrire dans le fichier
        try (Writer writer = new FileWriter(fichier)) {
            gson.toJson(liste, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAnnuler() {
        Stage stage = (Stage) champNom.getScene().getWindow();
        stage.close();
    }

    public Professeur getProfesseurCree() {
        return professeurCree;
    }
}