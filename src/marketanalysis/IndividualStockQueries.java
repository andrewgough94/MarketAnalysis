package marketanalysis;

/**
 * Lab 8
 * Andrew Gough (agough) & Jake Whipple
 *
 * File contains the SQL queries for individual stock analytics.
 */
public class IndividualStockQueries {
    public String holder;

    public IndividualStockQueries() {

    }

    // Q1: Day range for which pricing is available
    String dateRange = "select ticker, min(day), max(day)\n" +
            "from Prices    \n" +
            "where ticker = '" + holder + "'";

    public String getDateRange() {
        return dateRange;
    }


    // Q2: Stock performance for every year
    String performanceYearly = "select ticker, Year(day), avg(close), sum(volume), avg(volume)\n" +
            "from Prices\n" +
            "where ticker = '" + holder + "'\n" +
            "group by Year(day)";


    // Q3: For 2016, show avg close price, highest, lowest, and avg daily trade by month
    String performance2016 = "select ticker, Month(day), avg(close), max(high), min(low), avg(volume)\n" +
            "from Prices\n" +
            "where ticker = '" + holder + "' and Year(day) = 2016\n" +
            "group by Month(day);";

    // Q4: Determine the month of best performance for each year
    // Best month determined by the largest positive diff between highest price
    // of that month - lowest price of the month
    String bestMonths = "select allTable.ticker, allTable.yr, allTable.mon, allTable.diff as priceDelta\n" +
            "from\n" +
            "\n" +
            "(select ticker, Year(day) as yr, Month(day) as mon,\n" +
            "min(low) as lowest, max(high) as highest, max(high) - min(low) as diff\n" +
            "from Prices\n" +
            "where ticker = '" + holder + "'\n" +
            "group by year(day), Month(day)) allTable,\n" +
            "\n" +
            "(select ticker, yr, max(diff) as maxDiff\n" +
            "from\n" +
            "\n" +
            "(select ticker, Year(day) as yr, Month(day) as mon,\n" +
            "min(low) as lowest, max(high) as highest, max(high) - min(low) as diff\n" +
            "from Prices\n" +
            "where ticker = '" + holder + "'\n" +
            "group by year(day), Month(day)) inTable\n" +
            "\n" +
            "group by ticker, yr) maxTable\n" +
            "\n" +
            "where allTable.yr = maxTable.yr and allTable.diff = maxTable.maxDiff";


    // Q5: Determine buy, hold, sell ratings

    String rateStock = "select * from\n" +
            "\n" +
            "(select ticker, avg(close) as 50dayAvg from (select *\n" +
            "from Prices\n" +
            "where ticker = '" + ticker + "' and day < 'INSERTDATE'\n" +
            "order by day desc\n" +
            "limit 50) 50table) t1 natural join\n" +
            "\n" +
            "(select ticker, avg(close) as 200dayAvg from (select *\n" +
            "from Prices\n" +
            "where ticker = '" + ticker + "' and day < 'INSERTDATE'\n" +
            "order by day desc\n" +
            "limit 200) 200table) t2";


}
