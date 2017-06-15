package marketanalysis;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by andrewgough94 on 6/12/2017.
 */
public class MarketAnalysis {

    private static Connection conn;
    private static GeneralStockQueries genQueries;

    private static BufferedWriter bw = null;
    private static FileWriter fw = null;

    private static File htmlTemplate;
    private static String htmlString;


    public static void main(String args[]) {

        final long start = System.nanoTime();
        generateHtmlFile();

        System.out.println("Welcome to Market Analysis!");

        conn = initializeConnection();
        genQueries = new GeneralStockQueries();

        System.out.println();
        System.out.println("General Analysis Q1");
        generateTotalStockMarketAnalysisQ1(conn);
        final long end = System.nanoTime();

        System.out.println();
        System.out.println();
        System.out.println("General Analysis Q2");
        generateTotalStockMarketAnalysisQ2(conn);

        System.out.println();
        System.out.println();
        System.out.println("General Analysis Q3");
        generateTotalStockMarketAnalysisQ3(conn);
/*
        System.out.println();
        System.out.println();
        System.out.println("General Analysis Q5");
        generateTotalStockMarketAnalysisQ5(conn);
*/

        try {
            File outputFile = new File("serious.html");
            FileWriter fw = new FileWriter(outputFile);

            fw.write(htmlString);
            fw.close();

        }
        catch (Exception ex) {
            System.out.println("Writing failed");
            System.out.println(ex);
        }

        System.out.println("Took: " + ((end - start) / 10000000) + "ms");
    }

    public static void generateHtmlFile(){
        try{
            htmlTemplate = new File("Template.html");
            htmlString = new String(Files.readAllBytes(Paths.get("Template.html")));
        }
        catch(Exception ex){
            System.out.println("Error converting template html to string");
            System.out.println(ex);
        }
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

            //System.out.println("Secs at start: " + numSecsStart2016 + " Secs at end: " + numSecsEnd2016);
            //System.out.println("NumPriceIncreases: " + numPriceInc + " NumPriceDecreases: " + numPriceDec);

            htmlString = htmlString.replace("$numSecsStart2016", numSecsStart2016);
            htmlString = htmlString.replace("$numSecsEnd2016", numSecsEnd2016);
            htmlString = htmlString.replace("$numPriceInc", numPriceInc);
            htmlString = htmlString.replace("$numPriceDec", numPriceDec);
        }
        catch (Exception ex) {
            System.out.println("Failed");
            System.out.println(ex);
        }
    }

    public static void generateTotalStockMarketAnalysisQ2(Connection conn) {
        String arg = "$mostTraded";
        String nameArg = "$mostTradedName";
        int index = 0;

        try {
            Statement st = conn.createStatement();
            st.execute("use nyse");
            ArrayList<String> securities = new ArrayList<>();

            ResultSet result = st.executeQuery(genQueries.topTenTraded2016);
            while(result.next()) {
                securities.add(result.getString(1));
                arg = arg.substring(0, 11) + Integer.toString(index);
                nameArg = nameArg.substring(0,15) + Integer.toString(index++);
                htmlString = htmlString.replace(arg,result.getString(1));
                htmlString = htmlString.replace(nameArg,result.getString(2));
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

        String absArg = "$0abs";
        String relArg = "$0rel";
        int index = 1;

        try{
            Statement st = conn.createStatement();
            st.execute("use nyse");
            ArrayList<String> securities1 = new ArrayList<>();

            ResultSet result1 = st.executeQuery(genQueries.topFiveABS2010);
            absArg = absArg.substring(0,5) + Integer.toString(10);
            while(result1.next()) {
                securities1.add(result1.getString(1));
                absArg = "$" + Integer.toString(index++) + absArg.substring(2,7);
                //System.out.print(absArg);
                htmlString = htmlString.replace(absArg,result1.getString(1));
            }
/*
            System.out.println("\n\nTop Five ABS Change 2010:");
            int i1 = 0;
            while(i1 < securities1.size()) {
                System.out.print(securities1.get(i1++) + " ");
            }
*/
            ArrayList<String> securities11 = new ArrayList<>();

            ResultSet result11 = st.executeQuery(genQueries.topFive2010);
            relArg = relArg.substring(0,5) + Integer.toString(10);
            index = 1;
            while(result11.next()) {
                securities11.add(result11.getString(1));
                relArg = "$" + Integer.toString(index++) + relArg.substring(2,7);
                //System.out.print(relArg);
                htmlString = htmlString.replace(relArg,result11.getString(1));
            }
/*
            System.out.println("\n\nTop Five Relative Increase 2010:");
            int i11 = 0;
            index = 1;
            while(i11 < securities11.size()) {
                System.out.print(securities11.get(i11++) + " ");
                absArg = "$" + Integer.toString(index++) + relArg.substring(2,7);
                //System.out.print(relArg);
                htmlString = htmlString.replace(absArg,securities1.get(i1++));
            }
*/
            ArrayList<String> securities2 = new ArrayList<>();

            ResultSet result2 = st.executeQuery(genQueries.topFiveABS2011);
            index = 1;
            absArg = absArg.substring(0,5) + Integer.toString(11);
            while(result2.next()) {
                securities2.add(result2.getString(1));
                absArg = "$" + Integer.toString(index++) + absArg.substring(2,7);
                //System.out.print(absArg);
                htmlString = htmlString.replace(absArg,result2.getString(1));
            }
/*
            System.out.println("\n\nTop Five ABS Change 2011:");
            int i2 = 0;
            while(i2 < securities2.size()) {
                System.out.print(securities2.get(i2++) + " ");
            }
*/
            ArrayList<String> securities12 = new ArrayList<>();

            ResultSet result12 = st.executeQuery(genQueries.topFive2011);
            relArg = relArg.substring(0,5) + Integer.toString(11);
            index = 1;
            while(result12.next()) {
                securities12.add(result12.getString(1));
                relArg = "$" + Integer.toString(index++) + relArg.substring(2,7);
                //System.out.print(relArg);
                htmlString = htmlString.replace(relArg,result12.getString(1));
            }
/*
            System.out.println("\n\nTop Five Relative Increase 2011:");
            int i12 = 0;
            while(i12 < securities12.size()) {
                System.out.print(securities12.get(i12++) + " ");
            }
*/
            ArrayList<String> securities3 = new ArrayList<>();

            ResultSet result3 = st.executeQuery(genQueries.topFiveABS2012);
            index = 1;
            absArg = absArg.substring(0,5) + Integer.toString(12);
            while(result3.next()) {
                securities3.add(result3.getString(1));
                absArg = "$" + Integer.toString(index++) + absArg.substring(2,7);
                //System.out.print(absArg);
                htmlString = htmlString.replace(absArg,result3.getString(1));
            }
/*
            System.out.println("\n\nTop Five Absolute Increase 2012:");
            int i3 = 0;
            while(i3 < securities3.size()) {
                System.out.print(securities3.get(i3++) + " ");
            }
*/
            ArrayList<String> securities13 = new ArrayList<>();

            ResultSet result13 = st.executeQuery(genQueries.topFive2012);
            relArg = relArg.substring(0,5) + Integer.toString(12);
            index = 1;
            while(result13.next()) {
                securities13.add(result13.getString(1));
                relArg = "$" + Integer.toString(index++) + relArg.substring(2,7);
                //System.out.print(relArg);
                htmlString = htmlString.replace(relArg,result13.getString(1));
            }
/*
            System.out.println("\n\nTop Five Relative Increase 2012:");
            int i13 = 0;
            while(i13 < securities13.size()) {
                System.out.print(securities13.get(i13++) + " ");
            }
*/
            ArrayList<String> securities4 = new ArrayList<>();

            ResultSet result4 = st.executeQuery(genQueries.topFiveABS2013);
            index = 1;
            absArg = absArg.substring(0,5) + Integer.toString(13);
            while(result4.next()) {
                securities4.add(result4.getString(1));
                absArg = "$" + Integer.toString(index++) + absArg.substring(2,7);
                //System.out.print(absArg);
                htmlString = htmlString.replace(absArg,result4.getString(1));
            }
/*
            System.out.println("\n\nTop Five Absolute Increase 2013:");
            int i4 = 0;
            while(i4 < securities4.size()) {
                System.out.print(securities4.get(i4++) + " ");
            }
*/
            ArrayList<String> securities14 = new ArrayList<>();

            ResultSet result14 = st.executeQuery(genQueries.topFive2013);
            relArg = relArg.substring(0,5) + Integer.toString(13);
            index = 1;
            while(result14.next()) {
                securities11.add(result14.getString(1));
                relArg = "$" + Integer.toString(index++) + relArg.substring(2,7);
                //System.out.print(relArg);
                htmlString = htmlString.replace(relArg,result14.getString(1));
            }
/*
            System.out.println("\n\nTop Five Relative Increase 2013:");
            int i14 = 0;
            while(i14 < securities14.size()) {
                System.out.print(securities14.get(i14++) + " ");
            }
*/
            ArrayList<String> securities5 = new ArrayList<>();

            ResultSet result5 = st.executeQuery(genQueries.topFiveABS2014);
            index = 1;
            absArg = absArg.substring(0,5) + Integer.toString(14);
            while(result5.next()) {
                securities5.add(result5.getString(1));
                absArg = "$" + Integer.toString(index++) + absArg.substring(2,7);
                //System.out.print(absArg);
                htmlString = htmlString.replace(absArg,result5.getString(1));
            }
/*
            System.out.println("\n\nTop Five Absolute Increase 2014:");
            int i5 = 0;
            while(i5 < securities5.size()) {
                System.out.print(securities5.get(i5++) + " ");
            }
*/
            ArrayList<String> securities15 = new ArrayList<>();

            ResultSet result15 = st.executeQuery(genQueries.topFive2014);
            relArg = relArg.substring(0,5) + Integer.toString(14);
            index = 1;
            while(result15.next()) {
                securities11.add(result15.getString(1));
                relArg = "$" + Integer.toString(index++) + relArg.substring(2,7);
                //System.out.print(relArg);
                htmlString = htmlString.replace(relArg,result15.getString(1));
            }
/*
            System.out.println("\n\nTop Five Relative Increase 2014:");
            int i15 = 0;
            while(i15 < securities15.size()) {
                System.out.print(securities15.get(i15++) + " ");
            }

*/
            ArrayList<String> securities6 = new ArrayList<>();

            ResultSet result6 = st.executeQuery(genQueries.topFiveABS2015);
            index = 1;
            absArg = absArg.substring(0,5) + Integer.toString(15);
            while(result6.next()) {
                securities6.add(result6.getString(1));
                absArg = "$" + Integer.toString(index++) + absArg.substring(2,7);
                //System.out.print(absArg);
                htmlString = htmlString.replace(absArg,result6.getString(1));
            }
/*
            System.out.println("\n\nTop Five Absolute Increase 2015:");
            int i6 = 0;
            while(i6 < securities6.size()) {
                System.out.print(securities6.get(i6++) + " ");
            }
*/

            ArrayList<String> securities16 = new ArrayList<>();

            ResultSet result16 = st.executeQuery(genQueries.topFive2015);
            relArg = relArg.substring(0,5) + Integer.toString(15);
            index = 1;
            while(result16.next()) {
                securities11.add(result16.getString(1));
                relArg = "$" + Integer.toString(index++) + relArg.substring(2,7);
                //System.out.print(relArg);
                htmlString = htmlString.replace(relArg,result16.getString(1));
            }
/*
            System.out.println("\n\nTop Five Relative Increase 2015:");
            int i16 = 0;
            while(i16 < securities16.size()) {
                System.out.print(securities16.get(i16++) + " ");
            }
*/
            ArrayList<String> securities7 = new ArrayList<>();

            ResultSet result7 = st.executeQuery(genQueries.topFiveABS2016);
            index = 1;
            absArg = absArg.substring(0,5) + Integer.toString(16);
            while(result7.next()) {
                securities7.add(result7.getString(1));
                absArg = "$" + Integer.toString(index++) + absArg.substring(2,7);
                //System.out.print(absArg);
                htmlString = htmlString.replace(absArg,result7.getString(1));
            }
/*
            System.out.println("\n\nTop Five Absolute Increase 2016:");
            int i7 = 0;
            while(i7 < securities7.size()) {
                System.out.print(securities7.get(i7++) + " ");
            }
*/
            ArrayList<String> securities17 = new ArrayList<>();

            ResultSet result17 = st.executeQuery(genQueries.topFive2016);
            relArg = relArg.substring(0,5) + Integer.toString(16);
            index = 1;
            while(result17.next()) {
                securities17.add(result17.getString(1));
                relArg = "$" + Integer.toString(index++) + relArg.substring(2,7);
                //System.out.print(relArg);
                htmlString = htmlString.replace(relArg,result17.getString(1));
            }
/*
            System.out.println("\n\nTop Five Relative Increase 2016:");
            int i17 = 0;
            while(i17 < securities17.size()) {
                System.out.print(securities17.get(i17++) + " ");
            }
*/
        }
        catch (Exception ex) {
            System.out.println("Failure");
            System.out.println(ex);
        }
    }

    public static void generateTotalStockMarketAnalysisQ5(Connection conn) {

        try {
            Statement st1 = conn.createStatement();
            st1.execute("use nyse");

            ResultSet result1 = st1.executeQuery(genQueries.marketAvgInc2015);

            result1.next();

            double marketAvg2015 = result1.getDouble(1 );

            Statement st2 = conn.createStatement();
            ResultSet result2 = st2.executeQuery(genQueries.marketAvgInc2016);

            result2.next();

            double marketAvg2016 = result2.getDouble(1);

            Statement st3 = conn.createStatement();
            ResultSet sectorAvg2015 = st3.executeQuery(genQueries.sectorAvgInc2015);

            Statement st4 = conn.createStatement();
            ResultSet sectorAvg2016 = st4.executeQuery(genQueries.sectorAvgInc2016);

            while(sectorAvg2016.next()){

                String curSector = sectorAvg2016.getString(2);
                System.out.println();
                System.out.println(curSector);
                System.out.println("---------------------------------------------------------------------");

                double sectAvgInc2016 = sectorAvg2016.getDouble(1);

                sectorAvg2015.first();

                while(!sectorAvg2015.getString(2).equals(curSector)){
                    sectorAvg2015.next();
                }

                double sectAvgInc2015 = sectorAvg2015.getDouble(1);

                System.out.println("2016 (Absolute % Increase)");
                System.out.println("-- Sector Average: " + sectAvgInc2016);
                System.out.println("-- Sector Average Vs. Market Average (Difference): " + (sectAvgInc2016 - marketAvg2016));
                System.out.println();
                System.out.println("2015 (Absolute % Increase)");
                System.out.println("-- Sector Average: " + sectAvgInc2015);
                System.out.println("-- Sector Average Vs. Market Average (Difference): " + (sectAvgInc2015 - marketAvg2015));
                System.out.println();
                System.out.println("2015 to 2016 Absolute % Improvement: " + (sectAvgInc2016 - sectAvgInc2015));
                System.out.println();

            }
            System.out.println("Customer Discretionary: Outperforming year to year increase of other sectors. \n" +
                    "Consumer Staples: Growth in under overal growth but not in decline.\n" +
                    "Energy: Steady growth at market rate.\n" +
                    "Financials: Rebounding and performing well above average.\n" +
                    "Health Care: Nuetral\n" +
                    "Industrials: Increasing growth well above avergae.\n" +
                    "Information Technology: Showing resilence. \n" +
                    "Materials: Steady\n" +
                    "Real Estate: Decline\n" +
                    "Utilities: Strong growth");

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
            dataSource.setUser("jwhipple");
            dataSource.setPassword("4675Eng20");
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
