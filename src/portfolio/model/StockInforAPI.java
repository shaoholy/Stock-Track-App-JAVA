package portfolio.model;

import java.sql.Date;

/**
 * This is the interface for Stock API for stock-price query.
 */
public interface StockInforAPI {
  String apiKey = "W0M1JOKC82EZEQA8";

  /**
   * The methods returns the stock as the given stock ticker symbol.
   *
   * @param sym the String as the ticker symbol the the stock
   * @return a double value as the latest price of the stock
   * @throws IllegalArgumentException when API provided information not correct
   */
  double getLatestPrice(String sym) throws IllegalArgumentException;

  /**
   * The methods returns the stock as the given stock ticker symbol.
   *
   * @param sym        the String as the ticker symbol the the price
   * @param targetDate the Date the ticker symbol the the price
   * @return a double value as the close price of the stock of the given date
   * @throws IllegalArgumentException when API provided information not correct
   * @throws IllegalArgumentException when given date is not open for market trade
   */
  double getPriceofDate(String sym, Date targetDate) throws IllegalArgumentException;// {

  /**
   * The methods returns closest date before the given date which is an open market date. If the
   * input date itself is an open date, the method returns itself.
   *
   * @param targetDate the Date the ticker symbol the the price
   * @return a date object as the closest date before the given date which is an open market date
   */
  Date getLastOpenDate(Date targetDate);// {

  /**
   * The methods returns closest date after the given date which is an open market date. If the
   * input date itself is an open date, the method returns itself.
   *
   * @param targetDate the Date the ticker symbol the the price
   * @return a date object as the closest date after the given date which is an open market date
   */
  Date getNextOpenDate(Date targetDate);
}
