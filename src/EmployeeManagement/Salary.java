package EmployeeManagement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Salary extends JFrame implements ActionListener {

    JLabel empIdLabel, nameLabel, emailLabel, hraLabel, daLabel, midLabel, pfLabel, basicSalaryLabel, monthLabel, yearLabel, imageLabel;
    JComboBox<String> empIdDropdown, monthDropdown, yearDropdown;
    JTextField nameField, emailField, hraField, daField, midField, pfField, basicSalaryField;
    JButton displayButton, submitButton, cancelButton;

    ConnectionClass obj;

    Salary() {
        obj = new ConnectionClass();

        setTitle("Salary Details");
        setSize(800, 500);
        setLayout(null);

        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("EmployeeManagement/Images/profile.jpg"));
        Image newImg = img.getImage().getScaledInstance(800, 500, Image.SCALE_DEFAULT);
        ImageIcon img1 = new ImageIcon(newImg);
        imageLabel = new JLabel(img1);
        imageLabel.setBounds(0, 0, 800, 500);

        empIdLabel = new JLabel("Employee ID:");
        empIdLabel.setBounds(50, 20, 100, 30);
        empIdLabel.setForeground(Color.white);

        empIdDropdown = new JComboBox<>();
        empIdDropdown.setBounds(170, 20, 150, 30);
        empIdDropdown.addActionListener(this);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 60, 100, 30);
        nameLabel.setForeground(Color.white);

        nameField = new JTextField();
        nameField.setBounds(170, 60, 150, 30);
        nameField.setEditable(false);

        emailLabel = new JLabel("Email ID:");
        emailLabel.setBounds(50, 100, 100, 30);
        emailLabel.setForeground(Color.white);

        emailField = new JTextField();
        emailField.setBounds(170, 100, 150, 30);
        emailField.setEditable(false);

        hraLabel = new JLabel("HRA:");
        hraLabel.setBounds(50, 140, 100, 30);
        hraLabel.setForeground(Color.white);

        hraField = new JTextField();
        hraField.setBounds(170, 140, 150, 30);

        daLabel = new JLabel("DA:");
        daLabel.setBounds(50, 180, 100, 30);
        daLabel.setForeground(Color.white);

        daField = new JTextField();
        daField.setBounds(170, 180, 150, 30);

        midLabel = new JLabel("MID:");
        midLabel.setBounds(400, 20, 100, 30);
        midLabel.setForeground(Color.white);

        midField = new JTextField();
        midField.setBounds(520, 20, 150, 30);

        pfLabel = new JLabel("PF:");
        pfLabel.setBounds(400, 60, 100, 30);
        pfLabel.setForeground(Color.white);

        pfField = new JTextField();
        pfField.setBounds(520, 60, 150, 30);

        basicSalaryLabel = new JLabel("Basic Salary:");
        basicSalaryLabel.setBounds(400, 100, 100, 30);
        basicSalaryLabel.setForeground(Color.white);

        basicSalaryField = new JTextField();
        basicSalaryField.setBounds(520, 100, 150, 30);

        monthLabel = new JLabel("Select Month:");
        monthLabel.setBounds(400, 140, 100, 30);
        monthLabel.setForeground(Color.white);

        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        monthDropdown = new JComboBox<>(months);
        monthDropdown.setBounds(520, 140, 150, 30);

        yearLabel = new JLabel("Select Year:");
        yearLabel.setBounds(400, 180, 100, 30);
        yearLabel.setForeground(Color.white);

        String[] years = {"2023", "2024", "2025"}; // Add more years as needed
        yearDropdown = new JComboBox<>(years);
        yearDropdown.setBounds(520, 180, 150, 30);

        displayButton = new JButton("Display Details");
        displayButton.setBounds(170, 240, 150, 30);
        displayButton.addActionListener(this);
        displayButton.setBackground(Color.red);
        displayButton.setForeground(Color.white);

        submitButton = new JButton("Submit");
        submitButton.setBounds(350, 240, 150, 30);
        submitButton.addActionListener(this);
        submitButton.setBackground(Color.red);
        submitButton.setForeground(Color.white);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(520, 240, 150, 30);
        cancelButton.addActionListener(this);
        cancelButton.setBackground(Color.red);
        cancelButton.setForeground(Color.white);

        add(empIdLabel);
        add(empIdDropdown);
        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(hraLabel);
        add(hraField);
        add(daLabel);
        add(daField);
        add(midLabel);
        add(midField);
        add(pfLabel);
        add(pfField);
        add(basicSalaryLabel);
        add(basicSalaryField);
        add(monthLabel);
        add(monthDropdown);
        add(yearLabel);
        add(yearDropdown);
        add(displayButton);
        add(submitButton);
        add(cancelButton);
        add(imageLabel);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        loadEmployeeIds();
    }

    private void loadEmployeeIds() {
        try {
            String query = "SELECT employeeId FROM employeeTable";
            ResultSet rs = obj.stm.executeQuery(query);

            while (rs.next()) {
                empIdDropdown.addItem(rs.getString("employeeId"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while loading employee IDs.");
        }
    }

    private void displayEmployeeDetails(String empId) {
        try {
            String query = "SELECT name, email FROM employeeTable WHERE employeeId = '" + empId + "'";
            ResultSet rs = obj.stm.executeQuery(query);

            while (rs.next()) {
                nameField.setText(rs.getString("name"));
                emailField.setText(rs.getString("email"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while displaying employee details.");
        }
    }

    private void submitSalaryDetails() {
        try {
            String empId = (String) empIdDropdown.getSelectedItem();
            String hra = hraField.getText();
            String da = daField.getText();
            String mid = midField.getText();
            String pf = pfField.getText();
            String basicSalary = basicSalaryField.getText();
            String month = (String) monthDropdown.getSelectedItem();
            String year = (String) yearDropdown.getSelectedItem();

            String insertQuery = "INSERT INTO salaryTable (employeeId, hra, da, mid, pf, basicSalary, month, year) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = obj.connectDatabase.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, empId);
                preparedStatement.setString(2, hra);
                preparedStatement.setString(3, da);
                preparedStatement.setString(4, mid);
                preparedStatement.setString(5, pf);
                preparedStatement.setString(6, basicSalary);
                preparedStatement.setString(7, month);
                preparedStatement.setString(8, year);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Salary details submitted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to submit salary details. Please try again.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while submitting salary details.");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == displayButton) {
            String selectedEmpId = (String) empIdDropdown.getSelectedItem();
            displayEmployeeDetails(selectedEmpId);
        } else if (e.getSource() == submitButton) {
            submitSalaryDetails();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new Salary();
    }
}