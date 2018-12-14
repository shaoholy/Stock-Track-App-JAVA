package portfolio.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * This class implements the modelstock.portfolio interface, with a set of owned stock shares.
 * Extension update log 2018-11-26:
 * 1, adding a new field: portfolioSet as the scope of stocks in the portfolio.
 * 2, adding a new field: aphla as the real-world API for stock information instead of the mock API.
 * Extension update log 2018-12-05:
 * 1, adding method to return a list of Portfolio values.
 * 2, adding method to add a stock to a portfolio.
 */
public class PortfolioImp implements Portfolio {
  private LinkedHashMap<Integer, StockOwnership> stockPortfolio;
  private Set<String> portfolioSet;
  private int maxidx = 1;
  private StockInforAPI alpha = new AlphaAPI();

  /**
   * The constructor to build an empty modelstock.portfolio of a portfolio.
   * Update log 2018-11-26: add a set of Stock names as the stock scope of the portfolio
   */
  public PortfolioImp(Set<String> stockSet) {
    this.portfolioSet = new HashSet<>();
    this.portfolioSet.addAll(stockSet);
    this.stockPortfolio = new LinkedHashMap<>();
  }

  @Override
  public String getStockNames() {
    StringBuilder sb = new StringBuilder();
    for (String str: portfolioSet) {
      sb.append(" ").append(str);
    }
    return sb.length() == 0 ? sb.toString() : sb.toString().substring(1);
  }

  @Override
  public void highLevelFixedValueTrade(Map<String, Double> ratioMap,
                                       Date startDate, Date endDate, int interval, double amount,
                                       double comm) throws IllegalArgumentException {
    //throw exceptions of inputs
    if (startDate.after(endDate)) {
      throw new IllegalArgumentException("input date incorrect");
    }
    if (interval < 1) {
      throw new IllegalArgumentException("interval day input cannot be less than 1");
    }
    if (comm < 0.0) {
      throw new IllegalArgumentException("commission cannot be less than 0");
    }
    double sumRatio = 0.0;
    for (String str: ratioMap.keySet()) {
      if (!portfolioSet.contains(str)) {
        throw new IllegalArgumentException("input Stock not contained in input portfolio");
      }
      sumRatio += ratioMap.get(str);
    }
    if (sumRatio > 1.01 && sumRatio < 0.99) {
      throw new IllegalArgumentException("contained percentage numbers sum up not 100%");
    }

    //loop till later than endDate
    Date loopDate = new Date(startDate.getTime());
    while (!loopDate.after(endDate)) {
      Date nextOpenDate = alpha.getNextOpenDate(loopDate);
      if (nextOpenDate.after(endDate)) {
        break;
      }
      for (String sym: ratioMap.keySet()) {
        double ratio = ratioMap.get(sym);
        double tradePrice = alpha.getPriceofDate(sym, nextOpenDate);
        this.stockPortfolio.put(maxidx++, new StockOwnership(sym, ratio * amount / tradePrice,
                nextOpenDate, tradePrice, comm * ratio));
      }
      Calendar cal = Calendar.getInstance();
      cal.setTime(loopDate);
      cal.add(Calendar.DATE, interval);
      loopDate = new Date(cal.getTimeInMillis());
    }
  }

  @Override
  public void highLevelFixedValueTrade(Map<String, Double> ratioMap,
                                       Date startDate, int interval, double amount,
                                       double comm) throws IllegalArgumentException {
    highLevelFixedValueTrade(ratioMap, startDate, getTodayDate(), interval, amount,
            comm);
  }


  @Override
  public List<Double> getValueList(Date startDate, Date endDate) throws IllegalArgumentException {
    if (endDate.after(getTodayDate())) {
      throw new IllegalArgumentException("Given endDate is in Future");
    }
    List<Double> res = new ArrayList<>();
    Date date = Date.valueOf(startDate.toString());

    while (!date.after(endDate)) {
      double doubleres = getTotalVal(date);
      res.add(doubleres);
      date = new Date(date.getTime() + (1000 * 60 * 60 * 24));
    }
    return res;
  }



  @Override
  public void buyStockByAmount(String sym, Date targetDate, double amount, double comm)
          throws IllegalArgumentException {
    if (amount <= 0.0 || comm < 0.0) {
      throw new IllegalArgumentException("money or commission amount is not larger than 0");
    }
    double targetShare = amount / alpha.getPriceofDate(sym, targetDate);
    buyStockByShare(sym, targetDate, targetShare, comm);
  }

  @Override
  public void buyStockByShare(String sym, Date targetDate, double share, double comm)
          throws IllegalArgumentException {
    if (share <= 0.0 || comm < 0.0) {
      throw new IllegalArgumentException("share or commission amount is not larger than 0");
    }
    if (!portfolioSet.contains(sym)) {
      throw new IllegalArgumentException("given portfolio contains no such stock");
    }

    if (!alpha.getLastOpenDate(targetDate).toString().equals(targetDate.toString())) {
      throw new IllegalArgumentException("No trade on a non-open date");
    }
    this.stockPortfolio.put(maxidx, new StockOwnership(sym, share, targetDate,
            alpha.getPriceofDate(sym, targetDate), comm));
    maxidx++;
  }

  @Override
  public void buyStockByIdx(int idx, double share, double comm) throws IllegalArgumentException {
    if (!this.stockPortfolio.containsKey(idx)) {
      throw new IllegalArgumentException("idx does not exist in the portfolio");
    }
    if (share <= 0.0) {
      throw new IllegalArgumentException("share value is not larger than 0");
    }
    this.stockPortfolio.get(idx).changeShare(share, comm);
  }

  @Override
  public void addStock(String stockSym) {
    this.portfolioSet.add(stockSym);
  }

  @Override
  public double getTotalCostBasic() {
    double res = 0.0;
    for (Integer idx : stockPortfolio.keySet()) {
      res += stockPortfolio.get(idx).getCost();
    }
    return res;
  }

  @Override
  public double getTotalCostBasic(Date targetDate) {
    double res = 0.0;
    for (Integer idx : stockPortfolio.keySet()) {
      res += stockPortfolio.get(idx).getCost(targetDate);
    }
    return res;
  }

  @Override
  public double getTotalVal() {
    double res = 0.0;
    for (Integer idx : stockPortfolio.keySet()) {
      res += stockPortfolio.get(idx).getValue();
    }
    return res;
  }

  @Override
  public double getTotalVal(Date targetDate) {
    double res = 0.0;
    for (Integer idx : stockPortfolio.keySet()) {
      double indires = stockPortfolio.get(idx).getValue(targetDate);
      res += indires;
    }
    return res;
  }

  @Override
  public String examinePortfolio() {
    StringBuilder sb = new StringBuilder();
    for (Integer idx : stockPortfolio.keySet()) {
      sb.append(idx).append(": ").append(stockPortfolio.get(idx).examine());
    }
    return sb.toString();
  }

  private Date getTodayDate() {
    Calendar today = Calendar.getInstance();
    today.clear(Calendar.HOUR);
    today.clear(Calendar.MINUTE);
    today.clear(Calendar.SECOND);
    return new Date(today.getTimeInMillis());
  }
}
