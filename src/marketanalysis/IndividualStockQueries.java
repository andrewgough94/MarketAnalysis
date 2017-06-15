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
            "from AdjustedPrices\n" +
            "where ticker = '" + holder + "'\n" +
            "group by Year(day)";


    // Q3: For 2016, show avg close price, highest, lowest, and avg daily trade by month
    String performance2016 = "select ticker, Month(day), avg(close), max(high), min(low), avg(volume)\n" +
            "from AdjustedPrices\n" +
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
            "from AdjustedPrices\n" +
            "where ticker = '" + holder + "'\n" +
            "group by year(day), Month(day)) allTable,\n" +
            "\n" +
            "(select ticker, yr, max(diff) as maxDiff\n" +
            "from\n" +
            "\n" +
            "(select ticker, Year(day) as yr, Month(day) as mon,\n" +
            "min(low) as lowest, max(high) as highest, max(high) - min(low) as diff\n" +
            "from AdjustedPrices\n" +
            "where ticker = '" + holder + "'\n" +
            "group by year(day), Month(day)) inTable\n" +
            "\n" +
            "group by ticker, yr) maxTable\n" +
            "\n" +
            "where allTable.yr = maxTable.yr and allTable.diff = maxTable.maxDiff";


    // Q5: Determine buy, hold, sell ratings

    String rateStock = "select * from (select * from " +
            "((select ticker, avg(close) as 50dayAvg from (select *\n" +
            "from AdjustedPrices\n" +
            "where ticker = '" + holder + "' and day < 'INSERTDATE'\n" +
            "order by day desc\n" +
            "limit 50) 50table) t1 natural join" +
            "(select ticker, avg(close) as 200dayAvg from (select *\n" +
            "from AdjustedPrices\n" +
            "where ticker = '" + holder + "' and day < 'INSERTDATE'\n" +
            "order by day desc\n" +
            "limit 200) 200table) t2)) avgsTable natural join" +
            "(select ticker, close\n" +
            "from AdjustedPrices\n" +
            "where ticker = '" + holder + "' and day < 'INSERTDATE'\n" +
            "order by day desc\n" +
            "limit 1) curPrice";

    // Q6: Determine correctness of rating

    String curPrice = "select ticker, close\n" +
            "from AdjustedPrices\n" +
            "where ticker = '" + holder + "' and day < 'INSERTDATE'\n" +
            "order by day desc\n" +
            "limit 1";

    String futurePrice = "select ticker, close, day\n" +
            "from AdjustedPrices\n" +
            "where ticker = '" + holder + "' and day < DATE_ADD('INSERTDATE', interval 3 month)\n" +
            "order by day desc\n" +
            "limit 1";


    // Q7: Compare stock with others

    String priceComparisons = "select ticker, Month(day), avg(close)\n" +
            "from AdjustedPrices\n" +
            "where (ticker = 'NVDA' or ticker = 'ARNC' or ticker = 'EVHC'\n" +
            "or ticker = 'OKE' or ticker = 'FCX' or ticker = '" + holder + "') \n" +
            "and Year(day) = 2016\n" +
            "group by ticker, Month(day)";

    String volumeComparisons = "select ticker, Year(day), sum(volume)\n" +
            "from AdjustedPrices\n" +
            "where (ticker = 'NVDA' or ticker = 'ARNC' or ticker = 'EVHC'\n" +
            "or ticker = 'OKE' or ticker = 'FCX' or ticker = '" + holder + "')\n" +
            "and Year(day) = 2016\n" +
            "group by ticker";

    // Q8:
    String price2Comparisons = "select ticker, Month(day), avg(close)\n" +
            "from AdjustedPrices\n" +
            "where (ticker = 'FE' or ticker = 'PCLN' or ticker = 'MA'\n" +
            "or ticker = 'GPC') and Year(day) = 2016\n" +
            "group by ticker, Month(day)";

    String volume2Comparisons = "select ticker, Year(day), sum(volume)\n" +
            "from AdjustedPrices\n" +
            "where (ticker = 'FE' or ticker = 'PCLN' or ticker = 'MA'\n" +
            "or ticker = 'GPC') and Year(day) = 2016\n" +
            "group by ticker";


}
