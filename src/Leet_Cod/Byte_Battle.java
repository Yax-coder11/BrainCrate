package Leet_Cod;

import javax.swing.*;
import java.awt.*;

import static utils.UserUtils.getUsername;

public class Byte_Battle extends JPanel {

    CardLayout cardLayout;
    JPanel mainPanel;
    JPanel thisPanel;
    static String challenge_level = "beginner_questions";

    JPanel buttons = new JPanel();
    JButton beginner = new JButton("BEGINNER");
    JButton intermediate = new JButton("INTERMEDIATE");
    JButton expert = new JButton("EXPERT");
    JButton backbutton = new JButton("⬅ Back");

    public Byte_Battle(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        thisPanel = this;

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(227, 227, 216)); // ✅ Cream background

        // ✅ Title
        JLabel title = new JLabel("⚔️ Pick Your Challenge", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI Black", Font.BOLD, 50));
        title.setForeground(new Color(134, 209, 183)); // ✅ Pista title
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        add(title, BorderLayout.NORTH);

        // ✅ Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        buttonPanel.setOpaque(false);

        Dimension btnSize = new Dimension(500, 90);
        Font btnFont = new Font("Segoe UI Semibold", Font.BOLD, 35);

        JButton[] challengeBtns = {beginner, intermediate, expert};
        for (JButton btn : challengeBtns) {
            btn.setPreferredSize(btnSize);
            btn.setFont(btnFont);
            btn.setBackground(new Color(134, 209, 183)); // ✅ Pista background
            btn.setForeground(Color.BLACK);              // ✅ Black text
            btn.setFocusPainted(false);
            buttonPanel.add(btn);
        }

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        center.add(buttonPanel);

        add(center, BorderLayout.CENTER);

        // ✅ Back Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);

        backbutton.setPreferredSize(new Dimension(300, 70));
        backbutton.setFont(new Font("Segoe UI Semibold", Font.BOLD, 28));
        backbutton.setBackground(new Color(134, 209, 183)); // ✅ Pista
        backbutton.setForeground(Color.BLACK);              // ✅ Black text
        backbutton.setFocusPainted(false);

        bottomPanel.add(backbutton);
        add(bottomPanel, BorderLayout.SOUTH);

        // ✅ Actions
        beginner.addActionListener(e -> {
            challenge_level = "beginner_questions";
            cardLayout.show(mainPanel, "chapterIndex");
        });

        intermediate.addActionListener(e -> {
            challenge_level = "intermediate_questions";
            cardLayout.show(mainPanel, "chapterIndex");
        });

        expert.addActionListener(e -> {
            challenge_level = "expert_questions";
            cardLayout.show(mainPanel, "chapterIndex");
        });

        backbutton.addActionListener(e -> cardLayout.show(mainPanel, "leetcode"));
    }

    public JPanel getPanel() {
        return this;
    }
}
