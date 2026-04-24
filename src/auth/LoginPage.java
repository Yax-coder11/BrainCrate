package auth;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import utils.UserUtils;

public class LoginPage extends JPanel {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginPage(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        // Background → cream
        this.setLayout(new BorderLayout(20, 20));
        this.setBackground(new Color(227, 227, 216));

        // Top panel with title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 30, 0));

        JLabel title = new JLabel("LOGIN TO BRAINCRATE", 0);
        title.setFont(new Font("Segoe UI Black", Font.BOLD, 60));
        title.setForeground(new Color(134, 209, 183)); // pista text
        topPanel.add(title, "Center");

        // Form
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = 2;

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.DARK_GRAY);
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 35));

        this.emailField = new JTextField();
        this.emailField.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        this.emailField.setPreferredSize(new Dimension(500, 60));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.DARK_GRAY);
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 35));

        this.passwordField = new JPasswordField();
        this.passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 30));
        this.passwordField.setPreferredSize(new Dimension(500, 60));

        // Buttons
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(134, 209, 183)); // pista green
        loginButton.setForeground(Color.DARK_GRAY);
        loginButton.setFont(new Font("Segoe UI Semibold", Font.BOLD, 40));
        loginButton.setPreferredSize(new Dimension(250, 80));
        loginButton.setFocusPainted(false);

        loginButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(34, 139, 34), 2, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(134, 209, 183)); // cream
        backButton.setForeground(Color.DARK_GRAY);
        backButton.setFont(new Font("Segoe UI Semibold", Font.BOLD, 40));
        backButton.setPreferredSize(new Dimension(250, 80));
        backButton.setFocusPainted(false);

        backButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(134, 209, 183), 2, true), // pista border
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(emailLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        form.add(this.emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        form.add(passwordLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        form.add(this.passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        form.add(backButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        form.add(loginButton, gbc);

        this.add(topPanel, "North");
        this.add(form, "Center");

        // Actions
        loginButton.addActionListener((e) -> this.handleLogin());
        backButton.addActionListener((e) -> cardLayout.show(mainPanel, "welcome"));
    }

    private void handleLogin() {
        String email = this.emailField.getText().trim();
        String password = new String(this.passwordField.getPassword());
        if (!email.isEmpty() && !password.isEmpty()) {
            boolean success = false;
            String username = "";

            String line;
            try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
                while((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3 && parts[1].equals(email) && parts[2].equals(password)) {
                        username = parts[0];
                        success = true;
                        break;
                    }
                }
            } catch (Exception var10) {
                JOptionPane.showMessageDialog(this, "Error reading users.");
                return;
            }

            if (success) {
                JOptionPane.showMessageDialog(this, "Welcome back, " + username + "!");
                UserUtils.setCurrentUser(username, email);
                this.cardLayout.show(this.mainPanel, "dashboard");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password.");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        }
    }

    public JPanel getPanel() {
        return this;
    }
}
