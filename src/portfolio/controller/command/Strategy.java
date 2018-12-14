package portfolio.controller.command;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import portfolio.model.StockModel;
import portfolio.view.IView;

/**
 * This class represents a Strategy class that extends ICommandImpl abstract class.
 * It ask user to input "PortfolioName, weight(equal weights or not), money, commission fee,
 * interval, start and end date(or no end date)".
 * If user input in the wrong format, it will prompt and wait for the right input;
 * If user input all things correctly, it will show the result, prompt and back to MENU.
 * Extension Changes : Add Strategy class, @11.25.
 *
 */
public class Strategy extends ICommandImpl {

  /**
   * Construct a Strategy object.
   *
   * @param scan    the InputStream input.
   * @param model the StockModel.
   * @param view  the IView.
   */
  public Strategy(Scanner scan, StockModel model, IView view) {
    this.model = model;
    this.scan = scan;
    this.view = view;
  }

  @Override
  public boolean goCommand() {
    //get PortfolioName
    view.update("Please enter a Portfolio name, name can't be 'q' or 'Q':");
    String portfName = scan.next();

    view.update("Create new Strategy or retrieve old Strategy? 1.New 2.Retrieve");
    String choice = scan.next();
    if (choice.equals("1")) { //new
      createNew(portfName);
      return true;
    } else {   //old
      retrieveOld(portfName);
      return true;
    }

  }


  private void createNew(String portfolioName) {
    //get weightMap, (equal weight or customize weight)
    String[] stockNames = model.getStockNames(portfolioName).trim().split(" ");
    Map<String, Double> weightMap = getWeightMap(stockNames);

    //get money amount
    String money = getMoneyAmount();

    //get commission fee
    String fee = getCommissionFee();

    //get interval
    String interval = getInterval();

    //get Start Date
    String startDate = getStartDate();

    //get endDate
    String endDate = getEndDate();

    //Save
    boolean save = isSave();

    try {

      if (endDate.equals("N")) {
        model.highLevelFixedValueTrade(portfolioName, weightMap,
                Date.valueOf(startDate), Integer.valueOf(interval),
                Double.valueOf(money), Double.valueOf(fee), save);
      } else {
        model.highLevelFixedValueTrade(portfolioName, weightMap,
                Date.valueOf(startDate), Date.valueOf(endDate), Integer.valueOf(interval),
                Double.valueOf(money), Double.valueOf(fee), save);
      }
      view.update("Apply Strategy to Portfolio " + portfolioName + "Successfully. Back to MENU.");

    } catch (IllegalArgumentException e) {
      view.update("Invalid input : " + e.getMessage() + ", back to main MENU, try again!");
    }
  }

  private void retrieveOld(String portfName) {
    String path = getPath();
    try {
      model.highLevelFixedValueTrade(portfName, path);

      //valid input
      view.update("Retrieve Successfully! Portfolio " + portfName + ": "
              + model.getStockNames(portfName));


      //invalid input, go back, re-enter portfolio name
    } catch (IllegalArgumentException e) {
      view.update("Sorry, " + e.getMessage() + "\nBack to main MENU, please try again!");

    }
  }

  /**
   * Return the Weight Map that user input. Both equal weight and not equal weight.
   * If user choose equal weight, it will automatically apply equal weight to each stock;
   * If user choose NOT equal weight, it will ask user to input weight for each stack
   *  if user input 0 weight or the sum of weights != 1.0,
   *  it will prompt and ask user to input again.
   *
   * @param stockNames   the Stock array.
   * @return the Weight Map that user choose.
   */
  private Map<String, Double> getWeightMap(String[] stockNames) {
    Map<String, Double> weightMap = new LinkedHashMap<>();

    //get weightMap, (equal weight or customize weight)
    view.update("Do you want to invest using Equal Weights for all stocks? Enter Y or N");

    while (scan.hasNext()) {
      String choice = scan.next();
      if (choice.equals("Y")) {  //Equal weight
        getWeightMapEqual(weightMap, stockNames);
        return weightMap;

      } else if (choice.equals("N")) {  //Not Equal weight
        view.update("Please specify Weight Portion for each stock. Eg: 0.3 0.5 0.2, "
                + "the sum must be equal to 1");
        if (!getWeightMapNotEqual(weightMap, stockNames, scan, view)) {
          view.update("Do you want to invest using Equal Weights for all stocks? Enter Y or N");
          continue;
        }
        return weightMap;
      } else {
        view.update("Wrong input, please input Y or N");
      }
    }
    return weightMap;
  }

  /**
   * Return the Interval days that user input.
   * If user input invalid interval(not integer or < 1), it will prompt and ask user to input again.
   *
   * @return the Interval days that user input.
   */
  private String getInterval() {

    view.update("Please enter the Interval days (every how many days?). Eg: '30', "
            + "must be positive Integer >= 1, ");

    String interval = scan.next();
    while (!intNumCheck(interval)) {
      view.update("Invalid money, must be in the number format eg: \"30\", "
              + "and must be >= 1. Try again.");
      interval = scan.next();
      continue;
    }
    return interval;
  }



  /**
   * Return the End Date that user input.
   * If user input invalid Date(wrong Date format), it will prompt and ask user to input again.
   *
   * @return the End Date that user input.
   */
  private String getEndDate() {
    view.update("Do you want to specify the end date? Y/N ");
    String endDate = "";

    while (scan.hasNext()) {
      String choice = scan.next();
      if (choice.equals("N")) {
        return choice;

      } else if (choice.equals("Y")) {
        view.update("Please enter a End Date. Eg: 2018-03-04, "
                + "must be in the format 'yyyy-[m]m-[d]d'");

        endDate = scan.next();
        while (!dateCheck(endDate)) {
          view.update("Invalid date, must be in the format \"yyyy-[m]m-[d]d\" (eg: 2018-03-04). "
                  + "Try again.");
          endDate = scan.next();
          continue;
        }
        return endDate;

      } else {
        view.update("Invalid input, must be Y or N. Please try again!");
      }
    }
    return endDate;
  }


  /**
   * Put equal weight into map.
   *
   * @param weightMap    the weight map.
   * @param stockNames   the stock array.
   */
  private void getWeightMapEqual(Map<String, Double> weightMap, String[] stockNames) {
    double weight = 1.0 / stockNames.length;
    for (String str : stockNames) {
      weightMap.put(str, weight);
    }
  }


  /**
   * Put weight that user input into map, return true if weight is valid; false otherwise.
   *
   * @param weightMap    the weight map.
   * @param stockNames   the stock array.
   * @return true if weight is valid; false otherwise.
   */
  private boolean getWeightMapNotEqual(Map<String, Double> weightMap, String[] stockNames,
                                     Scanner scan, IView view) {
    double[] weight = new double[stockNames.length];
    double sum = 0.0;

    for (int i = 0; i < stockNames.length; i++) {
      view.update(stockNames[i] + ":");

      try {
        weight[i] = Double.valueOf(scan.next());
        if (weight[i] == 0.0) {
          view.update("Weight can't be 0! Try again!");
          return false;
        }
        weightMap.put(stockNames[i], weight[i]);
      } catch (NumberFormatException e) {  //input is not number
        view.update("Please input number! Try again!");
        return false;
      }
      sum += weight[i];
    }

    if (sum != 1.0) { //SUM is not 1
      view.update("Sum must be 1! Try Again!");
      return false;
    }
    return true;
  }

  private boolean isSave() {
    view.update("Want to save the strategy to JSON file? Y/N");
    String choice = scan.next();
    return choice.equals("Y");
  }

}
