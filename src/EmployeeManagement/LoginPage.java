package EmployeeManagement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {

    JFrame frame;
    JLabel label1, label2;
    JTextField Username;
    JPasswordField Passwd;
    JButton loginButton, cancelButton;

    LoginPage() {
        frame = new JFrame("Login");
        frame.setSize(550, 300);

        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("EmployeeManagement/Images/loginIcon.jpg"));
        Image image = img.getImage();
        Image newImg = image.getScaledInstance(550, 300, java.awt.Image.SCALE_SMOOTH);
        img = new ImageIcon(newImg);

        frame.setContentPane(new JLabel(img));

        frame.setLayout(null);

        label1 = new JLabel("Username");
        label1.setBounds(90, 20, 150, 30);
        label1.setForeground(Color.white);
        frame.add(label1);

        label2 = new JLabel("Password");
        label2.setBounds(90, 70, 150, 30);
        label2.setForeground(Color.white);
        frame.add(label2);

        Username = new JTextField();
        Username.setBounds(200, 20, 200, 30);
        frame.add(Username);

        Passwd = new JPasswordField();
        Passwd.setBounds(200, 70, 200, 30);
        frame.add(Passwd);

        loginButton = new JButton("Login");
        loginButton.setBackground(Color.gray);
        loginButton.setBounds(90, 140, 170, 30);
        loginButton.addActionListener(this);
        loginButton.setForeground(Color.black);
        frame.add(loginButton);

        cancelButton = new JButton("Close");
        cancelButton.setBackground(Color.red);
        cancelButton.setBounds(280, 140, 170, 30);
        cancelButton.addActionListener(this);
        cancelButton.setForeground(Color.white);
        frame.add(cancelButton);

        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent ee) {
        if (ee.getSource() == loginButton) {
            try {
                ConnectionClass obj = new ConnectionClass();
                String userName = Username.getText();
                String password = new String(Passwd.getPassword());
                String searchDatabase = "select * from loginTable where username='" + userName + "' and password='" + password + "'";
                ResultSet rs = obj.stm.executeQuery(searchDatabase);
                if (rs.next()) {
                    new HomePage().setVisible(true);
                    System.out.println("Logged in successfully");
                    frame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "You have entered the wrong username or password");
                    frame.setVisible(false);
                    frame.setVisible(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (ee.getSource() == cancelButton) {
            this.frame.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new LoginPage();
    }
}