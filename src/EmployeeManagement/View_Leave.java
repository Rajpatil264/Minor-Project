package EmployeeManagement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class View_Leave extends JFrame implements ActionListener {

    JLabel empIdLabel, nameLabel, mobileLabel, imageLabel;
    JComboBox<String> empIdDropdown;
    JTextField nameField, mobileField;
    JButton displayButton, cancelButton;
    JTable leaveTable;

    ConnectionClass obj;

    View_Leave() {
        obj = new ConnectionClass();

        setTitle("View Leave");
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

        displayButton = new JButton("Display Leave");
        displayButton.setBounds(350, 100, 150, 30);
        displayButton.addActionListener(this);
        displayButton.setBackground(Color.red);
        displayButton.setForeground(Color.white);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(550, 100, 150, 30);
        cancelButton.addActionListener(this);
        cancelButton.setBackground(Color.red);
        cancelButton.setForeground(Color.white);

        leaveTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(leaveTable);
        scrollPane.setBounds(50, 150, 700, 250);

        add(empIdLabel);
        add(empIdDropdown);
        add(nameLabel);
        add(nameField);
        add(mobileLabel);
        add(mobileField);
        add(displayButton);
        add(cancelButton);
        add(scrollPane);
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

    private void displayLeaveDetails(String empId) {
        try {
            String query = "SELECT * FROM leaveTable WHERE employeeId = '" + empId + "'";
            ResultSet rs = obj.stm.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Start Date");
            model.addColumn("End Date");
            model.addColumn("Reason");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("startDate"),
                        rs.getString("endDate"),
                        rs.getString("reason")
                });
            }

            leaveTable.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while loading leave details.");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == displayButton) {
            String selectedEmpId = (String) empIdDropdown.getSelectedItem();
            displayEmployeeDetails(selectedEmpId);
            displayLeaveDetails(selectedEmpId);
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    public static void main(String[] args) {
        new View_Leave();
    }
}