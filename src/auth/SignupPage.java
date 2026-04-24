package auth;

import Leet_Cod.DataBaseConnect;
import email.WelcomeMailSender;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignupPage extends JPanel {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;

    public SignupPage(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        // ✅ Cream background
        this.setLayout(new BorderLayout(20, 20));
        this.setBackground(new Color(227, 227, 216));

        // ✅ Title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 30, 0));

        JLabel title = new JLabel("REGISTER FOR CODECRATE", 0);
        title.setFont(new Font("Segoe UI Black", Font.BOLD, 60));
        title.setForeground(new Color(134, 209, 183)); // pista text
        topPanel.add(title, "Center");

        // ✅ Form panel
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = 2;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 45));

        JTextField usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 40));
        usernameField.setPreferredSize(new Dimension(500, 65));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 45));

        JTextField emailField = new JTextField();
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 40));
        emailField.setPreferredSize(new Dimension(500, 65));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.BLACK);
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 45));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 40));
        passwordField.setPreferredSize(new Dimension(500, 65));

        // ✅ Buttons with pista + cream style and BLACK text
        JButton signupButton = new JButton("Register");
        signupButton.setBackground(new Color(134, 209, 183)); // pista
        signupButton.setForeground(Color.BLACK); // black text
        signupButton.setFont(new Font("Segoe UI Semibold", Font.BOLD, 45));
        signupButton.setPreferredSize(new Dimension(250, 90));
        signupButton.setFocusPainted(false);

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(134, 209, 183)); // cream
        backButton.setForeground(Color.BLACK); // black text
        backButton.setFont(new Font("Segoe UI Semibold", Font.BOLD, 45));
        backButton.setPreferredSize(new Dimension(250, 90));
        backButton.setFocusPainted(false);

        // ✅ Borders
        signupButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(34, 139, 34), 2, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        backButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(134, 209, 183), 2, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // ✅ Layout arrangement
        gbc.gridx = 0; gbc.gridy = 0; form.add(usernameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; form.add(usernameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; form.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; form.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; form.add(passwordLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; form.add(passwordField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; form.add(backButton, gbc);
        gbc.gridx = 1; gbc.gridy = 3; form.add(signupButton, gbc);

        this.add(topPanel, "North");
        this.add(form, "Center");

        // ✅ Action listeners (no change in logic)
        signupButton.addActionListener((e) -> {
            String username = usernameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            try {
                String query = "INSERT INTO user (user_name, email, password) VALUES (?, ?, ?)";
                PreparedStatement ps = DataBaseConnect.con.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, email);
                ps.setString(3, password);
                ps.executeUpdate();
                JOptionPane.showMessageDialog((Component) null, "User registered successfully!");
            } catch (SQLException ex) {
                String errorMessage = ex.getMessage();
                if (errorMessage.contains("Invalid email address")) {
                    JOptionPane.showMessageDialog((Component) null, "❌ Please enter a valid email address (must contain @).");
                } else if (errorMessage.contains("Username already exists")) {
                    JOptionPane.showMessageDialog((Component) null, "⚠️ Username already exists. Try another one.");
                } else {
                    JOptionPane.showMessageDialog((Component) null, "Database Error: " + errorMessage);
                }
                return;
            }

            if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) {
                    bw.write(username + "," + email + "," + password + "\n");
                } catch (Exception var16) {
                    JOptionPane.showMessageDialog(this, "Error saving user.");
                    return;
                }

                try {
                    WelcomeMailSender.sendWelcomeMail(email, username);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(this, "Account created! You can now login.");
                cardLayout.show(mainPanel, "login");
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }
        });

        backButton.addActionListener((e) -> cardLayout.show(mainPanel, "welcome"));
    }

    public JPanel getPanel() {
        return this;
    }
}
