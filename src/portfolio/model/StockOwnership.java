package portfolio.model;

import java.sql.Date;

/**
 * This is the class as the stock ownership in a modelstock.portfolio for a certain user.
 * Extension update log 2018-11-26:
 * 1, adding a new field as commission fee of the trade.
 * 2, adding the commission into the getCost() method.
 * 3, adding a new field as the Alpha-Vantage API for real-world stock information query.
 * 4, adding the commission fee into the examine() method.
 */
public class StockOwnership {
  private final String stockSym;
  private double shareOwned;
  private final Date buyDate;
  private final double buyPrice;
  private double costBasis;
  private double commissionFee;
  private StockInforAPI alpha = new AlphaAPI();

  /**
   * The constructor to build a Stock ownership of a certain stock.
   * Update log 2018-11-26: add a double value as the commission fee.
   *
   * @param sym      the String as the ticker symbol the the stock
   * @param share    the String as the ticker symbol the the price
   * @param date     the date of the trade happens
   * @param buyPrice the buy-in price per share
   * @param comm     the commission fee
   */
  public StockOwnership(String sym, double share, Date date, double buyPrice, double comm) {
    this.stockSym = sym;
    this.shareOwned = share;
    this.buyDate = date;
    this.buyPrice = buyPrice;
    this.commissionFee = comm;
    this.costBasis = this.buyPrice * this.shareOwned + this.commissionFee;
  }

  /**
   * The method returns changes the shareOwned value of the .
   * Update log 2018-11-26: add a double value as the commission fee.
   *
   * @param changeShare the String as the ticker symbol the the price
   * @throws IllegalArgumentException when given amount is not larger than 0
   */
  public void changeShare(double changeShare, double comm) throws IllegalArgumentException {
    if (this.shareOwned + changeShare < 0.0) {
      throw new IllegalArgumentException("Cannot change share value below 0");
    }
    if (comm < 0) {
      throw new IllegalArgumentException("Commission fee cannot be lower than 0");
    }
    this.shareOwned += changeShare;
    this.commissionFee += comm;
    this.costBasis = this.shareOwned * this.buyPrice + this.commissionFee;
  }

  /**
   * The method returns the value of the owned stock share of the owner. If the given date is before
   * the stock buy date, return 0.0 as the value. If the given date is not an open date, return the
   * closest open market date before the given date.
   *
   * @param targetDate the String as the ticker symbol the the price
   * @return a double as the value as the owned stock share value of the owner
   */
  public double getValue(Date targetDate) {
    if (targetDate.before(buyDate)) {
      return 0;
    } else {
      try {
        double res = alpha.getPriceofDate(this.stockSym, targetDate) * this.shareOwned;
        return res;
      } catch (IllegalArgumentException e) {
        Date latestOpenDate = alpha.getLastOpenDate(targetDate);
        return this.getValue(latestOpenDate);
      }
    }
  }

  /**
   * The method returns the value of the owned stock share CURRENT value of the owner.
   *
   * @return a double as the value as the owned stock share CURRENT value of the owner
   */
  public double getValue() {
    //    try {
    //      Thread.sleep(20000);
    //    } catch (InterruptedException ex) {
    //    }
    return alpha.getLatestPrice(this.stockSym) * this.shareOwned;
  }

  /**
   * The method returns the value of the owned stock share CURRENT value of the owner in the given
   * date. If the stock is bought before the given date, return the value of 0.0.
   *
   * @return a double as the cost value as the owned stock share cost value of the owner
   */
  public double getCost(Date targetDate) {
    if (targetDate.before(buyDate)) {
      return 0.0;
    } else {
      return this.costBasis;
    }
  }

  /**
   * The method returns the value of the owned stock share CURRENT value of the owner.
   *
   * @return a double as the cost value as the owned stock share basic cost
   */
  public double getCost() {
    return this.costBasis;
  }

  /**
   * The method returns the String representing the owned stock information.
   * Update log 2018-11-26: add commission fee information into the returned String.
   *
   * @return a String as the represented information of the stock share
   */
  public String examine() {
    return String.format("StockTickerSymbol: %s, ShareOwned: %f, "
                    + "BuyPrice: %.2f, Cost: %.2f, Commission: %.2f, BuyDate: %tD%n"
                    + "\n",
            this.stockSym, this.shareOwned, this.buyPrice, this.costBasis,
            this.commissionFee, this.buyDate);
  }
}
