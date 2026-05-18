package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import org.example.model.Cours;
import org.example.model.Etudiant;
import org.example.model.Professeur;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class MainController {

    // date pour accueil
    @FXML private Label labelDate;

    // Table des professeurs
    @FXML private TableView<Professeur> tableProfs;
    @FXML private TableColumn<Professeur, String> colProfNom;
    @FXML private TableColumn<Professeur, String> colProfPrenom;
    @FXML private TableColumn<Professeur, String> colProfId;
    @FXML private TableColumn<Professeur, String> colProfSalaire;

    // Table des étudiants
    @FXML private TableView<Etudiant> tableEtudiants;
    @FXML private TableColumn<Etudiant, String> colEtudiantNom;
    @FXML private TableColumn<Etudiant, String> colEtudiantPrenom;
    @FXML private TableColumn<Etudiant, String> colEtudiantId;
    @FXML private TableColumn<Etudiant, String> colEtudiantCoteR;

    // Table des cours
    @FXML private TableView<Cours> tableCours;
    @FXML private TableColumn<Cours, String> colCoursNom;
    @FXML private TableColumn<Cours, String> colCoursCode;
    @FXML private TableColumn<Cours, String> colCoursDescription;
    @FXML private TableColumn<Cours, String> colCoursProf;

    private ObservableList<Professeur> listeProfs = FXCollections.observableArrayList();
    private ObservableList<Etudiant> listeEtudiants = FXCollections.observableArrayList();
    private ObservableList<Cours> listeCours = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        labelDate.setText("Aujourd'hui : " + java.time.LocalDate.now());

        // Configurer les colonnes des professeurs
        colProfNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNom()));
        colProfPrenom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPrenom()));
        colProfId.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getIdentifiant()));
        colProfSalaire.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getSalaire())));
        tableProfs.setItems(listeProfs);

        // Configurer les colonnes des étudiants
        colEtudiantNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNom()));
        colEtudiantPrenom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPrenom()));
        colEtudiantId.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getIdentifiant())));
        colEtudiantCoteR.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getCoteRDeLaSession())));
        tableEtudiants.setItems(listeEtudiants);

        // Configurer les colonnes des cours
        colCoursNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNom()));
        colCoursCode.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCode()));
        colCoursDescription.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDescription()));
        colCoursProf.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getProfesseur().getNom()));
        tableCours.setItems(listeCours);

        // Charger depuis le fichier xxx.json
        chargerProfs();
        chargerCours();
        chargerEtudiants();
    }

    private void chargerProfs() {
        Gson gson = new Gson();
        File fichier = new File("profs.json");
        if (fichier.exists()) {
            try (Reader reader = new FileReader(fichier)) {
                Type type = new TypeToken<List<Professeur>>(){}.getType();
                List<Professeur> liste = gson.fromJson(reader, type);
                if (liste != null) {
                    listeProfs.addAll(liste);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void chargerCours() {
        Gson gson = new Gson();
        File fichier = new File("cours.json");
        if (fichier.exists()) {
            try (Reader reader = new FileReader(fichier)) {
                Type type = new TypeToken<List<Cours>>(){}.getType();
                List<Cours> liste = gson.fromJson(reader, type);
                if (liste != null) {
                    listeCours.addAll(liste);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void chargerEtudiants() {
        Gson gson = new Gson();
        File fichier = new File("etudiants.json");
        if (fichier.exists()) {
            try (Reader reader = new FileReader(fichier)) {
                Type type = new TypeToken<List<Etudiant>>(){}.getType();
                List<Etudiant> liste = gson.fromJson(reader, type);
                if (liste != null) {
                    listeEtudiants.addAll(liste);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleAjouterProf() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ajout-prof-view.fxml"));
            Stage popup = new Stage();
            popup.setTitle("Ajouter un professeur");
            popup.setScene(new Scene(loader.load(), 400, 300));
            popup.initModality(Modality.WINDOW_MODAL);
            popup.initOwner(tableProfs.getScene().getWindow());
            popup.showAndWait();

            AjoutProfController ctrl = loader.getController();
            Professeur nouveau = ctrl.getProfesseurCree();
            if (nouveau != null) {
                listeProfs.add(nouveau);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAjouterEtudiant() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ajout-etudiant-view.fxml"));
            Stage popup = new Stage();
            popup.setTitle("Ajouter un étudiant");
            popup.setScene(new Scene(loader.load(), 400, 300));
            popup.initModality(Modality.WINDOW_MODAL);
            popup.initOwner(tableEtudiants.getScene().getWindow());
            popup.showAndWait();

            AjoutEtudiantController ctrl = loader.getController();
            Etudiant nouveau = ctrl.getEtudiantCree();
            if (nouveau != null) {
                listeEtudiants.add(nouveau);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAjouterCours() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ajout-cours-view.fxml"));
            Stage popup = new Stage();
            popup.setTitle("Ajouter un cours");
            popup.setScene(new Scene(loader.load(), 450, 350));
            popup.initModality(Modality.WINDOW_MODAL);
            popup.initOwner(tableCours.getScene().getWindow());
            popup.showAndWait();

            AjoutCoursController ctrl = loader.getController();
            Cours nouveau = ctrl.getCoursCree();
            if (nouveau != null) {
                listeCours.add(nouveau);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}