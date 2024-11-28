package EmployeeManagement;

import java.sql.*;

public class ConnectionClass {
    Connection connectDatabase;
    Statement stm;

    ConnectionClass() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connectDatabase = DriverManager.getConnection("jdbc:mysql://localhost:3306/employeeManagement", "root", "Raj@2003");
            stm = connectDatabase.createStatement();
        } catch (Exception connectionError) {
            connectionError.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ConnectionClass();
    }
}
