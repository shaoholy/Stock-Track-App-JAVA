package portfolio.controller;

/**
 * Interface for the Portfolio controller. An implementation will work with
 * the StockModel interface and IView interface to provide a Stock Portfolio.
 * It takes user inputs, tells model what to do and view what to display.
 * It does not care how model implements functionality,
 * does not care how the screen is laid out to display results.
 */
public interface IPortfoliosController {


  /**
   * Open a new Stock Portfolio.
   * This method returns only when the Stock Portfolio is quited.
   * (either by end of input or by user quitting)
   *
   * @throws IllegalStateException    if the controller is unable to read input
   *                                  or transmit output
   */
  void startPortfolio() throws IllegalStateException;
}
