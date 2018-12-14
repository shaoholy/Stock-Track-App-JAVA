package portfolio.view;

import java.awt.FlowLayout;

import javax.swing.JTextField;

import javax.swing.JLabel;
import javax.swing.BoxLayout;

/**
 * This class represents a Add Stock Panel class that extends AbstractPanel.
 * Extension Change: Add AddPanel class, @12.06.
 */
public class AddPanel extends AbstractPanel {

  /**
   * Construct a AddPanel object.
   */
  public AddPanel() {
    setLayout(new FlowLayout());
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    this.add(new JLabel("Portfolio Name:"));
    nameInput = new JTextField(10);
    this.add(nameInput);

    this.add(new JLabel("Stock Symbol (only one, eg: AAPL) :"));
    stockInput = new JTextField(20);
    this.add(stockInput);


    setVisible(true);

  }





}
