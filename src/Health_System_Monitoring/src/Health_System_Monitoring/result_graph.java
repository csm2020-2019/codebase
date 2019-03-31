package Health_System_Monitoring;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.management.Query;
import javax.swing.*;
import java.awt.*;


public class result_graph extends JFrame {

    private DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    private String frameTitle;

    private int value;
    private String rowKey, columnKey;

    /**
     * Create a line graph
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

        this.frameTitle = frameTitle;

        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new Dimension(560, 367));
        setContentPane(chartPanel);
    }

    /**
     * Add extra data to the graph
     * @param value number to be plotted along the y-axis
     * @param rowKey name of line/value
     * @param columnKey date in String format to be placed along the x-axis
     */
    public void SetValue(int value, String rowKey, String columnKey) {
        this.value = value;
        this.rowKey = rowKey;
        this.columnKey = columnKey;

        dataset.addValue(value, rowKey, columnKey);
    }

    /**
     * Called first to add data to the graph
     * @return dataset
     */
    public DefaultCategoryDataset createDataset() {
        dataset.addValue(value, rowKey, columnKey);

        return dataset;
    }

}
