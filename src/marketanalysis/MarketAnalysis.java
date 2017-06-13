package marketanalysis;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.*;

/**
 * Created by andrewgough94 on 6/12/2017.
 */
public class MarketAnalysis {

    private static Connection conn;

    public static void main(String args[]) {
        System.out.println("Welcome to Market Analysis!");
        conn = initializeConnection();


    }

    public static void generateTotalStockMarketAnalysis(Connection conn) {
        int numSecsStart2016, numSecsEnd2016;

        try {
            Statement st = conn.createStatement();
            st.execute("use nyse");

            ResultSet result = st.executeQuery(testJDBConnection.)
        }
        catch (Exception ex) {
            System.out.println("Failed");
            System.out.println(ex);
        }
    }



    public static Connection initializeConnection() {
        // Initialize connection
        try {
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser("agough");
            dataSource.setPassword("1654197");
            dataSource.setServerName("cslvm74.csc.calpoly.edu");
            conn = dataSource.getConnection();
        }
        catch (Exception ex) {
            System.out.println("Failed to connect");
            System.out.println(ex);
        }
        return conn;
    }

}
