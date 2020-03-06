package DatabasKopplingar;

import java.sql.*;

public class Dbas{


    public static void main(String[] args) {
        Connection dbCon = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();

            String computer ="atlas.dsv.su.se";
            String db_name ="db_20380857";
            String user = "usr_20380857";
            String password = "380857";
    
            String url = ("jdbc:mysql://" + computer + "/" + db_name);
            dbCon = DriverManager.getConnection(url, user, password);

            statement =dbCon.createStatement();
            
            rs = statement.executeQuery("select *");

            while(rs.next()){
                System.out.println(rs.getString(0));
            }





        
        } catch (Exception e) {
            //TODO: handle exception
        }

        
    }



}