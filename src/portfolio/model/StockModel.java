package portfolio.model;

import java.sql.Date;
import java.util.Map;
import java.util.Set;
import java.util.List;

/**
 * This interface represents a portfolio.model that does all the stock transfers for a client.
 * Extension update log 2018-11-26: 1, adding high level functions as required 2, adding one more
 * input as commission fee in buyStockByShare(), buyStockByAmount(), and buyStockByIndex() methods.
 * Extension update log 2018-12-05: 1, adding save portfolio to JSON function 2, adding create
 * portfolio from JSON function function 3, adding save strategy to JSON function 4, adding apply
 * JSON based strategy to a portfolio 5, adding get list of value from a certain time slot of a
 * portfolio
 */
public interface StockModel {

  /**
   * Added extension function 2018-12-05: adding method to return a list of Portfolio values.
   *
   * @param portfolioName the portfolioName of portfolio to buy stock
   * @param startDate     the Date as the buy date
   * @param endDate       the Date as the end of the strategy
   * @throws IllegalArgumentException when given String of portfolioName does not exist
   * @throws IllegalArgumentException when given endDate is later then today
   */
  List<Double> getPortfolioValueList(String portfolioName, Date startDate, Date endDate)
          throws IllegalArgumentException;

  /**
   * Added extension function 2018-12-04, applying strategy derived from a JSON file.
   *
   * @param path the String representing the path of the given JSON file of Strategy
   * @throws IllegalArgumentException when given path is incorrect
   */
  void highLevelFixedValueTrade(String portfolioName, String path) throws IllegalArgumentException;

  /**
   * Added extension function 2018-11-26: The methods buys a certain portfolio with certain ratio of
   * percentage. Added extension function 2018-12-04: adding saving strategy function as a boolean
   * switch input.
   *
   * @param portfolioName the portfolioName of portfolio to buy stock
   * @param ratioMap      the ratioMap of the ratio percentage of String
   * @param startDate     the Date as the buy date
   * @param endDate       the Date as the end of the strategy
   * @param interval      the interval of day-number for each transaction
   * @param amount        the value of money to buy stocks each term
   * @param comm          the commission cost
   * @param save          if save the strategy to a JSON file
   * @throws IllegalArgumentException when given String of portfolioName is incorrect
   * @throws IllegalArgumentException when given startDate is in future
   * @throws IllegalArgumentException when given amount is not larger than 0
   * @throws IllegalArgumentException when give sum of give ratio sum is not 100%
   * @throws IllegalArgumentException when interval day number is not larger than 0
   */
  void highLevelFixedValueTrade(String portfolioName, Map<String, Double> ratioMap, Date startDate,
                                Date endDate, int interval, double amount,
                                double comm, boolean save) throws IllegalArgumentException;

  /**
   * Added extension function 2018-11-26: The methods buys a certain portfolio with certain ratio of
   * percentage.
   *
   * @param portfolioName the portfolioName of portfolio to buy stock
   * @param ratioMap      the ratioMap of the ratio percentage of String
   * @param startDate     the Date as the buy date
   * @param endDate       the Date as the end of the strategy
   * @param interval      the interval of day-number for each transaction
   * @param amount        the value of money to buy stocks each term
   * @param comm          the commission cost
   * @throws IllegalArgumentException when given String of portfolioName is incorrect
   * @throws IllegalArgumentException when given startDate is in future
   * @throws IllegalArgumentException when given amount is not larger than 0
   * @throws IllegalArgumentException when give sum of give ratio sum is not 100%
   * @throws IllegalArgumentException when interval day number is not larger than 0
   */
  void highLevelFixedValueTrade(String portfolioName, Map<String, Double> ratioMap, Date startDate,
                                Date endDate, int interval, double amount,
                                double comm) throws IllegalArgumentException;

  /**
   * Added extension function 2018-11-26: the methods buys a certain portfolio with certain ratio of
   * percentage. Added extension function 2018-12-04: adding saving strategy function as a boolean
   * switch input.
   *
   * @param portfolioName the portfolioName of portfolio to buy stock
   * @param ratioMap      the ratioMap of the ratio percentage of String
   * @param startDate     the Date as the buy date
   * @param interval      the interval of day-number for each transaction
   * @param amount        the value of money to buy stocks each term
   * @param comm          the commission cost
   * @param save          if save the strategy to a JSON file
   * @throws IllegalArgumentException when given String of portfolioName is incorrect
   * @throws IllegalArgumentException when given startDate is in future
   * @throws IllegalArgumentException when given amount is not larger than 0
   * @throws IllegalArgumentException when give sum of give ratio sum is not 100%
   * @throws IllegalArgumentException when interval day number is not larger than 0
   */
  void highLevelFixedValueTrade(String portfolioName, Map<String, Double> ratioMap, Date startDate,
                                int interval, double amount,
                                double comm, boolean save) throws IllegalArgumentException;

  /**
   * Added extension function 2018-11-26: the methods buys a certain portfolio with certain ratio of
   * percentage.
   *
   * @param portfolioName the portfolioName of portfolio to buy stock
   * @param ratioMap      the ratioMap of the ratio percentage of String
   * @param startDate     the Date as the buy date
   * @param interval      the interval of day-number for each transaction
   * @param amount        the value of money to buy stocks each term
   * @param comm          the commission cost
   * @throws IllegalArgumentException when given String of portfolioName is incorrect
   * @throws IllegalArgumentException when given startDate is in future
   * @throws IllegalArgumentException when given amount is not larger than 0
   * @throws IllegalArgumentException when give sum of give ratio sum is not 100%
   * @throws IllegalArgumentException when interval day number is not larger than 0
   */
  void highLevelFixedValueTrade(String portfolioName, Map<String, Double> ratioMap, Date startDate,
                                int interval, double amount,
                                double comm) throws IllegalArgumentException;


  /**
   * The method that add a Stock to an existing portfolio. Update log 2018-12-05: add a fun
   *
   * @param portfolioName a String for the name of the portfolio to be saved
   * @param stockSym      a String for the name of the stock to be added
   * @throws IllegalArgumentException when the given portfolioName does not exist
   */
  void addStockPortfolio(String portfolioName, String stockSym) throws IllegalArgumentException;

  /**
   * The method that creates a new portfolio by saving to a JSON file, such as "f:\\test.json"
   * Update log 2018-12-03: add save Portfolio function to interface
   *
   * @param portfolioName a String for the name of the portfolio to be saved
   * @throws IllegalArgumentException when the given portfolioName does not exist
   */
  void savePortfolio(String portfolioName) throws IllegalArgumentException;

  /**
   * The method that creates a new portfolio by reading a JSON file, such as "f:\\test.json" Update
   * log 2018-12-03: add read Portfolio function to interface
   *
   * @param portfolioName  a String for the name of the portfolio to be created
   * @param pathOfJsonFile a String of a Path and file name of the Json input file, "f:\\test.json"
   * @throws IllegalArgumentException when the given name already exists
   * @throws IllegalArgumentException when the given path or filename does not exist
   */
  void createPortfolio(String portfolioName, String pathOfJsonFile) throws IllegalArgumentException;

  /**
   * The method that creates a new modelstock.portfolio for a client. Update log 2018-11-26: add a
   * set of stock names as another input.
   *
   * @param name the String as the name the modelstock.portfolio to be created
   * @param syms the set as the names of ticker symbols
   * @throws IllegalArgumentException when the given name already exists
   */
  void createPortfolio(String name, Set<String> syms) throws IllegalArgumentException;

  /**
   * The methods returns a double value as the total market value of a modelstock.portfolio until
   * the given status, with each modelstock.portfolio name in each row. If no stock share exists in
   * the modelstock.portfolio, return an empty String.
   *
   * @return a String as all the stock share within a given modelstock.portfolio
   */
  String getAllPortfolios();

  /**
   * The methods buys a certain stock with certain amount of money. Update log 2018-11-26: add an
   * int input as commission fee.
   *
   * @param portfolioName the String as the modelstock.portfolio Name of the modelstock.portfolio
   * @param stockSymbol   the String as the ticker symbol the the stock
   * @param targetDate    the Date as the buy date
   * @param share         the double share value as the share value of the stock
   * @param comm          the commission fee
   * @throws IllegalArgumentException when given portfolioName is incorrect
   * @throws IllegalArgumentException when given stockSymbol is incorrect
   * @throws IllegalArgumentException when given date is in future
   * @throws IllegalArgumentException when given date is not an open market date
   */
  void buyStockByShare(String portfolioName, String stockSymbol, Date targetDate,
                       double share, double comm) throws IllegalArgumentException;

  /**
   * The methods buys a certain stock with certain amount of share. Update log 2018-11-26: add an
   * int input as commission fee.
   *
   * @param portfolioName the String as the modelstock.portfolio Name of the modelstock.portfolio
   * @param stockSymbol   the String as the ticker symbol the the stock
   * @param targetDate    the Date as the buy date
   * @param amountMoney   the total amount value for the buy-trade
   * @param comm          the commission fee
   * @throws IllegalArgumentException when given portfolioName is incorrect
   * @throws IllegalArgumentException when given stockSymbol is incorrect
   * @throws IllegalArgumentException when given date is in future
   * @throws IllegalArgumentException when given date is not an open market date
   */
  void buyStockByAmount(String portfolioName, String stockSymbol, Date targetDate,
                        double amountMoney, double comm) throws IllegalArgumentException;

  /**
   * The methods buys more share of a stock share in a certain portfolio. Update log 2018-11-26: add
   * an int input as commission fee.
   *
   * @param portfolioName the String as the modelstock.portfolio Name of the modelstock.portfolio
   * @param idx           the String as the ticker symbol the the stock
   * @param share         the double share value as the share value of the stock
   * @param comm          the commission fee
   * @throws IllegalArgumentException when given portfolioName is incorrect
   * @throws IllegalArgumentException when given idx is incorrect
   * @throws IllegalArgumentException when given share is not larger than 0
   */

  void buyStockByIdx(String portfolioName, int idx, double share,
                     double comm) throws IllegalArgumentException;

  /**
   * The method that returns a String representing all the stock shares of a modelstock.portfolio by
   * the given name. Update log 2018-11-26: add an int input as commission fee.
   *
   * @param portfolioName the String as the name the modelstock.portfolio
   * @return a String as all the owned stockownership in the portfolio
   * @throws IllegalArgumentException when the given name does not exist
   */
  String examine(String portfolioName) throws IllegalArgumentException;

  /**
   * The methods returns a double value as the total market value of a modelstock.portfolio of the
   * given name until the latest status. If no stock exists in the modelstock.portfolio or no trades
   * haven happened before the given date, return 0.0 as the value.
   *
   * @param portfolioName the String as the ticker symbol the the stock
   * @param targetDate    the date as the ticker symbol the the stock
   * @return a double value as the total market value of a modelstock.portfolio
   * @throws IllegalArgumentException when the given name does not exist
   * @throws IllegalArgumentException when the given date is in future
   */
  double getPortfolioVal(String portfolioName, Date targetDate) throws IllegalArgumentException;

  /**
   * The methods returns a double value as the total market value of a modelstock.portfolio of the
   * given name until the latest status.
   *
   * @param portfolioName the String as the ticker symbol the the stock
   * @return a double value as the total market value of a modelstock.portfolio
   * @throws IllegalArgumentException when the given name does not exist
   */
  double getPortfolioVal(String portfolioName) throws IllegalArgumentException;

  /**
   * The methods returns a double value as the total cost of a modelstock.portfolio at a certain
   * date, while trades before the given date not counted.
   *
   * @param portfolioName the String as the ticker symbol the the stock
   * @param targetDate    the Date until when the cost is counted
   * @return a double value as the total cost of a modelstock.portfolio before the given date
   * @throws IllegalArgumentException when the given name does not exist
   * @throws IllegalArgumentException when the given date is in future
   */
  double getPortfolioCost(String portfolioName, Date targetDate) throws IllegalArgumentException;

  /**
   * The methods returns a double value as the total cost of a modelstock.portfolio by the given
   * name.
   *
   * @param portfolioName the String as the ticker symbol the the stock
   * @return a double value as the total cost of a modelstock.portfolio
   * @throws IllegalArgumentException when the given name does not exist
   */
  double getPortfolioCost(String portfolioName) throws IllegalArgumentException;

  /**
   * The methods return all the Stock Symbol in this Portfolio. Update log 2018-11-26: add this
   * method to get all the stock in the scope of the portfolio.
   *
   * @param portfolioName the String as the ticker symbol the the stock
   * @return all the Stock Symbol in this Portfolio.
   * @throws IllegalArgumentException when the given name does not exist
   */
  String getStockNames(String portfolioName) throws IllegalArgumentException;
}
