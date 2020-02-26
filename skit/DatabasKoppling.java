
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;

import javax.management.Query;

public class DatabasKoppling {

    class Person {
        public String name;
        public String email;
        public String webbpage;
        public String comment;

        public Person(String name, String email, String webbpage, String comment) {
            this.name = name;
            this.email = email;
            this.webbpage = webbpage;
            this.comment = comment;
        }
    }

    public static void main(String[] args) {
    try {
        Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
        String computer ="atlas.dsv.su.se";
        String db_name ="db_20380857";
        String user = "usr_20380857";
        String password = "380857";

        String url = ("jdbc:mysql://" + computer + "/" + db_name);
        Connection dbConnection = DriverManager.getConnection(url, user, password);
        
        Scanner scan = new Scanner(System.in);
        System.out.println("Name");

        String name = scan.nextLine();
        System.out.println("email");

        String email = scan.nextLine();
        System.out.println("webbpage");

        String webbpage  = scan.nextLine();
        System.out.println("Comment");
        String comment = scan.nextLine();

        String sql = name + ", " + email + ", " + webbpage + ", " + comment;
        Statement statement = dbConnection.createStatement();
        statement.executeUpdate(sql);
        statement.close();


        Statement statement2 = dbConnection.createStatement();
       String lastSql = "SELECT Id, Name, EmailAddress, HomePage, Comment "
                        + "FROM " +  " " + sql;

        ResultSet rs = statement2.executeQuery(lastSql);


        ArrayList<Person> lis = new ArrayList<>();

            Person p = new Person(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4));
            lis.add(p);
            
            System.out.println(lis.get(0).name);



        
        System.out.println(lis.get(1).toString() );
        // System.out.println(dbConnection. + "hello?");
    } catch (Exception e) {
        //TODO: handle exception
    }   

        
    }

}