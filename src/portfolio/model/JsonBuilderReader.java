package portfolio.model;

import org.json.JSONException;

import java.io.IOException;
import java.sql.Date;
import java.util.Map;

/**
 * This interface represents a JSON file reader and writer object.
 * Extension Change: Add JsonBuilderReader class(interface), @12.04.
 */
public interface JsonBuilderReader {


  /**
   * The method saves a strategy to a JSON file.
   *
   * @param ratioMap          the ratioMap of the ratio percentage of stocks
   * @param startDate         the Date as the strategy start date
   * @param endDate           the Date as the strategy end date
   * @param interval          the interval of day-number for each transaction
   * @param amount            the value of money to buy stocks each term
   * @param comm              the commission cost
   */
  void saveStrategy(Map<String, Double> ratioMap,
                    Date startDate, Date endDate, int interval, double amount,
                    double comm);

  /**
   * The method saves a strategy to a JSON file.
   *
   * @param ratioMap          the ratioMap of the ratio percentage of stocks
   * @param startDate         the Date as the strategy start date
   * @param interval          the interval of day-number for each transaction
   * @param amount            the value of money to buy stocks each term
   * @param comm              the commission cost
   */
  void saveStrategy(Map<String, Double> ratioMap,
                    Date startDate, int interval, double amount,
                    double comm);

  void applyStrategyFromJosn(StockModel model, String portfolioName, String path)
          throws IOException, JSONException;

  /**
   * The method builds a Portfolio from the path of a current JSON file.
   *
   * @param path           the String as the path of the JSON file to build from
   * @throws JSONException when reading JSON casts IOexception
   * @throws IOException   when reading JSON casts IOexception
   */
  Portfolio build(String path) throws IOException, JSONException;

  /**
   * The method saves a portfolio to a JSON file.
   *
   * @param portfolioName     the ratioMap of the ratio percentage of stocks
   * @param pf                the Portfolio to be saved
   * @throws IllegalArgumentException when writing JSON casts IOexception
   */
  void save(String portfolioName, Portfolio pf) throws IllegalArgumentException;
}
