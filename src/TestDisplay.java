import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import javax.swing.*;
import java.awt.*;

public class TestDisplay extends StrokeDisplay {

    public TestDisplay() {
        super();
    }

    public TestDisplay(String frameTitle) {
        super(frameTitle);
    }

    // ABSTRACT METHODS
    public XYDataset createDataset(Stroke stroke) {

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries path = new XYSeries("path");

        for (Point p : stroke.getPointList()) { path.add(p.x, p.y); }
        dataset.addSeries(path);

        return dataset;

    }


    public JPanel createPanel(Stroke stroke, XYDataset dataset) {
        JFreeChart chart = ChartFactory.createScatterPlot(("Duration: " + stroke.getDuration()), "X", "Y", dataset);

        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint((Paint)null);
        plot.setAxisOffset(RectangleInsets.ZERO_INSETS);
        plot.setOutlineVisible(false);

        XYDotRenderer renderer = new XYDotRenderer();

        // CHANGES
        renderer.setDotWidth(2);
        renderer.setDotHeight(2);


        plot.setRenderer(renderer);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        NumberAxis xAxis = (NumberAxis)plot.getDomainAxis();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // CHANGES
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(screenSize.getWidth());




        xAxis.setPositiveArrowVisible(true);
        xAxis.setAutoRangeIncludesZero(false);

        NumberAxis yAxis = (NumberAxis)plot.getRangeAxis();

        // CHANGES
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(screenSize.getHeight());


        yAxis.setPositiveArrowVisible(true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);

        // CHANGES
        chartPanel.getChart().getXYPlot().getRangeAxis().setInverted(true);

        return chartPanel;
    }


    public void updatePanel(Stroke stroke) {

    }



}
