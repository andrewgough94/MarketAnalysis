package marketanalysis;

/**
 * Created by andrewgough94 on 6/12/2017.
 */
public class GeneralStockQueries {
    String numSecsStart2016 = "select count(distinct ticker) from Prices where day = (select max(distinct day) from Prices where Month(day) = 1 and Year(day) = 2016)";

    String numSecsEnd2016 = "select count(distinct ticker) from Prices where day = (select max(distinct day) from Prices where Month(day) = 12 and Year(day) = 2016)";

    String numPriceIncreases = "select count(*) as numIncreases\n" +
            "from\n" +
            "(select ticker, close\n" +
            "from Prices\n" +
            "where day =\n" +
            "(select max(distinct day)\n" +
            "from Prices\n" +
            "where Month(day) = 12 and Year(day) = 2015)) price2015,\n" +
            "(select ticker, close\n" +
            "from Prices where day =\n" +
            "(select max(distinct day)\n" +
            "from Prices\n" +
            "where Month(day) = 12 and Year(day) = 2016)) price2016\n" +
            "\n" +
            "where price2015.ticker = price2016.ticker\n" +
            "and price2015.close < price2016.close";

    String numPriceDecreases = "select count(*) as numDecreases\n" +
            "from\n" +
            "(select ticker, close\n" +
            "from Prices\n" +
            "where day =\n" +
            "(select max(distinct day)\n" +
            "from Prices\n" +
            "where Month(day) = 12 and Year(day) = 2015)) price2015,\n" +
            "(select ticker, close\n" +
            "from Prices where day =\n" +
            "(select max(distinct day)\n" +
            "from Prices\n" +
            "where Month(day) = 12 and Year(day) = 2016)) price2016\n" +
            "\n" +
            "where price2015.ticker = price2016.ticker\n" +
            "and price2015.close > price2016.close";


}
