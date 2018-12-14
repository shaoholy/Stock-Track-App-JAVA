package portfolio.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This interface represents a set of features that the program offers. Each
 * feature is exposed as a function in this interface. This function is used
 * suitably as a callback by the view, to pass control to the controller. How
 * the view uses them as callbacks is completely up to how the view is
 * designed (e.g. it could use them as a callback for a button, or a callback
 * for a dialog box, or a set of text inputs, etc.).
 * Each function is designed to take in the necessary data to complete that
 * functionality.
 * Extension Change: Add a Features Interface, @12.06.
 */
public interface Features {
  /**
   * Process the input string entered by the user.
   */
  void viewAll();

  /**
   * View all portfolios.
   *
   * @param portfName the portfolio name.
   */
  void examineOne(String portfName);

  /**
   * Create one portfolio.
   *
   * @param portfName the portfolio name.
   * @param stockSet a set of Stock symbols.
   */
  void create(String portfName, Set<String> stockSet);

  /**
   * Retrieve one portfolio.
   *
   * @param portfName the portfolio name.
   * @param path the file path.
   */
  void createFromFile(String portfName, String path);

  /**
   * Buy a stock in a portfolio.
   *
   * @param portfName the String as the portfolio name
   * @param stockSymbol the String as the ticker symbol the the stock
   * @param date the Date as the buy date
   * @param shareOrMoney the share value or money amount
   * @param fee the commission fee
   * @param choice user's choice, 1: buy share; 2: buy money amount
   */
  void buyStock(String portfName, String stockSymbol, String date,
                       String shareOrMoney, String fee, int choice);

  /**
   * Check total cost of a portfolio.
   *
   * @param portfName the String as the portfolio name
   * @param date the Date to check
   * @param choice user's choice, 1: today; 2: previous day
   */
  void totalCost(String portfName, String date, int choice);

  /**
   * Check total value of a portfolio.
   *
   * @param portfName the String as the portfolio name
   * @param date the Date to check
   * @param choice user's choice, 1: today; 2: previous day
   */
  void totalValue(String portfName, String date, int choice);

  /**
   * Return all the stock symbols of a portfolio.
   *
   * @param portfName the String as the portfolio name
   * @return all the stock symbols of a portfolio
   */
  String[] getAllStocks(String portfName);

  /**
   * Apply strategy to a portfolio.
   *
   * @param portfolioName the portfolioName of portfolio to buy stock
   * @param weightMap the ratioMap of the ratio percentage of String
   * @param startDate the startDate as the strategy
   * @param endDate   the endDate as the strategy
   * @param interval the interval of day-number for each transaction
   * @param money the value of money to buy stocks each term
   * @param fee the commission cost
   * @param save if save the strategy to a JSON file
   */
  void strategy(String portfolioName, Map<String, Double> weightMap, String startDate,
                String endDate, String interval, String money, String fee, boolean save);


  /**
   * Format the weight map.
   *
   * @param stockNames the stock name array.
   * @return the weight map.
   */
  Map<String, Double> getWeightMapEqual(String[] stockNames);

  /**
   * Retrieve a strategy.
   *
   * @param portfName the portfolio name.
   * @param path the file path.
   */
  void strategyFromFile(String portfName, String path);

  /**
   * Save a portfolio.
   *
   * @param portfName the portfolio name.
   */
  void savePortfolio(String portfName);

  /**
   * Get a list of values day by day from startDate to endDate of a portfolio.
   *
   * @param portfName the portfolioName of portfolio to buy stock
   * @param startDate the startDate as the strategy
   * @param endDate   the endDate as the strategy
   * @return a List as the value sequence
   */
  List<Double> getPortfolioValList(String portfName, String startDate, String endDate);


  /**
   * Add one stock to an existing portfolio.
   *
   * @param porfName the portfolio name.
   * @param stockSymbol the stock symbol.
   */
  void addStock(String porfName, String stockSymbol);

  /**
   * Exit the program.
   */
  void exitProgram();

}
