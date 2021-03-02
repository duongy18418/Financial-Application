/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financial.application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author yduong
 */
public class HomeController{

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button download;
    @FXML
    private Label name;
    @FXML
    private Label info;
    Connection connect;
    String user, pass, email, first, last;
    int income, bill, saving;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    
    public void displayInfo(String first, String last, int income, int bill, int saving){
        info.setText("Average income:\n$" + income
                    + "\nAverage living expenses:\n$" + bill
                    + "\nAverage saving:\n$" + saving);
    }

    @FXML
    private void onDownload(ActionEvent event) {
    }
    
   
    
    public void storeInfo(String user, String pass, String email, String first, String last, int income, int bill, int saving) {
        this.user = user;
        this.pass = pass;
        this.email = email;
        this.first = first;
        this.last = last;
        this.income = income;
        this.bill = bill;
        this.saving = saving;
    }
    
    void DBConnect() {
        try {
            String url = "jdbc:mysql://localhost:3306/UserDatabase?useTimezone=true&serverTimezone=UTC";
            String userDB = "root";
            String password = "root";

            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection(url, userDB, password);
            System.out.println("Connection success");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
