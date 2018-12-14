package portfolio.controller.command;

import java.sql.Date;
import java.util.NoSuchElementException;
import java.util.Scanner;

import portfolio.model.StockModel;
import portfolio.view.IView;

/**
 * This class represents a BuyStock class that extends ICommandImpl abstract class. It has 2 layers:
 * 1. It ask user "Do you want to buy stock by which way?"
 *    " 1. Share 2. Money Amount 3. Back to Menu 4. Quit?? ", user choose one number from it,
 *     leading to the second layer.
 * 2. The second layer handle buy Stock by share or money amount.
 *    User input "PortfolioName StockSymbol Date Share" or "PortfolioName StockSymbol Date Money".
 *    If user input Date in the wrong format, it will prompt and wait for the right Date to input;
 *    If user input Share in the wrong format, it will prompt and wait for the right Share to input;
 *    If user input other properties wrong, like portfolio name not exit or invalid,
 *    if will prompt and back to upper layer and wait for next input;
 *    If user not input anything, it will prompt and back to upper level;
 *    If user input all things correctly, it will show the result, prompt and back to upper level.
 */
public class BuyStock extends ICommandImpl {

  /**
   * Construct a BuyStock object.
   *
   * @param scan    the InputStream input.
   * @param model the StockModel.
   * @param view  the IView.
   */
  public BuyStock(Scanner scan, StockModel model, IView view) {
    this.model = model;
    this.scan = scan;
    this.view = view;
  }

  @Override
  public boolean goCommand() {
    view.update("Do you want to buy stock by: 1. Share 2. Money Amount 3. Back to Menu 4. Quit? "
            + "please enter one number: ");
    while (scan.hasNext()) {
      switch (scan.next()) {
        case "1" :      //By Share
          buyStock(1);
          break;
        case "2" :     //By Money Amount
          buyStock(2);
          break;
        case "3" :    //Back to menu
          view.update("Back to MENU");
          return true;
        case "4" :    //Quit
          return false;
        default:     //number other than 1 2 3,4 go back
          view.update("Invalid input, please try again");
          break;
      }
      view.update("Do you want to buy stock by: 1. Share 2. Money Amount 3. Back to Menu 4. Quit "
              + "please enter one number: ");
    }
    return true;
  }

  /**
   * Represent the third layer behind above layer (Do you want to buy stock by which way?).
   * Handle buy Stock by share or by money amount..
   * User input "PortfolioName StockSymbol Date Share (or Money Amount)".
   * If user input Date in the wrong format, it will prompt and wait for the right Date to input;
   * If user input Share in the wrong format, it will prompt and wait for the right Share to input;
   * If user input other properties wrong, like portfolio name not exit or invalid,
   * it will prompt and back to upper layer and wait for next input;
   * If user not input anything, it will prompt and back to upper level;
   * If user input all things correctly, it will show the result, prompt and back to upper level.
   * Extension Changes : Add commision Fee variable and getCommissionFee function to this function,
   * change buyStock signatures by adding Fee @11.25.
   *
   * @param choice   user's choice, 1 means By Share; 2 means By Money Amount.
   */
  private void buyStock(int choice) {
    try {
      //get PortfolioName
      String portfName = getPortfolioName();

      //get Stock Symbol
      String stockSymbol = getStockSymbol(portfName);

      //get Date
      String date = getDate();

      //get Fee
      String fee = getCommissionFee();  //Add this line

      try {
        if (choice == 1) { //By Share
          //get Share
          String share = getShare();

          model.buyStockByShare(portfName, stockSymbol, Date.valueOf(date),
                  Double.valueOf(share), Double.valueOf(fee));
        } else {          //By Money Amount
          //get Money
          String money = getMoneyAmount();

          model.buyStockByAmount(portfName, stockSymbol, Date.valueOf(date),
                  Double.valueOf(money), Double.valueOf(fee));
        }

        //valid input
        view.update(model.examine(portfName));
        view.update("Buy Successfully. Back to Upper level");
        return;

        //model throw exception, invalid input, go back, re-enter portfolio name
      } catch (IllegalArgumentException e) {
        view.update("Back to upper level. Please try again. " + e.getMessage());
        return;
      }

    } catch (NoSuchElementException e) {
      view.update("Not enough inputs. Back to Upper level. ");
      return;
    }
  }


  /**
   * Fetch Stock data of one Portfolio, and numbering them for users to choose.
   * Return the Stock that user choose.
   *
   * @param portName   the Portfolio's name.
   * @return the the Stock that user choose.
   */
  private String getStockSymbol(String portName) {
    String[] stockNames = model.getStockNames(portName).split(" ");
    String nameWithSequence = addSequence(stockNames);
    view.update("Choose one Stock : " + nameWithSequence);
    String stock = "";

    while (scan.hasNext()) {
      String input = scan.next();
      int num;
      try {
        num = Integer.parseInt(input);
      } catch (NumberFormatException e) { //input is NOT a number
        view.update("Please input a number! Please try again!");
        view.update("Choose one Stock : " + nameWithSequence);
        continue;
      }

      if (num <= stockNames.length) { //valid
        stock = stockNames[num - 1];
        break;

      } else {
        view.update("Please input a Valid number! Please try again!");
        view.update("Choose one Stock : " + nameWithSequence);
        continue;
      }
    }
    return stock;
  }


  /**
   * Return the Share that user input.
   *
   */
  private String getShare() {
    view.update("Please enter the share. Eg: 500.00, must > 0, ");
    String share = scan.next();

    while (!doubleNumCheckNotZero(share)) {
      view.update("Invalid share, must > 0. Try again.");
      share = scan.next();
      continue;
    }
    return share;
  }


}
