/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financial.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yduong
 */
public class GetConnection {

    public Connection DatabaseConnection() {
        String url = "jdbc:mysql://localhost:3306/UserDatabase?useTimezone=true&serverTimezone=UTC";
        String user = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connect = DriverManager.getConnection(url, user, password);
            System.out.println("Connection success");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FinancialApplication.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(FinancialApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
