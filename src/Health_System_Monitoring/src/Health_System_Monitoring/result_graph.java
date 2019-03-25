package Health_System_Monitoring;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.ui.ApplicationFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;


public class result_graph extends JFrame {


    /**
     *
     * @param frameTitle - Title displayed at the top of the frame
     * @param chartTitle - Title of the chart
     * @param categoryLabel - y axis label
     * @param valueLabel - x axis label
     */
    public result_graph(String frameTitle, String chartTitle, String categoryLabel, String valueLabel){
        super(frameTitle);
        JFreeChart lineChart = ChartFactory.createLineChart(
                chartTitle,
               categoryLabel,
                valueLabel,
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(560, 367));
        setContentPane(chartPanel);
    }

    /**
     * Retrieve data from tables to convert them to chart values
     */
    public void setData() {

    }

    /**
     * Plot values to the chart
     * @return dataset
     */
    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(15, "schools", "1970");
        dataset.addValue(30, "schools", "1980");
        dataset.addValue(60, "schools", "1990");
        dataset.addValue(120, "schools", "2000");
        dataset.addValue(240, "schools", "2010");
        dataset.addValue(300, "schools", "2014");

        return dataset;
    }

}
