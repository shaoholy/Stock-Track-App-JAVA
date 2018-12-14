package portfolio.controller;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import portfolio.controller.command.AddStock;
import portfolio.controller.command.AllCost;
import portfolio.controller.command.AllValue;
import portfolio.controller.command.BuyStock;
import portfolio.controller.command.Create;
import portfolio.controller.command.ExamOne;
import portfolio.controller.command.ICommand;
import portfolio.controller.command.Save;
import portfolio.controller.command.Strategy;
import portfolio.controller.command.ViewAll;
import portfolio.model.MenuType;
import portfolio.model.StockModel;
import portfolio.view.IView;

/**
 * This class represents a PortfoliosController that implements IPortfoliosController.
 */
public class PortfoliosController implements IPortfoliosController {
  private final InputStream in;
  protected StockModel model;
  protected IView view;

  /**
   * Construct a PortfoliosController object.
   *
   * @param rd    the InputStream input.
   * @param model the StockModel.
   * @param view  the IView.
   * @throws IllegalArgumentException if input is invalid.
   */
  public PortfoliosController(InputStream rd, StockModel model, IView view)
          throws IllegalArgumentException {
    //    if (rd == null || model == null || view == null) {
    //      throw new IllegalArgumentException("Input can't be NULL!!");
    //    }
    this.in = rd;
    this.model = model;
    this.view = view;
  }

  /**
   * Extension Changes : Add else if(STRATEGY), @11.25.
   */
  @Override
  public void startPortfolio() throws IllegalStateException {
    Scanner scan;
    try {
      scan = new Scanner(this.in);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Can't read from input stream!!");
    }
    ICommand cmd = null;

    view.update(welcomMessage());
    while (scan.hasNext()) {
      String input = getMenuItem(scan.next());

      try {
        if (input.equals(MenuType.VIEWALL.getMessage())) { //1. View all Stock Portfolios
          cmd = new ViewAll(scan, model, view);
        } else if (input.equals(MenuType.EXAMONE.getMessage())) { //2. Examine one Stock Portfolio
          cmd = new ExamOne(scan, model, view);
        } else if (input.equals(MenuType.CREATE.getMessage())) { //3. Create Stock Portfolio
          cmd = new Create(scan, model, view);
        } else if (input.equals(MenuType.BUYSTOCK.getMessage())) { //4. Buy Stock
          cmd = new BuyStock(scan, model, view);
        } else if (input.equals(MenuType.TOTALCOST.getMessage())) { //5. Show total Cost Basis
          cmd = new AllCost(scan, model, view);
        } else if (input.equals(MenuType.TOTALVALUE.getMessage())) { //6. Show total Value
          cmd = new AllValue(scan, model, view);
        } else if (input.equals(MenuType.STRATEGY.getMessage())) { //Add line.7. Apply Strategy
          cmd = new Strategy(scan, model, view);
        } else if (input.equals(MenuType.SAVE.getMessage())) { //Add line.8. Save
          cmd = new Save(scan, model, view);
        } else if (input.equals(MenuType.ADD.getMessage())) { //Add line.9. Add Stock
          cmd = new AddStock(scan, model, view);
        } else if (input.equals(MenuType.QUIT.getMessage())) { //Quit
          quit();
          return;
        }

        if (!cmd.goCommand()) { //check if user QUIT in the middle of process
          quit();
          return;
        }
        view.update(welcomMessage());
      } catch (NoSuchElementException e) {
        view.update("Invalid input, " + e.getLocalizedMessage()
                + "back to main menu, please try again!");
      } catch (IllegalArgumentException e) {
        view.update("Invalid input, " + e.getLocalizedMessage()
                + "back to main menu, please try again!");
      }
    }
    quit();  //no more input, QUIT Portfolio
  }

  /**
   * Quit the Stock Portfolio. Return true if game over; false otherwise. (either by end of input or
   * by user quitting).
   */
  private void quit() {
    view.update("Quit Portfolio. Thank you!");
  }

  /**
   * Return the message of the MenuType.
   * Extension Changes : Add case 7 : STRATEGY, @11.25.
   *
   * @param input the String representation of menu.
   * @return the message of the MenuType.
   */
  private String getMenuItem(String input) {
    String result;
    switch (input) {
      case "1":
        result = MenuType.VIEWALL.getMessage();
        break;
      case "2":
        result = MenuType.EXAMONE.getMessage();
        break;
      case "3":
        result = MenuType.CREATE.getMessage();
        break;
      case "4":
        result = MenuType.BUYSTOCK.getMessage();
        break;
      case "5":
        result = MenuType.TOTALCOST.getMessage();
        break;
      case "6":
        result = MenuType.TOTALVALUE.getMessage();
        break;
      case "7":
        result = MenuType.STRATEGY.getMessage(); //Add this line
        break;
      case "8":
        result = MenuType.SAVE.getMessage(); //Add this line
        break;
      case "9":
        result = MenuType.ADD.getMessage(); //Add this line
        break;
      case "q":
      case "Q":
        result = MenuType.QUIT.getMessage();
        break;
      default:
        result = "None";
        break;
    }
    return result;
  }

  /**
   * Form the welcome message.
   *
   * @return the welcome message.
   */
  private String welcomMessage() {
    StringBuilder message = new StringBuilder();
    message.append("Welcome to Stock Portfolio! ");
    message.append("Please choose one number : (Enter 'q' or 'Q' to quit)\n");
    message.append(MenuType.VIEWALL.getMessage() + "\n");
    message.append(MenuType.EXAMONE.getMessage() + "\n");
    message.append(MenuType.CREATE.getMessage() + "\n");
    message.append(MenuType.BUYSTOCK.getMessage() + "\n");
    message.append(MenuType.TOTALCOST.getMessage() + "\n");
    message.append(MenuType.TOTALVALUE.getMessage() + "\n");
    message.append(MenuType.STRATEGY.getMessage() + "\n");
    message.append(MenuType.SAVE.getMessage() + "\n");

    return message.toString();
  }
}
