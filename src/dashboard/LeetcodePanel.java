package dashboard;

import Leet_Cod.LeaderBoard;
import Leet_Cod.MyResponses;

import javax.swing.*;
import java.awt.*;

public class LeetcodePanel extends JPanel {
    public LeetcodePanel(CardLayout cardLayout, JPanel mainPanel) {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(227, 227, 216)); // ✅ Cream background

        // ✅ Title
        JLabel title = new JLabel("⚡ LeetCode Section", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI Black", Font.BOLD, 45));
        title.setForeground(new Color(134, 209, 183)); // ✅ Pista title
        title.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));
        add(title, BorderLayout.NORTH);

        // ✅ Buttons
        JButton byte_battle = new JButton(" BYTE_BATTLE ");
        JButton leaderBoardBtn = new JButton(" LEADERBOARD 🏅 ");
        JButton saveMyResponse = new JButton(" SAVE MY RESPONSE ");
        JButton backBtn = new JButton(" ⬅ Back to Dashboard");

        JButton[] buttons = {byte_battle, leaderBoardBtn, saveMyResponse, backBtn};
        for (JButton btn : buttons) {
            btn.setPreferredSize(new Dimension(400, 90));
            btn.setFont(new Font("Segoe UI Semibold", Font.BOLD, 28));
            btn.setBackground(new Color(134, 209, 183)); // ✅ Pista
            btn.setForeground(Color.BLACK);              // ✅ Black text
            btn.setFocusPainted(false);
        }

        // ✅ Actions
        byte_battle.addActionListener(e -> cardLayout.show(mainPanel, "byte_battle"));

        leaderBoardBtn.addActionListener(e -> {
            LeaderBoard leaderBoard = new LeaderBoard(cardLayout, mainPanel);
            mainPanel.add(leaderBoard.getPanel(), "leaderBoard");
            cardLayout.show(mainPanel, "leaderBoard");
        });

        saveMyResponse.addActionListener(e -> {
            MyResponses myResponses = new MyResponses(cardLayout, mainPanel);
            mainPanel.add(myResponses.getPanel(), "myResponse");
            cardLayout.show(mainPanel, "myResponse");
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));

        // ✅ Center Layout
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 30, 30));
        centerPanel.setOpaque(false);
        centerPanel.add(byte_battle);
        centerPanel.add(leaderBoardBtn);
        centerPanel.add(saveMyResponse);
        centerPanel.add(backBtn);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setOpaque(false);
        wrapper.add(centerPanel);

        add(wrapper, BorderLayout.CENTER);
    }

    public JPanel getPanel() {
        return this;
    }
}
