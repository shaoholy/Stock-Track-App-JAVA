package portfolio.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * This interface represents a modelstock.portfolio of a customer stock, which might contain from 0
 * to X stock share kinds of a customer.
 * Extension update log 2018-11-26:
 * 1, adding high level functions as required
 * 2, adding one more input as commission fee in buyStockByShare(), buyStockByAmount(), and
 * buyStockByIndex() methods.
 * Extension update log 2018-12-05:
 * 1, adding method to return a list of Portfolio values.
 * 2, adding method to add a stock to a portfolio.
 */
public interface Portfolio {

  /**
   * Added extension function 2018-12-05: adding method to return a list of Portfolio values.
   *
   * @param startDate         the Date as the start date
   * @param endDate           the Date as the end date
   * @throws IllegalArgumentException when given endDate is later then today
   */
  List<Double> getValueList(Date startDate, Date endDate) throws IllegalArgumentException;

  /**
   * Added extension function 2018-11-26.
   * The methods buys a certain portfolio with certain ratio of percentage.
   *
   * @param ratioMap          the ratioMap of the ratio percentage of String
   * @param startDate         the Date as the buy date
   * @param interval          the interval of day-number for each transaction
   * @param amount            the value of money to buy stocks each term
   * @param comm              the commission cost
   * @throws IllegalArgumentException when given String of portfolioName is incorrect
   * @throws IllegalArgumentException when given startDate is in future
   * @throws IllegalArgumentException when given amount is not larger than 0
   * @throws IllegalArgumentException when give sum of give ratio sum is not 100%
   * @throws IllegalArgumentException when interval day number is not larger than 0
   */
  void highLevelFixedValueTrade(Map<String, Double> ratioMap, Date startDate,
                                int interval, double amount, double comm)
                                throws IllegalArgumentException;

  /**
   * Added extension function 2018-11-26.
   * The methods buys a certain portfolio with certain ratio of percentage.
   *
   * @param ratioMap          the ratioMap of the ratio percentage of String
   * @param startDate         the Date as the buy date
   * @param endDate           the Date as the end date of the strategy
   * @param interval          the interval of day-number for each transaction
   * @param amount            the value of money to buy stocks each term
   * @param comm              the commission cost
   * @throws IllegalArgumentException when given String of portfolioName is incorrect
   * @throws IllegalArgumentException when given startDate is in future
   * @throws IllegalArgumentException when given amount is not larger than 0
   * @throws IllegalArgumentException when give sum of give ratio sum is not 100%
   * @throws IllegalArgumentException when interval day number is not larger than 0
   */
  void highLevelFixedValueTrade(Map<String, Double> ratioMap, Date startDate,
                                Date endDate, int interval, double amount, double comm)
          throws IllegalArgumentException;

  /**
   * The methods buys a certain stock with certain amount of money.
   * Update log 2018-11-26: add an int input as commission fee.
   *
   * @param sym        the String as the ticker symbol the the stock
   * @param targetDate the Date as the buy date
   * @param amount     the double value as the total amount of money
   * @param comm       the commission cost
   * @throws IllegalArgumentException when given ticker symbol is incorrect
   * @throws IllegalArgumentException when given date is in future
   * @throws IllegalArgumentException when given date is not an open market date
   * @throws IllegalArgumentException when given amount is not larger than 0
   * @throws IllegalArgumentException when give sym name is not contained in target Portfolio
   */
  void buyStockByAmount(String sym, Date targetDate, double amount, double comm)
          throws IllegalArgumentException;

  /**
   * The methods buys a certain stock with certain amount of share.
   * Update log 2018-11-26: add an int input as commission fee.
   *
   * @param sym        the String as the ticker symbol the the stock
   * @param targetDate the Date as the buy date
   * @param share      the double value as the share value of the stock
   * @param comm       the commission cost
   * @throws IllegalArgumentException when given ticker symbol is incorrect
   * @throws IllegalArgumentException when given date is in future
   * @throws IllegalArgumentException when given date is not an open market date
   * @throws IllegalArgumentException when given share value is not larger than 0
   * @throws IllegalArgumentException when given share value is not larger than 0
   * @throws IllegalArgumentException when give sym name is not contained in target Portfolio
   */
  void buyStockByShare(String sym, Date targetDate, double share, double comm)
          throws IllegalArgumentException;

  /**
   * The methods buys more of a certain stock in current portfolio with certain amount of share.
   * Update log 2018-11-26: add an int input as commission fee.
   *
   * @param idx        the index of the stock share in the portfolio
   * @param share      the double value as the share value of the stock
   * @param comm       the commission cost
   * @throws IllegalArgumentException when given idx does not exist in the portfolio
   * @throws IllegalArgumentException when given share value is not larger than 0
   */
  void buyStockByIdx(int idx, double share, double comm) throws IllegalArgumentException;

  /**
   * The methods adds a stock to this portfolio.
   * Update log 2018-12-05: add a stock to this portfolio.
   *
   * @param stockSym       the String as the stock symbol to be added
   */
  void addStock(String stockSym);

  /**
   * The methods returns a double value as the total cost of a modelstock.portfolio.
   *
   * @return a double value as the total cost of a modelstock.portfolio
   */
  double getTotalCostBasic();

  /**
   * The methods returns a double value as the total cost of a modelstock.portfolio at a certain
   * date, while trades before the given date not counted.
   *
   * @return a double value as the total cost of a modelstock.portfolio before the given date
   */
  double getTotalCostBasic(Date targetDate);

  /**
   * The methods returns a double value as the total market value of a modelstock.portfolio until
   * the latest status.
   *
   * @return a double value as the total market value of a modelstock.portfolio
   */
  double getTotalVal();

  /**
   * The methods returns a double value as the total market value of a modelstock.portfolio until
   * the given status.
   *
   * @return a double value as the total market value of a modelstock.portfolio before a given date
   */
  double getTotalVal(Date targetDate);

  /**
   * The methods returns a String that represents all the shares of stocks owned in this
   * modelstock.portfolio. If no stock share exists in the modelstock.portfolio, return an empty
   * String.
   *
   * @return a String as all the stock share within a given modelstock.portfolio
   */
  String examinePortfolio();

  /**
   * The methods return all the Stock Symbol in this Portfolio.
   *
   * @return all the Stock Symbol in this Portfolio.
   */
  String getStockNames();

}