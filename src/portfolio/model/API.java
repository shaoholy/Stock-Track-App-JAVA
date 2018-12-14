package portfolio.model;

import java.sql.Date;

public class API {

  static double getLastestPrice(String sym) throws IllegalArgumentException {
    return sym.equals("GOOG") ? 200.00 : 300.00;
  }

  static double getPriceofDate(String sym, Date targetDate) throws IllegalArgumentException {
    return sym.equals("GOOG") ? 200.00 : 300.00;
  }

  static Date getLastestOpenDate(Date targetDate) {
    return targetDate.toString().equals("2018-11-10") ? Date.valueOf("2018-11-08") : targetDate;
  }

  static Date getNextOpenDate(Date targetDate) {
    return targetDate.toString().equals("2018-11-10") ? Date.valueOf("2018-11-11") : targetDate;
  }
}
