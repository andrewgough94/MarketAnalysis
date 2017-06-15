package marketanalysis;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.io.*;
import java.lang.reflect.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
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
    private static IndividualStockQueries indQueries;

    private static BufferedWriter bw = null;
    private static FileWriter fw = null;

    private static File htmlTemplate;
    private static String htmlString;

    private static DecimalFormat df = new DecimalFormat("###,###,###,###,###.##");


    public static void main(String args[]) {
        System.out.println("Welcome to Market Analysis!");
        String ticker = "";
        conn = initializeConnection();
        genQueries = new GeneralStockQueries();

        // TODO: use an input file of ticker names to initialize indQueries
        indQueries = new IndividualStockQueries();


        generateHtmlFile();

        generateTotalStockMarketAnalysisQ1(conn);
        generateTotalStockMarketAnalysisQ2(conn);
        generateTotalStockMarketAnalysisQ3(conn);

        if (args.length == 1) {
            ticker = args[0];
        }
        else {
            System.out.println("Usage: enter a ticker symbol to analyze");
        }

        generateIndividualStockAnalysisQ1(conn, ticker);
        generateIndividualStockAnalysisQ2(conn, ticker);
        generateIndividualStockAnalysisQ3(conn, ticker);
        generateIndividualStockAnalysisQ4(conn, ticker);
        generateIndividualStockAnalysisQ5Q6(conn, ticker);
        generateIndividualStockAnalysisQ7(conn, ticker);
        generateIndividualStockAnalysisQ8(conn, ticker);


        try {
            File outputFile = new File(ticker + ".html");
            FileWriter fw = new FileWriter(outputFile);

            htmlString = htmlString.replaceAll("tickerNameHolder", ticker);
            fw.write(htmlString);
            fw.close();

        }
        catch (Exception ex) {
            System.out.println("Writing failed");
            System.out.println(ex);
        }

    }

    public static void generateHtmlFile() {
        try {
            htmlTemplate = new File("fake.html");
            htmlString = new String(Files.readAllBytes(Paths.get("fake.html")));
        }
        catch (Exception ex) {
            System.out.println("Error converting template.html to string");
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

            /*System.out.println("Top Ten Secs by volume:");
            int i = 0;
            while(i < securities.size()) {
                System.out.print(securities.get(i++) + " ");
            }*/
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
    

    public static void generateIndividualStockAnalysisQ1(Connection conn, String ticker) {
        String start, end;
        start = end = "";

        try {
            Statement st = conn.createStatement();
            st.execute("use nyse");

            ResultSet result = st.executeQuery(indQueries.dateRange.replaceAll("null", ticker));

            while(result.next()) {
                start = result.getString(2);
                end = result.getString(3);
            }

            htmlString = htmlString.replaceAll("q1", ticker + " traded from: (" + start + ") to (" + end + ")");

            //System.out.println(start + " " + end);
        }
        catch (Exception ex) {
            System.out.println("Failure in individual Q1");
            System.out.println(ex);
        }
    }

    public static void generateIndividualStockAnalysisQ2(Connection conn, String ticker) {
        int yearCount = 0;

        ArrayList<Integer> years = new ArrayList<>();
        ArrayList<Double> avgPrices = new ArrayList<>();
        ArrayList<Double> yearlyVolumes = new ArrayList<>();
        ArrayList<Double> dailyVolumeAvg = new ArrayList<>();

        ArrayList<Double> avgPricesChange = new ArrayList<>();
        ArrayList<Double> yearlyVolumesChange = new ArrayList<>();
        ArrayList<Double> dailyVolumeAvgChange = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            st.execute("use nyse");

            ResultSet result = st.executeQuery(indQueries.performanceYearly.replaceAll("null", ticker));

            while(result.next()) {
                yearCount++;
                years.add(result.getInt(2));
                avgPrices.add(result.getDouble(3));
                yearlyVolumes.add(result.getDouble(4));
                dailyVolumeAvg.add(result.getDouble(5));
            }

            String q2Table1 = "<center><table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=75%>"
                    + "<tr><th>Year</th><th>Avg Price</th><th>Yearly Volume</th><th>Daily Volume</th></tr>";
            for(int i = 0; i < yearCount; i++) {
                //System.out.println(years.get(i) + " " + df.format(avgPrices.get(i)) + " " + df.format(yearlyVolumes.get(i))
                       // + " " + df.format(dailyVolumeAvg.get(i)));
                q2Table1 = q2Table1.concat("<tr><td>" + years.get(i) +
                        "</td><td>"  + df.format(avgPrices.get(i)) +
                        "</td><td>" + df.format(yearlyVolumes.get(i))
                        + "</td><td>" + df.format(dailyVolumeAvg.get(i)) + "</td></tr>");
            }
            q2Table1 = q2Table1.concat("</table></center>");
            htmlString = htmlString.replaceAll("q2table1", q2Table1);

            for (int i = 1; i < yearCount; i++) {
                avgPricesChange.add((avgPrices.get(i) - avgPrices.get(i-1))/avgPrices.get(i-1));
                yearlyVolumesChange.add((yearlyVolumes.get(i) - yearlyVolumes.get(i-1))/yearlyVolumes.get(i-1));
                dailyVolumeAvgChange.add((dailyVolumeAvg.get(i) - dailyVolumeAvg.get(i-1))/dailyVolumeAvg.get(i-1));
            }

            System.out.println();

            String q2Table2 = "<center><table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=75%>"
                    + "<tr><th>Year</th><th>Avg Price Growth</th><th>Yearly Volume Growth</th><th>Daily Volume Growth</th></tr>";
            // HERE IS THE PERCENT CHANGE
            for(int i = 0; i < yearCount-1; i++) {
                //System.out.println(years.get(i+1) + " " + df.format(avgPricesChange.get(i)*100) + " "
                  //      + df.format(yearlyVolumesChange.get(i)*100)
                    //    + " " + df.format(dailyVolumeAvgChange.get(i)*100));

                q2Table2 = q2Table2.concat("<tr><td>" + years.get(i+1) +
                        "</td><td>" + df.format(avgPricesChange.get(i)*100) + " %" +
                        "</td><td>" + df.format(yearlyVolumesChange.get(i)*100) + "%"
                        + "</td><td>" + df.format(dailyVolumeAvgChange.get(i)*100) + "%" + "</td></tr>");
            }
            q2Table2 = q2Table2.concat("</table></center>");
            htmlString = htmlString.replaceAll("q2table2", q2Table2);

        }
        catch (Exception ex) {
            System.out.println("Failure in individual Q2");
            System.out.println(ex);
        }
    }

    public static void generateIndividualStockAnalysisQ3(Connection conn, String ticker) {
        ArrayList<Double> avgClose = new ArrayList<>();
        ArrayList<Double> highestPrice = new ArrayList<>();
        ArrayList<Double> lowestPrice = new ArrayList<>();
        ArrayList<Double> avgVolume = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            st.execute("use nyse");

            ResultSet result = st.executeQuery(indQueries.performance2016.replaceAll("null", ticker));

            while(result.next()) {
                avgClose.add(result.getDouble(3));
                highestPrice.add(result.getDouble(4));
                lowestPrice.add(result.getDouble(5));
                avgVolume.add(result.getDouble(6));
            }


            String q3Table = "<center><table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=75%> " +
                    "<tr><th>Month</th><th>Avg Close</th><th>Max High</th><th>Min Low</th><th>Avg Daily Volume</th></tr>";
            for(int i = 0; i < avgClose.size(); i++) {
                //System.out.println(i+1 + " " + avgClose.get(i) + " " + highestPrice.get(i) + " " + lowestPrice.get(i) +
                //    " " + avgVolume.get(i));

                q3Table = q3Table.concat("<tr><td>" + new DateFormatSymbols().getMonths()[i] +
                        "</td><td>"  + df.format(avgClose.get(i)) +
                        "</td><td>" + df.format(highestPrice.get(i))
                        + "</td><td>" + df.format(lowestPrice.get(i))
                        + "</td><td>" + df.format(avgVolume.get(i))
                        + "</td></tr>");
            }
            q3Table = q3Table.concat("</table></center>");
            htmlString = htmlString.replaceAll("q3table", q3Table);

        }
        catch (Exception ex) {
            System.out.println("Failure in individual Q3");
            System.out.println(ex);
        }
    }

    public static void generateIndividualStockAnalysisQ4(Connection conn, String ticker) {
        ArrayList<Integer> years = new ArrayList<>();
        ArrayList<Integer> bestMonth = new ArrayList<>();
        ArrayList<Double> priceJump = new ArrayList<>();

        try {
            Statement st = conn.createStatement();
            st.execute("use nyse");

            ResultSet result = st.executeQuery(indQueries.bestMonths.replaceAll("null", ticker));

            while(result.next()) {
                years.add(result.getInt(2));
                bestMonth.add(result.getInt(3));
                priceJump.add(result.getDouble(4));
            }

            String q4Table = "<center><table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=75%>" +
                    "           <tr><th>Year</th><th>Best Month</th><th>Price Increase (start-end of month)</th></tr>";
            for(int i = 0; i < years.size(); i++) {
                //System.out.println(years.get(i) + " " + bestMonth.get(i) + " " + priceJump.get(i));
                q4Table = q4Table.concat("<tr><td>" + years.get(i) +
                    "</td><td>" + new DateFormatSymbols().getMonths()[i] +
                        "</td><td>"  + df.format(priceJump.get(i)) +
                        "</td></tr>");
            }
            q4Table = q4Table.concat("</table></center>");
            htmlString = htmlString.replaceAll("q4table", q4Table);

        }
        catch (Exception ex) {
            System.out.println("Failure in individual Q3");
            System.out.println(ex);
        }
    }

    public static void generateIndividualStockAnalysisQ5Q6(Connection conn, String ticker) {
        String d1 = "2015-01-01";
        String d2 = "2015-06-01";
        String d3 = "2015-10-01";
        String d4 = "2016-01-01";
        String d5 = "2016-05-01";
        String d6 = "2016-10-01";

        String res1, res2, res3, res4, res5, res6;

        // Get stock ratings
        res1 = rateStock(conn, ticker, d1);
        htmlString = htmlString.replaceAll("rating1", res1);
        htmlString = htmlString.replaceAll("eval1", getStockChange(conn, ticker, d1, res1));
        res2 = rateStock(conn, ticker, d2);
        htmlString = htmlString.replaceAll("rating2", res2);
        htmlString = htmlString.replaceAll("eval2", getStockChange(conn, ticker, d2, res2));
        res3 = rateStock(conn, ticker, d3);
        htmlString = htmlString.replaceAll("rating3", res3);
        htmlString = htmlString.replaceAll("eval3", getStockChange(conn, ticker, d3, res3));
        res4 = rateStock(conn, ticker, d4);
        htmlString = htmlString.replaceAll("rating4", res4);
        htmlString = htmlString.replaceAll("eval4", getStockChange(conn, ticker, d4, res4));
        res5 = rateStock(conn, ticker, d5);
        htmlString = htmlString.replaceAll("rating5", res5);
        htmlString = htmlString.replaceAll("eval5", getStockChange(conn, ticker, d5, res5));
        res6 = rateStock(conn, ticker, d6);
        htmlString = htmlString.replaceAll("rating6", res6);
        htmlString = htmlString.replaceAll("eval6", getStockChange(conn, ticker, d6, res6));
    }

    public static String getStockChange(Connection conn, String ticker, String date, String rating) {
        Double curPrice = 0.0;
        Double futurePrice = 0.0;
        Double percentChange = 0.0;
        String ret = "";

        try {
            Statement st = conn.createStatement();
            st.execute("use nyse");

            String query = indQueries.curPrice.replaceAll("null", ticker);
            query = query.replaceAll("INSERTDATE", date);
            ResultSet result = st.executeQuery(query);

            while(result.next()) {
                curPrice = result.getDouble(2);
            }

            String query1 = indQueries.futurePrice.replaceAll("null", ticker);
            query1 = query1.replaceAll("INSERTDATE", date);
            ResultSet result1 = st.executeQuery(query1);

            while(result1.next()) {
                futurePrice = result1.getDouble(2);
            }

            percentChange = (futurePrice - curPrice) / curPrice;

            //System.out.println("curprice: " + curPrice);
            //System.out.println("futureprice: " + futurePrice);
            //System.out.println("%change: " + percentChange * 100);


            if(rating.equals("Buy")) {
                if(percentChange > -.02) {
                    ret = "Correct rating... ";
                }
                else {
                    ret = "Incorrect rating... ";
                }
            }
            else if(rating.equals("Hold")) {
                if(percentChange > -.03 && percentChange < .03) {
                    ret = "Correct rating... ";
                }
                else {
                    ret = "Incorrect rating... ";
                }
            }
            else if(rating.equals("Sell")) {
                if(percentChange < .02) {
                    ret = "Correct rating... ";
                }
                else {
                    ret = "Incorrect rating... ";
                }
            }


            ret = ret + "Price Change %: " + df.format(percentChange * 100);

        }
        catch (Exception ex) {
            System.out.println("Failure in individual Q3");
            System.out.println(ex);
        }
        return ret;
    }

    public static String rateStock(Connection conn, String ticker, String date) {
        double fiftyDayAvg, twoHundoDayAvg, curPrice;
        String rating = "";
        fiftyDayAvg = twoHundoDayAvg = curPrice = 0;
        try {
            Statement st = conn.createStatement();
            st.execute("use nyse");

            String query = indQueries.rateStock.replaceAll("null", ticker);
            query = query.replaceAll("INSERTDATE", date);
            ResultSet result = st.executeQuery(query);

            while(result.next()) {
                fiftyDayAvg = result.getDouble(2);
                twoHundoDayAvg = result.getDouble(3);
                curPrice = result.getDouble(4);
            }

            //System.out.println(fiftyDayAvg + " " + twoHundoDayAvg + " " + curPrice);

            // INFLATED, sell
            if (curPrice > twoHundoDayAvg && curPrice > fiftyDayAvg) {
                rating = "Sell";
            }
            // CRASHING, sell
            else if (curPrice < twoHundoDayAvg && curPrice < fiftyDayAvg) {
                rating = "Sell";
            }
            // UNDERVALUED, buy
            else if (curPrice < twoHundoDayAvg && curPrice > fiftyDayAvg) {
                rating = "Buy";
            }
            // STABLE, hold
            else if (curPrice < fiftyDayAvg && curPrice > twoHundoDayAvg) {
                rating = "Hold";
            }

        }
        catch (Exception ex) {
            System.out.println("Failure in individual Q5");
            System.out.println(ex);
        }
        //System.out.println(date + " " + rating);
        return rating;
    }

    public static void generateIndividualStockAnalysisQ7(Connection conn, String ticker) {

        try {
            Statement st = conn.createStatement();
            st.execute("use nyse");

            String query = indQueries.priceComparisons.replaceAll("null", ticker);
            ResultSet result = st.executeQuery(query);
            String q7Table1 = "<center><table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=75%>" +
                    "           <tr><th>Ticker</th><th>Month</th><th>Avg Price</th></tr>";
            while(result.next()) {
                q7Table1 = q7Table1.concat("<tr><td>" + result.getString(1) +
                        "</td><td>" + new DateFormatSymbols().getMonths()[result.getInt(2)-1] +
                        "</td><td>"  + df.format(result.getDouble(3)) +
                        "</td></tr>");
            }
            q7Table1 = q7Table1.concat("</table></center>");
            htmlString = htmlString.replaceAll("q7table1", q7Table1);

            String query1 = indQueries.volumeComparisons.replaceAll("null", ticker);
            ResultSet result1 = st.executeQuery(query1);
            String q7Table2 = "<center><table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=75%>" +
                    "           <tr><th>Ticker</th><th>Year</th><th>Total Volume</th></tr>";
            while(result1.next()) {
                q7Table2 = q7Table2.concat("<tr><td>" + result1.getString(1) +
                        "</td><td>" + result1.getInt(2) +
                        "</td><td>"  + result1.getLong(3) +
                        "</td></tr>");
            }
            q7Table2 = q7Table2.concat("</table></center>");
            htmlString = htmlString.replaceAll("q7table2", q7Table2);
        }
        catch (Exception ex) {
            System.out.println("Failure in individual Q3");
            System.out.println(ex);
        }
    }


    public static void generateIndividualStockAnalysisQ8(Connection conn, String ticker) {
        try {
            Statement st = conn.createStatement();
            st.execute("use nyse");

            String query = indQueries.price2Comparisons;
            ResultSet result = st.executeQuery(query);
            String q8Table1 = "<center><table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=75%>" +
                    "           <tr><th>Ticker</th><th>Month</th><th>Avg Price</th></tr>";
            while(result.next()) {
                q8Table1 = q8Table1.concat("<tr><td>" + result.getString(1) +
                        "</td><td>" + new DateFormatSymbols().getMonths()[result.getInt(2)-1] +
                        "</td><td>"  + df.format(result.getDouble(3)) +
                        "</td></tr>");
            }
            q8Table1 = q8Table1.concat("</table></center>");
            htmlString = htmlString.replaceAll("q8table1", q8Table1);

            String query1 = indQueries.volume2Comparisons;
            ResultSet result1 = st.executeQuery(query1);
            String q8Table2 = "<center><table BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=75%>" +
                    "           <tr><th>Ticker</th><th>Year</th><th>Total Volume</th></tr>";
            while(result1.next()) {
                q8Table2 = q8Table2.concat("<tr><td>" + result1.getString(1) +
                        "</td><td>" + result1.getInt(2) +
                        "</td><td>"  + result1.getLong(3) +
                        "</td></tr>");
            }
            q8Table2 = q8Table2.concat("</table></center>");
            htmlString = htmlString.replaceAll("q8table2", q8Table2);
        }
        catch (Exception ex) {
            System.out.println("Failure in individual Q3");
            System.out.println(ex);
        }
    }

    public static Connection initializeConnection() {
        // Initialize connection
        try {
            MysqlDataSource dataSource = new MysqlDataSource();

            BufferedReader br = new BufferedReader(new FileReader("credentials.in"));
            String user;
            String password;

            user = br.readLine();
            password = br.readLine();

            dataSource.setUser(user);
            dataSource.setPassword(password);
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
