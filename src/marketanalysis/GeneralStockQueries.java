package marketanalysis;

/**
 * Created by andrewgough94 on 6/12/2017.
 */
public class GeneralStockQueries {
    String numSecsStart2016 = "select count(distinct ticker) from Prices where day = (select max(distinct day) from Prices where Month(day) = 1 and Year(day) = 2016)";

    String numSecsEnd2016 = "select count(distinct ticker) from Prices where day = (select max(distinct day) from Prices where Month(day) = 12 and Year(day) = 2016)";

}
