package EmployeeManagement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.io.FileWriter;
import java.io.IOException;

public class Generate_PaySlip extends JFrame implements ActionListener {

    JLabel empIdLabel, monthLabel, yearLabel, payslipLabel, imageLabel;
    JComboBox<String> empIdDropdown, monthDropdown, yearDropdown;
    JTextArea payslipTextArea;
    JButton displayButton, saveButton, cancelButton;

    ConnectionClass obj;

    Generate_PaySlip() {
        obj = new ConnectionClass();

        setTitle("Generate Payslip");
        setSize(800, 500);
        setLayout(null);

        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("EmployeeManagement/Images/view.jpeg"));
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

        monthLabel = new JLabel("Select Month:");
        monthLabel.setBounds(50, 60, 100, 30);
        monthLabel.setForeground(Color.white);

        String[] monthOptions = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        monthDropdown = new JComboBox<>(monthOptions);
        monthDropdown.setBounds(170, 60, 150, 30);

        yearLabel = new JLabel("Select Year:");
        yearLabel.setBounds(50, 100, 100, 30);
        yearLabel.setForeground(Color.white);

        String[] yearOptions = {"2023", "2024", "2025"}; // Update with your desired years
        yearDropdown = new JComboBox<>(yearOptions);
        yearDropdown.setBounds(170, 100, 150, 30);

        displayButton = new JButton("Display Details");
        displayButton.setBounds(350, 20, 150, 30);
        displayButton.addActionListener(this);
        displayButton.setBackground(Color.red);
        displayButton.setForeground(Color.white);

        payslipLabel = new JLabel("Payslip:");
        payslipLabel.setBounds(50, 140, 100, 30);
        payslipLabel.setForeground(Color.white);

        payslipTextArea = new JTextArea();
        payslipTextArea.setBounds(170, 140, 500, 250);
        payslipTextArea.setEditable(false);

        saveButton = new JButton("Save Payslip");
        saveButton.setBounds(170, 410, 150, 30);
        saveButton.addActionListener(this);
        saveButton.setBackground(Color.red);
        saveButton.setForeground(Color.white);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(350, 410, 150, 30);
        cancelButton.addActionListener(this);
        cancelButton.setBackground(Color.red);
        cancelButton.setForeground(Color.white);

        add(empIdLabel);
        add(empIdDropdown);
        add(monthLabel);
        add(monthDropdown);
        add(yearLabel);
        add(yearDropdown);
        add(displayButton);
        add(payslipLabel);
        add(payslipTextArea);
        add(saveButton);
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

    private void generatePaySlip() {
        try {
            String empId = (String) empIdDropdown.getSelectedItem();
            String month = (String) monthDropdown.getSelectedItem();
            String year = (String) yearDropdown.getSelectedItem();

            String query = "SELECT e.name, e.email, s.basicSalary FROM employeeTable e JOIN salaryTable s ON e.employeeId = s.employeeId WHERE e.employeeId = '" + empId + "'";
            ResultSet rs = obj.stm.executeQuery(query);

            String name = "";
            String email = "";
            double basicSalary = 0.0;

            if (rs.next()) {
                name = rs.getString("name");
                email = rs.getString("email");
                basicSalary = rs.getDouble("basicSalary");
            }

            String payslipText = "DR Pvt. Ltd\n\n";
            payslipText += "Employee ID: " + empId + "\n";
            payslipText += "Employee Name: " + name + "\n";
            payslipText += "Email: " + email + "\n\n";
            payslipText += "Salary Details:\n";
            payslipText += "Basic Salary: " + basicSalary + "\n\n";
            payslipText += "Generated for " + month + " " + year;

            payslipTextArea.setText(payslipText);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while generating the payslip.");
        }
    }


    private void savePaySlipToFile() {
        try {
            String empId = (String) empIdDropdown.getSelectedItem();
            String month = (String) monthDropdown.getSelectedItem();
            String year = (String) yearDropdown.getSelectedItem();

            String fileName = "PaySlip_" + empId + "" + month + "" + year + ".txt";

            String folderPath = "E:\\Raj programming\\JAVA\\EmployeeManagementSystem\\src\\EmployeeManagement\\Pay_Slips";
            String filePath = folderPath + "\\" + fileName;

            String payslipContent = payslipTextArea.getText();

            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(payslipContent);
                JOptionPane.showMessageDialog(this, "Payslip saved successfully!");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while saving the payslip to a file.");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == displayButton) {
            generatePaySlip();
        } else if (e.getSource() == saveButton) {
            savePaySlipToFile();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new Generate_PaySlip();
    }
}