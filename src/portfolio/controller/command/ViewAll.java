package portfolio.controller.command;

import java.util.Scanner;

import portfolio.model.StockModel;
import portfolio.view.IView;

/**
 * This class represents a ViewAll class that extends ICommandImpl abstract class. It has 1 layer:
 * It will show user all the profolios she has.
 */
public class ViewAll extends ICommandImpl {

  /**
   * Construct a ViewAll object.
   *
   * @param scan    the InputStream input.
   * @param model the StockModel.
   * @param view  the IView.
   */
  public ViewAll(Scanner scan, StockModel model, IView view) {
    this.model = model;
    this.scan = scan;
    this.view = view;
  }

  @Override
  public boolean goCommand() {
    view.update("You have those Portfolios: " + model.getAllPortfolios());
    //viewAllStock();
    return true;
  }
}
