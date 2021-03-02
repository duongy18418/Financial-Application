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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yduong
 */
public class CreateAccountController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField email;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private Button create;
    @FXML
    private Button logIn;
    Connection connect;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void onCreate(ActionEvent event) throws SQLException, ClassNotFoundException {
        DBConnect();
        checkUser();
    }

    @FXML
    private void onLogIn(ActionEvent event) {
        try {
            Stage stage = (Stage) logIn.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void registerUser() throws SQLException, ClassNotFoundException, IOException {
        String user = username.getText();
        String pass = password.getText();
        String mail = email.getText();
        String first = firstName.getText();
        String last = lastName.getText();
        int start = 0;

        String query = "INSERT INTO User(username, password, email, first, last, income, bill, saving, period) VALUE(?,?,?,?,?,?,?,?,?)";
        PreparedStatement insert = connect.prepareStatement(query);
        insert.setString(1, user);
        insert.setString(2, pass);
        insert.setString(3, mail);
        insert.setString(4, first);
        insert.setString(5, last);
        insert.setInt(6, start);
        insert.setInt(7, start);
        insert.setInt(8, start);
        insert.setInt(9, start);

        insert.execute();
        insert.close();

        Stage stage = (Stage) create.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void checkUser() {
        try {
            String user = username.getText();
            String SQL = "SELECT *FROM User WHERE username='" + user + "'";
            Statement checkData = connect.createStatement();
            ResultSet result = checkData.executeQuery(SQL);

            if (result.next()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("The username had been taken!");
                alert.setContentText("Please log in or choose a different username");

                alert.showAndWait();
            } else {
                registerUser();
            }
        } catch (SQLException ex) {
            Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CreateAccountController.class.getName()).log(Level.SEVERE, null, ex);
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

}
