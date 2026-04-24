package Leet_Cod;

import Leet_Cod.DatabaseQueue.Queue1;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static utils.UserUtils.getUsername;

public class ChapterIndex extends JPanel {

    static int chapterId;
    private int noOfChapter;
    private String nameOfChapter;
    static boolean practiceQuestion=false;

    static Statement st;
    CardLayout cardLayout;
    JPanel mainPanel;
    JPanel thisPanel ;
    static int selectedChapterId;
    static Queue1 solvedInChapter;

    public ChapterIndex(CardLayout cardLayout , JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(227, 227, 216)); // ✅ Cream background
        thisPanel = this;

        // 🔹 Title
        JLabel title = new JLabel("📚 Select a Chapter", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI Black", Font.BOLD, 50));
        title.setForeground(Color.BLACK); // ✅ Black text
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        // 🔹 Back Button
        JButton backButton = new JButton("🔙 Back");
        backButton.setPreferredSize(new Dimension(250, 70));
        backButton.setFont(new Font("Segoe UI Semibold", Font.BOLD, 30));
        backButton.setBackground(new Color(193, 255, 193)); // ✅ Pista green
        backButton.setForeground(Color.BLACK); // ✅ Black text
        backButton.setFocusPainted(false);
        add(backButton, BorderLayout.SOUTH);

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "byte_battle"));

        // 🔹 Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 20, 20));
        buttonPanel.setOpaque(false);
        JScrollPane chapterScrollPane = new JScrollPane(buttonPanel);
        chapterScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chapterScrollPane.setBorder(null);
        add(chapterScrollPane, BorderLayout.CENTER);

        // Setup DB
        setupDatabase();

        // Create Chapter Buttons
        Dimension btnSize = new Dimension(500, 80);
        Font btnFont = new Font("Segoe UI Semibold", Font.BOLD, 30);
        for (int i = 1; i <= noOfChapter; i++) {
            chapterId = i;
            JButton chapterButton = new JButton("Chapter " + chapterId + ": " + chapterName(i));
            chapterButton.setName("" + chapterId);
            chapterButton.setPreferredSize(btnSize);
            chapterButton.setFont(btnFont);
            chapterButton.setBackground(new Color(134, 209, 183)); // ✅ Pista green
            chapterButton.setForeground(Color.BLACK); // ✅ Black text
            chapterButton.setFocusPainted(false);

            buttonPanel.add(chapterButton);

            chapterButton.addActionListener(e -> {
                JButton clickedButton = (JButton) e.getSource();
                selectedChapterId = Integer.parseInt(clickedButton.getName());
                System.out.println("Clicked Chapter ID: " + selectedChapterId);
                setStatus();

                PracticeQuestion practiceQuestion = new PracticeQuestion(cardLayout, mainPanel);
                mainPanel.add(practiceQuestion.getPanel(),"practiceQuestion");

                cardLayout.show(mainPanel, "practiceQuestion");
            });
        }
    }

    private void setupDatabase() {
        try {
            new DataBaseConnect();
            st = DataBaseConnect.con.createStatement();
            String sql1 = "SELECT COUNT(*) FROM "+ Byte_Battle.challenge_level + " group by chapter_id" ;
            ResultSet rsNoOfChapter = st.executeQuery(sql1);
            if (rsNoOfChapter.next()) {
                noOfChapter = rsNoOfChapter.getInt(1);
            } else {
                JOptionPane.showMessageDialog(null, "No chapters found in DB.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String chapterName(int i) {
        String sql3 = "Select chapter_name from chapter_index where chapter_id="+i;
        try {
            ResultSet rsNameOfChapter = st.executeQuery(sql3);
            if (rsNameOfChapter.next()) {
                nameOfChapter = rsNameOfChapter.getString(1);
            } else {
                System.out.println("no chapter name returned");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return nameOfChapter ;
    }

    public JPanel getPanel() {
        return this;
    }

    public static void setStatus() {
        File f = new File(getUsername()+ ".txt");
        solvedInChapter = new Queue1(100);

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.contains(".")) {
                    String[] parts = line.split("\\.");
                    String challangeLevel = parts[0];
                    int chapter = Integer.parseInt(parts[1]);
                    int question = Integer.parseInt(parts[2]);

                    if((chapter == selectedChapterId)&&(challangeLevel.equals(Byte_Battle.challenge_level))) {
                        solvedInChapter.enqueue(question);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("user has not done any questions yet");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
