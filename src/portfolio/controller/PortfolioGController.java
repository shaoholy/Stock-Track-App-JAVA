package portfolio.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import portfolio.controller.command.ICommandImpl;
import portfolio.model.StockModel;
import portfolio.view.IView;

/**
 * The controller now implements the Features interface. This means each of
 * those functions will give control to the controller.
 * Extension Change: Add PortfolioGController class, @12.06.
 */
public class PortfolioGController extends ICommandImpl implements Features {
  private StockModel model;

  /**
   * Construct a PortfolioGController object.
   *
   * @param model the StockModel.
   */
  public PortfolioGController(StockModel model) {
    super();
    this.model = model;
  }

  /**
   * Give the feature callbacks to the view.
   *
   * @param v the view.
   */
  public void setView(IView v) {
    view = v;
    view.setFeatures(this);
  }

  @Override
  public void viewAll() {
    //viewAllStock();
    view.update("You have those Portfolios: " + this.model.getAllPortfolios());
  }

  @Override
  public void examineOne(String portfName) {
    try {
      view.update(model.examine(portfName));
    } catch (IllegalArgumentException e) {
      view.update("Sorry, " + e.getMessage() + "\nBack to main MENU, please try again!");
    }
  }

  @Override
  public void create(String portfName, Set<String> stockSet) {

    try {
      model.createPortfolio(portfName, stockSet);

      //valid input
      view.update("Create Successfully! Portfolio " + portfName + ": "
              + model.getStockNames(portfName));


      //invalid input, go back, re-enter portfolio name
    } catch (IllegalArgumentException e) {
      view.update("Sorry, " + e.getMessage() + "\nBack to main MENU, please try again!");

    }
  }

  @Override
  public void createFromFile(String portfName, String path) {
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

  @Override
  public void buyStock(String portfName, String stockSymbol, String date,
                              String shareOrMoney, String fee, int choice) {
    try {
      if (choice == 1) { //By Share

        model.buyStockByShare(portfName, stockSymbol, Date.valueOf(date),
                Double.valueOf(shareOrMoney), Double.valueOf(fee));

      } else {          //By Money Amount

        model.buyStockByAmount(portfName, stockSymbol, Date.valueOf(date),
                Double.valueOf(shareOrMoney), Double.valueOf(fee));
      }

      //valid input
      view.update(model.examine(portfName) + "\n" + "Buy Successfully. Back to Upper level");


      //model throw exception, invalid input, go back, re-enter portfolio name
    } catch (IllegalArgumentException e) {
      view.update("Sorry, " + e.getMessage() + "\nBack to main MENU, please try again!");

    }
  }


  @Override
  public void totalCost(String portfName, String date, int choice) {

    try {
      double result;

      if (choice == 1) { //today
        result = model.getPortfolioCost(portfName);
      } else {           //previous day

        result = model.getPortfolioCost(portfName, Date.valueOf(date));
      }

      //valid input
      view.update("Total Cost: " + String.format("%.2f", result)
              + "\n" + "Check total Cost Successfully. Back to upper level");

      return;

      //invalid input, go back, re-enter portfolio name
    } catch (IllegalArgumentException e) {
      view.update("Sorry, " + e.getMessage() + "\nBack to main MENU, please try again!");
      return;
    }

  }

  @Override
  public void totalValue(String portfName, String date, int choice) {
    try {
      double result;

      if (choice == 1) { //today
        result = model.getPortfolioVal(portfName);
      } else {           //previous day

        result = model.getPortfolioVal(portfName, Date.valueOf(date));
      }

      //valid input
      view.update("Total Value: " + String.format("%.2f", result)
              + "\n" + "Check total Value Successfully. Back to upper level");

      return;

      //invalid input, go back, re-enter portfolio name
    } catch (IllegalArgumentException e) {
      view.update("Sorry, " + e.getMessage() + "\nBack to main MENU, please try again!");
      return;
    }
  }

  @Override
  public String[] getAllStocks(String portfName) {
    return model.getStockNames(portfName).split(" ");
  }

  @Override
  public void strategy(String portfolioName, Map<String, Double> weightMap, String startDate,
                       String endDate, String interval, String money, String fee, boolean save) {

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
      view.update("Apply Strategy to Portfolio " + portfolioName + " Successfully. Back to MENU.");

    } catch (IllegalArgumentException e) {
      //e.printStackTrace();
      view.update("Sorry, " + e.getMessage() + "\nBack to main MENU, try again!");
    }
  }

  @Override
  public Map<String, Double> getWeightMapEqual(String[] stockNames) {
    Map<String, Double> weightMap = new HashMap<>();
    double w = 1.0 / stockNames.length;
    for (String str : stockNames) {
      weightMap.put(str, w);
    }
    return weightMap;
  }

  @Override
  public void strategyFromFile(String portfName, String path) {
    try {
      model.highLevelFixedValueTrade(portfName, path);
      view.update("Apply Retrieve Strategy to Portfolio " + portfName
              + "Successfully. Back to MENU.");
    } catch (IllegalArgumentException e) {
      view.update("Sorry, " + e.getMessage() + "\nBack to main MENU, try again!");
    }

  }

  @Override
  public void savePortfolio(String portfolioName) {
    try {
      model.savePortfolio(portfolioName);
      view.update("Save Portfolio " + portfolioName
              + "to program's Root_Folder Successfully. Back to MENU.");
    } catch (IllegalArgumentException e) {
      view.update("Sorry, " + e.getMessage() + "\nBack to main MENU, please try again!");
    }

  }

  @Override
  public List<Double> getPortfolioValList(String portfName, String startDate, String endDate) {
    List<Double> list = new ArrayList<>();
    try {
      list = this.model.getPortfolioValueList(portfName, Date.valueOf(startDate),
              Date.valueOf(endDate));
      view.update("Save Graphical Performance of Portfolio " + portfName
              + " to program's Root_Folder Successfully. Back to MENU.");

    } catch (IllegalArgumentException e) {
      view.update("Sorry, " + e.getMessage() + "\nBack to main MENU, please try again!");
    }
    return list;
  }

  @Override
  public void addStock(String porfName, String stockSymbol) {
    try {
      model.addStockPortfolio(porfName, stockSymbol);
      view.update("Add " +  stockSymbol + " to " + porfName
              + " Successfully." + " Portfolio " + porfName + ": "
              + model.getStockNames(porfName) + "\nBack to MENU.");
    } catch (IllegalArgumentException e) {
      view.update("Sorry, " + e.getMessage() + "\nBack to main MENU, please try again!");
    }

  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }


  @Override
  public boolean goCommand() {
    return false;
  }
}
