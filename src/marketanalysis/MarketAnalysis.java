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

        generateTotalStockMarketAnalysisQ3(conn);

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


    public static void generateTotalStockMarketAnalysisQ3(Connection conn){

        try{
            Statement st = conn.createStatement();
            st.execute("use nyse");
            ArrayList<String> securities1 = new ArrayList<>();

            ResultSet result1 = st.executeQuery(genQueries.topFiveABS2010);
            while(result1.next()) {
                securities1.add(result1.getString(1));
            }

            System.out.println("\n\nTop Five ABS Change 2010:");
            int i1 = 0;
            while(i1 < securities1.size()) {
                System.out.print(securities1.get(i1++) + " ");
            }

            ArrayList<String> securities11 = new ArrayList<>();

            ResultSet result11 = st.executeQuery(genQueries.topFive2010);
            while(result11.next()) {
                securities11.add(result11.getString(1));
            }

            System.out.println("\n\nTop Five Price Increase 2010:");
            int i11 = 0;
            while(i11 < securities11.size()) {
                System.out.print(securities11.get(i11++) + " ");
            }

            ArrayList<String> securities2 = new ArrayList<>();

            ResultSet result2 = st.executeQuery(genQueries.topFiveABS2011);
            while(result2.next()) {
                securities2.add(result2.getString(1));
            }

            System.out.println("\n\nTop Five ABS Change 2011:");
            int i2 = 0;
            while(i2 < securities2.size()) {
                System.out.print(securities2.get(i2++) + " ");
            }

            ArrayList<String> securities12 = new ArrayList<>();

            ResultSet result12 = st.executeQuery(genQueries.topFive2011);
            while(result12.next()) {
                securities12.add(result12.getString(1));
            }

            System.out.println("\n\nTop Five Price Increase 2011:");
            int i12 = 0;
            while(i12 < securities12.size()) {
                System.out.print(securities12.get(i12++) + " ");
            }

            ArrayList<String> securities3 = new ArrayList<>();

            ResultSet result3 = st.executeQuery(genQueries.topFiveABS2012);
            while(result3.next()) {
                securities3.add(result3.getString(1));
            }

            System.out.println("\n\nTop Five ABS Change 2012:");
            int i3 = 0;
            while(i3 < securities3.size()) {
                System.out.print(securities3.get(i3++) + " ");
            }

            ArrayList<String> securities13 = new ArrayList<>();

            ResultSet result13 = st.executeQuery(genQueries.topFive2012);
            while(result13.next()) {
                securities13.add(result13.getString(1));
            }

            System.out.println("\n\nTop Five Price Increase 2012:");
            int i13 = 0;
            while(i13 < securities13.size()) {
                System.out.print(securities13.get(i13++) + " ");
            }

            ArrayList<String> securities4 = new ArrayList<>();

            ResultSet result4 = st.executeQuery(genQueries.topFiveABS2013);
            while(result4.next()) {
                securities4.add(result4.getString(1));
            }

            System.out.println("\n\nTop Five ABS Change 2013:");
            int i4 = 0;
            while(i4 < securities4.size()) {
                System.out.print(securities4.get(i4++) + " ");
            }

            ArrayList<String> securities14 = new ArrayList<>();

            ResultSet result14 = st.executeQuery(genQueries.topFive2013);
            while(result14.next()) {
                securities14.add(result14.getString(1));
            }

            System.out.println("\n\nTop Five Price Increase 2013:");
            int i14 = 0;
            while(i14 < securities14.size()) {
                System.out.print(securities14.get(i14++) + " ");
            }

            ArrayList<String> securities5 = new ArrayList<>();

            ResultSet result5 = st.executeQuery(genQueries.topFiveABS2014);
            while(result5.next()) {
                securities5.add(result5.getString(1));
            }

            System.out.println("\n\nTop Five ABS Change 2014:");
            int i5 = 0;
            while(i5 < securities5.size()) {
                System.out.print(securities5.get(i5++) + " ");
            }

            ArrayList<String> securities15 = new ArrayList<>();

            ResultSet result15 = st.executeQuery(genQueries.topFive2014);
            while(result15.next()) {
                securities15.add(result15.getString(1));
            }

            System.out.println("\n\nTop Five Price Increase 2014:");
            int i15 = 0;
            while(i15 < securities15.size()) {
                System.out.print(securities15.get(i15++) + " ");
            }


            ArrayList<String> securities6 = new ArrayList<>();

            ResultSet result6 = st.executeQuery(genQueries.topFiveABS2015);
            while(result6.next()) {
                securities6.add(result6.getString(1));
            }

            System.out.println("\n\nTop Five ABS Change 2015:");
            int i6 = 0;
            while(i6 < securities6.size()) {
                System.out.print(securities6.get(i6++) + " ");
            }


            ArrayList<String> securities16 = new ArrayList<>();

            ResultSet result16 = st.executeQuery(genQueries.topFive2015);
            while(result16.next()) {
                securities16.add(result16.getString(1));
            }

            System.out.println("\n\nTop Five Price Increase 2015:");
            int i16 = 0;
            while(i16 < securities16.size()) {
                System.out.print(securities16.get(i16++) + " ");
            }

            ArrayList<String> securities7 = new ArrayList<>();

            ResultSet result7 = st.executeQuery(genQueries.topFiveABS2016);
            while(result7.next()) {
                securities7.add(result7.getString(1));
            }

            System.out.println("\n\nTop Five ABS Change 2016:");
            int i7 = 0;
            while(i7 < securities7.size()) {
                System.out.print(securities7.get(i7++) + " ");
            }

            ArrayList<String> securities17 = new ArrayList<>();

            ResultSet result17 = st.executeQuery(genQueries.topFive2016);
            while(result17.next()) {
                securities17.add(result17.getString(1));
            }

            System.out.println("\n\nTop Five Price Increase 2016:");
            int i17 = 0;
            while(i17 < securities17.size()) {
                System.out.print(securities17.get(i17++) + " ");
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
