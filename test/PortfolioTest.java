import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Date;

import portfolio.controller.IPortfoliosController;
import portfolio.controller.PortfoliosController;
import portfolio.model.StockModel;
import portfolio.model.StockModelImpl;
import portfolio.view.PortfolioView;

import static org.junit.Assert.assertEquals;

/**
 * This test class contain All Junit test for Portfolio.
 */
public class PortfolioTest {
  StockModel model;
  IPortfoliosController controller;
  StringBuilder log;
  PortfolioView view;
  StringBuilder out;

  /**
   * Create objects.
   */
  @Before
  public void setUp() {

    model = new StockModelImpl();
    out = new StringBuilder();

  }


  /**
   * Varify invalid constructor throw exception.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructor() {
    InputStream in = new ByteArrayInputStream("1".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    view = null;
    controller = new PortfoliosController(in, model, view);
  }


  /**
   * Test Create Portfolio.
   */
  @Test
  public void testCreatePortfolio() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT #").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);
    controller.startPortfolio();

    String outExpected = "retirement ";

    assertEquals(model.getAllPortfolios(), outExpected);
  }



  /**
   * Test View All Portfolios function.
   */
  @Test
  public void testViewAll() {
    InputStream in = new ByteArrayInputStream("3 retirement AAPL MSFT # 3 school AAPL MSFT # 1"
            .getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);

    controller.startPortfolio();

    String outExpected = "retirement school ";

    assertEquals(model.getAllPortfolios(), outExpected);
  }

  /**
   * Test Exam one Portfolio function.
   */
  @Test
  public void testExamOne() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT # 4 1 1 1 2018-03-12 10 "
            + "100 2 1").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);

    controller.startPortfolio();

    String outExpected = "1: StockTickerSymbol: MSFT, ShareOwned: 100.000000, BuyPrice: 96.77, "
            + "Cost: 9687.00, Commission: 10.00, BuyDate: 03/12/18\n\n";

    assertEquals(model.examine("retirement"), outExpected);

  }

  /**
   * Test Create Portfolio and then Buy Stock By Share.
   */
  @Test
  public void testCreatePortfolioBuyStockByShare() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT # 4 1 1 1 2018-03-12 10 "
            + "100 4").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);
    controller.startPortfolio();

    String outExpected =
            "1: StockTickerSymbol: MSFT, ShareOwned: 100.000000, BuyPrice: 96.77, Cost: 9687.00, "
            + "Commission: 10.00, BuyDate: 03/12/18\n\n";

    assertEquals(model.examine("retirement"), outExpected);
  }

  /**
   * Test Create Portfolio and then Buy Stock By Money Amount.
   */
  @Test
  public void testCreatePortfolioBuyStockByMoneyAmount() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT # 4 2 1 1 2018-03-12 "
            + "10 100 4").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);
    controller.startPortfolio();

    String outExpected = "1: StockTickerSymbol: MSFT, ShareOwned: 1.033378, BuyPrice: 96.77, "
            + "Cost: 110.00, Commission: 10.00, BuyDate: 03/12/18\n\n";

    assertEquals(model.examine("retirement"), outExpected);
  }



  /**
   * Test Buy Stock at holiday. This application don't support buy stock after trad hours.
   * It will prompt to user the date is not a trading day, and ask user to input again.
   */
  @Test
  public void testBuyStockAtHoliday() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT # 4 2 1 1 2018-11-03 "
            + "10 100 4").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);
    controller.startPortfolio();

    String outExpected = "";

    assertEquals(model.examine("retirement"), outExpected);
  }


  /**
   * Test Buy Stock with Invalid Money Amount.
   * It will ask user to input the right Money.
   */
  @Test
  public void testBuyStockInvalidMoneyAmount() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT # 4 2 1 1 2018-11-01 10 0")
            .getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);
    controller.startPortfolio();

    String outExpected = "";

    assertEquals(model.examine("retirement"), outExpected);
  }

  /**
   * Test Buy Stock with Invalid Date input.
   * It will ask user to input the right Date.
   */
  @Test
  public void testBuyStockByShareInvalidDate() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT # 4 2 1 1 2015/03/01 "
            + "2018-11-01 10 100").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);
    controller.startPortfolio();

    String outExpected = "1: StockTickerSymbol: MSFT, ShareOwned: 0.944109, BuyPrice: 105.92, "
            + "Cost: 110.00, Commission: 10.00, BuyDate: 11/01/18\n\n";

    assertEquals(model.examine("retirement"), outExpected);
  }



  /**
   * Test Buy Stock with Invalid negative Commission Fee.
   */
  @Test
  public void testBuyStockInvalidFee() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT # 4 2 1 1 2018-11-01 -10 0")
            .getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);
    controller.startPortfolio();

    String outExpected = "";

    assertEquals(model.examine("retirement"), outExpected);
  }


  /**
   * Test Create Portfolio and Buy Two Stocks By Share and by Money Amount.
   */
  @Test
  public void testCreatePortfolioAndBuyTwoStocks() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT # 4 1 1 1 2018-11-01 "
            + "10 100 1 1 2 2018-11-02 10 500").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);
    controller.startPortfolio();

    String outExpected = "1: StockTickerSymbol: MSFT, ShareOwned: 100.000000, BuyPrice: 105.92, "
            + "Cost: 10602.00, Commission: 10.00, BuyDate: 11/01/18\n\n"
            + "2: StockTickerSymbol: AAPL, ShareOwned: 500.000000, BuyPrice: 207.48, "
            + "Cost: 103750.00, Commission: 10.00, BuyDate: 11/02/18\n\n";

    assertEquals(model.examine("retirement"), outExpected);
  }


  /**
   * Test Buy one Stock(one transaction),
   * and then check Total Costs at Certain Date(with commission fee).
   */
  @Test
  public void testTotalCostsAtCertainDateOneTime() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT # 4 2 1 1 "
            + "2018-03-12 10 100 3 5 2 1 2018-03-13").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);
    controller.startPortfolio();

    String transaction = "1: StockTickerSymbol: MSFT, ShareOwned: 1.033378, BuyPrice: 96.77, "
            + "Cost: 110.00, Commission: 10.00, BuyDate: 03/12/18\n\n";
    assertEquals(model.examine("retirement"), transaction);

    double outExpected = 110.0; //1 * (100 + 10)
    Assert.assertEquals(model.getPortfolioCost("retirement",
            Date.valueOf("2018-03-13")), outExpected, 0.1);
  }


  /**
   * Test apply Strategy for 3 months(multiple transactions on different days),
   * and then check Total Costs (with commission fee).
   */
  @Test
  public void testTotalCostsAtCertainDateMultipleTime() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT # 7 1 Y 200 10 30 "
            + "2018-01-02 Y 2018-03-06").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);
    controller.startPortfolio();

    String transaction = "1: StockTickerSymbol: MSFT, ShareOwned: 1.163467, BuyPrice: 85.95, "
            + "Cost: 105.00, Commission: 5.00, BuyDate: 01/02/18\n"
            + "\n"
            + "2: StockTickerSymbol: AAPL, ShareOwned: 0.580518, BuyPrice: 172.26, "
            + "Cost: 105.00, Commission: 5.00, BuyDate: 01/02/18\n"
            + "\n"
            + "3: StockTickerSymbol: MSFT, ShareOwned: 1.060895, BuyPrice: 94.26, "
            + "Cost: 105.00, Commission: 5.00, BuyDate: 02/01/18\n"
            + "\n"
            + "4: StockTickerSymbol: AAPL, ShareOwned: 0.596019, BuyPrice: 167.78, "
            + "Cost: 105.00, Commission: 5.00, BuyDate: 02/01/18\n"
            + "\n"
            + "5: StockTickerSymbol: MSFT, ShareOwned: 1.067920, BuyPrice: 93.64, "
            + "Cost: 105.00, Commission: 5.00, BuyDate: 03/05/18\n"
            + "\n"
            + "6: StockTickerSymbol: AAPL, ShareOwned: 0.565547, BuyPrice: 176.82, "
            + "Cost: 105.00, Commission: 5.00, BuyDate: 03/05/18\n\n";
    assertEquals(model.examine("retirement"), transaction);


    Assert.assertEquals(model.getPortfolioCost("retirement",
            Date.valueOf("2018-01-12")), 210.0, 0.1); //1 * (200 + 10)

    Assert.assertEquals(model.getPortfolioCost("retirement",
            Date.valueOf("2018-02-12")), 420.0, 0.1); //2 * (200 + 10)


    Assert.assertEquals(model.getPortfolioCost("retirement",
            Date.valueOf("2018-03-12")), 630.0, 0.1); //3 * (200 + 10)

  }

  /**
   * Test Buy one Stock(one transaction),
   * and then check Total Value at Certain Date.
   */
  @Test
  public void testTotalValueAtCertainDateOneTime() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT # 4 2 1 1 "
            + "2018-03-12 10 500 3 6 2 1 2018-04-01").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);
    controller.startPortfolio();

    double outExpected = 550.11;

    assertEquals(model.getPortfolioVal("retirement"), outExpected, 0.1);
  }



  /**
   * Test apply Strategy for 3 months(multiple transactions on different days),
   * and then check Total Value.
   */
  @Test
  public void testTotalValueAtCertainDateMultipleTime() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT # 7 1 Y 200 10 30 "
            + "2018-01-02 Y 2018-03-06").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);
    controller.startPortfolio();

    String transaction = "1: StockTickerSymbol: MSFT, ShareOwned: 1.163467, BuyPrice: 85.95, "
            + "Cost: 105.00, Commission: 5.00, BuyDate: 01/02/18\n"
            + "\n"
            + "2: StockTickerSymbol: AAPL, ShareOwned: 0.580518, BuyPrice: 172.26, "
            + "Cost: 105.00, Commission: 5.00, BuyDate: 01/02/18\n"
            + "\n"
            + "3: StockTickerSymbol: MSFT, ShareOwned: 1.060895, BuyPrice: 94.26, "
            + "Cost: 105.00, Commission: 5.00, BuyDate: 02/01/18\n"
            + "\n"
            + "4: StockTickerSymbol: AAPL, ShareOwned: 0.596019, BuyPrice: 167.78, "
            + "Cost: 105.00, Commission: 5.00, BuyDate: 02/01/18\n"
            + "\n"
            + "5: StockTickerSymbol: MSFT, ShareOwned: 1.067920, BuyPrice: 93.64, "
            + "Cost: 105.00, Commission: 5.00, BuyDate: 03/05/18\n"
            + "\n"
            + "6: StockTickerSymbol: AAPL, ShareOwned: 0.565547, BuyPrice: 176.82, "
            + "Cost: 105.00, Commission: 5.00, BuyDate: 03/05/18\n\n";
    assertEquals(model.examine("retirement"), transaction);


    Assert.assertEquals(model.getPortfolioVal("retirement",
            Date.valueOf("2018-01-12")), 207.05, 0.1);

    Assert.assertEquals(model.getPortfolioVal("retirement",
            Date.valueOf("2018-04-12")), 611.4, 0.1);


  }

  /**
   * Test Apply Strategy to a Portfolio with not equal weight and end date,
   * and if this transaction day belongs to holiday (2018-03-03 / 2019-03-04),
   * it will choose the next available day to invest (2018-03-05).
   */
  @Test
  public void testApplyStrategyNotEqualWeightEndDate() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT # 7 1 N 0.3 0.7 500 10 30 "
            + "2018-01-02 Y 2018-03-05").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);
    controller.startPortfolio();

    String outExpected = "1: StockTickerSymbol: MSFT, ShareOwned: 1.745201, BuyPrice: 85.95, "
            + "Cost: 153.00, Commission: 3.00, BuyDate: 01/02/18\n\n"
            + "2: StockTickerSymbol: AAPL, ShareOwned: 2.031812, BuyPrice: 172.26, "
            + "Cost: 357.00, Commission: 7.00, BuyDate: 01/02/18\n\n"
            + "3: StockTickerSymbol: MSFT, ShareOwned: 1.591343, BuyPrice: 94.26, "
            + "Cost: 153.00, Commission: 3.00, BuyDate: 02/01/18\n\n"
            + "4: StockTickerSymbol: AAPL, ShareOwned: 2.086065, BuyPrice: 167.78, "
            + "Cost: 357.00, Commission: 7.00, BuyDate: 02/01/18\n\n"
            + "5: StockTickerSymbol: MSFT, ShareOwned: 1.601880, BuyPrice: 93.64, "
            + "Cost: 153.00, Commission: 3.00, BuyDate: 03/05/18\n\n"
            + "6: StockTickerSymbol: AAPL, ShareOwned: 1.979414, BuyPrice: 176.82, "
            + "Cost: 357.00, Commission: 7.00, BuyDate: 03/05/18\n" +
            "\n";

    assertEquals(model.examine("retirement"), outExpected);
  }

  /**
   * Test Apply Strategy to a Portfolio with convenient equal weight and end date.
   */
  @Test
  public void testApplyStrategyEqualWeightEndDate() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT # 7 1 Y 500 10 30 "
            + "2018-03-02 Y 2018-06-04").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);
    controller.startPortfolio();

    String outExpected = "1: StockTickerSymbol: MSFT, ShareOwned: 2.686728, BuyPrice: 93.05, "
            + "Cost: 255.00, Commission: 5.00, BuyDate: 03/02/18\n"
            + "\n"
            + "2: StockTickerSymbol: AAPL, ShareOwned: 1.418762, BuyPrice: 176.21, "
            + "Cost: 255.00, Commission: 5.00, BuyDate: 03/02/18\n"
            + "\n"
            + "3: StockTickerSymbol: MSFT, ShareOwned: 2.824221, BuyPrice: 88.52, "
            + "Cost: 255.00, Commission: 5.00, BuyDate: 04/02/18\n"
            + "\n"
            + "4: StockTickerSymbol: AAPL, ShareOwned: 1.499880, BuyPrice: 166.68, "
            + "Cost: 255.00, Commission: 5.00, BuyDate: 04/02/18\n"
            + "\n"
            + "5: StockTickerSymbol: MSFT, ShareOwned: 2.631579, BuyPrice: 95.00, "
            + "Cost: 255.00, Commission: 5.00, BuyDate: 05/01/18\n"
            + "\n"
            + "6: StockTickerSymbol: AAPL, ShareOwned: 1.478415, BuyPrice: 169.10, "
            + "Cost: 255.00, Commission: 5.00, BuyDate: 05/01/18\n"
            + "\n"
            + "7: StockTickerSymbol: MSFT, ShareOwned: 2.529340, BuyPrice: 98.84, "
            + "Cost: 255.00, Commission: 5.00, BuyDate: 05/31/18\n"
            + "\n"
            + "8: StockTickerSymbol: AAPL, ShareOwned: 1.337828, BuyPrice: 186.87, "
            + "Cost: 255.00, Commission: 5.00, BuyDate: 05/31/18\n\n";

    assertEquals(model.examine("retirement"), outExpected);
  }

  /**
   * Test Apply Strategy to a Portfolio with convenient equal weight and no end date.
   */
  @Test
  public void testApplyStrategyEqualWeightNoEndDate() {
    InputStream in = new ByteArrayInputStream(("3 retirement AAPL MSFT # 7 1 Y 500 10 30 "
            + "2018-09-02 N").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, model, view);
    controller.startPortfolio();

    String outExpected = "1: StockTickerSymbol: MSFT, ShareOwned: 2.237938, BuyPrice: 111.71, "
            + "Cost: 255.00, Commission: 5.00, BuyDate: 09/04/18\n"
            + "\n"
            + "2: StockTickerSymbol: AAPL, ShareOwned: 1.094763, BuyPrice: 228.36, "
            + "Cost: 255.00, Commission: 5.00, BuyDate: 09/04/18\n"
            + "\n"
            + "3: StockTickerSymbol: MSFT, ShareOwned: 2.171081, BuyPrice: 115.15, "
            + "Cost: 255.00, Commission: 5.00, BuyDate: 10/02/18\n"
            + "\n"
            + "4: StockTickerSymbol: AAPL, ShareOwned: 1.090370, BuyPrice: 229.28, "
            + "Cost: 255.00, Commission: 5.00, BuyDate: 10/02/18\n"
            + "\n"
            + "5: StockTickerSymbol: MSFT, ShareOwned: 2.360272, BuyPrice: 105.92, "
            + "Cost: 255.00, Commission: 5.00, BuyDate: 11/01/18\n"
            + "\n"
            + "6: StockTickerSymbol: AAPL, ShareOwned: 1.125011, BuyPrice: 222.22, "
            + "Cost: 255.00, Commission: 5.00, BuyDate: 11/01/18\n"
            + "\n";

    assertEquals(model.examine("retirement"), outExpected);
  }
}
