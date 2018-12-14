package portfolio.view;

import java.awt.FlowLayout;

import javax.swing.JTextField;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 * This class represents a Cost Panel class that extends AbstractPanel.
 * Extension Change: Add CostPanel class, @12.06.
 */
public class CostPanel extends AbstractPanel {
  private JComboBox<String> tradeType;
  private final String[] OPT = {"Which day do you want to see total cost basis",
    "Today", "Previous day"};
  String prevDay;

  /**
   * Construct a CostPanel object.
   */
  public CostPanel() {

    setLayout(new FlowLayout());
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    tradeType = new JComboBox<>(OPT);
    this.add(tradeType);
    tradeType.addActionListener(l -> {
      if (getMode() == 2) {
        prevDay = JOptionPane.showInputDialog("Date (YYYY-MM-DD):");
      }
    });


    JLabel nameLabel = new JLabel("Portfolio Name:");
    this.add(nameLabel);
    nameInput = new JTextField(10);
    this.add(nameInput);


    setVisible(true);

  }


  /**
   * Get the mode, 1 means today, 2 means previous day.
   *
   * @return the mode as int.
   */
  int getMode() {
    if (tradeType.getSelectedItem().equals(OPT[1])) { //today
      return 1;
    } else if (tradeType.getSelectedItem().equals(OPT[2])) { //previous day
      return 2;
    }
    return 0;
  }


  /**
   * Get the date.
   *
   * @return the date as String.
   */
  String getDate() {
    return this.prevDay;
  }


}

