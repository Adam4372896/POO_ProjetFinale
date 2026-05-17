package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.model.Cours;
import org.example.model.Professeur;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AjoutCoursController {

    @FXML private TextField champNom;
    @FXML private TextField champCode;
    @FXML private TextArea champDescription;
    @FXML private ComboBox<Professeur> comboProf;

    private Cours coursCree = null;

    @FXML
    public void initialize() {
        // Charger les profs depuis profs.json pour le dropdown
        Gson gson = new Gson();
        File fichier = new File("profs.json");
        if (fichier.exists()) {
            try (Reader reader = new FileReader(fichier)) {
                Type type = new TypeToken<List<Professeur>>(){}.getType();
                List<Professeur> liste = gson.fromJson(reader, type);
                if (liste != null) {
                    comboProf.getItems().addAll(liste);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleSauvegarder() {
        String nom = champNom.getText().trim();
        String code = champCode.getText().trim();
        String description = champDescription.getText().trim();
        Professeur prof = comboProf.getValue();

        if (nom.isEmpty() || code.isEmpty() || description.isEmpty() || prof == null) {
            System.out.println("Veuillez remplir tous les champs");
            return;
        }

        coursCree = new Cours(nom, code, description, prof);
        sauvegarderJson();

        Stage stage = (Stage) champNom.getScene().getWindow();
        stage.close();
    }

    private void sauvegarderJson() {
        Gson gson = new Gson();
        List<Cours> liste = new ArrayList<>();

        File fichier = new File("cours.json");
        if (fichier.exists()) {
            try (Reader reader = new FileReader(fichier)) {
                Type type = new TypeToken<List<Cours>>(){}.getType();
                liste = gson.fromJson(reader, type);
                if (liste == null) liste = new ArrayList<>();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        liste.add(coursCree);

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

    public Cours getCoursCree() {
        return coursCree;
    }
}