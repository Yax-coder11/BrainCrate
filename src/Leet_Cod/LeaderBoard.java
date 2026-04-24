package Leet_Cod;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeaderBoard extends JPanel {

    CardLayout cardLayout;
    JPanel mainPanel;

    public LeaderBoard(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(227, 227, 216)); // Cream background

        new DataBaseConnect();

        // Title
        JLabel title = new JLabel("🏆 Leaderboard", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.BLACK); // Black text
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Panel for leaderboard cards
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(new Color(227, 227, 216)); // ✅ Cream

        // Fetch leaderboard data
        String sql = "SELECT user_name, total_points, beginner_points, intermediate_points, expert_points " +
                "FROM user_data ORDER BY total_points DESC";

        List<Object[]> rows = new ArrayList<>();

        try (Statement st = DataBaseConnect.con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            int rank = 1;
            while (rs.next()) {
                rows.add(new Object[]{
                        rank++, // rank
                        rs.getString("user_name"),
                        rs.getInt("total_points"),
                        rs.getInt("beginner_points"),
                        rs.getInt("intermediate_points"),
                        rs.getInt("expert_points")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching leaderboard data.");
        }

        // Create a card for each user
        for (Object[] row : rows) {
            int rank = (int) row[0];
            String username = (String) row[1];
            int total = (int) row[2];
            int beginner = (int) row[3];
            int intermediate = (int) row[4];
            int expert = (int) row[5];

            JPanel card = new JPanel(new BorderLayout());
            card.setMaximumSize(new Dimension(600, 60));
            card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
            card.setBackground(new Color(227, 227, 216)); // ✅ Cream card

            // Rank / Medal
            String medal = rank == 1 ? "🥇" : rank == 2 ? "🥈" : rank == 3 ? "🥉" : "#" + rank;
            JLabel rankLabel = new JLabel(medal);
            rankLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
            rankLabel.setForeground(Color.BLACK);

            // Username
            JLabel nameLabel = new JLabel(username);
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            nameLabel.setForeground(Color.BLACK);

            // Total points
            JLabel pointLabel = new JLabel(total + " pts");
            pointLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            pointLabel.setForeground(Color.DARK_GRAY);

            // Tooltip shows detailed breakdown
            card.setToolTipText(
                    "Beginner: " + beginner +
                            " | Intermediate: " + intermediate +
                            " | Expert: " + expert
            );

            card.add(rankLabel, BorderLayout.WEST);
            card.add(nameLabel, BorderLayout.CENTER);
            card.add(pointLabel, BorderLayout.EAST);

            listPanel.add(card);
            listPanel.add(Box.createVerticalStrut(8));
        }

        // Scroll pane for leaderboard
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(227, 227, 216)); // ✅ Cream
        add(scrollPane, BorderLayout.CENTER);

        // Back button (Pista green, Black text)
        JButton backBtn = new JButton("⬅ Back");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backBtn.setBackground(new Color(193, 255, 193)); // 🌿 Pista green
        backBtn.setForeground(Color.BLACK);
        backBtn.setFocusPainted(false);
        backBtn.setPreferredSize(new Dimension(120, 40));
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "dashboard"));
        add(backBtn, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return this;
    }
}
