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

        generateTotalStockMarketAnalysis(conn);

    }

    public static void generateTotalStockMarketAnalysis(Connection conn) {
        String numSecsStart2016, numSecsEnd2016, numPriceInc, numPriceDec;
        numSecsStart2016 = numSecsEnd2016 = numPriceInc = numPriceDec = "";
        GeneralStockQueries queries = new GeneralStockQueries();

        try {
            Statement st = conn.createStatement();
            st.execute("use nyse");

            ResultSet result = st.executeQuery(queries.numSecsStart2016);
            while(result.next()) {
                numSecsStart2016 = result.getString(1);
            }

            result = st.executeQuery(queries.numSecsEnd2016);
            while(result.next()) {
                numSecsEnd2016 = result.getString(1);
            }

            result = st.executeQuery(queries.numPriceIncreases);
            while(result.next()) {
                numPriceInc = result.getString(1);
            }

            result = st.executeQuery(queries.numPriceDecreases);
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
