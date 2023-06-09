/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package clientlourd;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author olivi
 */
public class CommandesHistController implements Initializable {

    @FXML
    private TableColumn<Commandes, Integer> colId;
    @FXML
    private TableColumn<Commandes, String> colEtat;
    @FXML
    private TableColumn<Commandes, String> colDate;
    @FXML
    private TableColumn<Commandes, Integer> colIdU;
    @FXML
    private TableColumn<Commandes, String> colEmail;
    @FXML
    private Button btnRetour;
    @FXML
    private Button btnMenu;
    @FXML
    private Button btnDeco;
    @FXML
    private TableView<Commandes> tvCommandes;
    @FXML
    public Label labelUtilisateur;
    public int userId;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        showCommandes();
    }    

    @FXML
    private void handleMouseAction(MouseEvent event) {
    }

    @FXML
    private void retour(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("clientsGui.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToMenu(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }  

    @FXML
    public void disconnect(ActionEvent event) throws Exception {
        root = FXMLLoader.load(getClass().getResource("authentification.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }  
    
    
    
    
    public Connection getConnection() {
        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://192.168.3.30:3306/slam4", "slam4", "LoKb5nDr!B");
            return conn;
        }catch(Exception e) {
            System.out.println("Error " + e.getMessage());
            return null;
        }
    }
    
    
    public ObservableList<Commandes> getCommandesList() {
        ObservableList<Commandes> commandesList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT c.Id_Commandes, c.etat, c.date_de_commande, c.Id_Utilisateur, u.email  FROM Commandes c INNER JOIN Utilisateur u ON c.Id_Utilisateur=u.Id_Utilisateur WHERE c.Id_Utilisateur=" + userId +"";
                
        Statement st;
        ResultSet rs;
        
        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Commandes commandes;
            while (rs.next()) {
                commandes = new Commandes(rs.getInt("Id_Commandes"), rs.getString("etat"), rs.getString("date_de_commande"), rs.getInt("Id_Utilisateur"),rs.getString("email"));
                commandesList.add(commandes);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return commandesList;
    }
    
    
    public void showCommandes() {
        ObservableList<Commandes> list = getCommandesList();
        
        colId.setCellValueFactory(new PropertyValueFactory<Commandes, Integer>("id"));  
        colEtat.setCellValueFactory(new PropertyValueFactory<Commandes, String>("etat"));        
        colDate.setCellValueFactory(new PropertyValueFactory<Commandes, String>("date"));     
        colIdU.setCellValueFactory(new PropertyValueFactory<Commandes, Integer>("idU"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Commandes, String>("utilisateur"));
    
        
        tvCommandes.setItems(list);
    }
}
