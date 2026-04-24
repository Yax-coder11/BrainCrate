package Leet_Cod;

import AppLauncher.AppLauncher;
import AppLauncher.AppLauncher.*;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Leet_Cod.PracticeQuestion.selectedQuestionId;
import static utils.UserUtils.getUsername;
import static Leet_Cod.ChapterIndex.selectedChapterId;


public class checkAnswerAddPoints  {
    String answer;
    int time;
    String expectedAnswer;
    int expectedPoint = 0;
    int normalTime;

    public checkAnswerAddPoints(){}
    public checkAnswerAddPoints(String answer, int time) {
        this.answer = answer;
        this.time = time;
        assignPoints();
    }

    public void assignPoints() {
        try {
            new DataBaseConnect();
            String setPoints = "Select answer,points,normalTime from "+Byte_Battle.challenge_level+" where question_id = ? " +
                    "and chapter_id=?";
            selectedQuestionId=selectedQuestionId+10*(selectedChapterId-1);
            PreparedStatement pst = DataBaseConnect.con.prepareStatement(setPoints);
            pst.setInt(2,selectedChapterId);
            pst.setInt(1, selectedQuestionId);

            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                expectedAnswer = rs.getString("answer");
                expectedPoint = expectedPoint +  rs.getInt("points");
                normalTime =rs.getInt("normalTime");
                System.out.println(expectedAnswer);
            }
if( expectedAnswer.trim().equalsIgnoreCase(answer.trim())){

    JOptionPane.showMessageDialog(null, "Correct Answer 🎊", "-- Answer Status --", JOptionPane.INFORMATION_MESSAGE);


    //BONUS pointsss
                if(time<normalTime){
                    expectedPoint = expectedPoint + 10;
                    JOptionPane.showMessageDialog(null,"+ "+ 10 +" added to your points chart for faster response \n KEEP YOUR PACE HIGH ✨"
                            ,"Answer status" , JOptionPane.INFORMATION_MESSAGE);
                }

                    CallableStatement cst = DataBaseConnect.con.prepareCall("{call updatepoints(?,?,?)}");
                    cst.setString(1, getUsername());
                    cst.setInt(2, expectedPoint);
                    cst.setString(3,Byte_Battle.challenge_level);
                    cst.execute();
                        JOptionPane.showMessageDialog(null,"+ "+ expectedPoint +" added to your points chart "
                                ,"Answer status" , JOptionPane.INFORMATION_MESSAGE);


                    File f = new File(getUsername()+".txt");
                    FileWriter fw = new FileWriter(f ,true);
                    fw.write(Byte_Battle.challenge_level + "."+selectedChapterId + "." + selectedQuestionId +"\n");
                    fw.flush();
                    fw.close();

                    AppLauncher.cardLayout.show(AppLauncher.mainPanel,"practiceQuestions");

                }

            else{
                JOptionPane.showMessageDialog(null,"incorrect answer"
                        ,"-- Answer Status --" , JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}