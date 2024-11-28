package EmployeeManagement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class Employee_Attendance extends JFrame implements ActionListener {

    JLabel empIdLabel, nameLabel, emailLabel, statusLabel, imageLabel;
    JComboBox<String> empIdDropdown, statusDropdown;
    JTextField nameField, emailField;
    JButton displayButton, markAttendanceButton, cancelButton;

    ConnectionClass obj;

    Employee_Attendance() {
        obj = new ConnectionClass();

        setTitle("Employee Attendance");
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

        statusLabel = new JLabel("Status:");
        statusLabel.setBounds(50, 140, 100, 30);
        statusLabel.setForeground(Color.white);

        String[] statusOptions = {"Present", "Absent", "Late"};
        statusDropdown = new JComboBox<>(statusOptions);
        statusDropdown.setBounds(170, 140, 150, 30);

        displayButton = new JButton("Display Details");
        displayButton.setBounds(350, 20, 150, 30);
        displayButton.addActionListener(this);
        displayButton.setBackground(Color.red);
        displayButton.setForeground(Color.white);

        markAttendanceButton = new JButton("Mark Attendance");
        markAttendanceButton.setBounds(170, 200, 150, 30);
        markAttendanceButton.addActionListener(this);
        markAttendanceButton.setBackground(Color.red);
        markAttendanceButton.setForeground(Color.white);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(350, 200, 150, 30);
        cancelButton.addActionListener(this);
        cancelButton.setBackground(Color.red);
        cancelButton.setForeground(Color.white);

        add(empIdLabel);
        add(empIdDropdown);
        add(nameLabel);
        add(nameField);
        add(emailLabel);
        add(emailField);
        add(statusLabel);
        add(statusDropdown);
        add(displayButton);
        add(markAttendanceButton);
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

    private void markAttendance(String empId, String status) {
        try {
            java.sql.Timestamp timestamp = new java.sql.Timestamp(System.currentTimeMillis());

            String insertQuery = "INSERT INTO attendanceTable (employeeId, status, timestamp) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = obj.connectDatabase.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, empId);
                preparedStatement.setString(2, status);
                preparedStatement.setTimestamp(3, timestamp);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Attendance marked successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to mark attendance. Please try again.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while marking attendance.");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == displayButton) {
            String selectedEmpId = (String) empIdDropdown.getSelectedItem();
            displayEmployeeDetails(selectedEmpId);
        } else if (e.getSource() == markAttendanceButton) {
            String selectedEmpId = (String) empIdDropdown.getSelectedItem();
            String selectedStatus = (String) statusDropdown.getSelectedItem();
            markAttendance(selectedEmpId, selectedStatus);
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new Employee_Attendance();
    }
}
