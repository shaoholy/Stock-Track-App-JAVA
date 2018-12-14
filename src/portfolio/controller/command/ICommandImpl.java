package portfolio.controller.command;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import portfolio.model.StockModel;
import portfolio.view.IView;

/**
 * This class represents an ABSTRACT class that implements ICommand interface.
 */
public abstract class ICommandImpl implements ICommand {
  private static final Set<String> QUIT = new HashSet<>(Arrays.asList("Q", "q"));
  protected Scanner scan;
  protected StockModel model;
  protected IView view;

  //  /**
  //   * Construct a ICommandImpl object.
  //   *
  //   * @param scan    the InputStream input.
  //   * @param model the StockModel.
  //   * @param view  the IView.
  //   */
  //  public ICommandImpl(Scanner scan, StockModel model, IView view) {
  //    this.model = model;
  //    this.scan = scan;
  //    this.view = view;
  //  }


  /**
   * Check if user input 'q' or 'Q' to quick game. Return true if quited; false otherwise.
   *
   * @param input the user input.
   * @return  true if quited; false otherwise.
   */
  protected boolean quitCheck(String input) {
    return QUIT.contains(input);
  }

  /**
   * Check if user input valid Date.
   * Valid Date must be in the format "yyyy-[m]m-[d]d\, eg: 2018-05-03.
   * Return true if valid; false otherwise.
   *
   * @param date the user input date.
   * @return  true if valid; false otherwise.
   */
  protected boolean dateCheck(String date) {
    try {
      Date dataformat = Date.valueOf(date);
      return true;
    } catch (IllegalArgumentException e) {
      return false;
    }
  }

  /**
   * Check if user input valid double number.
   * Valid number must can be successfully transformed to double, and must be bigger than 0.
   * Return true if valid; false otherwise.
   *
   * @param money the user input number.
   * @return  true if valid; false otherwise.
   */
  protected boolean doubleNumCheckNotZero(String money) {
    try {
      double num = Double.valueOf(money);
      return num > 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Check if user input valid double number.
   * Valid number must can be successfully transformed to double,
   * and must be bigger than or equal to 0.
   * Return true if valid; false otherwise.
   *
   * @param money the user input number.
   * @return  true if valid; false otherwise.
   */
  protected boolean doubleNumCheckZero(String money) {
    try {
      double num = Double.valueOf(money);
      return num >= 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Check if user input valid Integer number.
   * Valid number must can be successfully transformed to double, and must be bigger than 0.
   * Return true if valid; false otherwise.
   *
   * @param num the user input number.
   * @return  true if valid; false otherwise.
   */
  protected boolean intNumCheck(String num) {
    try {
      double intNum = Integer.valueOf(num);
      return intNum > 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Numbering and return the Portfolio sequence.
   *
   * @param portNames   the Portfolio Array.
   * @return the the Portfolio sequence.
   */
  protected String addSequence(String[] portNames) {
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < portNames.length; i++) {
      res.append(i + 1).append(". ").append(portNames[i]).append(" ");
    }
    return res.toString();
  }


  /**
   * Return the Commission Fee that user input.
   * If user input invalid Fee(not number or < 0), it will prompt and ask user to input again.
   * Extension Changes : Add getCommissionFee function, @11.25.
   *
   * @return the Commission Fee that user input.
   */
  protected String getCommissionFee() {
    String fee;
    view.update("Is there a commission fee? Y/N");
    String choice = scan.next();
    if (choice.equals("Y")) {
      view.update("Please enter the total commission fee per trade. "
              + "Eg: '10.00', must be in the number format , and must >= 0.");

      fee = scan.next();
      while (!doubleNumCheckZero(fee)) {
        view.update("Invalid money, must be in the number format eg: \"10.00\", "
                + "and must >= 0. Try again.");
        fee = scan.next();
        continue;
      }
    } else {
      fee = "0.0";
    }

    return fee;
  }


  /**
   * Return the Money Amount that user input.
   * If user input invalid Money(not number or <= 0), it will prompt and ask user to input again.
   *
   * @return the Money Amount that user input.
   */
  protected String getMoneyAmount() {
    view.update("Please enter the money you want to invest. "
            + "Eg: '500.00', must be in the number format , and must > 0.");

    String money = scan.next();
    while (!doubleNumCheckNotZero(money)) {
      view.update("Invalid money, must be in the number format eg: \"500.00\", "
              + "and must > 0. Try again.");
      money = scan.next();
      continue;
    }
    return money;
  }


  /**
   * Fetch Portfolio data, and numbering them for users to choose.
   * Return the Portfolio that user choose.
   *
   * @return the the Portfolio that user choose.
   */
  protected String getPortfolioName() {
    String[] portNames = model.getAllPortfolios().split(" ");
    String nameWithSequence = addSequence(portNames);
    view.update("Choose one Portfolio: " + nameWithSequence);
    String portfName = "";

    while (scan.hasNext()) {
      String input = scan.next();

      int num;
      try {
        num = Integer.parseInt(input);
      } catch (NumberFormatException e) { //input is NOT a number
        view.update("Not a number! Please try again!");
        view.update("Choose one Portfolio: " + nameWithSequence);
        continue;
      }

      if (num <= portNames.length) { //valid
        portfName = portNames[num - 1];
        break;

      } else {
        view.update("Invalid number! Please try again!");
        view.update("Choose one Portfolio: " + nameWithSequence);
        continue;
      }
    }
    return portfName;
  }


  /**
   * Return the Date that user input.
   * If user input invalid Date(wrong Date format), it will prompt and ask user to input again.
   *
   * @return the Date that user input.
   */
  protected String getDate() {
    view.update("Please enter a date. Eg: 2018-11-02, must be in the format 'yyyy-[m]m-[d]d'");
    String date = scan.next();

    while (!dateCheck(date)) {
      view.update("Invalid date, must be in the format \"yyyy-[m]m-[d]d\" (eg: 2018-11-02). "
              + "Try again.");
      date = scan.next();
      continue;
    }
    return date;
  }

  protected String getPath() {
    view.update("Enter the File path. Eg: ./song.json, must be ended with.json");
    String path = scan.next();
    return path;
  }

  /**
   * Return the Start Date that user input.
   * If user input invalid Date(wrong Date format), it will prompt and ask user to input again.
   *
   * @return the Start Date that user input.
   */
  protected String getStartDate() {

    view.update("Please enter a Start Date. Eg: 2018-01-02, "
            + "must be in the format 'yyyy-[m]m-[d]d'");

    String startDate = scan.next();
    while (!dateCheck(startDate)) {
      view.update("Invalid date, must be in the format \"yyyy-[m]m-[d]d\" (eg: 2018-01-02). "
              + "Try again.");
      startDate = scan.next();
      continue;
    }
    return startDate;
  }

  //  protected void viewAllStock() {
  //    view.update("You have those Portfolios: " + model.getAllPortfolios());
  //  }

}
