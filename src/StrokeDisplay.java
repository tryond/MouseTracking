
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

public abstract class StrokeDisplay extends ApplicationFrame implements StrokeBuilderListener {

    public StrokeDisplay() {
        this("Stroke Display");
    }

    public StrokeDisplay(String frameTitle) {
        super(frameTitle);

        // create Dataset
        XYDataset dataset = createDataset(new Stroke());

        // create Panel
        JPanel jPanel = createPanel(new Stroke(), dataset);

        // set frame contents to returned panel
        this.setContentPane((jPanel));
        this.pack();
        this.setVisible(true);
        this.setAlwaysOnTop(true);
        RefineryUtilities.centerFrameOnScreen(this);
    }


    // ABSTRACT METHODS
    public abstract XYDataset createDataset(Stroke stroke);
    public abstract JPanel createPanel(Stroke stroke, XYDataset dataset);
    public abstract void updatePanel(Stroke stroke);



    // StrokeBuilderListener: runs every time a new stroke is created
    public void strokeBuilt(Stroke stroke) {
        updatePanel(stroke);
    }
}