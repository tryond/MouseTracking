
import java.awt.*;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;

public class StrokeMonitorDisplay extends ApplicationFrame implements StrokeBuilderListener {



    // StrokeBuilderListener: runs every time a new stroke is created
    public void strokeBuilt(Stroke stroke) {

        System.out.println(stroke.getDirection());

        getContentPane().removeAll();
        this.revalidate();

        // REPLACE TIME in CreateScatterPanel
        JPanel jPanel = createScatterPanel((ArrayList<Point>) stroke.getPointList(), Double.toString(stroke.getDuration()));
        jPanel.setPreferredSize(new Dimension(500, 270));
        this.setContentPane((jPanel));

        this.revalidate();
    }

    public StrokeMonitorDisplay() {
        super("Stroke: Monitor Display");
        JPanel jPanel = createScatterPanel(new ArrayList<>(), "...");
        jPanel.setPreferredSize(new Dimension(500, 270));
        this.setContentPane((jPanel));

        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
        this.setAlwaysOnTop(true);
    }

    public XYDataset createDataset(ArrayList<Point> points) {

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries path = new XYSeries("path");

        for (Point p : points) { path.add(p.x, p.y); }
        dataset.addSeries(path);

        return dataset;
    }

    public JPanel createScatterPanel(ArrayList<Point> points, String time) {
        JFreeChart chart = ChartFactory.createScatterPlot(("Time: " + time), "X", "Y", createDataset(points));

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
}