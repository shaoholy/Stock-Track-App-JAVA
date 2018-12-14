package portfolio.controller.command;

import java.sql.Date;
import java.util.Scanner;

import portfolio.model.StockModel;
import portfolio.view.IView;

/**
 * This class represents a AllValue class that extends ICommandImpl abstract class. It has 2 layers:
 * 1. It ask user "Which day do you want to see total value?"
 *    " 1. Today 2. Previous day, 3. Back to Menu 4. Quit? ", user choose one number from it,
 *     leading to the second layer.
 * 2. The second layer handle show previous day's total value or today's.
 *    User input "PortfolioName Date" or only "PortfolioName".
 *    If user input Date in the wrong format, it will prompt and wait for the right Date to input;
 *    If user input other properties wrong, like portfolio name not exit or invalid,
 *    it will prompt and back to upper layer and wait for next input;
 *    If user not input anything, it will prompt and back to upper level;
 *    If user input all things correctly, it will show the result, prompt and back to upper level.
 */
public class AllValue extends ICommandImpl {

  /**
   * Construct a AllValue object.
   *
   * @param scan    the InputStream input.
   * @param model the StockModel.
   * @param view  the IView.
   */
  public AllValue(Scanner scan, StockModel model, IView view) {
    this.model = model;
    this.scan = scan;
    this.view = view;
  }

  @Override
  public boolean goCommand() {
    view.update("Which day do you want to see total value : 1. Today "
            + "2. Previous day, 3. Back to Menu 4. Quit"
            + " Please enter one number: ");
    while (scan.hasNext()) {

      switch (scan.next()) {
        case "1" :    //today
          showValue(1);
          break;
        case "2" :    //previous day
          showValue(2);
          break;
        case "3" :     //Back to menu
          return true;
        case "4" :     //Quit
          return false;
        default:
          view.update("Invalid input, please try again");
          break;   //number other than 1 2 3, go back
      }
      view.update("Which day do you want to see total value : 1. Today "
              + "2. Previous day, 3. Back to Menu 4. Quit"
              + " Please enter one number: ");
    }
    return true;
  }


  /**
   * Represent the third layer behind above layer (Which day do you want to see total value?).
   * Handle show previous day or today's total value.
   * User input "PortfolioName Date" or "PortfolioName".
   * If user input Date in the wrong format, it will prompt and wait for the right Date to input;
   * If user input other properties wrong, like portfolio name not exit or invalid,
   * it will prompt and back to upper layer and wait for next input;
   * If user not input anything, it will prompt and back to upper level;
   * If user input all things correctly, it will show the result, prompt and back to upper level.
   *
   * @param choice   user's choice, 1 means today; 2 means previous day.
   */
  private void showValue(int choice) {
    //get PortfolioName
    String portfName = getPortfolioName();

    try {
      double result;
      if (choice == 1) { //today
        result = model.getPortfolioVal(portfName);
      } else {           //previous day
        String date = getDate();
        result = model.getPortfolioVal(portfName, Date.valueOf(date));
      }

      //valid input
      view.update("Total Value: " + String.format("%.2f", result));
      view.update("Check total Value Successfully. Back to upper level");
      return;

      //invalid input, go back, re-enter portfolio name
    } catch (IllegalArgumentException e) {
      view.update("Back to upper level. Please try again. " + e.getMessage());
      return;
    }
  }

}
