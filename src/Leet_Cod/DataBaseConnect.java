package Leet_Cod;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnect {
    public static Connection con;
    public DataBaseConnect() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);

            String dburl = "jdbc:mysql://localhost:3306/byte_battle";
            String dbuser = "root";
            String dbpass = "";
            con = DriverManager.getConnection(dburl, dbuser, dbpass);
//            DataBaseConnect.connect(); // ✅ Connect first



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DataBaseConnect();
    }
}