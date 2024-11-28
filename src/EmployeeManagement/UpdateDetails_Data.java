package EmployeeManagement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class UpdateDetails_Data extends JFrame implements ActionListener {

    JLabel empIdLabel, nameLabel, ageLabel, mobileLabel, emailLabel, designationLabel, educationLabel, fatherNameLabel, imageLabel;
    JComboBox<String> empIdDropdown;
    JTextField nameField, ageField, mobileField, emailField, designationField, educationField, fatherNameField;
    JButton displayButton, updateButton, cancelButton;

    ConnectionClass obj;

    UpdateDetails_Data() {
        obj = new ConnectionClass();

        setTitle("Update Employee Details");
        setSize(750, 400);
        setLayout(null);

        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("EmployeeManagement/Images/profile.jpg"));
        Image newImg = img.getImage().getScaledInstance(750, 400, Image.SCALE_DEFAULT);
        ImageIcon img1 = new ImageIcon(newImg);
        imageLabel = new JLabel(img1);
        imageLabel.setBounds(0, 0, 750, 400);

        empIdLabel = new JLabel("Employee ID:");
        empIdLabel.setBounds(50, 20, 100, 30);
        empIdLabel.setForeground(Color.white);

        empIdDropdown = new JComboBox<>();
        empIdDropdown.setBounds(170, 20, 150, 30);

        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 60, 100, 30);
        nameLabel.setForeground(Color.white);

        nameField = new JTextField();
        nameField.setBounds(170, 60, 150, 30);

        ageLabel = new JLabel("Age:");
        ageLabel.setBounds(50, 100, 100, 30);
        ageLabel.setForeground(Color.white);

        ageField = new JTextField();
        ageField.setBounds(170, 100, 150, 30);

        mobileLabel = new JLabel("Mobile Number:");
        mobileLabel.setBounds(50, 140, 150, 30);
        mobileLabel.setForeground(Color.white);

        mobileField = new JTextField();
        mobileField.setBounds(170, 140, 150, 30);

        emailLabel = new JLabel("Email ID:");
        emailLabel.setBounds(400, 20, 100, 30);
        emailLabel.setForeground(Color.white);

        emailField = new JTextField();
        emailField.setBounds(520, 20, 150, 30);

        designationLabel = new JLabel("Designation:");
        designationLabel.setBounds(400, 60, 100, 30);
        designationLabel.setForeground(Color.white);

        designationField = new JTextField();
        designationField.setBounds(520, 60, 150, 30);

        educationLabel = new JLabel("Education:");
        educationLabel.setBounds(400, 100, 100, 30);
        educationLabel.setForeground(Color.white);

        educationField = new JTextField();
        educationField.setBounds(520, 100, 150, 30);

        fatherNameLabel = new JLabel("Father's Name:");
        fatherNameLabel.setBounds(400, 140, 150, 30);
        fatherNameLabel.setForeground(Color.white);

        fatherNameField = new JTextField();
        fatherNameField.setBounds(520, 140, 150, 30);


        displayButton = new JButton("Display Details");
        displayButton.setBounds(170, 230, 150, 30);
        displayButton.addActionListener(this);
        displayButton.setBackground(Color.red);
        displayButton.setForeground(Color.white);

        updateButton = new JButton("Update Details");
        updateButton.setBounds(345, 230, 150, 30);
        updateButton.addActionListener(this);
        updateButton.setBackground(Color.red);
        updateButton.setForeground(Color.white);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(520, 230, 150, 30);
        cancelButton.addActionListener(this);
        cancelButton.setBackground(Color.red);
        cancelButton.setForeground(Color.white);

        add(empIdLabel);
        add(empIdDropdown);
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
        add(displayButton);
        add(updateButton);
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
            String query = "SELECT * FROM employeeTable WHERE employeeId = '" + empId + "'";
            ResultSet rs = obj.stm.executeQuery(query);

            while (rs.next()) {
                nameField.setText(rs.getString("name"));
                ageField.setText(String.valueOf(rs.getInt("age")));
                mobileField.setText(rs.getString("mobile"));
                emailField.setText(rs.getString("email"));
                designationField.setText(rs.getString("designation"));
                educationField.setText(rs.getString("education"));
                fatherNameField.setText(rs.getString("fatherName"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while displaying employee details.");
        }
    }

    private void updateEmployeeDetails(String empId) {
        try {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            String mobile = mobileField.getText();
            String email = emailField.getText();
            String designation = designationField.getText();
            String education = educationField.getText();
            String fatherName = fatherNameField.getText();

            String updateQuery = "UPDATE employeeTable SET name=?, age=?, mobile=?, email=?, designation=?, education=?, fatherName=? WHERE employeeId=?";

            try (PreparedStatement preparedStatement = obj.connectDatabase.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, age);
                preparedStatement.setString(3, mobile);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, designation);
                preparedStatement.setString(6, education);
                preparedStatement.setString(7, fatherName);
                preparedStatement.setString(8, empId);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Employee details updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update employee details. Please try again.");
                }
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while updating employee details. Please check the input values.");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == displayButton) {
            String selectedEmpId = (String) empIdDropdown.getSelectedItem();
            displayEmployeeDetails(selectedEmpId);
        } else if (e.getSource() == updateButton) {
            String selectedEmpId = (String) empIdDropdown.getSelectedItem();
            updateEmployeeDetails(selectedEmpId);
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new UpdateDetails_Data();
    }
}
