import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import portfolio.controller.IPortfoliosController;
import portfolio.controller.PortfoliosController;
import portfolio.model.MockModel;
import portfolio.model.StockModel;
import portfolio.view.PortfolioView;


import static org.junit.Assert.assertEquals;

/**
 * Test if controller works correctly with correct input, transmitting the right input to model.
 * Test if q quits game as intended.
 */
public class IPortfoliosControllerTest {
  StockModel mockModel;
  IPortfoliosController controller;
  StringBuilder log;
  PortfolioView view;
  StringBuilder out;

  /**
   * Create objects.
   */
  @Before
  public void setUp() {
    log = new StringBuilder();
    mockModel = new MockModel(log);
    out = new StringBuilder();
  }


  /**
   * Test View All function.
   */
  @Test
  public void testViewAll() {
    InputStream in = new ByteArrayInputStream("1".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, mockModel, view);

    controller.startPortfolio();

    String outExpected = "getAllPortfolios";
    assertEquals(mockModel.getAllPortfolios(), outExpected);

  }

  /**
   * Test Examine one portfolio function.
   */
  @Test
  public void testExamine() {
    InputStream in = new ByteArrayInputStream("3 song AAPL MSFT # 2 1".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, mockModel, view);

    controller.startPortfolio();

    String outExpected = "examine";
    String logExpected = "name: songStock Symbols: [AAAA, BBBB]\n" +
            "portfolioName: getAllPortfolios\n";
    assertEquals(log.toString(), logExpected);
    assertEquals(mockModel.examine("song"), outExpected);

  }

  /**
   * Test quit during the process of Examine.
   */
  @Test
  public void testExamineQuit() {
    InputStream in = new ByteArrayInputStream("q".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, mockModel, view);

    controller.startPortfolio();

    String outExpected = "Welcome to Stock Portfolio! Please choose one number : "
            + "(Enter 'q' or 'Q' to quit)\n"
            + "1. View all Stock Portfolios\n"
            + "2. Examine one Stock Portfolio\n"
            + "3. Create Stock Portfolio\n"
            + "4. Buy Stock\n"
            +  "5. Show total Cost Basis\n"
            +  "6. Show total Value\n"
            +  "7. Apply Strategy\n"
            +  "\n"
            + "Quit Portfolio. Thank you!\n";
    String logExpected = "";
    assertEquals(log.toString(), logExpected);
    assertEquals(new String(bytes.toByteArray()), outExpected);
  }



  /**
   * Test Create Portfolio.
   */
  @Test
  public void testCreatePortfolio() {
    InputStream in = new ByteArrayInputStream("3 song AAPL MSFT #".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, mockModel, view);

    controller.startPortfolio();

    String outExpected = "examine";

    String logExpected = "name: songStock Symbols: [AAAA, BBBB]\n";
    assertEquals(log.toString(), logExpected);
    assertEquals(mockModel.examine("song"), outExpected);

  }

  /**
   * Test Buy Stock.
   */
  @Test
  public void testBuyStock() {
    InputStream in = new ByteArrayInputStream("3 song AAPL MSFT # 4 2 1 1 2018-03-12 10 100 4"
            .getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, mockModel, view);

    controller.startPortfolio();

    String outExpected = "examine";

    String logExpected = "name: songStock Symbols: [AAAA, BBBB]\n"
            + "portfolioName: getAllPortfolios stockSymbol: All date: 2018-03-12 Money: "
            + "100.0Fee: 10.0\n"
            + "portfolioName: getAllPortfolios\n";
    assertEquals(log.toString(), logExpected);
    assertEquals(mockModel.examine("song"), outExpected);

  }


  /**
   * Test Total costs at certain date.
   */
  @Test
  public void testTotalCost() {
    InputStream in = new ByteArrayInputStream(("3 song AAPL MSFT # 4 2 1 1 2018-03-12 10 100 "
            + "3 5 2 1 2018-03-13").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, mockModel, view);

    controller.startPortfolio();

    double outExpected = 1111.0;

    String logExpected = "name: songStock Symbols: [AAAA, BBBB]\n"
            + "portfolioName: getAllPortfolios stockSymbol: All date: 2018-03-12 "
            + "Money: 100.0Fee: 10.0\n"
            + "portfolioName: getAllPortfolios\n"
            + "portfolioName: getAllPortfolios\n";
    assertEquals(log.toString(), logExpected);
    assertEquals(mockModel.getPortfolioCost("song"), outExpected, 0.1);

  }


  /**
   * Test Total costs with invalid input.
   */
  @Test
  public void testTotalCostInvalidInput() {
    InputStream in = new ByteArrayInputStream(("3 song AAPL MSFT # 4 2 1 1 2018-03-12 10 500 "
            + "3 6 2 1 2018-04-01").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes);
    view = new PortfolioView(out);
    controller = new PortfoliosController(in, mockModel, view);

    controller.startPortfolio();

    double outExpected = 1111.0;

    String logExpected = "name: songStock Symbols: [AAAA, BBBB]\n"
            + "portfolioName: getAllPortfolios stockSymbol: All date: 2018-03-12 "
            + "Money: 500.0Fee: 10.0\n"
            + "portfolioName: getAllPortfolios\n"
            + "portfolioName: getAllPortfoliosdate: 2018-04-01\n";
    assertEquals(log.toString(), logExpected);
    assertEquals(mockModel.getPortfolioCost("song"), outExpected, 0.1);

  }

  //  /**
  //   * Test combine operations.
  //   */
  //  @Test
  //  public void testCombine() {
  //    InputStream in = new ByteArrayInputStream("1 2 song".getBytes());
  //    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
  //    PrintStream out = new PrintStream(bytes);
  //    view = new PortfolioView(out);
  //    controller = new PortfoliosController(in, mockModel, view);
  //
  //    controller.startPortfolio();
  //
  //    String outExpected = "Welcome to Stock Portfolio! Please choose one number from below: \n" +
  //            "1. View all Stock Portfolios\n" +
  //            "2. Examine one Stock Portfolio\n" +
  //            "3. Create Stock Portfolio\n" +
  //            "4. Buy Stock\n" +
  //            "5. Show total Cost Basis\n" +
  //            "6. Show total Value\n" +
  //            "\n" +
  //            "getAllPortfolios\n" +
  //            "Welcome to Stock Portfolio! Please choose one number from below: \n" +
  //            "1. View all Stock Portfolios\n" +
  //            "2. Examine one Stock Portfolio\n" +
  //            "3. Create Stock Portfolio\n" +
  //            "4. Buy Stock\n" +
  //            "5. Show total Cost Basis\n" +
  //            "6. Show total Value\n" +
  //            "\n" +
  //            "Please enter the Portfolio name you want to exam, name can't be 'q' or 'Q':\n" +
  //            "examine\n" +
  //            "Exam Successfully! Back to MENU.\n" +
  //            "Welcome to Stock Portfolio! Please choose one number from below: \n" +
  //            "1. View all Stock Portfolios\n" +
  //            "2. Examine one Stock Portfolio\n" +
  //            "3. Create Stock Portfolio\n" +
  //            "4. Buy Stock\n" +
  //            "5. Show total Cost Basis\n" +
  //            "6. Show total Value\n" +
  //            "\n" +
  //            "Quit Portfolio. Thank you!\n";
  //
  //    String logExpected = "portfolioName: song\n";
  //    assertEquals(log.toString(), logExpected);
  //    assertEquals(new String(bytes.toByteArray()), outExpected);
  //
  //  }




}