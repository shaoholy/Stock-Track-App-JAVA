package portfolio.view;

import java.io.PrintStream;

import portfolio.controller.Features;


/**
 * This class represents a PortfolioView that implements IView interface.
 */
public class PortfolioView implements IView {
  private final PrintStream out;

  /**
   * Construct a PortfolioView object.
   *
   * @param ap the PrintStream output.
   * @throws IllegalArgumentException if PrintStream is invalid.
   */
  public PortfolioView(PrintStream ap) throws IllegalArgumentException {
    if (ap == null) {
      throw new IllegalArgumentException("Input can't be NULL!!");
    }
    this.out = ap;
  }



  @Override
  public void update(String message) {
    appendMessage(message);
  }



  @Override
  public void setFeatures(Features f) {
    //suppress
  }


  /**
   * Transmit message to PrintStream out.
   *
   * @param message the message to be transmitted.
   */
  private void appendMessage(String message) throws IllegalStateException {
    this.out.append(message);
    this.out.append("\n");
  }





}
