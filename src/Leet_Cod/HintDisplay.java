/*

package Leet_Cod;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static Leet_Cod.DataBaseConnect.con;
import static Leet_Cod.PracticeQuestion.selectedChapterId;
import static Leet_Cod.PracticeQuestion.selectedQuestionId;
import static Leet_Cod.Byte_Battle.challenge_level;


public class HintDisplay{

    public HintDisplay() {

        try {
            Statement st =con.createStatement();
            String sql = "Select description from " +challenge_level+" where chapter_id = " + selectedChapterId + " and question_id = " +selectedQuestionId;
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                JOptionPane.showMessageDialog(null, rs.getString("description"), "Hint", JOptionPane.INFORMATION_MESSAGE);
            }

            //-2 points for hint
            checkAnswerAddPoints obj = new checkAnswerAddPoints();
            obj.expectedPoint =obj.expectedPoint - 2;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
*/package Leet_Cod;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static Leet_Cod.DataBaseConnect.con;
import static Leet_Cod.ChapterIndex.selectedChapterId;
import static Leet_Cod.PracticeQuestion.selectedQuestionId;
import static Leet_Cod.Byte_Battle.challenge_level;



public class HintDisplay {
    String tablename = challenge_level;
    int chapterId = selectedChapterId;
    int questionId = selectedQuestionId;

    public HintDisplay() {


        new DataBaseConnect();
        try {
            Statement st =con.createStatement();
            selectedQuestionId=10*(selectedChapterId-1)+selectedQuestionId;
            String sql = "Select description from " +tablename+" where chapter_id = " + chapterId + " and question_id = " +questionId;
            System.out.println("Executing: " + sql);

            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                JOptionPane.showMessageDialog(null, rs.getString("description"), "Hint", JOptionPane.INFORMATION_MESSAGE);
            }

            //-2 points for hint
            checkAnswerAddPoints obj = new checkAnswerAddPoints();
            obj.expectedPoint =obj.expectedPoint - 2;



        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"error in loading hint , or maay be a sign to think moreeee" , "status" ,JOptionPane.INFORMATION_MESSAGE);
        }

    }
}
