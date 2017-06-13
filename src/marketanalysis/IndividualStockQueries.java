package marketanalysis;

/**
 * Created by agough on 6/12/17.
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
