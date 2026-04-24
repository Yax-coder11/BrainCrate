package auth;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class WelcomePage extends JPanel {
    public WelcomePage(CardLayout cardLayout, JPanel mainPanel) {

        this.setLayout(new BorderLayout(20, 20));
        this.setBackground(new Color(227, 227, 216)); // cream background

        JLabel welcomeLabel = new JLabel(" Welcome to BrainCrate", 0);
        welcomeLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 42));
        welcomeLabel.setForeground(new Color(134, 209, 183)); // pista text
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(60, 0, 40, 0));
        this.add(welcomeLabel, BorderLayout.CENTER);

        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Register");

        Dimension btnSize = new Dimension(250, 70);
        Font btnFont = new Font("Segoe UI Semibold", Font.BOLD, 28);

        // Login button → pista green
        loginButton.setPreferredSize(btnSize);
        loginButton.setFont(btnFont);
        loginButton.setBackground(new Color(134, 209, 183)); // pista
        loginButton.setForeground(Color.DARK_GRAY);
        loginButton.setFocusPainted(false);

        loginButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(34, 139, 34), 2, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // Register button → cream with pista border
        signupButton.setPreferredSize(btnSize);
        signupButton.setFont(btnFont);
        signupButton.setBackground(new Color(134, 209, 183)); // cream (beige)
        signupButton.setForeground( Color.DARK_GRAY); //
        signupButton.setFocusPainted(false);

        signupButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(134, 209, 183), 2, true), // pista border
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 30));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        loginButton.addActionListener((e) -> cardLayout.show(mainPanel, "login"));
        signupButton.addActionListener((e) -> cardLayout.show(mainPanel, "signup"));
    }

    public JPanel getPanel() {
        return this;
    }
}
