package portfolio.controller.command;

import java.util.Scanner;

import portfolio.model.StockModel;
import portfolio.view.IView;

/**
 * This class represents a ExamOne class that extends ICommandImpl abstract class. It has 1 layer:
 * It ask user to input " Portfolio name you want to exam "
 * If user input "q" or "Q", the portfolio will be quitted.
 * If user input invalid name, then it will prompt and go back to menu and wait for the next input.
 * If user input all things correctly, it will show the result, prompt and back to menu.
 * If there is no inputs any more, it will go back to menu.
 */
public class ExamOne extends ICommandImpl {

  /**
   * Construct a ExamOne object.
   *
   * @param scan    the InputStream input.
   * @param model the StockModel.
   * @param view  the IView.
   */
  public ExamOne(Scanner scan, StockModel model, IView view) {
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
      String result = model.examine(portfName);

      //valid input
      view.update(portfName + ":" + "\n" + result);
      view.update("Exam Successfully! Back to MENU.");
      return true;

      //invalid input, go back, re-enter portfolio name
    } catch (IllegalArgumentException e) {
      view.update("Back to MENU. Please Try Again. Invalid name " + e.getMessage());
      return true;
    }
  }
}
