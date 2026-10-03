package ui;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {

    public HomeFrame(String username) {
        UIHelper.setupFrame(this, "Billing System - Home", 600, 470);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        add(UIHelper.createHeader("Billing System  |  Welcome, " + username), BorderLayout.NORTH);

        JButton addButton = UIHelper.createButton("Add Item", UIHelper.PRIMARY);
        JButton updateButton = UIHelper.createButton("Update Item", UIHelper.PRIMARY);
        JButton deleteButton = UIHelper.createButton("Delete Item", UIHelper.DANGER);
        JButton reportButton = UIHelper.createButton("Report", UIHelper.SUCCESS);
        JButton logoutButton = UIHelper.createButton("Logout", UIHelper.NEUTRAL);
        JButton exitButton = UIHelper.createButton("Exit", UIHelper.NEUTRAL);

        JPanel grid = new JPanel(new GridLayout(2, 2, 20, 20));
        grid.setBackground(UIHelper.BACKGROUND);
        grid.setBorder(BorderFactory.createEmptyBorder(40, 70, 20, 70));
        for (JButton b : new JButton[]{addButton, updateButton, deleteButton, reportButton}) {
            b.setPreferredSize(new Dimension(200, 80));
            b.setFont(new Font("SansSerif", Font.BOLD, 17));
            grid.add(b);
        }
        add(grid, BorderLayout.CENTER);
        add(UIHelper.createButtonPanel(logoutButton, exitButton), BorderLayout.SOUTH);

        addButton.addActionListener(e -> openPage(new AddItemFrame(this)));
        updateButton.addActionListener(e -> openPage(new UpdateItemFrame(this)));
        deleteButton.addActionListener(e -> openPage(new DeleteItemFrame(this)));
        reportButton.addActionListener(e -> openPage(new ReportFrame(this)));

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        exitButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Do you want to exit the application?",
                    "Exit", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private void openPage(JFrame page) {
        setVisible(false);
        page.setVisible(true);
    }
}
