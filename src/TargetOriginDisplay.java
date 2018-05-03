/*
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StrokeDisplay implements StrokeBuilderListener {

    public void strokeBuilt(Stroke stroke) {

        System.out.println("STROKE BUILT: " + stroke.pointList.size());
        System.out.println(stroke.pointList.get(0));
        System.out.println(stroke.pointList.get(stroke.pointList.size()-1));
    }
}
*/

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

public class TargetOriginDisplay extends ApplicationFrame implements StrokeBuilderListener {


    public void strokeBuilt(Stroke stroke) {


        getContentPane().removeAll();
        this.revalidate();

        // REPLACE TIME in CreateScatterPanel
        JPanel jPanel = createScatterPanel((ArrayList<Point>) stroke.pointList, Double.toString(stroke.duration));
        jPanel.setPreferredSize(new Dimension(500, 270));
        this.setContentPane((jPanel));

        this.revalidate();

        // System.out.println("STROKE BUILT: " + stroke.pointList.size());
        // System.out.println(stroke.pointList.get(0));
        // System.out.println(stroke.pointList.get(stroke.pointList.size()-1));

        // update((ArrayList<Point>) stroke.pointList, "non");

    }

    public TargetOriginDisplay() {
        super("Stroke: Terminate at Origin");
        JPanel jPanel = createScatterPanel(new ArrayList<>(), "...");
        jPanel.setPreferredSize(new Dimension(500, 270));
        this.setContentPane((jPanel));

        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);

        this.setAlwaysOnTop(true);
    }

    public XYDataset createDataset(ArrayList<Point> points) {

        Point origin = points.isEmpty() ? new Point(0,0) : points.get(points.size()-1);

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries path = new XYSeries("path");

        for (Point p : points) { path.add(p.x - origin.x, p.y - origin.y); }
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
        renderer.setDotWidth(4);
        renderer.setDotHeight(4);
        plot.setRenderer(renderer);
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        NumberAxis xAxis = (NumberAxis)plot.getDomainAxis();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        xAxis.setLowerBound(screenSize.getWidth() * -1.0);
        xAxis.setUpperBound(screenSize.getWidth());

        xAxis.setPositiveArrowVisible(true);
        xAxis.setAutoRangeIncludesZero(false);

        NumberAxis yAxis = (NumberAxis)plot.getRangeAxis();
        yAxis.setLowerBound(screenSize.getHeight() * -1.0);
        yAxis.setUpperBound(screenSize.getHeight());
        yAxis.setPositiveArrowVisible(true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);

        chartPanel.getChart().getXYPlot().getRangeAxis().setInverted(true);

        return chartPanel;
    }
}