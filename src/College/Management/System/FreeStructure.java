package College.Management.System;

import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class FreeStructure extends JFrame {

    FreeStructure() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        JLabel heading = new JLabel("Fee Structure");
        heading.setBounds(400, 10, 400, 30);
        heading.setFont(new Font("Tahoma", Font.BOLD, 30));
        add(heading);

        // Create a JTable
        JTable table = new JTable();

        try {
            Conn c = new Conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM fee");
            table.setModel(DbUtils.resultSetToTableModel(resultSet)); // Populating table
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Wrap JTable inside JScrollPane
        JScrollPane js = new JScrollPane(table);
        js.setBounds(50, 60, 900, 600); // Set bounds correctly
        add(js);

        setSize(1000, 700);
        setLocation(250, 50);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FreeStructure();
    }
}
