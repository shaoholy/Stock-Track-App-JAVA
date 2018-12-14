package portfolio;

//import portfolio.view.PortfolioView;
//import portfolio.controller.PortfoliosController;
//import portfolio.controller.IPortfoliosController;
//import java.util.Scanner;
//import java.io.InputStream;

import portfolio.controller.PortfolioGController;
import portfolio.model.StockModel;
import portfolio.model.StockModelImpl;
import portfolio.view.IView;
import portfolio.view.JFrameView;



/**
 * This class represent the main class of Portfolio.
 */
public class PortfolioRunner {

  /**
   * This is the main function.
   */
  public static void main(String[] args) {

    StockModel model = new StockModelImpl();

    //Console-Based

    //InputStream in = System.in;
    //IView view = new PortfolioView(System.out);
    //IPortfoliosController controller = new PortfoliosController(in, model, view);
    //controller.startPortfolio();





    //GUI

    PortfolioGController controller = new PortfolioGController(model);
    IView view = new JFrameView("Portfolio");
    controller.setView(view);

  }
}

