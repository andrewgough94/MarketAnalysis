package marketanalysis;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;
import java.util.ArrayList;

/**
 * Lab 8
 * Andrew Gough (agough) & Jake Whipple
 *
 * MarketAnalysis executes sql queries and retrieves data into our
 * java application.
 */
public class MarketAnalysis {

    private static Connection conn;
    private static GeneralStockQueries genQueries;


    public static void main(String args[]) {
        System.out.println("Welcome to Market Analysis!");
        conn = initializeConnection();
        genQueries = new GeneralStockQueries();

        generateTotalStockMarketAnalysisQ1(conn);

        generateTotalStockMarketAnalysisQ2(conn);



    }

    public static void generateTotalStockMarketAnalysisQ1(Connection conn) {
        String numSecsStart2016, numSecsEnd2016, numPriceInc, numPriceDec;
        numSecsStart2016 = numSecsEnd2016 = numPriceInc = numPriceDec = "";

        try {
            Statement st = conn.createStatement();
            st.execute("use nyse");

            ResultSet result = st.executeQuery(genQueries.numSecsStart2016);
            while(result.next()) {
                numSecsStart2016 = result.getString(1);
            }

            result = st.executeQuery(genQueries.numSecsEnd2016);
            while(result.next()) {
                numSecsEnd2016 = result.getString(1);
            }

            result = st.executeQuery(genQueries.numPriceIncreases);
            while(result.next()) {
                numPriceInc = result.getString(1);
            }

            result = st.executeQuery(genQueries.numPriceDecreases);
            while(result.next()) {
                numPriceDec = result.getString(1);
            }
            System.out.println("Secs at start: " + numSecsStart2016 + " Secs at end: " + numSecsEnd2016);
            System.out.println("NumPriceIncreases: " + numPriceInc + " NumPriceDecreases: " + numPriceDec);

        }
        catch (Exception ex) {
            System.out.println("Failed");
            System.out.println(ex);
        }
    }

    public static void generateTotalStockMarketAnalysisQ2(Connection conn) {
        ArrayList<String> securities = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            st.execute("use nyse");


            ResultSet result = st.executeQuery(genQueries.topTenTraded2016);
            while(result.next()) {
                securities.add(result.getString(1));
            }

            System.out.println("Top Ten Secs by volume:");
            int i = 0;
            while(i < securities.size()) {
                System.out.print(securities.get(i++) + " ");
            }
        }
        catch (Exception ex) {
            System.out.println("Failure");
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
