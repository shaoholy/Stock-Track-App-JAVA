package portfolio.view;

import java.sql.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.BoxLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.BorderFactory;

import portfolio.controller.Features;

import portfolio.model.MenuType;

/**
 * This class represents a GUI JFrameView that implements IView interface.
 * Extension Change: Add JFrameView class, @12.06.
 */
public class JFrameView extends JFrame implements IView {
  private JRadioButton[] radioButtons;
  private JButton exitButton;
  private JTextArea sTextArea;


  /**
   * Construct a JFrameView object.
   *
   * @param caption the caption.
   */
  public JFrameView(String caption) {
    super(caption);
    setSize(500, 500);
    setLocation(200, 200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //main panel
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS)); //vertically
    JScrollPane mainScrollPane = new JScrollPane(mainPanel); //scroll bars
    add(mainScrollPane);
    mainPanel.add(new JLabel("Welcome to Stock Portfolio!"));

    //radio buttons
    JPanel radioPanel = new JPanel();
    radioPanel.setBorder(BorderFactory.createTitledBorder("Choose One"));
    radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.PAGE_AXIS));
    radioButtons = new JRadioButton[10];
    String[] menu = setMenu();
    ButtonGroup rGroup = new ButtonGroup();
    for (int i = 0; i < radioButtons.length; i++) {
      radioButtons[i] = new JRadioButton(menu[i]);
      //radioButtons[i].setActionCommand("RB" + (i + 1));
      rGroup.add(radioButtons[i]);
      radioPanel.add(radioButtons[i]);
    }
    mainPanel.add(radioPanel);

    //text area with a scrollbar
    sTextArea = new JTextArea(10, 20);
    JScrollPane scrollPane = new JScrollPane(sTextArea);
    sTextArea.setEditable(false);
    sTextArea.setLineWrap(true);
    scrollPane.setBorder(BorderFactory.createTitledBorder("User Information Screen: "));
    mainPanel.add(scrollPane);
    sTextArea.setText("Please wait. Information of trade will be displayed here soon.");

    //exit button
    exitButton = new JButton("Exit");
    exitButton.setActionCommand("Exit Button");
    mainPanel.add(exitButton);

    pack();
    setVisible(true);
  }



  @Override
  public void update(String message) {
    sTextArea.setText(message);
  }

  /**
   * Accept the set of callbacks from the controller, and hook up as needed to various things in
   * this view.
   *
   * @param f the set of feature callbacks as a Features object
   */
  @Override
  public void setFeatures(Features f) {
    //View All
    radioButtons[0].addActionListener(l -> f.viewAll());

    //Exam One
    radioButtons[1].addActionListener(l
        -> f.examineOne(JOptionPane.showInputDialog("Enter Portfolio Name")));

    //Create one Portfolio
    create(f);

    //Buy Stock
    buyStock(f);

    //All Cost
    cost(f);

    //All Value
    value(f);

    //Strategy
    strategy(f);

    //Save Portfolio
    radioButtons[7].addActionListener(l
        -> f.savePortfolio(JOptionPane.showInputDialog("Enter Portfolio Name")));

    //Add Stock
    add(f);

    //Draw Graph
    drawGraph(f);

    //Exit
    exitButton.addActionListener(l -> f.exitProgram());
  }


  /**
   * Accept drawGraph callback from the controller, and hook up view.
   *
   * @param f the set of feature callbacks as a Features object
   */
  private void drawGraph(Features f) {
    radioButtons[9].addActionListener(l -> {
      GraphPanel graphPanel = new GraphPanel();
      int res = JOptionPane.showConfirmDialog(null, graphPanel,
              "Graph", JOptionPane.OK_CANCEL_OPTION);
      if (res == JOptionPane.OK_OPTION) {

        String startDate = graphPanel.getStartDate();
        String endDate = graphPanel.getEndDate();
        String porfName = graphPanel.getPortfolioName();
        List<Double> valList = f.getPortfolioValList(porfName, startDate, endDate);
        graphPanel.drawGraph(valList, porfName, Date.valueOf(startDate));
      }
    });
  }

  /**
   * Accept Create callback from the controller, and hook up view.
   *
   * @param f the set of feature callbacks as a Features object
   */
  private void create(Features f) {
    radioButtons[2].addActionListener(l -> {
      CreatePanel createPanel = new CreatePanel();
      int res = JOptionPane.showConfirmDialog(null, createPanel,
              "Create One", JOptionPane.OK_CANCEL_OPTION);
      if (res == JOptionPane.OK_OPTION) {
        if (createPanel.getMode() == 1) { //new
          f.create(createPanel.getPortfolioName(), createPanel.getStockSet());
        } else { //retrieve
          System.out.println(createPanel.getPath() + " : path");
          f.createFromFile(createPanel.getPortfolioName(), createPanel.getPath());
        }
      }
    });
  }

  /**
   * Accept buy stock callback from the controller, and hook up view.
   *
   * @param f the set of feature callbacks as a Features object
   */
  private void buyStock(Features f) {
    radioButtons[3].addActionListener(l -> {
      BuyPanel buyPanel = new BuyPanel();
      int res = JOptionPane.showConfirmDialog(null, buyPanel,
              "Exam One", JOptionPane.OK_CANCEL_OPTION);
      if (res == JOptionPane.OK_OPTION) {
        f.buyStock(buyPanel.getPortfolioName(), buyPanel.getStock(), buyPanel.getDate(),
                buyPanel.getShareOrMoney(), buyPanel.getFee(), buyPanel.getMode());
      }
    });
  }

  /**
   * Accept cost callback from the controller, and hook up view.
   *
   * @param f the set of feature callbacks as a Features object
   */
  private void cost(Features f) {
    radioButtons[4].addActionListener(l -> {
      CostPanel costPanel = new CostPanel();
      int res = JOptionPane.showConfirmDialog(null, costPanel,
              "All Cost", JOptionPane.OK_CANCEL_OPTION);
      if (res == JOptionPane.OK_OPTION) {
        f.totalCost(costPanel.getPortfolioName(), costPanel.getDate(), costPanel.getMode());
      }
    });
  }

  /**
   * Accept value callback from the controller, and hook up view.
   *
   * @param f the set of feature callbacks as a Features object
   */
  private void value(Features f) {
    radioButtons[5].addActionListener(l -> {
      ValuePanel valuePanel = new ValuePanel();
      int res = JOptionPane.showConfirmDialog(null, valuePanel,
              "All Value", JOptionPane.OK_CANCEL_OPTION);
      if (res == JOptionPane.OK_OPTION) {
        f.totalValue(valuePanel.getPortfolioName(), valuePanel.getDate(), valuePanel.getMode());
      }
    });
  }

  /**
   * Accept strategy callback from the controller, and hook up view.
   *
   * @param f the set of feature callbacks as a Features object
   */
  private void strategy(Features f) {
    radioButtons[6].addActionListener(l -> {
      StrategyPanel strategyPanel = new StrategyPanel(f);
      int res = JOptionPane.showConfirmDialog(null, strategyPanel,
              "Strategy", JOptionPane.OK_CANCEL_OPTION);
      if (res == JOptionPane.OK_OPTION) {

        if (strategyPanel.getMode() == 1) { //new
          f.strategy(strategyPanel.getPortfolioName(), strategyPanel.getWeightMap(),
                  strategyPanel.getStartDate(), strategyPanel.getEndDate(),
                  strategyPanel.getInterval(), strategyPanel.getMoney(),
                  strategyPanel.getFee(), strategyPanel.getSaveOpt());
        } else { //retrieve
          f.strategyFromFile(strategyPanel.getPortfolioName(), strategyPanel.getPath());
        }

      }
    });
  }

  /**
   * Accept add stock callback from the controller, and hook up view.
   *
   * @param f the set of feature callbacks as a Features object
   */
  private void add(Features f) {
    radioButtons[8].addActionListener(l -> {
      AddPanel addPanel = new AddPanel();
      int res = JOptionPane.showConfirmDialog(null, addPanel,
              "Add Stock", JOptionPane.OK_CANCEL_OPTION);
      if (res == JOptionPane.OK_OPTION) {
        f.addStock(addPanel.getPortfolioName(), addPanel.getStock());
      }
    });
  }

  /**
   * Set up menu display.
   */
  private String[] setMenu() {
    String[] menu = new String[10];
    menu[0] = MenuType.VIEWALL.getMessage();
    menu[1] = MenuType.EXAMONE.getMessage();
    menu[2] = MenuType.CREATE.getMessage();
    menu[3] = MenuType.BUYSTOCK.getMessage();
    menu[4] = MenuType.TOTALCOST.getMessage();
    menu[5] = MenuType.TOTALVALUE.getMessage();
    menu[6] = MenuType.STRATEGY.getMessage();
    menu[7] = MenuType.SAVE.getMessage();
    menu[8] = MenuType.ADD.getMessage();
    menu[9] = MenuType.GRAPH.getMessage();
    return menu;
  }


}
