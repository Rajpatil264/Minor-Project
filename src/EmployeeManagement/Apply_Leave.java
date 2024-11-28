package EmployeeManagement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.sql.*;

public class Apply_Leave extends JFrame implements ActionListener {

    JLabel empIdLabel, nameLabel, mobileLabel, startDateLabel, endDateLabel, reasonLabel, imageLabel;
    JComboBox<String> empIdDropdown, reasonDropdown;
    JTextField nameField, mobileField;
    JDateChooser startDateChooser, endDateChooser;
    JButton displayButton, submitButton, cancelButton;

    ConnectionClass obj;

    Apply_Leave() {
        obj = new ConnectionClass();

        setTitle("Apply for Leave");
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

        mobileLabel = new JLabel("Mobile Number:");
        mobileLabel.setBounds(50, 100, 150, 30);
        mobileLabel.setForeground(Color.white);

        mobileField = new JTextField();
        mobileField.setBounds(170, 100, 150, 30);
        mobileField.setEditable(false);

        startDateLabel = new JLabel("Start Date:");
        startDateLabel.setBounds(50, 140, 100, 30);
        startDateLabel.setForeground(Color.white);

        startDateChooser = new JDateChooser();
        startDateChooser.setBounds(170, 140, 150, 30);

        endDateLabel = new JLabel("End Date:");
        endDateLabel.setBounds(50, 180, 100, 30);
        endDateLabel.setForeground(Color.white);

        endDateChooser = new JDateChooser();
        endDateChooser.setBounds(170, 180, 150, 30);

        reasonLabel = new JLabel("Reason for Leave:");
        reasonLabel.setBounds(50, 220, 150, 30);
        reasonLabel.setForeground(Color.white);

        String[] reasonOptions = {"Employee Health Issue", "Family Health Issue", "Function/Celebration", "Party", "Personal Things", "Other"};
        reasonDropdown = new JComboBox<>(reasonOptions);
        reasonDropdown.setBounds(170, 220, 150, 30);

        displayButton = new JButton("Display Details");
        displayButton.setBounds(350, 20, 150, 30);
        displayButton.addActionListener(this);
        displayButton.setBackground(Color.red);
        displayButton.setForeground(Color.white);

        submitButton = new JButton("Submit");
        submitButton.setBounds(170, 270, 150, 30);
        submitButton.addActionListener(this);
        submitButton.setBackground(Color.red);
        submitButton.setForeground(Color.white);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(350, 270, 150, 30);
        cancelButton.addActionListener(this);
        cancelButton.setBackground(Color.red);
        cancelButton.setForeground(Color.white);

        add(empIdLabel);
        add(empIdDropdown);
        add(nameLabel);
        add(nameField);
        add(mobileLabel);
        add(mobileField);
        add(startDateLabel);
        add(startDateChooser);
        add(endDateLabel);
        add(endDateChooser);
        add(reasonLabel);
        add(reasonDropdown);
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
            String query = "SELECT name, mobile FROM employeeTable WHERE employeeId = '" + empId + "'";
            ResultSet rs = obj.stm.executeQuery(query);

            while (rs.next()) {
                nameField.setText(rs.getString("name"));
                mobileField.setText(rs.getString("mobile"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while displaying employee details.");
        }
    }

    private void applyForLeave(String empId, String startDate, String endDate, String reason) {
        try {
            String insertQuery = "INSERT INTO leaveTable (employeeId, startDate, endDate, reason) VALUES (?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = obj.connectDatabase.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, empId);
                preparedStatement.setString(2, startDate);
                preparedStatement.setString(3, endDate);
                preparedStatement.setString(4, reason);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Leave applied successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to apply for leave. Please try again.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while applying for leave.");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == displayButton) {
            String selectedEmpId = (String) empIdDropdown.getSelectedItem();
            displayEmployeeDetails(selectedEmpId);
        } else if (e.getSource() == submitButton) {
            String selectedEmpId = (String) empIdDropdown.getSelectedItem();
            String startDate = getDateAsString(startDateChooser.getDate());
            String endDate = getDateAsString(endDateChooser.getDate());
            String reason = (String) reasonDropdown.getSelectedItem();

            applyForLeave(selectedEmpId, startDate, endDate, reason);
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    private String getDateAsString(java.util.Date date) {
        return new java.sql.Date(date.getTime()).toString();
    }

    public static void main(String[] args) {
        new Apply_Leave();
    }
}
