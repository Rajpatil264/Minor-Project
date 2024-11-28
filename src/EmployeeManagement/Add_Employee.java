package EmployeeManagement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Add_Employee extends JFrame implements ActionListener {

    JLabel empIdLabel, nameLabel, ageLabel, mobileLabel, emailLabel, designationLabel, educationLabel, fatherNameLabel, imageLabel;
    JTextField empIdField, nameField, ageField, mobileField, emailField, designationField, educationField, fatherNameField;
    JButton addButton, cancelButton;

    ConnectionClass obj;
    Add_Employee() {
        obj = new ConnectionClass();

        setTitle("Add Employee");
        setSize(730, 400);
        setLayout(null);

        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("EmployeeManagement/Images/profile.jpg"));
        Image newImg = img.getImage().getScaledInstance(730, 400, Image.SCALE_DEFAULT);
        ImageIcon img1 = new ImageIcon(newImg);
        imageLabel = new JLabel(img1);
        imageLabel.setBounds(0, 0, 730, 400);

        empIdLabel = new JLabel("Employee Id:");
        empIdLabel.setBounds(50, 20, 150, 30);

        empIdField = new JTextField();
        empIdField.setBounds(180, 20, 150, 30);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 60, 150, 30);

        nameField = new JTextField();
        nameField.setBounds(180, 60, 150, 30);

        ageLabel = new JLabel("Age:");
        ageLabel.setBounds(50, 100, 150, 30);

        ageField = new JTextField();
        ageField.setBounds(180, 100, 150, 30);

        mobileLabel = new JLabel("Mobile Number:");
        mobileLabel.setBounds(50, 140, 150, 30);

        mobileField = new JTextField();
        mobileField.setBounds(180, 140, 150, 30);

        emailLabel = new JLabel("Email ID:");
        emailLabel.setBounds(400, 20, 150, 30);

        emailField = new JTextField();
        emailField.setBounds(520, 20, 150, 30);

        designationLabel = new JLabel("Designation:");
        designationLabel.setBounds(400, 60, 150, 30);

        designationField = new JTextField();
        designationField.setBounds(520, 60, 150, 30);

        educationLabel = new JLabel("Education:");
        educationLabel.setBounds(400, 100, 150, 30);

        educationField = new JTextField();
        educationField.setBounds(520, 100, 150, 30);

        fatherNameLabel = new JLabel("Father's Name:");
        fatherNameLabel.setBounds(400, 140, 150, 30);

        fatherNameField = new JTextField();
        fatherNameField.setBounds(520, 140, 150, 30);

        addButton = new JButton("Add");
        addButton.setBounds(180, 240, 150, 30);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(400, 240, 150, 30);

        addButton.addActionListener(this);
        cancelButton.addActionListener(this);

        Color white = new Color(255, 255, 255);
        Color red = new Color(255, 0, 0);

        empIdLabel.setForeground(white);
        nameLabel.setForeground(white);
        ageLabel.setForeground(white);
        mobileLabel.setForeground(white);
        emailLabel.setForeground(white);
        designationLabel.setForeground(white);
        educationLabel.setForeground(white);
        fatherNameLabel.setForeground(white);
        addButton.setForeground(white);
        cancelButton.setForeground(white);
        addButton.setBackground(red);
        cancelButton.setBackground(red);

        add(empIdLabel);
        add(empIdField);
        add(nameLabel);
        add(nameField);
        add(ageLabel);
        add(ageField);
        add(mobileLabel);
        add(mobileField);
        add(emailLabel);
        add(emailField);
        add(designationLabel);
        add(designationField);
        add(educationLabel);
        add(educationField);
        add(fatherNameLabel);
        add(fatherNameField);
        add(addButton);
        add(cancelButton);
        add(imageLabel);


        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            try {

                String empId = empIdField.getText();
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String mobile = mobileField.getText();
                String email = emailField.getText();
                String designation = designationField.getText();
                String education = educationField.getText();
                String fatherName = fatherNameField.getText();


                String insertQuery = "INSERT INTO employeeTable (employeeId, name, age, mobile, email, designation, education, fatherName) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement preparedStatement = obj.connectDatabase.prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, empId);
                    preparedStatement.setString(2, name);
                    preparedStatement.setInt(3, age);
                    preparedStatement.setString(4, mobile);
                    preparedStatement.setString(5, email);
                    preparedStatement.setString(6, designation);
                    preparedStatement.setString(7, education);
                    preparedStatement.setString(8, fatherName);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Employee added successfully!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add employee. Please try again.");
                    }
                }
            } catch (SQLException | NumberFormatException ex) {
                System.err.println("Error adding employee: " + ex.getMessage());

                JOptionPane.showMessageDialog(this, "An error occurred while adding the employee. Please check the input values.");
            }
        } else if (e.getSource() == cancelButton) {
            dispose(); // Close the frame if cancel button is clicked
        }
    }

    public static void main(String[] args) {
        new Add_Employee();
    }
}
