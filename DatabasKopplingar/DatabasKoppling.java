
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.jar.Attributes.Name;

import javax.management.Query;

public class DatabasKoppling {
    private static final String tableName = "GuestBook";
    private static Connection dbConnection;
    private static ArrayList<Person> personList;
    private String filter = null;
    String orderBy = null;
public void addStatement(String name, String email, String webbpage, String comment) {

    String sqlStatement = "INSTER INTO " + tableName + " " + "( Name, Email, Webbpage, Comment) " + "VALUES ( "
            + sqlQuote(name) + ", " + sqlQuote(email) + ", " + sqlQuote(webbpage) + ", " + sqlQuote(comment) + ", "
            + " )";

    try {
        Statement statement = dbConnection.createStatement();
        statement.executeUpdate(sqlStatement);
        System.out.println("Added");
        statement.close();
    } catch (Exception e) {
        // TODO: handle exception
    }

}

public void getAll() {


    String sqlStatement = "SELECT Name, Email, Webbpage, Comment " + "FROM " + tableName + " "
            + "FROM " + tableName + " " 
            + (  filter  == null ? "" : "WHERE " + filter + " " ) 
            + (  orderBy == null ? "" : "ORDER BY " + orderBy );
    try {
        Statement statement = dbConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sqlStatement);

        while (resultSet.next()) {
            this.personList.add(new Person(resultSet.getString("Name"), resultSet.getString("Email"), resultSet.getString("Webbpage"),
                    resultSet.getString("Comment")));
                    // System.out.println(resultSet.getString(1) +resultSet.getString(2) +resultSet.getString(3) +resultSet.getString(4));
            System.out.println(personList.get(0).name);
        }
        System.out.println("Gett all");
        resultSet.close();
        statement.close();

    } catch (Exception e) {
        // TODO: handle exception
    }

}

private static String suppressHtml(String str, String censored) {
    return str.replaceAll("<.*?>", censored) // Replaces all HTML tags with censored param
            .replaceAll("<", "") // Removes stand-alone '<'
            .replaceAll(">", ""); // Removes stand-alone '>'
}

private static String sqlQuote(String str) {
    return str == null || str.length() == 0 ? "NULL" // empty/null --> NULL
            : "'" + str.replaceAll("'", "''") + "'"; // ' --> ''
}

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
        String computer = "atlas.dsv.su.se";
        String db_name = "db_20380857";
        String user = "usr_20380857";
        String password = "380857";

        String url = ("jdbc:mysql://" + computer + "/" + db_name);
        dbConnection = DriverManager.getConnection(url, user, password);
        Scanner scan = new Scanner(System.in);
        System.out.println("Name");

        String name = scan.nextLine();
        System.out.println("email");

        String email = scan.nextLine();
        System.out.println("webbpage");

        String webbpage = scan.nextLine();
        System.out.println("Comment");
        String comment = scan.nextLine();

        DatabasKoppling dbasK = new DatabasKoppling();
        personList = new ArrayList<Person>();

        dbasK.addStatement(name, email, webbpage, comment);


        dbasK.getAll();
        

            // String sql = name + ", " + email + ", " + webbpage + ", " + comment;
            // Statement statement = dbConnection.createStatement();
            // statement.executeUpdate(sql);
            // statement.close();

            // Statement statement2 = dbConnection.createStatement();
            // String lastSql = "SELECT Id, Name, EmailAddress, HomePage, Comment " + "FROM " + " " + sql;

            // ResultSet rs = statement2.executeQuery(lastSql);

            // ArrayList<Person> lis = new ArrayList<>();

            // Person p = new Person(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
            // lis.add(p);

            // System.out.println(lis.get(0).name);

            // System.out.println(lis.get(1).toString());
            // System.out.println(dbConnection. + "hello?");
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}