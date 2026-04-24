

package Leet_Cod;

import javax.swing.*;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.*;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

import static Leet_Cod.ChapterIndex.selectedChapterId;
import static Leet_Cod.DataBaseConnect.con;
import static Leet_Cod.PracticeQuestion.*;
import static utils.UserUtils.getUsername;

public class DisplayQuestion extends JPanel {

    static Timer swingTimer;
    static int secondsElapsed = 0;
    static int timeTaken;
    static String cleanedCode;

    static JLabel timerLabel;
    static Statement st;
    JTextField  question_Area = new JTextField(); ;
    CardLayout cardLayout;
    JPanel mainPanel;
    static JPanel thisPanel ;
    RSyntaxTextArea textArea;

    public DisplayQuestion(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        thisPanel = this;

        DataBaseSetup(selectedQuestionId);
        // Timer label
        timerLabel = new JLabel("Time: 00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));  // ✅ 2 rows since we only add 2 items
        topPanel.add(question_Area);
        topPanel.add(timerLabel);
        add(topPanel, BorderLayout.NORTH);

        // Buttons
        JPanel buttons = new JPanel();
        JButton submitButton = new JButton("SUBMIT");
        JButton hintButton = new JButton("HINT");
        JButton saveMyResponseButton = new JButton("Save my response");
        JButton backButton = new JButton("GO BACK");
        submitButton.setBackground(new Color(134, 209, 183)); //
        hintButton.setBackground(new Color(134, 209, 183)); //
        saveMyResponseButton.setBackground(new Color(134, 209, 183)); //
        backButton.setBackground(new Color(134, 209, 183)); //
        buttons.add(submitButton);
        buttons.add(hintButton);
        buttons.add(saveMyResponseButton);
        buttons.add(backButton);
        add(buttons, BorderLayout.SOUTH);

        // Syntax highlighting text area
        textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        RTextScrollPane scrollPane = new RTextScrollPane(textArea);
        textArea.setText("class Main{\n" +
                "public static void main(String[]args){\n" +
                "}}");
        add(scrollPane, BorderLayout.CENTER);

        // ✅ Start timer only when typing
        textArea.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                if (swingTimer == null || !swingTimer.isRunning()) {
                    startTimer();
                }
            }
        });

        // Focus on code area
        SwingUtilities.invokeLater(() -> textArea.requestFocusInWindow());

        // Submit
        submitButton.addActionListener(e -> {
            stopTimerAndShowTime();

            String rawCode = textArea.getText();
            cleanedCode = rawCode.trim();

            if (cleanedCode.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Cannot submit empty code!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                new CodeConnector();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            timerLabel.setText("Time: 00:00");
        });

        // Hint
        hintButton.addActionListener(e -> new HintDisplay());

        //save my response
        saveMyResponseButton.addActionListener(e -> saveResponse());


        // Back
        backButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "practiceQuestion");});

    }


    public  void DataBaseSetup(int selectedQuestionId) {
        try {
            selectedQuestionId = 10*(selectedChapterId-1)+selectedQuestionId;
            String sqlQuestionFetching = "Select question from "+ Byte_Battle.challenge_level +" where chapter_id = "+
                                          selectedChapterId +" and question_id = " + selectedQuestionId;
            System.out.println(sqlQuestionFetching);
            st = con.createStatement();
            ResultSet rsStoreQuestion = st.executeQuery(sqlQuestionFetching);

            if (rsStoreQuestion.next()) {
                question_Area.setText(rsStoreQuestion.getString(1));
                question_Area.setEditable(false);
            } else {
                System.out.println("Question not found for QID=" + selectedQuestionId +
                        " CID=" + selectedChapterId + " in " + Byte_Battle.challenge_level);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    static void stopTimerAndShowTime() {
        if (swingTimer != null && swingTimer.isRunning()) {
            swingTimer.stop();
        }

        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        timeTaken = secondsElapsed;

        String formattedTime = String.format("Time Taken: %02d:%02d", minutes, seconds);
        JOptionPane.showMessageDialog(null, formattedTime, "Submission Time", JOptionPane.INFORMATION_MESSAGE);

        timerLabel.setText("Time: 00:00");
    }

    private static void startTimer() {
        secondsElapsed = 0;
        timerLabel.setText("Time: 00:00");

        swingTimer = new Timer(1000, e -> {
            secondsElapsed++;
            int minutes = secondsElapsed / 60;
            int seconds = secondsElapsed % 60;
            timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));

        });
        swingTimer.start();
    }

    public DisplayQuestion() {
    }
    private void saveResponse() {
        String username = getUsername(); // from UserUtils
        if (username == null || username.isEmpty()) {
            JOptionPane.showMessageDialog(null,"⚠ Cannot save: No user logged in." , "ERROR" , JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Folder structure: responses/<username>/
        File userDir = new File("responses" + File.separator + username);
        if (!userDir.exists()) {
            userDir.mkdirs(); // create folders if they don't exist
        }

        // File name: ChapterX_QY.txt
        String filename = "Chapter" + selectedChapterId + "_Q" + selectedQuestionId + ".txt";
        File fileToSave = new File(userDir, filename);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileToSave))) {
            bw.write("// Chapter: " + selectedChapterId + " | Question: " + selectedQuestionId + "\n");
            String rawCode = textArea.getText();
            cleanedCode = rawCode.trim();
            bw.write(cleanedCode);
            JOptionPane.showMessageDialog(null ,"💾 Saved response to " + fileToSave.getPath() ,"STATUS" , JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null ,"⚠ Error saving response: " + e.getMessage() ,"STATUS" , JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public JPanel getPanel() {
        return this;
    }
}
