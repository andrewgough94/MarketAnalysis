package test;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;

/**
 * Created by andrewgough94 on 6/12/2017.
 */
public class testJDBConnection {

    private static Connection conn;
    private static int target = 1;

    public static void main(String args[]) {
        System.out.println("Testing");

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch (Exception ex) {
            System.out.println("Driver not found");
        }

        String url = "jdbc:mysql://cslvm74.csc.calpoly.edu/agough?";

        conn = null;
        try {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser("agough");
            dataSource.setPassword("1654197");
            dataSource.setServerName("cslvm74.csc.calpoly.edu");
            conn = dataSource.getConnection();
        }
        catch (Exception ex) {
            System.out.println("Failed to open a connection");
            System.out.println(ex);
        }

        System.out.println("Connected baby!");
        printAssignmentsTable(conn);
    }

    public static void printAssignmentsTable(Connection conn) {

        System.out.println("Begin print assignments table test\n~~~~~~~~~~~~~~~~~~~~~");
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("use nyse");
            ResultSet result = stmt.executeQuery("select * from assignments");

            while(result.next()) {
                System.out.println(result.getString(1) + " " + result.getString(2));
            }
        }
        catch (Exception ex) {
            System.out.println("Print assignments table failed");
            System.out.println(ex);
        }
    }
}
