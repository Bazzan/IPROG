
import java.awt.LayoutManager;
import java.sql.*;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.jar.Attributes.Name;
import java.awt.*;
import javax.management.Query;
import javax.swing.*;

public class DatabasKoppling {
    static TArea ta = new TArea();
    private static final String newLine = "\n";
    private static final String tableName = "GuestBook";
    private static Connection dbConnection;
    private static ArrayList<Person> personList = new ArrayList<>();
    private String filter = null;
    String orderBy = null;

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS GuestBook ("
            + "NAME VARCHAR(45) NOT NULL," + "EMAIL VARCHAR(45)," + "HOMEPAGE VARCHAR(45)," + "COMMENT VARCHAR(100))";

    public void addStatement(String name, String epost, String homepage, String comment) {
        name = suppressHtml(name, "[censored]");
        epost = suppressHtml(epost, "[censored]");
        homepage = suppressHtml(homepage, "[censored]");
        comment = suppressHtml(comment, "[censored]");

        String sqlStatement = "insert into GuestBook" + "(name, email, homepage, comment) " + "VALUES ( " + "'" + name
                + "', '" + epost + "', '" + homepage + "', '" + comment + "')";


        try {
            Statement statement = dbConnection.createStatement();

            System.out.println("statment object created");
            System.out.println(sqlStatement);

            ta.ta.append(sqlStatement);

            statement.executeUpdate(sqlStatement);

            System.out.println("Added");
            statement.close();
        } catch (SQLException sql) {
            System.out.println(sql);

        }

    }

    public void getAll() {
        String sqlStatement = "SELECT name, email, homepage, comment " + "FROM " + tableName + " "
                + (filter == null ? "" : "WHERE " + filter + " ") + (orderBy == null ? "" : "ORDER BY " + orderBy);
        ta.ta.append(sqlStatement + newLine);
        try {
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlStatement);

            while (resultSet.next()) {

                personList.add(new Person(resultSet.getString("Name"), resultSet.getString("Email"),
                        resultSet.getString("Homepage"), resultSet.getString("Comment")));

                ta.ta.append("Name:" + resultSet.getString("Name") + ", Email:" + resultSet.getString("Email")
                        + ", Homepage: " + resultSet.getString("Homepage") + ", Comment: "
                        + resultSet.getString("Comment") + newLine);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException sql) {
            System.out.println(sql);

        }

    }

    private static String suppressHtml(String str, String censored) {
        return str.replaceAll("<.*?>", censored).replaceAll("<", "").replaceAll(">", "");
    }

    private static String sqlQuote(String str) {
        return str == null || str.length() == 0 ? "NULL" // empty/null --> NULL
                : "" + str.replaceAll("'", "''"); // ' --> ''
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

    static class TArea extends JFrame {
        JTextArea ta;
        JScrollPane scrollP;

        public TArea() {
            ta = new JTextArea(30, 50);
            scrollP = new JScrollPane(ta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            setLayout(new FlowLayout());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            add(scrollP);
            setSize(600, 600);
            setLocationRelativeTo(null);
            setVisible(true);
        }
    }

    public static void main(String[] args) {
        try {
            Scanner scan = new Scanner(System.in);

            Class.forName("com.mysql.jdbc.Driver").getDeclaredConstructor().newInstance();
            String computer = "atlas.dsv.su.se:3306";
            String db_name = "db_20380857";
            String user = "usr_20380857";
            String password = "380857";

            String url = ("jdbc:mysql://" + computer + "/" + db_name);

            System.out.println("connecting to DBas");

            DatabasKoppling dbasK = new DatabasKoppling();

            dbConnection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected");

            try {
                Statement statement = dbConnection.createStatement();
                statement.execute(CREATE_TABLE_SQL);

            } catch (SQLException e) {
                System.out.println(e);

            }

            while (true) {
                System.out.println("press 1 for insert, 2 for printing all, (write in the terminal)");
                int input = scan.nextInt();
                if (input == 1) {
                    insert(dbasK);

                }

                if (input == 2) {
                    dbasK.getAll();

                }
            }

        } catch (SQLException e) {
            System.out.println(e);
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    private static void insert(DatabasKoppling dbasK) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Name");

        String name = scan.nextLine();

        System.out.println("email");

        String email = scan.nextLine();
        System.out.println("webbpage");

        String webbpage = scan.nextLine();
        System.out.println("Comment");
        String comment = scan.nextLine();

        System.out.println("trying to make statement");

        dbasK.addStatement(name, email, webbpage, comment);

        System.out.println("addstatement is done");
    }

}