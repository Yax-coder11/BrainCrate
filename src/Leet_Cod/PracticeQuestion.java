package Leet_Cod;

import Leet_Cod.DatabaseQueue.Queue1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import static Leet_Cod.ChapterIndex.*;


public class PracticeQuestion extends JPanel {

    public static int noOfQuestion;
    static int selectedQuestionId;
    CardLayout cardLayout;
    JPanel mainPanel;

    // 🎨 Colors
    private final Color pistaGreen = new Color(134, 209, 183);

    public PracticeQuestion(CardLayout cardLayout, JPanel mainPanel) {

        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(227, 227, 216)); // Dark theme background

        // 🔹 Title
        JLabel title = new JLabel("📘 Practice Questions", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI Black", Font.BOLD, 40));
        title.setForeground(new Color(1, 16, 23));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // 🔹 Button Panel (Grid inside ScrollPane)
        JPanel buttonPanel = new JPanel(new GridLayout(0, 5, 20, 20)); // 5 per row
        buttonPanel.setOpaque(false);

        JScrollPane questionScrollPane = new JScrollPane(buttonPanel);
        questionScrollPane.setBorder(null);
        add(questionScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);

        // 🔹 Back Button
        JButton backButton = new JButton("🔙 Back");
        backButton.setPreferredSize(new Dimension(250, 70));
        backButton.setFont(new Font("Segoe UI Semibold", Font.BOLD, 28));
        backButton.setBackground(pistaGreen);
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);

        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "chapterIndex"));

        // Query for questions
        try {
            System.out.println(selectedQuestionId);
            String sql2 = "SELECT COUNT(*) FROM " + Byte_Battle.challenge_level + " WHERE chapter_id = " + selectedChapterId;
            ResultSet rsNoOfQuestion = st.executeQuery(sql2);

            if (rsNoOfQuestion.next()) {
                noOfQuestion = rsNoOfQuestion.getInt(1);
                System.out.println(noOfQuestion);
            } else {
                JOptionPane.showMessageDialog(null, "No questions found.");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Buffering question visit again ");
        }

        // ✅ Add question buttons
        for (int q = 1; q <= noOfQuestion; q++) {
            JButton questionButton;
            if (solvedInChapter.contains1(q)) {
                questionButton = new JButton("Q" + q + " \u2713"); // " ☑️"
                questionButton.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 22));
            } else {
                questionButton = new JButton("Q" + q);
            }

            questionButton.setName("" + q);
            questionButton.setPreferredSize(new Dimension(100, 25));
            questionButton.setBackground(pistaGreen);
            questionButton.setForeground(Color.BLACK);
            questionButton.setFocusPainted(false);

            buttonPanel.add(questionButton);

            JButton finalQuestionButton = questionButton;
            questionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedQuestionId = Integer.parseInt(finalQuestionButton.getName());
                    System.out.println(selectedQuestionId);
                    DisplayQuestion displayQuestion = new DisplayQuestion(cardLayout, mainPanel);
                    mainPanel.add(displayQuestion.getPanel(), "displayQuestion");
                    cardLayout.show(mainPanel, "displayQuestion");
                }
            });
        }

        revalidate();
        repaint();
    }

    public JPanel getPanel() {
        return this;
    }
}
