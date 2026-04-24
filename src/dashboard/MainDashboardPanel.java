package dashboard;

import taskmanager.TaskManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class MainDashboardPanel extends JPanel {

    public MainDashboardPanel(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(227, 227, 216)); // ✅ Cream background

        JLabel title = new JLabel("📦 Welcome to CodeCrate", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI Black", Font.BOLD, 50));
        title.setForeground(new Color(134, 209, 183)); // ✅ Pista title text

        JButton leetcodeBtn = new JButton("Leetcode");
        JButton taskManagerBtn = new JButton("Task Manager");

        Dimension btnSize = new Dimension(500, 100);
        Font btnFont = new Font("Segoe UI Semibold", Font.BOLD, 35);

        // ✅ Common button style (pista bg, black text)
        JButton[] buttons = {leetcodeBtn, taskManagerBtn};
        for (JButton btn : buttons) {
            btn.setPreferredSize(btnSize);
            btn.setFont(btnFont);
            btn.setBackground(new Color(134, 209, 183)); // ✅ Pista
            btn.setForeground(Color.BLACK);              // ✅ Black text
            btn.setFocusPainted(false);
        }

        // ✅ Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(227, 227, 216)); // ✅ Cream background
        buttonPanel.setLayout(new GridLayout(2, 1, 20, 20));
        buttonPanel.add(leetcodeBtn);
        buttonPanel.add(taskManagerBtn);

        // ✅ Center panel
        JPanel center = new JPanel(new GridBagLayout());
        center.setBackground(new Color(245, 245, 240)); // ✅ Lighter cream
        center.add(buttonPanel);

        add(title, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);

        // ✅ Logic unchanged
        leetcodeBtn.addActionListener((ActionEvent e) -> {
            cardLayout.show(mainPanel, "leetcode");
        });

        taskManagerBtn.addActionListener(e -> {
            TaskManager taskManager = new TaskManager(cardLayout, mainPanel);
            mainPanel.add(taskManager.getPanel(), "taskmanager");
            cardLayout.show(mainPanel, "taskmanager");
        });
    }

    public JPanel getPanel() {
        return this;
    }
}
