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


    public void strokeBuilt(Stroke stroke, double timeEllapsed) {


        getContentPane().removeAll();
        this.revalidate();

        // REPLACE TIME in CreateScatterPanel
        JPanel jPanel = createScatterPanel((ArrayList<Point>) stroke.pointList, Double.toString(timeEllapsed));
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
        JFreeChart var0 = ChartFactory.createScatterPlot(("Time: " + time), "X", "Y", createDataset(points));

        XYPlot var1 = (XYPlot)var0.getPlot();
        var1.setBackgroundPaint((Paint)null);
        var1.setAxisOffset(RectangleInsets.ZERO_INSETS);
        var1.setOutlineVisible(false);

        XYDotRenderer var2 = new XYDotRenderer();
        var2.setDotWidth(4);
        var2.setDotHeight(4);
        var1.setRenderer(var2);
        var1.setDomainCrosshairVisible(true);
        var1.setRangeCrosshairVisible(true);

        NumberAxis var3 = (NumberAxis)var1.getDomainAxis();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        var3.setLowerBound(screenSize.getWidth() * -1.0);
        var3.setUpperBound(screenSize.getWidth());



        // AttributedString var4 = new AttributedString("H20");
        // var4.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB, 1, 2);

        // var3.setAttributedLabel(var4);

        var3.setPositiveArrowVisible(true);
        var3.setAutoRangeIncludesZero(false);

        NumberAxis var5 = (NumberAxis)var1.getRangeAxis();
        var5.setLowerBound(screenSize.getHeight() * -1.0);
        var5.setUpperBound(screenSize.getHeight());

        // AttributedString var6 = new AttributedString("kg x 106");
        // var6.addAttribute(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER, 7, 8);
        // var5.setAttributedLabel(var6);

        var5.setPositiveArrowVisible(true);

        ChartPanel var7 = new ChartPanel(var0);
        var7.setMouseWheelEnabled(true);

        var7.getChart().getXYPlot().getRangeAxis().setInverted(true);

        return var7;
    }
}