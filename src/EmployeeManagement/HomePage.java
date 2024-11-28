package EmployeeManagement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePage extends JFrame implements ActionListener {

    JLabel profile;
    Font f1,f2,f3;
    JPanel p1;
    HomePage(){
        super("Employee Home Page");
        setLocation(200,100);
        setSize(850,700);

        f1 = new Font("Verdana", Font.BOLD, 20);
        f2 = new Font("Open Sans", Font.BOLD, 15);
        f3 = new Font("Montserrat", Font.PLAIN, 16);

        ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("EmployeeManagement/Images/Home.jpeg"));
        Image newImg = img.getImage().getScaledInstance(850, 700, Image.SCALE_DEFAULT);
        ImageIcon img1 = new ImageIcon(newImg);

        profile=new JLabel(img1);

        JMenuBar m1=new JMenuBar();
        JMenu Menu1=new JMenu("Profile");
        JMenuItem subMenu1_1=new JMenuItem("Add Employee");
        JMenuItem subMenu1_2=new JMenuItem("View Employee");

        JMenu Menu2=new JMenu("Manage");
        JMenuItem subMenu2=new JMenuItem("Update Details");

        JMenu Menu3=new JMenu("Attendance");
        JMenuItem subMenu3_1=new JMenuItem("Employee Attendance");
        JMenuItem subMenu3_2=new JMenuItem("View Attendance");

        JMenu Menu4=new JMenu("Leave");
        JMenuItem subMenu4_1=new JMenuItem("Apply Leave");
        JMenuItem subMenu4_2=new JMenuItem("View Leaves");

        JMenu Menu5=new JMenu("Salary");
        JMenuItem subMenu5_1=new JMenuItem("Add Salary");
        JMenuItem subMenu5_2=new JMenuItem("Generate Salary Slip");

        JMenu Menu6=new JMenu("Exit");
        JMenuItem subMenu6=new JMenuItem("Logout");


        Menu1.add(subMenu1_1);
        Menu1.add(subMenu1_2);
        Menu2.add(subMenu2);
        Menu3.add(subMenu3_1);
        Menu3.add(subMenu3_2);
        Menu4.add(subMenu4_1);
        Menu4.add(subMenu4_2);
        Menu5.add(subMenu5_1);
        Menu5.add(subMenu5_2);
        Menu6.add(subMenu6);

        m1.add(Menu1);
        m1.add(Box.createHorizontalStrut(15));
        m1.add(Menu2);
        m1.add(Box.createHorizontalStrut(15));
        m1.add(Menu3);
        m1.add(Box.createHorizontalStrut(15));
        m1.add(Menu4);
        m1.add(Box.createHorizontalStrut(15));
        m1.add(Menu5);
        m1.add(Box.createHorizontalStrut(15));
        m1.add(Menu6);
        m1.add(Box.createHorizontalStrut(15));

        Menu1.setFont(f1);
        Menu2.setFont(f1);
        Menu3.setFont(f1);
        Menu4.setFont(f1);
        Menu5.setFont(f1);
        Menu6.setFont(f1);

        subMenu1_1.setFont(f2);
        subMenu1_2.setFont(f2);
        subMenu2.setFont(f2);
        subMenu3_1.setFont(f2);
        subMenu3_2.setFont(f2);
        subMenu4_1.setFont(f2);
        subMenu4_2.setFont(f2);
        subMenu5_1.setFont(f2);
        subMenu5_2.setFont(f2);
        subMenu6.setFont(f2);

        m1.setBackground(Color.white);

        Menu1.setForeground(Color.black);
        Menu2.setForeground(Color.black);
        Menu3.setForeground(Color.black);
        Menu4.setForeground(Color.black);
        Menu5.setForeground(Color.black);
        Menu6.setForeground(Color.black);


        subMenu1_1.setBackground(Color.white);
        subMenu1_2.setBackground(Color.white);
        subMenu2.setBackground(Color.white);
        subMenu3_1.setBackground(Color.white);
        subMenu3_2.setBackground(Color.white);
        subMenu4_1.setBackground(Color.white);
        subMenu4_2.setBackground(Color.white);
        subMenu5_1.setBackground(Color.white);
        subMenu5_2.setBackground(Color.white);
        subMenu6.setBackground(Color.white);


        subMenu1_1.setForeground(Color.black);
        subMenu1_2.setForeground(Color.black);
        subMenu2.setForeground(Color.black);
        subMenu3_1.setForeground(Color.black);
        subMenu3_2.setForeground(Color.black);
        subMenu4_1.setForeground(Color.black);
        subMenu4_2.setForeground(Color.black);
        subMenu5_1.setForeground(Color.black);
        subMenu5_2.setForeground(Color.black);
        subMenu6.setForeground(Color.black);


        subMenu1_1.addActionListener(this);
        subMenu1_2.addActionListener(this);
        subMenu2.addActionListener(this);
        subMenu3_1.addActionListener(this);
        subMenu3_2.addActionListener(this);
        subMenu4_1.addActionListener(this);
        subMenu4_2.addActionListener(this);
        subMenu5_1.addActionListener(this);
        subMenu5_2.addActionListener(this);
        subMenu6.addActionListener(this);


        setJMenuBar(m1);
        add(profile);

    }
    public void actionPerformed(ActionEvent e){
        String command=e.getActionCommand();
        if(command.equals("Add Employee")){
            new Add_Employee();
        }
        else if(command.equals("View Employee")){
            new View_Employee();
        }
        else if(command.equals("Update Details")){
            new UpdateDetails_Data().setVisible(true);
        }
        else if(command.equals("Employee Attendance")){
            new Employee_Attendance().setVisible(true);
        }
        else if(command.equals("View Attendance")){
            new View_Attendance().setVisible(true);
        }
        else if(command.equals("Apply Leave")){
            new Apply_Leave().setVisible(true);
        }
        else if(command.equals("View Leaves")){
            new View_Leave().setVisible(true);
        }
        else if(command.equals("Add Salary")){
            new Salary().setVisible(true);
        }
        else if(command.equals("Generate Salary Slip")){
            new Generate_PaySlip().setVisible(true);
        }
        else if(command.equals("Logout")){
            System.exit(0);
        }

    }

    public static void main(String[] args) {

        new HomePage().setVisible(true);
    }
}