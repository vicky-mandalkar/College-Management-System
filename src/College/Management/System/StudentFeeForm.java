package College.Management.System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class StudentFeeForm extends JFrame implements ActionListener {

    Choice CrollNumber;
    JComboBox<String> courseBox, departmentBox, semesterBox;
    JLabel totalAmount;
    JButton pay, update, back;

    StudentFeeForm() {

        getContentPane().setBackground(new Color(210, 252, 251));
        setLayout(null);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon/fee.png"));
        Image i2 = i1.getImage().getScaledInstance(500, 300, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel img = new JLabel(i3);
        img.setBounds(400, 50, 500, 300);
        add(img);

        JLabel rollNumber = new JLabel("Select Roll Number");
        rollNumber.setBounds(40, 60, 150, 20);
        rollNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
        add(rollNumber);

        CrollNumber = new Choice();
        CrollNumber.setBounds(200, 60, 150, 20);
        add(CrollNumber);

        try {
            Conn c = new Conn();
            ResultSet rs = c.statement.executeQuery("SELECT * FROM student");
            while (rs.next()) {
                CrollNumber.add(rs.getString("rollno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel name = new JLabel("First Name");
        name.setBounds(40, 100, 150, 20);
        add(name);

        JLabel textName = new JLabel();
        textName.setBounds(200, 100, 150, 20);
        add(textName);

        JLabel fname = new JLabel("Last Name");
        fname.setBounds(40, 140, 150, 20);
        add(fname);

        JLabel textfName = new JLabel();
        textfName.setBounds(200, 140, 150, 20);
        add(textfName);

        CrollNumber.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    Conn c = new Conn();
                    String Q = "SELECT * FROM student WHERE rollno = '" + CrollNumber.getSelectedItem() + "'";
                    ResultSet resultSet = c.statement.executeQuery(Q);

                    while (resultSet.next()) {
                        textName.setText(resultSet.getString("FName"));
                        textfName.setText(resultSet.getString("LName"));
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JLabel qualification = new JLabel("Course");
        qualification.setBounds(40, 180, 150, 20);
        add(qualification);

        String course[] = {"BTech", "BBA", "BCA", "BSC", "MSC", "MBA", "MCA", "MCom", "MA", "BA"};
        courseBox = new JComboBox<>(course);
        courseBox.setBounds(200, 180, 150, 20);
        courseBox.setBackground(Color.WHITE);
        add(courseBox);

        JLabel department = new JLabel("Branch");
        department.setBounds(40, 220, 150, 20);
        add(department);

        String departmentList[] = {"Computer Science", "Electronics & Computer", "Mechanical", "Civil", "AIDS"};
        departmentBox = new JComboBox<>(departmentList);
        departmentBox.setBounds(200, 220, 150, 20);
        departmentBox.setBackground(Color.WHITE);
        add(departmentBox);

        // ✅ Fix: Add proper Semester Label
        JLabel semesterLabel = new JLabel("Semester");
        semesterLabel.setBounds(40, 260, 150, 20);
        add(semesterLabel);

        // ✅ Fix: Remove extra comma in array
        String semester[] = {"semester1", "semester2", "semester3", "semester4", "semester5", "semester6", "semester7", "semester8"};
        semesterBox = new JComboBox<>(semester);
        semesterBox.setBounds(200, 260, 150, 20);
        semesterBox.setBackground(Color.WHITE);
        add(semesterBox);

        JLabel total = new JLabel("Total Payable");
        total.setBounds(40, 300, 150, 20);
        add(total);

        totalAmount = new JLabel();
        totalAmount.setBounds(200, 300, 150, 20);
        add(totalAmount);

        update = new JButton("Update");
        update.setBounds(30, 380, 100, 25);
        update.addActionListener(this);
        add(update);

        pay = new JButton("Pay");
        pay.setBounds(150, 380, 100, 25);
        pay.addActionListener(this);
        add(pay);

        back = new JButton("Back");
        back.setBounds(270, 380, 100, 25);
        back.addActionListener(this);
        add(back);

        setSize(900, 500);
        setLocation(300, 100);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Add actions for buttons
        if (e.getSource()==update){
            String course=(String) courseBox.getSelectedItem();
            String semester=(String) semesterBox.getSelectedItem();
            try {

                Conn c=new Conn();
                ResultSet resultSet=c.statement.executeQuery("select * from fee where course='"+course+"'");
                while (resultSet.next()){
                    totalAmount.setText(resultSet.getString(semester));
                }
            }catch (Exception E){
                E.printStackTrace();
            }
        } else if (e.getSource()==pay) {
            String rollno = CrollNumber.getSelectedItem();
            String course = (String) courseBox.getSelectedItem();
            String semester = (String) semesterBox.getSelectedItem();
            String branch = (String) departmentBox.getSelectedItem();
            String total = totalAmount.getText();
//            String course=(String) courseBox.getSelectedItem();

            try {
                Conn c=new Conn();
                String Q = "INSERT INTO feecollege (rollno, course, branch, semester, total) VALUES ('"
                        + rollno + "', '" + course + "', '" + branch + "', '" + semester + "', '" + total + "')";
                c.statement.executeUpdate(Q);
                JOptionPane.showMessageDialog(null,"Fee Submited Successfully");
            }catch (Exception E){
                E.printStackTrace();
            }
        }
        else {
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new StudentFeeForm();
    }
}
