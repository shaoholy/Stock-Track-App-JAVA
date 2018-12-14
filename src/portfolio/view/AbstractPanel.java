package portfolio.view;

import java.awt.FlowLayout;
import java.io.File;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class represents an ABSTRACT class that extends JPanel.
 * Extension Change: Add AbstractPanel class, @12.06.
 */
public abstract class AbstractPanel extends JPanel {
  protected String path;
  protected JTextField nameInput;
  protected JTextField commInput;
  protected JTextField stockInput;

  /**
   * Form a JPanel that retrieve an old file from file path.
   */
  protected void retrievePanel() {
    JPanel retrievePanel = new JPanel();

    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    retrievePanel.add(fileopenPanel);
    JButton fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    fileopenPanel.add(fileOpenButton);
    JLabel fileOpenDisplay = new JLabel("File path will appear here");
    fileopenPanel.add(fileOpenDisplay);

    fileOpenButton.addActionListener(l -> {
      final JFileChooser fchooser = new JFileChooser(".");
      FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "JSON file", "JSON", "gif");
      fchooser.setFileFilter(filter);
      int retvalue = fchooser.showOpenDialog(null);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        path = f.getAbsolutePath();
        fileOpenDisplay.setText(path);
      }
    });
    int res = JOptionPane.showConfirmDialog(null, retrievePanel,
            "Retrieve from Old", JOptionPane.OK_CANCEL_OPTION);
  }

  /**
   * Get the file's path.
   *
   * @return the file's path as String.
   */
  protected String getPath() {
    return this.path;
  }

  /**
   * Get the portfolio's name.
   *
   * @return the portfolio's name as String.
   */
  protected String getPortfolioName() {
    return this.nameInput.getText();
  }

  /**
   * Get the fee.
   *
   * @return the fee as String.
   */
  protected String getFee() {
    //No Fee
    if (this.commInput.getText().equals("")) {
      return "0.0";
    }
    return this.commInput.getText();
  }


  /**
   * Get the stock symbols.
   *
   * @return the stock symbols as String.
   */
  protected String getStock() {
    return this.stockInput.getText();
  }


}
