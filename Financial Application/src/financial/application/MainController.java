/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financial.application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author yduong
 */
public class MainController implements Initializable {

    @FXML
    private Button income;
    @FXML
    private Button bill;
    @FXML
    private Button saving;
    @FXML
    private Button home;
    @FXML
    private Button logOut;
    @FXML
    private Button exit;
    @FXML
    private ImageView sidebar;
    @FXML
    private AnchorPane rootPane;
    private StackPane parentContainer;
    @FXML
    private Label name;
    @FXML
    private Button download;
    @FXML
    private Label info;
    public String user, pass, email, first, last;
    private int incomeNum, billNum, savingNum, period;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void onIncome(ActionEvent event) throws IOException {
       FXMLLoader loader = new FXMLLoader(getClass().getResource("Income.fxml"));
        AnchorPane root = loader.load();

        IncomeController controller = loader.getController();
        controller.storeInfo(user, pass, email, first, last, incomeNum, billNum, savingNum, period);

        rootPane.getChildren().setAll(root);
    }

    @FXML
    private void onBill(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Bill.fxml"));
        AnchorPane root = loader.load();

        BillController controller = loader.getController();
        controller.storeInfo(user, pass, email, first, last, incomeNum, billNum, savingNum, period);

        rootPane.getChildren().setAll(root);
    }

    @FXML
    private void onSaving(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Saving.fxml"));
        AnchorPane root = loader.load();

        SavingController controller = loader.getController();
        controller.storeInfo(user, pass, email, first, last, incomeNum, billNum, savingNum, period);
        
        rootPane.getChildren().setAll(root);
    }

    @FXML
    private void onHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        AnchorPane root = loader.load();

        HomeController controller = loader.getController();
        controller.displayInfo(first, last, incomeNum, billNum, savingNum);
        controller.storeInfo(user, pass, email, first, last, incomeNum, billNum, savingNum);

        rootPane.getChildren().setAll(root);
    }

    @FXML
    private void onLogOut(ActionEvent event) {
    }

    @FXML
    private void onExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void onDownload(ActionEvent event) {
        String content = info.getText();
        
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF files (.pdf)", "*.pdf"));
        File file = chooser.showSaveDialog(null);

        if (file != null) {
            try {
                saveFile(content, file);
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void saveFile(String content, File file) throws IOException {
        FileWriter fileWriter;

        fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
    }

    public void displayInfo(String first, String last, int income, int bill, int saving, int period) {
        name.setText(first + " " + last);
        info.setText("Average income:\n$" + income
                + "\nAverage living expenses:\n$" + bill
                + "\nAverage saving:\n$" + saving
                + "\nSaving period: " + period + " month(s)");
    }

    public void setInfo(String user, String pass, String email, String first, String last, int income, int bill, int saving, int period) {
        this.user = user;
        this.pass = pass;
        this.email = email;
        this.first = first;
        this.last = last;
        this.incomeNum = income;
        this.billNum = bill;
        this.savingNum = saving;
        this.period = period;
    }

}
