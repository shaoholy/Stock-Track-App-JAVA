package portfolio.view;


import java.util.HashSet;
import java.util.Set;

import java.awt.FlowLayout;

import javax.swing.JTextField;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

/**
 * This class represents a Create Stock Panel class that extends AbstractPanel.
 * Extension Change: Add CreatePanel class, @12.06.
 */
public class CreatePanel extends AbstractPanel {
  private JTextField stockInput;
  private JComboBox<String> type;
  private final String[] OPT = {"Create new or retrieve old?", "New", "Retrieve"};

  /**
   * Construct a CreatePanel object.
   */
  public CreatePanel() {
    setLayout(new FlowLayout());
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

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

    setVisible(true);

  }


  /**
   * Form a new Panel for user to create a new Portfolio.
   */
  private void newPanel() {
    JPanel newPanel = new JPanel();


    newPanel.add(new JLabel("Stocks Symbols (separated by space, Upper Case):"));
    stockInput = new JTextField(20);
    newPanel.add(stockInput);

    JOptionPane.showConfirmDialog(null, newPanel,
            "Create New", JOptionPane.OK_CANCEL_OPTION);
  }



  /**
   * Get the stock set.
   *
   * @return the stock as Set.
   */
  Set<String> getStockSet() {
    Set<String> stockSet = new HashSet<>();
    String[] arr = this.stockInput.getText().split(" ");
    for (String stock : arr) {
      stockSet.add(stock);
    }
    return stockSet;
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

}
