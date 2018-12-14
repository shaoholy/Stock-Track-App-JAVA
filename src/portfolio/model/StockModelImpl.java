package portfolio.model;

import java.io.IOException;
import java.util.Calendar;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class implements the StockModel interface, with a map of portfolio name to portfolios.
 */
public class StockModelImpl implements StockModel {
  private LinkedHashMap<String, Portfolio> userPortfolios = new LinkedHashMap<>();

  @Override
  public String getStockNames(String portfolioName) throws IllegalArgumentException {
    if (!userPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("no such portfolio in this client");
    }
    return userPortfolios.get(portfolioName).getStockNames();
  }

  @Override
  public List<Double> getPortfolioValueList(String portfolioName, Date startDate, Date endDate)
          throws IllegalArgumentException {
    if (!userPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("no such portfolio in this client");
    }
    return this.userPortfolios.get(portfolioName).getValueList(startDate, endDate);
  }

  @Override
  public void highLevelFixedValueTrade(String portfoilioName, String path)
          throws IllegalArgumentException {
    try {
      new PortfolioBuilder().applyStrategyFromJosn(this, portfoilioName, path);
    } catch (IOException e) {
      throw new IllegalArgumentException("Not valid input");
    }
  }

  @Override
  public void highLevelFixedValueTrade(String portfolioName, Map<String, Double> ratioMap,
                                       Date startDate, Date endDate, int interval, double amount,
                                       double comm, boolean save) throws IllegalArgumentException {
    highLevelFixedValueTrade(portfolioName, ratioMap, startDate, endDate, interval, amount, comm);
    if (save) {
      new PortfolioBuilder().saveStrategy(ratioMap, startDate, endDate, interval,
              amount, comm);
    }
  }

  @Override
  public void highLevelFixedValueTrade(String portfolioName, Map<String, Double> ratioMap,
                                       Date startDate, Date endDate, int interval,
                                       double amount, double comm) throws IllegalArgumentException {
    if (!userPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("No such portfolio for current user");
    }
    this.userPortfolios.get(portfolioName).highLevelFixedValueTrade(ratioMap, startDate, endDate,
            interval, amount, comm);
  }

  @Override
  public void highLevelFixedValueTrade(String portfolioName, Map<String, Double> ratioMap,
                                       Date startDate, int interval, double amount,
                                       double comm, boolean save) throws IllegalArgumentException {
    highLevelFixedValueTrade(portfolioName, ratioMap, startDate, interval, amount, comm);
    if (save) {
      new PortfolioBuilder().saveStrategy(ratioMap, startDate, interval, amount, comm);
    }
  }

  @Override
  public void highLevelFixedValueTrade(String portfolioName, Map<String, Double> ratioMap,
                                       Date startDate, int interval, double amount,
                                       double comm) throws IllegalArgumentException {
    if (!userPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("No such portfolio for current user");
    }
    this.userPortfolios.get(portfolioName).highLevelFixedValueTrade(ratioMap, startDate,
            interval, amount, comm);
  }

  @Override
  public void addStockPortfolio(String portfolioName, String stockSym)
          throws IllegalArgumentException {
    if (!userPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("No such portfolio for current user");
    }
    this.userPortfolios.get(portfolioName).addStock(stockSym);
  }

  @Override
  public void savePortfolio(String portfolioName)
          throws IllegalArgumentException {
    if (!userPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("Portfolio does not exist");
    }
    new PortfolioBuilder().save(portfolioName, userPortfolios.get(portfolioName));
  }

  @Override
  public void createPortfolio(String portfolioName, String pathOfJsonFile)
          throws IllegalArgumentException {
    if (userPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("Portfolio with input name already exists");
    }
    try {
      userPortfolios.put(portfolioName, new PortfolioBuilder().build(pathOfJsonFile));
      System.out.println("success put " + portfolioName);
    } catch (IOException e) {
      throw new IllegalArgumentException("Input incorrect");
    }
  }


  @Override
  public void createPortfolio(String name, Set<String> syms) throws IllegalArgumentException {
    if (name == null || this.userPortfolios.containsKey(name)) {
      throw new IllegalArgumentException("Portfolio with input name already exists");
    }
    userPortfolios.put(name, new PortfolioImp(syms));
  }

  @Override
  public String getAllPortfolios() {
    StringBuilder sb = new StringBuilder();
    for (String portfolioName : userPortfolios.keySet()) {
      sb.append(portfolioName).append(" ");
    }
    return sb.toString();
  }

  @Override
  public void buyStockByShare(String portfolioName, String stockSymbol, Date targetDate,
                              double share, double comm) throws IllegalArgumentException {
    if (checkDateInFuture(targetDate)) {
      throw new IllegalArgumentException("Input date is in the future");
    }
    if (portfolioName == null || !userPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("No such portfolio");
    }
    if (stockSymbol == null) {
      throw new IllegalArgumentException("Null input not allowed");
    }
    this.userPortfolios.get(portfolioName).buyStockByShare(stockSymbol, targetDate, share, comm);
  }

  @Override
  public void buyStockByAmount(String portfolioName, String stockSymbol, Date targetDate,
                               double amountMoney, double comm) throws IllegalArgumentException {
    if (checkDateInFuture(targetDate)) {
      throw new IllegalArgumentException("Input date is in the future");
    }
    if (portfolioName == null || !userPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("No such portfolio");
    }
    if (stockSymbol == null) {
      throw new IllegalArgumentException("Null input not allowed");
    }
    this.userPortfolios.get(portfolioName).buyStockByAmount(stockSymbol, targetDate, amountMoney,
            comm);
  }

  @Override
  public void buyStockByIdx(String portfolioName, int idx, double share, double comm)
          throws IllegalArgumentException {
    if (portfolioName == null || !userPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("No such portfolio");
    }
    if (idx < 0) {
      throw new IllegalArgumentException("negative idx not allowed");
    }
    if (share <= 0) {
      throw new IllegalArgumentException("share value must be larger than 0");
    }
    this.userPortfolios.get(portfolioName).buyStockByIdx(idx, share, comm);
  }

  @Override
  public String examine(String portfolioName) throws IllegalArgumentException {
    if (portfolioName == null || !userPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("No such portfolio");
    }
    return userPortfolios.get(portfolioName).examinePortfolio();
  }

  @Override
  public double getPortfolioVal(String portfolioName, Date targetDate)
          throws IllegalArgumentException {
    if (portfolioName == null || !userPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("No such portfolio");
    }
    if (checkDateInFuture(targetDate)) {
      throw new IllegalArgumentException("Input date is in the future");
    }
    return userPortfolios.get(portfolioName).getTotalVal(targetDate);
  }

  @Override
  public double getPortfolioVal(String portfolioName) throws IllegalArgumentException {
    if (portfolioName == null || !userPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("No such portfolio");
    }
    return userPortfolios.get(portfolioName).getTotalVal();
  }

  @Override
  public double getPortfolioCost(String portfolioName, Date targetDate)
          throws IllegalArgumentException {
    if (portfolioName == null || !userPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("No such portfolio");
    }
    if (checkDateInFuture(targetDate)) {
      throw new IllegalArgumentException("Input date is in the future");
    }
    return userPortfolios.get(portfolioName).getTotalCostBasic(targetDate);
  }

  @Override
  public double getPortfolioCost(String portfolioName) throws IllegalArgumentException {
    if (portfolioName == null || !userPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("No such portfolio");
    }
    return userPortfolios.get(portfolioName).getTotalCostBasic();
  }

  private boolean checkDateInFuture(Date targetDate) {
    Calendar today = Calendar.getInstance();
    today.clear(Calendar.HOUR);
    today.clear(Calendar.MINUTE);
    today.clear(Calendar.SECOND);

    Date todayDate = new Date(today.getTimeInMillis());
    return (targetDate.after(todayDate));
  }
}
