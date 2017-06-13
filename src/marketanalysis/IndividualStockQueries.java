package marketanalysis;

/**
 * Lab 8
 * Andrew Gough (agough) & Jake Whipple
 *
 * File contains the SQL queries for individual stock analytics.
 */
public class IndividualStockQueries {
    private static String ticker;

    public IndividualStockQueries(String ticker) {
        this.ticker = ticker;
    }

    String dateRange = "select ticker, min(day), max(day)\n" +
            "from Prices    \n" +
            "where ticker = '" + ticker + "'";
}
