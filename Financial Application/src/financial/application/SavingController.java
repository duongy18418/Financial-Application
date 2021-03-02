/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financial.application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author yduong
 */
public class SavingController implements Initializable {

    @FXML
    private Button submit;

    Connection connect;
    String user, pass, email, first, last;
    int income, bill, saving, month;
    int sum = 0;
    @FXML
    private TextField average;
    @FXML
    private ChoiceBox<String> period;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        period.getItems().addAll("1 month", "2 months", "3 months", "4 months", "5 months", "6 months");
    }

    @FXML
    private void onSubmit(ActionEvent event) throws IOException {
        DBConnect();
        insertData();
        
        Stage stage = (Stage) submit.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();

        MainController controller = loader.getController();
        controller.setInfo(user, pass, email, first, last, income, bill, saving, month);
        controller.displayInfo(first, last, income, bill, saving, month);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void insertData() {
        saving = Integer.parseInt(average.getText());
        
        if (period.getValue() == "1 month"){
            month = 1;
        } else if (period.getValue() == "2 months"){
            month = 2;
        } else if (period.getValue() == "3 months"){
            month = 3;
        } else if (period.getValue() == "4 months"){
            month = 4;
        } else if (period.getValue() == "5 months"){
            month = 5;
        } else if (period.getValue() == "6 months"){
            month = 6;
        } else {
            month = 0;
        }
        
        try {
            String SQL = "SELECT * FROM User WHERE username='" + user + "' and password='" + pass + "'";
            Statement checkData = connect.createStatement();
            ResultSet result = checkData.executeQuery(SQL);

            if (result.next()) {
                String query = "UPDATE User SET saving=" + saving + " WHERE username='" + user + "' and password='" + pass + "'";
                PreparedStatement insert = connect.prepareStatement(query);
                insert.executeUpdate();
                insert.close();
                
                String query2 = "UPDATE User SET period=" + month + " WHERE username='" + user + "' and password='" + pass + "'";
                PreparedStatement insert2 = connect.prepareStatement(query2);
                insert2.executeUpdate();
                insert2.close();
            } else {

            }
        } catch (SQLException ex) {
            Logger.getLogger(SavingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void storeInfo(String user, String pass, String email, String first, String last, int income, int bill, int saving, int period) {
        this.user = user;
        this.pass = pass;
        this.email = email;
        this.first = first;
        this.last = last;
        this.income = income;
        this.bill = bill;
        this.saving = saving;
        this.month = period;
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
            Logger.getLogger(SavingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
