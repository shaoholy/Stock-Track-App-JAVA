import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import portfolio.model.StockModel;
import portfolio.model.StockModelImpl;

import static org.junit.Assert.assertEquals;

public class StockModelTest {
  private StockModel sm1;
  private StockModel sm2;
  Set<String> stock;

  /**
   * Build all the test objects needed for test cases.
   */
  @Before
  public void setup() {
    sm1 = new StockModelImpl();
    sm2 = new StockModelImpl();
    stock = new HashSet<>(Arrays.asList("AAPL", "MSFT"));
    sm1.createPortfolio("pf1", stock);

  }

  /**
   * createPortfolioTest test code, to verify: 1, if createPortfolio functions correctly with right
   * input. 2, if examine() works with empty portfolio. 3, if getAllPortfolios() works correctly
   * with non-empty portfolio. 4, if getAllPortfolios() works correctly with empty portfolio. 5, if
   * create portfolio with repeated name casts correct exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void createPortfolioTest() {
    sm1.createPortfolio("pf2", stock);
    assertEquals(sm1.getAllPortfolios(), "pf1\n"
            + "pf2\n");
    assertEquals(sm1.examine("pf1"), "");

    assertEquals(sm2.getAllPortfolios(), "");
    sm2.createPortfolio("pf10", stock);
    sm2.createPortfolio("pf20", stock);
    assertEquals(sm2.getAllPortfolios(), "pf10\n"
            + "pf20\n");
    assertEquals(sm1.examine("pf1"), "");

    sm1.createPortfolio("pf1", stock);
  }

  //  /**
  //   * createPortfolioTest test code, to verify:
  //   * 1, if createPortfolio functions correctly with right input.
  //   * 2, if examine() works with empty portfolio.
  //   * 3, if getAllPortfolios() works correctly with non-empty portfolio.
  //   * 4, if getAllPortfolios() works correctly with empty portfolio.
  //   * 5, if create portfolio with repeated name casts correct exception.
  //   */
  //  @Test(expected = IllegalArgumentException.class)
  //  public void buyStockRealAPITest() {
  //    assertEquals(sm1.examine("pf1"), "");
  //    sm1.buyStockByAmount("pf1", "GOOG", Date.valueOf("2018-11-13"), 1000);
  //    assertEquals(sm1.examine("pf1"), "StockTickerSymbol: GOOG, ShareOwned: 0.000661,
  //          BuyPrice: 1513702.00, "
  //            + "Cost: 1000.00 , BuyDate: 11/13/18\n" +
  //            "\n" +
  //            "\n");
  //    sm1.createPortfolio("pf1");
  //  }

  /**
   * buyStockMockAPITest test code, to verify:
   * 1, if buy stock by money amount works correctly with
   * correct input.
   * 2, if buy the same stock with the same amount on the same day works correctly,
   * as two separate items in the portfolio as designed;
   * 3, if buy stock by share value works correctly with correct input.
   * 4, if getPortfolioCost at a certain date works correctly to show part of the portfolio.
   * 5, if getPortfolioCost on a non-open date works correctly to show part of the portfolio.
   * 6, if getPortfolioCost with no input works correctly to show the latest status cost.
   * 7, if getPortfolioVal at a certain date works correctly to show part of the portfolio.
   * 8, if getPortfolioVal on a non-open date works correctly to show part of the portfolio.
   * 9, if getPortfolioVal with no input works correctly to show the latest status cost.
   * 10, if by stock on a not open date casts correct exception as designed. 11,
   * if by stock on a not open date casts correct exception as designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buyStockMockAPITest() {
    sm2.createPortfolio("pf10", stock);
    sm2.buyStockByAmount("pf10", "GOOG", Date.valueOf("2018-11-13"), 1000, 10);
    sm2.buyStockByAmount("pf10", "GOOG", Date.valueOf("2018-11-13"), 1000, 10);
    sm2.buyStockByShare("pf10", "AAPI", Date.valueOf("2018-11-07"), 5, 10);
    assertEquals(sm2.examine("pf10"), "1: StockTickerSymbol: GOOG, ShareOwned: 5.000000, "
            + "BuyPrice: 200.00, Cost: 1000.00 , BuyDate: 11/13/18\n"
            + "\n"
            + "\n"
            + "2: StockTickerSymbol: GOOG, ShareOwned: 5.000000, BuyPrice: 200.00, "
            + "Cost: 1000.00 , BuyDate: 11/13/18\n"
            + "\n"
            + "\n"
            + "3: StockTickerSymbol: AAPI, ShareOwned: 5.000000, BuyPrice: 300.00, "
            + "Cost: 1500.00 , BuyDate: 11/07/18\n"
            + "\n"
            + "\n");

    assertEquals(sm2.getPortfolioCost("pf10", Date.valueOf("2018-10-08")), 0.0, 0.001);
    assertEquals(sm2.getPortfolioVal("pf10", Date.valueOf("2018-10-08")), 0.0, 0.001);
    assertEquals(sm2.getPortfolioCost("pf10", Date.valueOf("2018-11-08")), 1500.0, 0.001);
    assertEquals(sm2.getPortfolioCost("pf10", Date.valueOf("2018-11-10")), 1500.0, 0.001);
    assertEquals(sm2.getPortfolioCost("pf10"), 3500.0, 0.001);
    assertEquals(sm2.getPortfolioVal("pf10", Date.valueOf("2018-11-08")), 1500.0, 0.001);
    assertEquals(sm2.getPortfolioVal("pf10", Date.valueOf("2018-11-10")), 1500.0, 0.001);
    assertEquals(sm2.getPortfolioVal("pf10"), 3500.0, 0.001);
    sm2.buyStockByAmount("pf10", "AAPI", Date.valueOf("2018-11-10"), 1500, 10);
  }

  /**
   * buyStockZeroAmountTest test code, to verify if buy stock with 0 amount casts correct exception
   * as designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buyStockZeroAmountTest() {
    sm1.buyStockByAmount("pf1", "GOOG", Date.valueOf("2018-11-13"), 0.0, 10);
  }

  /**
   * buyStockZeroAmountTest test code, to verify if buy stock by index in the portfolio works
   * correctly.
   */
  @Test
  public void buyStockByIdxTest() {
    sm2.createPortfolio("pf10", stock);
    sm2.buyStockByAmount("pf10", "GOOG", Date.valueOf("2018-11-13"), 1000, 10);
    assertEquals(sm2.examine("pf10"), "1: StockTickerSymbol: GOOG, ShareOwned: 5.000000, "
            + "BuyPrice: 200.00, Cost: 1000.00 , BuyDate: 11/13/18\n"
            + "\n"
            + "\n");
    sm2.buyStockByIdx("pf10", 1, 5.0, 10);
    assertEquals(sm2.examine("pf10"), "1: StockTickerSymbol: GOOG, ShareOwned: 10.000000, "
            + "BuyPrice: 200.00, Cost: 2000.00 , BuyDate: 11/13/18\n"
            + "\n"
            + "\n");
  }

  /**
   * buyStockNegativeAmountTest test code, to verify if buy stock with negative amount casts correct
   * exception as designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buyStockNegativeAmountTest() {
    sm1.buyStockByAmount("pf1", "GOOG", Date.valueOf("2018-11-13"), -10.0, 10);
  }

  /**
   * buyStockZeroShareTest test code, to verify if buy stock with 0 amount casts correct exception
   * as designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buyStockZeroShareTest() {
    sm1.buyStockByShare("pf1", "GOOG", Date.valueOf("2018-11-13"), 0.0, 10);
  }

  /**
   * buyStockMockAPITest test code, to verify if buy stock with negative amount casts correct
   * exception as designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buyStockNegativeShareTest() {
    sm1.buyStockByShare("pf1", "GOOG", Date.valueOf("2018-11-13"), -10.0, 10);
  }

  /**
   * buyStockInFutureTest test code, to verify if buy stock in future casts correct exception as
   * designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buyStockInFutureTest() {
    sm1.buyStockByShare("pf1", "GOOG", Date.valueOf("2020-11-13"), 1500.0, 10);
  }

  /**
   * buyStockInFutureTest test code, to verify if buy stock in future casts correct exception as
   * designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buyStockInFuture2Test() {
    sm1.buyStockByAmount("pf1", "GOOG", Date.valueOf("2020-11-13"), 1500.0, 10);
  }

  /**
   * getCostInFutureTest test code, to verify if getting cost in future casts correct exception as
   * designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void getCostInFutureTest() {
    sm1.getPortfolioCost("pf1", Date.valueOf("2020-11-13"));
  }

  /**
   * getValInFutureTest test code, to verify if getting value in future casts correct exception as
   * designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void getValInFutureTest() {
    sm1.getPortfolioVal("pf1", Date.valueOf("2020-11-13"));
  }

  /**
   * getValNonNameTest test code, to verify if getting val with non-exist portfolio casts correct
   * exception as designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void getValNonNameTest() {
    sm1.getPortfolioVal("pf3", Date.valueOf("2017-11-13"));
  }

  /**
   * getCostNonNameTest test code, to verify if getting val with non-exist portfolio casts correct
   * exception as designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void getCostNonNameTest() {
    sm1.getPortfolioCost("pf3", Date.valueOf("2017-11-13"));
  }

  /**
   * buyStockNonName2Test test code, to verify if buy stock by wrong portfolio name casts correct
   * exception as designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buyStockNonName2Test() {
    sm1.buyStockByAmount("pf3", "GOOG", Date.valueOf("2016-11-13"), 1500.0, 10);
  }

  /**
   * buyStockNonNameTest test code, to verify if buy stock by wrong portfolio name casts correct
   * exception as designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buyStockNonNameTest() {
    sm1.buyStockByShare("pf3", "GOOG", Date.valueOf("2016-11-13"), 1500.0, 10);
  }

  /**
   * buyStockNullNameTest test code, to verify if buy stock by null portfolio name casts correct
   * exception as designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buyStockNullNameTest() {
    sm1.buyStockByShare(null, "GOOG", Date.valueOf("2016-11-13"), 1500.0, 10);
  }

  /**
   * buyStockNullName2Test test code, to verify if buy stock by null portfolio name casts correct
   * exception as designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buyStockNullName2Test() {
    sm1.buyStockByAmount(null, "GOOG", Date.valueOf("2016-11-13"), 1500.0, 10);
  }

  /**
   * buyNullStockTest test code, to verify if buy null stock casts correct exception as designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buyNullStockTest() {
    sm1.buyStockByShare("pf1", null, Date.valueOf("2016-11-13"), 1500.0, 10);
  }

  /**
   * buyNullStock2Test test code, to verify if buy null stock casts correct exception as designed.
   */
  @Test(expected = IllegalArgumentException.class)
  public void buyNullStock2Test() {
    sm1.buyStockByAmount("pf1", null, Date.valueOf("2016-11-13"), 1500.0, 10);
  }

  /**
   * createPortByNullTest test code, to verify if creating null name portfolio casts correct
   * exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void createPortByNullTest() {
    sm1.createPortfolio(null, stock);
  }

  /**
   * createPortByNullTest test code, to verify if examining null name portfolio casts correct
   * exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void examinePortByNullTest() {
    sm1.examine(null);
  }
}