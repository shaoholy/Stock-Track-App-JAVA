package portfolio.controller.command;

import java.util.Scanner;

import portfolio.model.StockModel;
import portfolio.view.IView;

/**
 * This class represents a Save class that extends ICommandImpl abstract class. It has 1 layer.
 */
public class Save extends ICommandImpl  {

  /**
   * Construct a Save object.
   *
   * @param scan    the InputStream input.
   * @param model the StockModel.
   * @param view  the IView.
   */
  public Save(Scanner scan, StockModel model, IView view) {
    this.model = model;
    this.scan = scan;
    this.view = view;
  }

  @Override
  public boolean goCommand() {

    String portfName = getPortfolioName();

    // quit
    if (quitCheck(portfName)) {
      return false;
    }

    try {
      model.savePortfolio(portfName);

      //valid input
      view.update("Save Portfolio Successfully! Back to MENU.");
      return true;

      //invalid input, go back, re-enter portfolio name
    } catch (IllegalArgumentException e) {
      view.update("Sorry, " + e.getMessage() + "\nBack to main MENU, please try again!");
      return true;
    }
  }
}
