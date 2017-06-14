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

    String topTenTraded2016 = "select ticker, sum(volume) as totalVol\n" +
            "from Prices\n" +
            "where Year(day) = 2016\n" +
            "group by ticker\n" +
            "order by totalVol desc\n" +
            "limit 10;";


    String topFiveABS2010 = "SELECT  a1.Ticker, YEAR(a1.Day) as 'Year', (a2.Close - a1.open) as 'Change'\n" +
            "FROM AdjustedPrices a1, AdjustedPrices a2\n" +
            "WHERE a1.Ticker = a2.Ticker\n" +
            "AND a1.Day = '2010-01-04'\n" +
            "AND a2.Day = '2010-12-31'\n" +
            "ORDER BY (a2.close - a1.open) DESC\n" +
            "LIMIT 5;\n";

    String topFiveABS2011 = "SELECT  a1.Ticker, YEAR(a1.Day) as 'Year', (a2.Close - a1.open) as 'Change'\n" +
            "FROM AdjustedPrices a1, AdjustedPrices a2\n" +
            "WHERE a1.Ticker = a2.Ticker\n" +
            "AND a1.Day = '2011-01-03'\n" +
            "AND a2.Day = '2011-12-30'\n" +
            "ORDER BY (a2.close - a1.open) DESC\n" +
            "LIMIT 5;";
    String topFiveABS2012 = "SELECT  a1.Ticker, YEAR(a1.Day) as 'Year', (a2.Close - a1.open) as 'Change'\n" +
            "FROM AdjustedPrices a1, AdjustedPrices a2\n" +
            "WHERE a1.Ticker = a2.Ticker\n" +
            "AND a1.Day = '2012-01-03'\n" +
            "AND a2.Day = '2012-12-31'\n" +
            "ORDER BY (a2.close - a1.open) DESC\n" +
            "LIMIT 5;";

    String topFiveABS2013 = "SELECT  a1.Ticker, YEAR(a1.Day) as 'Year', (a2.Close - a1.open) as 'Change'\n" +
            "FROM AdjustedPrices a1, AdjustedPrices a2\n" +
            "WHERE a1.Ticker = a2.Ticker\n" +
            "AND a1.Day = '2013-01-02'\n" +
            "AND a2.Day = '2013-12-31'\n" +
            "ORDER BY (a2.close - a1.open) DESC\n" +
            "LIMIT 5;";

    String topFiveABS2014 = "SELECT  a1.Ticker, YEAR(a1.Day) as 'Year', (a2.Close - a1.open) as 'Change'\n" +
            "FROM AdjustedPrices a1, AdjustedPrices a2\n" +
            "WHERE a1.Ticker = a2.Ticker\n" +
            "AND a1.Day = '2014-01-02'\n" +
            "AND a2.Day = '2014-12-31'\n" +
            "ORDER BY (a2.close - a1.open) DESC\n" +
            "LIMIT 5;";

    String topFiveABS2015 = "SELECT  a1.Ticker, YEAR(a1.Day) as 'Year', (a2.Close - a1.open) as 'Change'\n" +
            "FROM AdjustedPrices a1, AdjustedPrices a2\n" +
            "WHERE a1.Ticker = a2.Ticker\n" +
            "AND a1.Day = '2015-01-02'\n" +
            "AND a2.Day = '2015-12-31'\n" +
            "ORDER BY (a2.close - a1.open) DESC\n" +
            "LIMIT 5;";

    String topFiveABS2016 = "SELECT  a1.Ticker, YEAR(a1.Day) as 'Year', (a2.Close - a1.open) as 'Change'\n" +
            "FROM AdjustedPrices a1, AdjustedPrices a2\n" +
            "WHERE a1.Ticker = a2.Ticker\n" +
            "AND a1.Day = '2016-01-04'\n" +
            "AND a2.Day = '2016-12-30'\n" +
            "ORDER BY (a2.close - a1.open) DESC\n" +
            "LIMIT 5;";

    String topFive2010 = "SELECT  a1.Ticker, YEAR(a1.Day) as 'Year', ((a2.close - a1.open) / a1.open) as 'Change'\n" +
            "FROM AdjustedPrices a1, AdjustedPrices a2\n" +
            "WHERE a1.Ticker = a2.Ticker\n" +
            "\tAND a1.Day = '2010-01-04'\n" +
            "\tAND a2.Day = '2010-12-31'\n" +
            "ORDER BY ((a2.close - a1.open) / a1.open) DESC\n" +
            "LIMIT 5;\n";

    String topFive2011 = "SELECT  a1.Ticker, YEAR(a1.Day) as 'Year', ((a2.close - a1.open) / a1.open) as 'Change'\n" +
            "FROM AdjustedPrices a1, AdjustedPrices a2\n" +
            "WHERE a1.Ticker = a2.Ticker\n" +
            "AND a1.Day = '2011-01-03'\n" +
            "AND a2.Day = '2011-12-30'\n" +
            "ORDER BY ((a2.close - a1.open) / a1.open) DESC\n" +
            "LIMIT 5;";
    String topFive2012 = "SELECT  a1.Ticker, YEAR(a1.Day) as 'Year', ((a2.close - a1.open) / a1.open) as 'Change'\n" +
            "FROM AdjustedPrices a1, AdjustedPrices a2\n" +
            "WHERE a1.Ticker = a2.Ticker\n" +
            "AND a1.Day = '2012-01-03'\n" +
            "AND a2.Day = '2012-12-31'\n" +
            "ORDER BY ((a2.close - a1.open) / a1.open) DESC\n" +
            "LIMIT 5;";

    String topFive2013 = "SELECT  a1.Ticker, YEAR(a1.Day) as 'Year', ((a2.close - a1.open) / a1.open) as 'Change'\n" +
            "FROM AdjustedPrices a1, AdjustedPrices a2\n" +
            "WHERE a1.Ticker = a2.Ticker\n" +
            "AND a1.Day = '2013-01-02'\n" +
            "AND a2.Day = '2013-12-31'\n" +
            "ORDER BY ((a2.close - a1.open) / a1.open) DESC\n" +
            "LIMIT 5;";

    String topFive2014 = "SELECT  a1.Ticker, YEAR(a1.Day) as 'Year', ((a2.close - a1.open) / a1.open) as 'Change'\n" +
            "FROM AdjustedPrices a1, AdjustedPrices a2\n" +
            "WHERE a1.Ticker = a2.Ticker\n" +
            "AND a1.Day = '2014-01-02'\n" +
            "AND a2.Day = '2014-12-31'\n" +
            "ORDER BY ((a2.close - a1.open) / a1.open) DESC\n" +
            "LIMIT 5;";

    String topFive2015 = "SELECT  a1.Ticker, YEAR(a1.Day) as 'Year', ((a2.close - a1.open) / a1.open) as 'Change'\n" +
            "FROM AdjustedPrices a1, AdjustedPrices a2\n" +
            "WHERE a1.Ticker = a2.Ticker\n" +
            "AND a1.Day = '2015-01-02'\n" +
            "AND a2.Day = '2015-12-31'\n" +
            "ORDER BY ((a2.close - a1.open) / a1.open) DESC\n" +
            "LIMIT 5;";

    String topFive2016 = "SELECT  a1.Ticker, YEAR(a1.Day) as 'Year', ((a2.close - a1.open) / a1.open) as 'Change'\n" +
            "FROM AdjustedPrices a1, AdjustedPrices a2\n" +
            "WHERE a1.Ticker = a2.Ticker\n" +
            "AND a1.Day = '2016-01-04'\n" +
            "AND a2.Day = '2016-12-30'\n" +
            "ORDER BY ((a2.close - a1.open) / a1.open) DESC\n" +
            "LIMIT 5;";

    String marketAvgInc2016 = "SELECT SUM(a.Close - a.Open) * 100 / COUNT(*) as 'Avg Inc', YEAR(a.Day) as 'Year'\n" +
            "FROM AdjustedPrices a\n" +
            "GROUP BY YEAR(a.Day)\n" +
            "HAVING Year = 2016;";

    String marketAvgInc2015 = "SELECT SUM(a.Close - a.Open) * 100 / COUNT(*) as 'Avg Inc', YEAR(a.Day) as 'Year'\n" +
            "FROM AdjustedPrices a\n" +
            "GROUP BY YEAR(a.Day)\n" +
            "HAVING Year = 2015;";

    String sectorAvgInc2015 = "SELECT  SUM(a.Close - a.Open) * 100 / COUNT(*) as 'Avg Inc', s.Sector, YEAR(a.Day) as 'Year'\n" +
            "FROM AdjustedPrices a, Securities s\n" +
            "WHERE a.Ticker = s.Ticker\n" +
            "\tAND s.Sector <> 'Telecommunications Services'" +
            "GROUP BY s.Sector, YEAR(a.Day)\n" +
            "HAVING Year = 2015;";

    String sectorAvgInc2016 = "SELECT  SUM(a.Close - a.Open) * 100 / COUNT(*) as 'Avg Inc', s.Sector, YEAR(a.Day) as 'Year'\n" +
            "FROM AdjustedPrices a, Securities s\n" +
            "WHERE a.Ticker = s.Ticker\n" +
            "\tAND s.Sector <> 'Telecommunications Services'" +
            "GROUP BY s.Sector, YEAR(a.Day)\n" +
            "HAVING Year = 2016;";
}
