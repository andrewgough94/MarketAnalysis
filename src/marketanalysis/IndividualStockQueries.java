package marketanalysis;

/**
 * Created by agough on 6/12/17.
 */
public class IndividualStockQueries {
    private static String ticker;

    public IndividualStockQueries(String ticker) {
        this.ticker = ticker;
    }

    // Q1: Day range for which pricing is available
    String dateRange = "select ticker, min(day), max(day)\n" +
            "from Prices    \n" +
            "where ticker = '" + ticker + "'";


    // Q2: Stock performance for every year
    String performanceYearly = "select ticker, Year(day), avg(close), sum(volume), avg(volume)\n" +
            "from Prices\n" +
            "where ticker = '" + ticker + "'\n" +
            "group by Year(day)";


    // Q3: For 2016, show avg close price, highest, lowest, and avg daily trade by month
    String performance2016 = "select ticker, Month(day), avg(close), max(high), min(low), avg(volume)\n" +
            "from Prices\n" +
            "where ticker = '" + ticker + "' and Year(day) = 2016\n" +
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
            "where ticker = '" + ticker + "'\n" +
            "group by year(day), Month(day)) allTable,\n" +
            "\n" +
            "(select ticker, yr, max(diff) as maxDiff\n" +
            "from\n" +
            "\n" +
            "(select ticker, Year(day) as yr, Month(day) as mon,\n" +
            "min(low) as lowest, max(high) as highest, max(high) - min(low) as diff\n" +
            "from Prices\n" +
            "where ticker = '" + ticker + "'\n" +
            "group by year(day), Month(day)) inTable\n" +
            "\n" +
            "group by ticker, yr) maxTable\n" +
            "\n" +
            "where allTable.yr = maxTable.yr and allTable.diff = maxTable.maxDiff";




}
