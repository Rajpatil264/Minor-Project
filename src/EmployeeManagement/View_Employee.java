package EmployeeManagement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class View_Employee extends JFrame implements ActionListener {

    JLabel searchLabel, imageLabel;
    JTextField searchField;
    JButton searchButton;
    JTable table;

    ConnectionClass obj;

    View_Employee() {
        obj = new ConnectionClass();

        setTitle("View Employee");
        setSize(800, 500);
        setLayout(null);

        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("EmployeeManagement/Images/view.jpeg"));
        Image newImg = img.getImage().getScaledInstance(800, 500, Image.SCALE_DEFAULT);
        ImageIcon img1 = new ImageIcon(newImg);
        imageLabel = new JLabel(img1);
        imageLabel.setBounds(0, 0, 800, 500);

        searchLabel = new JLabel("Employee ID:");
        searchLabel.setBounds(50, 20, 100, 30);
        searchLabel.setForeground(Color.white);

        searchField = new JTextField();
        searchField.setBounds(150, 20, 150, 30);

        searchButton = new JButton("Search");
        searchButton.setBounds(320, 20, 100, 30);
        searchButton.addActionListener(this);
        searchButton.setBackground(Color.red);
        searchButton.setForeground(Color.white);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 70, 700, 350);

        add(searchLabel);
        add(searchField);
        add(searchButton);
        add(imageLabel);
        add(scrollPane);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    private void loadAllEmployees() {
        try {
            String query = "SELECT * FROM employeeTable";
            ResultSet rs = obj.stm.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("Attribute");
            model.addColumn("Value");

            while (rs.next()) {
                model.addRow(new Object[]{"Employee ID", rs.getString("employeeId")});
                model.addRow(new Object[]{"Name", rs.getString("name")});
                model.addRow(new Object[]{"Age", rs.getInt("age")});
                model.addRow(new Object[]{"Mobile", rs.getString("mobile")});
                model.addRow(new Object[]{"Email", rs.getString("email")});
                model.addRow(new Object[]{"Designation", rs.getString("designation")});
                model.addRow(new Object[]{"Education", rs.getString("education")});
                model.addRow(new Object[]{"Father's Name", rs.getString("fatherName")});
            }

            table.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while loading employees.");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            searchEmployee();
        }
    }

    private void searchEmployee() {
        try {
            String empId = searchField.getText();
            String query = "SELECT * FROM employeeTable WHERE employeeId = '" + empId + "'";
            ResultSet rs = obj.stm.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("Attribute");
            model.addColumn("Data");

            while (rs.next()) {
                model.addRow(new Object[]{"Employee ID", rs.getString("employeeId")});
                model.addRow(new Object[]{"Name", rs.getString("name")});
                model.addRow(new Object[]{"Age", rs.getInt("age")});
                model.addRow(new Object[]{"Mobile", rs.getString("mobile")});
                model.addRow(new Object[]{"Email", rs.getString("email")});
                model.addRow(new Object[]{"Designation", rs.getString("designation")});
                model.addRow(new Object[]{"Education", rs.getString("education")});
                model.addRow(new Object[]{"Father's Name", rs.getString("fatherName")});
            }

            table.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while searching for the employee.");
        }
    }

    public static void main(String[] args) {
        new View_Employee();
    }
}
