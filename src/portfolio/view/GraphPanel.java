package portfolio.view;


import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.sql.Date;

import java.util.List;

import javax.swing.JTextField;

import javax.swing.JLabel;
import javax.swing.BoxLayout;


/**
 * This class represents a GraphPanel Panel class that extends AbstractPanel.
 * Extension Change: Add GraphPanel class, @12.06.
 */
public class GraphPanel extends AbstractPanel {
  private JTextField startDateInput;
  private JTextField endDateInput;

  /**
   * Construct a BuyPanel object.
   */
  public GraphPanel() {
    setLayout(new FlowLayout());
    setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

    //Portfolio Name
    this.add(new JLabel("Portfolio Name:"));
    nameInput = new JTextField(15);
    this.add(nameInput);

    //startDate
    this.add(new JLabel("Start Date (YYYY-MM-DD):"));
    startDateInput = new JTextField(15);
    this.add(startDateInput);

    //endDate
    this.add(new JLabel("End Date (YYYY-MM-DD) (No later than today):"));
    endDateInput = new JTextField(15);
    this.add(endDateInput);

    setVisible(true);
  }

  /**
   * Get the startDate.
   *
   * @return the date as String.
   */
  String getStartDate() {
    return this.startDateInput.getText();
  }

  /**
   * Get the endDate.
   *
   * @return the date as String.
   */
  String getEndDate() {
    return this.endDateInput.getText();
  }

  void drawGraph(List<Double> valList, String porfName, Date startDate) {
    //chart try
    String chartName = "Value Chart of " + porfName;
    String origiStart = startDate.toString();
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();



    for (double value : valList) {
      dataset.addValue(value, porfName, startDate.toString().substring(5));
      startDate = new Date(startDate.getTime() + (1000 * 60 * 60 * 24));
    }
    JFreeChart chart = ChartFactory.createLineChart(chartName, "Date",
            "Portfolio Value", dataset, PlotOrientation.VERTICAL, true, true, true);



    CategoryPlot cp = chart.getCategoryPlot();
    cp.setBackgroundPaint(ChartColor.WHITE); // 背景色设置
    cp.setRangeGridlinePaint(ChartColor.GRAY); // 网格线色设置


    try {
      System.out.println("Chart Generatred");
      ChartUtilities.saveChartAsPNG(new File("./" + porfName + origiStart + "-"
                      + startDate.toString() + "LineChart.png"), chart, 90 * valList.size(), 500);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
