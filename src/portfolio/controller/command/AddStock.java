package portfolio.controller.command;

import java.util.Scanner;

import portfolio.model.StockModel;
import portfolio.view.IView;

/**
 * This class represents a AddStock class that extends ICommandImpl abstract class. It has 1 layer.
 */
public class AddStock extends ICommandImpl {

  /**
   * Construct a AddStock object.
   *
   * @param scan  the InputStream input.
   * @param model the StockModel.
   * @param view  the IView.
   */
  public AddStock(Scanner scan, StockModel model, IView view) {
    this.model = model;
    this.scan = scan;
    this.view = view;
  }

  @Override
  public boolean goCommand() {
    String porfName = getPortfolioName();

    view.update("Please enter the Stock Symbol you want to add, Eg: MSFT");
    String stockSymbol = scan.next();

    try {
      model.addStockPortfolio(porfName, stockSymbol);
      view.update("Add " +  stockSymbol + " to " + porfName
              + " Successfully." + " Portfolio " + porfName + ": "
              + model.getStockNames(porfName) + "\nBack to MENU.");
    } catch (IllegalArgumentException e) {
      view.update("Sorry, " + e.getMessage() + "\nBack to main MENU, please try again!");
    }

    return true;
  }
}
