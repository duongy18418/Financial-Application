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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yduong
 */
public class IncomeController implements Initializable {

    @FXML
    private TextField amount2;
    @FXML
    private TextField amount3;
    @FXML
    private Button submit;
    @FXML
    private ChoiceBox<String> type1;
    @FXML
    private ChoiceBox<String> type2;
    @FXML
    private ChoiceBox<String> type3;
    @FXML
    private TextField amount1;
    Connection connect;
    String user, pass, email, first, last;
    int income, bill, saving, period;
    int sum = 0;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setType(type1, type2, type3);
    }

    @FXML
    private void onSubmit(ActionEvent event) throws SQLException {
        try {
            DBConnect();
            checkDB();
            calculate();
            insertToMainTable();
            
            Stage stage = (Stage) submit.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
            Parent root = loader.load();
            
            MainController controller = loader.getController();
            controller.setInfo(user, pass, email, first, last, income, bill, saving, period);
            controller.displayInfo(first, last, income, bill, saving, period);
            
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(IncomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setType(ChoiceBox<String> choice1, ChoiceBox<String> choice2, ChoiceBox<String> choice3) {
        choice1.getItems().addAll("Monthly", "Yearly", "Self Employed");
        choice2.getItems().addAll("Monthly", "Yearly", "Self Employed");
        choice3.getItems().addAll("Monthly", "Yearly", "Self Employed");
    }

    private void checkDB() {
        try {
            String tableName = user + "IncomeTable";
            System.out.println(tableName);
            String SQL = "create table " + tableName + " ( type char(40) NOT NULL, amount int NOT NULL)";

            DatabaseMetaData dbm = connect.getMetaData();
            ResultSet tables = dbm.getTables(null, null, tableName, null);

            if (tables.next()) {
                System.out.println("Table exist!");
            } else {
                PreparedStatement create = connect.prepareStatement(SQL);
                create.execute();

                String query = "INSERT INTO " + tableName + "(type, amount) VALUE(?,?)";
                PreparedStatement insert = connect.prepareStatement(query);
                insert.setString(1, "Monthly");
                insert.setInt(2, 0);

                PreparedStatement insert2 = connect.prepareStatement(query);
                insert2.setString(1, "Yearly");
                insert2.setInt(2, 0);

                PreparedStatement insert3 = connect.prepareStatement(query);
                insert3.setString(1, "Self Employed");
                insert3.setInt(2, 0);

                insert.execute();
                insert2.execute();
                insert3.execute();
                insert.close();
                insert2.close();
                insert3.close();

                System.out.println("New table created");
            }
        } catch (SQLException ex) {
            Logger.getLogger(IncomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void insertToMainTable() {
        try {
            
            String SQL = "SELECT * FROM User WHERE username='" + user + "' and password='" + pass + "'";
            Statement checkData = connect.createStatement();
            ResultSet result = checkData.executeQuery(SQL);

            if (result.next()) {
                String query = "UPDATE User SET income=" + income + " WHERE username='" + user + "' and password='" + pass + "'";
                PreparedStatement insert = connect.prepareStatement(query);
                insert.executeUpdate();
                insert.close();
            } else {

            }

        } catch (SQLException ex) {
            Logger.getLogger(IncomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void calculate() throws SQLException {
        getType (type1, amount1);
        getType (type2, amount2);
        getType (type3, amount3);
        
        income = sum;
        System.out.println("Income: " + income);
    }
    
    private void getType(ChoiceBox<String> choice, TextField field) throws SQLException {
        String tableName = user + "IncomeTable";
        
        if (choice.getValue() == "Monthly") {
            String text = choice.getValue().toString();
            String SQL = "SELECT * FROM " + tableName + " WHERE type='" + text + "'";
            Statement checkData = connect.createStatement();
            ResultSet result = checkData.executeQuery(SQL);

            if (result.next()) {
                int num = Integer.parseInt(field.getText());
                sum += num;
                String query = "UPDATE " + tableName + " SET amount=" + num + " WHERE type='" + text + "'";
                PreparedStatement insert = connect.prepareStatement(query);
                insert.executeUpdate();
                insert.close();
            }
        } else if (choice.getValue() == "Yearly") {
            String text = choice.getValue().toString();
            String SQL = "SELECT * FROM " + tableName + " WHERE type='" + text + "'";
            Statement checkData = connect.createStatement();
            ResultSet result = checkData.executeQuery(SQL);

            if (result.next()) {
                int num = Integer.parseInt(field.getText());
                sum += num;
                String query = "UPDATE " + tableName + " SET amount=" + num + " WHERE type='" + text + "'";
                PreparedStatement insert = connect.prepareStatement(query);
                insert.executeUpdate();
                insert.close();
            }
        } else if (choice.getValue() == "Self Employed") {
            String text = choice.getValue().toString();
            String SQL = "SELECT * FROM " + tableName + " WHERE type='" + text + "'";
            Statement checkData = connect.createStatement();
            ResultSet result = checkData.executeQuery(SQL);

            if (result.next()) {
                int num = Integer.parseInt(field.getText());
                sum += num;
                String query = "UPDATE " + tableName + " SET amount=" + num + " WHERE type='" + text + "'";
                PreparedStatement insert = connect.prepareStatement(query);
                insert.executeUpdate();
                insert.close();
            }
        } else {
            sum += 0;
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
        this.period = period;
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
