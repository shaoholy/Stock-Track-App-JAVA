package portfolio.view;

import java.awt.FlowLayout;

import javax.swing.JTextField;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 * This class represents a BuyStock Panel class that extends AbstractPanel.
 * Extension Change: Add BuyPanel class, @12.06.
 */
public class BuyPanel extends AbstractPanel {
  private JTextField stockSymInput;
  private JTextField buyDateInput;
  private JComboBox<String> tradeType;
  private String share;
  private String money;
  private final String[] OPT = {"Choose one way to buy stock",
      "Buy by Share", "Buy by Money Amount"};

  /**
   * Construct a BuyPanel object.
   */
  public BuyPanel() {
    setLayout(new FlowLayout());
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    //Portfolio Name
    this.add(new JLabel("Portfolio Name:"));
    nameInput = new JTextField(15);
    this.add(nameInput);

    //Stock Symbol
    this.add(new JLabel("Stock Symbol (Upper Case):"));
    stockInput = new JTextField(15);
    this.add(stockInput);

    //By share or money Amount
    tradeType = new JComboBox<>(OPT);
    this.add(tradeType);
    tradeType.addActionListener(l -> {
      if (tradeType.getSelectedItem().equals(OPT[1])) {
        share = JOptionPane.showInputDialog("Share ( > 0, eg 100):");
      } else if (tradeType.getSelectedItem().equals(OPT[2])) {
        money = JOptionPane.showInputDialog("Money ( > 0, eg 500.00):");
      }
    });

    //Date
    this.add(new JLabel("Buy Date (YYYY-MM-DD):"));
    buyDateInput = new JTextField(15);
    this.add(buyDateInput);

    //Fee
    this.add(new JLabel("Commission Fee (left blank if no commission fee, >= 0):"));
    commInput = new JTextField(15);
    this.add(commInput);

    setVisible(true);
  }


  /**
   * Get the mode, 1 means by share, 2 means by money amount.
   *
   * @return the mode as int.
   */
  int getMode() {
    if (tradeType.getSelectedItem().equals(OPT[1])) { //share
      return 1;
    } else if (tradeType.getSelectedItem().equals(OPT[2])) { //money
      return 2;
    }
    return 0;
  }

  /**
   * Get the share or money.
   *
   * @return the share or money as String.
   */
  String getShareOrMoney() {
    if (getMode() == 1) {
      return share;
    } else {
      return money;
    }
  }


  /**
   * Get the date.
   *
   * @return the date as String.
   */
  String getDate() {
    return this.buyDateInput.getText();
  }


}
