package portfolio.view;

import java.util.HashMap;
import java.util.Map;

import java.awt.FlowLayout;

import javax.swing.JTextField;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import portfolio.controller.Features;

/**
 * This class represents a Strategy Panel class that extends AbstractPanel.
 * Extension Change: Add StrategyPanel class, @12.06.
 */
public class StrategyPanel extends AbstractPanel {
  private JTextField moneyInput;
  private JTextField intervalInput;
  private JTextField startDateInput;
  private JComboBox<String> weightType;
  private JComboBox<String> endDateType;
  private JComboBox<String> saveType;
  private JComboBox<String> type;

  private Map<String, Double> weightMap;
  private String endDate;
  private Features f;
  private final String[] WEIGHTOPT = {"Do you want to invest using Equal Weights for all stocks?",
      "Y", "N"};
  private final String[] ENDDATEOPT = {"Do you want to specify the end date?", "Y", "N"};
  private final String[] SAVEOPT = {"N", "Y"};
  private final String[] OPT = {"Create new or retrieve old?", "New", "Retrieve"};

  /**
   * Construct a StrategyPanel object.
   */
  public StrategyPanel(Features f) {
    setLayout(new FlowLayout());
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    this.f = f;

    this.add(new JLabel("Portfolio Name:"));
    nameInput = new JTextField(10);
    this.add(nameInput);

    type = new JComboBox<>(OPT);
    this.add(type);
    type.addActionListener(l -> {
      if (type.getSelectedItem().equals(OPT[1])) { //new
        newPanel();
      } else if (type.getSelectedItem().equals(OPT[2])) { //retrieve
        retrievePanel();
      }
    });

  }

  /**
   * Form a new Panel for user to create a new Strategy.
   */
  private void newPanel() {
    JPanel newPanel = new JPanel();
    newPanel.setLayout(new FlowLayout());
    newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.PAGE_AXIS));

    //get weightMap, (equal weight or customize weight)
    weightType = new JComboBox<>(WEIGHTOPT);

    newPanel.add(weightType);
    weightType.addActionListener(l -> {
      String[] stockNames = f.getAllStocks(nameInput.getText());
      if (weightType.getSelectedItem().equals(WEIGHTOPT[1])) {
        weightMap = f.getWeightMapEqual(stockNames);
      } else if (weightType.getSelectedItem().equals(WEIGHTOPT[2])) {
        getWeightMapNotEqual(stockNames);
      }
    });

    //get money amount
    newPanel.add(new JLabel("Money ( >= 0):"));
    moneyInput = new JTextField(15);
    newPanel.add(moneyInput);
    //money = moneyInput.getText();

    //Fee
    newPanel.add(new JLabel("Commission Fee (left Blank if no fee, >= 0):"));
    commInput = new JTextField(15);
    newPanel.add(commInput);

    //Intervals
    newPanel.add(new JLabel("Intervals ( >= 1):"));
    intervalInput = new JTextField(15);
    newPanel.add(intervalInput);

    //Start Date
    newPanel.add(new JLabel("Start Date (YYYY-MM-DD):"));
    startDateInput = new JTextField(15);
    newPanel.add(startDateInput);

    //End Date
    endDateType = new JComboBox<>(ENDDATEOPT);
    newPanel.add(endDateType);
    endDateType.addActionListener(l -> {
      if (endDateType.getSelectedItem().equals(ENDDATEOPT[1])) {
        endDate = JOptionPane.showInputDialog("Please enter the End Date (YYYY-MM-DD):");
      }
    });

    //save strategy
    newPanel.add(new JLabel("Want to save the strategy to JSON file (default: no save)?"));
    saveType = new JComboBox<>(SAVEOPT);
    newPanel.add(saveType);

    JOptionPane.showConfirmDialog(null, newPanel,
            "Create New", JOptionPane.OK_CANCEL_OPTION);

  }

  /**
   * Get the mode, 1 means new, 2 means retrieve.
   *
   * @return the mode as int.
   */
  int getMode() {
    if (type.getSelectedItem().equals(OPT[1])) { //new
      return 1;
    } else if (type.getSelectedItem().equals(OPT[2])) { //retrieve
      return 2;
    }
    return 0;
  }




  /**
   * Record the weight that user input.
   *
   * @stockNames all the stocks in the portfolio.
   */
  private void getWeightMapNotEqual(String[] stockNames) {
    weightMap = new HashMap<>();
    double[] weight = new double[stockNames.length];
    JTextField[] weightInput = new JTextField[stockNames.length];
    JPanel weightPanel = new JPanel();

    for (int i = 0; i < stockNames.length; i++) {
      weightPanel.add(new JLabel(stockNames[i] + ":"));
      weightInput[i] = new JTextField(10);
      weightPanel.add(weightInput[i]);
    }

    int res = JOptionPane.showConfirmDialog(null, weightPanel,
            "Weight", JOptionPane.OK_CANCEL_OPTION);
    if (res == JOptionPane.OK_OPTION) {
      for (int i = 0; i < stockNames.length; i++) {
        weightMap.put(stockNames[i], Double.valueOf(weightInput[i].getText()));
      }
    }
  }



  /**
   * Get the weight map.
   *
   * @return the weight map as Map.
   */
  Map<String, Double> getWeightMap() {
    return this.weightMap;
  }

  /**
   * Get the interval.
   *
   * @return the interval as String.
   */
  String getInterval() {
    return intervalInput.getText();
  }

  /**
   * Get the money.
   *
   * @return the moeny as String.
   */
  String getMoney() {
    return moneyInput.getText();
  }

  /**
   * Get the start date.
   *
   * @return the start date as String.
   */
  String getStartDate() {
    return startDateInput.getText();
  }

  /**
   * Get the end date.
   *
   * @return the end date as String.
   */
  String getEndDate() {
    if (endDateType.getSelectedItem().equals(ENDDATEOPT[1])) {
      return this.endDate;
    } else {
      return "N";
    }
  }


  /**
   * Get the save option.
   *
   * @return true if save; false otherwise.
   */
  boolean getSaveOpt() {
    return saveType.getSelectedItem().equals(SAVEOPT[1]);
  }

}
