package ui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class LoginFrame extends JFrame {

    private static final Color ORANGE = new Color(244, 93, 10);
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();

    public LoginFrame() {
        setTitle("Billing System - Login");
        setSize(480, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLayout(new GridBagLayout()); // keeps the card in the centre

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 225, 225)),
                BorderFactory.createEmptyBorder(30, 35, 30, 35)));

        JLabel title = new JLabel("Login");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Billing System");
        subtitle.setFont(UIHelper.NORMAL);
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton signIn = new JButton("Login");
        signIn.setFont(new Font("SansSerif", Font.BOLD, 16));
        signIn.setBackground(ORANGE);
        signIn.setForeground(Color.WHITE);
        signIn.setOpaque(true);
        signIn.setBorderPainted(false);
        signIn.setFocusPainted(false);
        signIn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        signIn.setPreferredSize(new Dimension(320, 44));
        signIn.setMaximumSize(new Dimension(320, 44));

        card.add(title);
        card.add(Box.createVerticalStrut(4));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(25));
        addField(card, "Username", usernameField);
        card.add(Box.createVerticalStrut(15));
        addField(card, "Password", passwordField);
        card.add(Box.createVerticalStrut(25));
        card.add(signIn);

        add(card);

        signIn.addActionListener(e -> signIn());
        getRootPane().setDefaultButton(signIn); // Enter key = Login
    }

    private void addField(JPanel card, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setFont(UIHelper.BOLD);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        field.setFont(UIHelper.NORMAL);
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setPreferredSize(new Dimension(320, 40));
        field.setMaximumSize(new Dimension(320, 40));

        card.add(label);
        card.add(Box.createVerticalStrut(5));
        card.add(field);
    }

    private void signIn() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty()) {
            UIHelper.showError(this, "Please enter your username.");
            usernameField.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            UIHelper.showError(this, "Please enter your password.");
            passwordField.requestFocus();
            return;
        }

        try {
            if (new UserDAO().validateLogin(username, password)) {
                new HomeFrame(username).setVisible(true);
                dispose();
            } else {
                UIHelper.showError(this, "Incorrect username or password.");
                passwordField.setText("");
            }
        } catch (SQLException ex) {
            UIHelper.showDbError(this, ex);
        }
    }
}
