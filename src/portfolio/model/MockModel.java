package portfolio.model;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class represents a Mock StockModel for Controller and View test.
 */
public class MockModel implements StockModel {
  private StringBuilder log;
  private final String uniqueString = "uniqueString";
  private final double uniqueNum = 1111.00;
  private final Set<String> stockSymbols;
  private final String weights = "AAAA : 0.5 BBBB : 0.5";

  public MockModel(StringBuilder log) {
    this.log = log;
    this.stockSymbols  = new HashSet<>(Arrays.asList("AAAA", "BBBB"));
  }

  @Override
  public void createPortfolio(String name, Set<String> syms) throws IllegalArgumentException {
    log.append("name: " + name + "Stock Symbols: " + stockSymbols.toString() + "\n");

  }

  @Override
  public void createPortfolio(String portfolioName, String pathOfJsonFile)
          throws IllegalArgumentException {
    log.append("portfolioName: " + portfolioName + "path: " + pathOfJsonFile + "\n");
  }

  @Override
  public String getAllPortfolios() {
    return "getAllPortfolios";
  }

  @Override
  public void buyStockByShare(String portfolioName, String stockSymbol,
                              Date targetDate, double share, double comm)
          throws IllegalArgumentException {
    log.append("portfolioName: " + portfolioName + " " + "stockSymbol: " + stockSymbol
            + " date: " + targetDate.toString() + " share: " + String.valueOf(share)
            + "Fee: " + String.valueOf(comm) + "\n");
  }

  @Override
  public void buyStockByAmount(String portfolioName, String stockSymbol,
                               Date targetDate, double amountMoney, double comm)
          throws IllegalArgumentException {
    log.append("portfolioName: " + portfolioName + " " + "stockSymbol: " + stockSymbol
            + " date: " + targetDate.toString() + " Money: " + String.valueOf(amountMoney)
            + "Fee: " + String.valueOf(comm) + "\n");
  }

  @Override
  public void buyStockByIdx(String portfolioName, int idx, double share, double comm)
          throws IllegalArgumentException {
    log.append("portfolioName: " + portfolioName + " " + "index: " + idx
            + "Share: " + String.valueOf(share) + "Fee: " + String.valueOf(comm) + "\n");
  }


  @Override
  public String examine(String portfolioName) throws IllegalArgumentException {
    log.append("portfolioName: " + portfolioName + "\n");
    return "examine";
  }

  @Override
  public double getPortfolioVal(String portfolioName,
                                Date targetDate) throws IllegalArgumentException {
    log.append("portfolioName: " + portfolioName + "date: " + targetDate.toString() + "\n");
    return uniqueNum;
  }

  @Override
  public double getPortfolioVal(String portfolioName) throws IllegalArgumentException {
    log.append("portfolioName: " + portfolioName + "\n");
    return uniqueNum;
  }

  @Override
  public double getPortfolioCost(String portfolioName,
                                 Date targetDate) throws IllegalArgumentException {
    log.append("portfolioName: " + portfolioName + "\n");
    return uniqueNum;
  }

  @Override
  public double getPortfolioCost(String portfolioName) throws IllegalArgumentException {
    log.append("portfolioName: " + portfolioName + "\n");
    return uniqueNum;
  }

  @Override
  public List<Double> getPortfolioValueList(String portfolioName, Date startDate, Date endDate)
          throws IllegalArgumentException {
    log.append("portfolioName: " + portfolioName + "startDate"
            + startDate + "endDate" + endDate + "\n");
    return null;
  }

  @Override
  public void highLevelFixedValueTrade(String portfolioName, String path)
          throws IllegalArgumentException {
    log.append("portfolioName: " + portfolioName + "path: " + path + "\n");
  }

  @Override
  public void highLevelFixedValueTrade(String portfolioName, Map<String, Double> ratioMap,
                                       Date startDate, Date endDate, int interval,
                                       double amount, double comm, boolean save)
          throws IllegalArgumentException {
    log.append("portfolioName" + portfolioName + "weights : " + weights + "Start Date : "
            + startDate.toString() + "End Date : " + endDate.toString() + "Interval : " + interval
            + "Amount : " + amount + "Fee: " + comm + "save" + save);
  }

  @Override
  public void highLevelFixedValueTrade(String portfolioName, Map<String, Double> ratioMap,
                                       Date startDate, Date endDate, int interval, double amount,
                                double comm) throws IllegalArgumentException {
    log.append("portfolioName" + portfolioName + "weights : " + weights + "Start Date : "
            + startDate.toString() + "End Date : " + endDate.toString() + "Interval : " + interval
            + "Amount : " + amount + "Fee: " + comm);

  }

  @Override
  public void highLevelFixedValueTrade(String portfolioName, Map<String, Double> ratioMap,
                                       Date startDate, int interval, double amount,
                                       double comm, boolean save) throws IllegalArgumentException {
    log.append("portfolioName" + portfolioName + "weights : " + weights + "Start Date : "
            + startDate.toString() + "Interval : " + interval
            + "Amount : " + amount + "Fee: " + comm + "save" + save);
  }


  @Override
  public void highLevelFixedValueTrade(String portfolioName, Map<String, Double> ratioMap,
                                       Date startDate, int interval, double amount,
                                double comm) throws IllegalArgumentException {
    log.append("portfolioName" + portfolioName + "weights : " + weights + "Start Date : "
            + startDate.toString() + "Interval : " + interval
            + "Amount : " + amount + "Fee: " + comm);

  }

  @Override
  public void addStockPortfolio(String portfolioName, String stockSym)
          throws IllegalArgumentException {
    log.append("portfolioName: " + portfolioName + "stockSymbol: " + stockSym + "\n");
  }

  @Override
  public void savePortfolio(String portfolioName) throws IllegalArgumentException {
    log.append("portfolioName: " + portfolioName + "\n");
  }



  @Override
  public String getStockNames(String portfolioName) {
    return "All Stocks' name in this Portfolio";
  }
}
