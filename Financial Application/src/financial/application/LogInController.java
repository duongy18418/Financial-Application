/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financial.application;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yduong
 */
public class LogInController{

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button logIn;
    @FXML
    private Button createAccount;
    Connection connect;
    
    public String user, pass,  email, first, last;
    private int income, bill, saving, period;

    /**
     * Initializes the controller class.
     */
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onLogIn(ActionEvent event) {
        DBConnect();
        checkUser();
    }

    private void checkUser() {
        try {
            user = username.getText();
            pass = password.getText();
            String SQL = "SELECT * FROM User WHERE username='" + user + "' and password='" + pass + "'";
            Statement checkData = connect.createStatement();
            ResultSet result = checkData.executeQuery(SQL);

            if (result.next()) {
                user = result.getString("username");
                pass = result.getString("password");
                email = result.getString("email");
                first = result.getString("first");
                last = result.getString("last");
                income = result.getInt("income");
                bill = result.getInt("bill");
                saving = result.getInt("saving");
                
                //System.out.format("%s, %s, %s, %s, %s, %s, %s, %s\n", user, pass, email, first, last, income, bill, saving);
                
                /*FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
                HomeController controller = loader.getController();
                controller.displayInfo(first, last, income, bill, saving);*/
                
                Stage stage = (Stage) logIn.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                Parent root = loader.load();
                
                MainController controller = loader.getController();
                controller.setInfo(user, pass, email, first, last, income, bill, saving, period);
                controller.displayInfo(first, last, income, bill, saving, period);
                
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The username or password were incorrect!");
                alert.setContentText("Please try again or create a new account");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onCreateAccount(ActionEvent event) {
        try {
            Stage stage = (Stage) createAccount.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    
    /*public LogInController(){
        user = username.getText();
        pass = password.getText();
    }*/

}
