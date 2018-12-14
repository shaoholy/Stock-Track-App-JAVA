package portfolio.controller.command;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import portfolio.model.StockModel;
import portfolio.view.IView;

/**
 * This class represents a Create class that extends ICommandImpl abstract class. It has 1 layer: It
 * ask user to input " Portfolio name to create " If user input "q" or "Q", the portfolio will be
 * quitted. If user input invalid name, then it will prompt and go back to menu and wait for the
 * next input. If user input all things correctly, it will show the result, prompt and back to menu.
 * If there is no inputs any more, it will go back to menu.
 */
public class Create extends ICommandImpl {
  /**
   * Construct a Create object.
   *
   * @param scan  the InputStream input.
   * @param model the StockModel.
   * @param view  the IView.
   */
  public Create(Scanner scan, StockModel model, IView view) {
    this.model = model;
    this.scan = scan;
    this.view = view;
  }

  @Override
  public boolean goCommand() {
    view.update("Please enter a Portfolio name to create, name can't be 'q' or 'Q':");
    //while (scan.hasNext()) {

    String portfName = scan.next();

    // quit
    if (quitCheck(portfName)) {
      return false;
    }

    view.update("Create new or retrieve old? 1.New 2.Retrieve");
    String choice = scan.next();
    if (choice.equals("1")) { //new
      createNew(portfName);
      return true;
    } else { //old
      retrieveOld(portfName);
      return true;
    }


    //}
    //view.update("No input anymore. Back to Menu");
    //return true;
  }

  private void createNew(String portfName) {
    //get all the preset Stock symbols in this Portfolio
    Set<String> stockSet = new HashSet<>();
    view.update("Please enter all the Stock symbols(UPPER CASE) in this Portfolio, "
            + "eg: AAPL MSFT # "
            + "press enter after each one. Enter '#' to end");
    while (scan.hasNext()) {
      String input = scan.next();
      if (input.equals("#")) {
        break;
      }
      stockSet.add(input);
    }

    try {
      model.createPortfolio(portfName, stockSet);

      //valid input
      view.update("Create Successfully! Portfolio " + portfName + ": "
              + model.getStockNames(portfName));
      view.update("Back to Menu");
      return;

      //invalid input, go back, re-enter portfolio name
    } catch (IllegalArgumentException e) {
      view.update("Back to Menu. Please try again. Invalid name " + e.getMessage());
      return;
    }
  }

  private void retrieveOld(String portfName) {
    String path = getPath();
    try {
      model.createPortfolio(portfName, path);

      //valid input
      view.update("Retrieve Successfully! Portfolio " + portfName + ": "
              + model.getStockNames(portfName));


      //invalid input, go back, re-enter portfolio name
    } catch (IllegalArgumentException e) {
      view.update("Sorry, " + e.getMessage() + "\nBack to main MENU, please try again!");

    }
  }

}
