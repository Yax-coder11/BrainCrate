package AppLauncher;

import Leet_Cod.*;
import auth.LoginPage;
import auth.SignupPage;
import dashboard.LeetcodePanel;
import dashboard.MainDashboardPanel;
import auth.WelcomePage;

import javax.swing.*;
import java.awt.*;

public class AppLauncher {
    public static CardLayout cardLayout;
    public static  JPanel mainPanel;
    public static void main(String[] args) {

        //connecting to database
        new DataBaseConnect();

        //to prevent overlap of gui elements
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("BrainCrate - Developer Productivity Kit");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setSize(screenSize);
            //frame.setSize(900, 650);
            frame.setLocationRelativeTo(null); // Centers the JFrame to the monitor screen
            frame.setResizable(false);
            // default layout set to borderlayout


            cardLayout = new CardLayout();
            mainPanel = new JPanel(cardLayout);

            // Create all panels
            WelcomePage welcomePage = new WelcomePage(cardLayout, mainPanel);
            LoginPage loginPage = new LoginPage(cardLayout, mainPanel);
            SignupPage signupPage = new SignupPage(cardLayout, mainPanel);
            MainDashboardPanel mainDashboard = new MainDashboardPanel(cardLayout, mainPanel);

            Byte_Battle byte_Battle = new Byte_Battle(cardLayout, mainPanel);
            ChapterIndex chapterIndex = new ChapterIndex(cardLayout, mainPanel);
            //PracticeQuestion practiceQuestion = new PracticeQuestion(cardLayout, mainPanel);
            //DisplayQuestion displayQuestion = new DisplayQuestion(cardLayout, mainPanel);
            //LeaderBoard leaderBoard = new LeaderBoard(cardLayout, mainPanel);
            //MyResponses myResponses = new MyResponses(cardLayout,mainPanel);

            LeetcodePanel leetcodePanel = new LeetcodePanel(cardLayout,mainPanel);
             //TaskManager taskManager = new TaskManager(cardLayout,mainPanel);


            // Add all panels to mainPanel
            mainPanel.add(welcomePage.getPanel(), "welcome");
            mainPanel.add(loginPage.getPanel(), "login");
            mainPanel.add(signupPage.getPanel(), "signup");

            mainPanel.add(mainDashboard.getPanel(), "dashboard");
            mainPanel.add(leetcodePanel.getPanel(), "leetcode");

            mainPanel.add(byte_Battle.getPanel(),"byte_battle");
            mainPanel.add(chapterIndex.getPanel(),"chapterIndex");
            // mainPanel.add(practiceQuestion.getPanel(),"practiceQuestion");
            // mainPanel.add(displayQuestion.getPanel(),"displayQuestion");
            //mainPanel.add(leaderBoard .getPanel(),"leaderBoard");
            //mainPanel.add(myResponses.getPanel())
            //mainPanel.add(taskManagerPanel.getPanel(), "TaskManager1");
            //mainPanel.add(taskManager.getPanel(), "TaskManager");


            cardLayout.show(mainPanel, "welcome");
            frame.add(mainPanel);
            frame.setVisible(true);
        });
    }
}



/*
SwingUtilities.invokeLater(() -> {
     Swing has its own Thread [ EDT -> Event Dispatch Thread ] which ensure bug fix enviroment for gui components ]
     main thread is different and EDT is different so for bug free gui interface we use this .
     invokeLater() tells run the below code as soon as possible .

ImageIcon
    .getImage() because setIconImage specifically needs Image not ImageIcon
     where as Jbutton and Jlabel accepts ImageIcon not Image
*/

// we have mainpanel[JPanel] which contains all other panels , we add every panels in MainPanel and then we add mainpanel in frame .

// When we extend any class by JPanel that class act as a JPanel .
// by default frames layout is set to BorderLayout