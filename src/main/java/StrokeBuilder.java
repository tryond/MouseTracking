package main.java;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StrokeBuilder implements CursorTrackerListener {

    // Class Attributes
    private final double TIMEOUT_SECONDS = 0.25;             // amount of time before building times out

    private List<Stroke> strokeList = new ArrayList<>();     // Strokes constructed thus far
    private Stroke currentStroke = new Stroke();             // current main.Stroke

    private double strokeDuration = 0.0;                                // stroke time from start to finish
    private List<StrokeBuilderListener> listeners = new ArrayList<>();  // listeners need to be notified when new main.Stroke built

    private long updateTime = System.nanoTime();            // time of last stroke update
    private long startTime = System.nanoTime();             // time of last stroke start
    private boolean nowBuilding = false;                    // true if currently constructing a main.Stroke

    // main.CursorTrackerListener: runs when cursor is moved
    public void cursorMoved(int x, int y) {

        // if cursor has timed out
        if (cursorIsIdle())
            startStroke(x, y);
        else
            updateStroke(x, y);
    }

    // main.CursorTrackerListener: runs when cursor is clicked
    public void cursorClicked(int x, int y) {

        // finish current stroke and start new one
        if (nowBuilding) {
            finishStroke(x, y);
            startStroke(x, y);
        }
    }

    // add listener to be notified of new main.Stroke's contstruction
    public void addListener(StrokeBuilderListener listener) {
        listeners.add(listener);
    }

    // notifies all listeners that a stroke has been created
    public void notifyListeners() {

        // let all listeners know that new stroke has been created
        for (StrokeBuilderListener listener : listeners) {
            listener.strokeBuilt(currentStroke);
        }
    }

    // starts tracking new main.Stroke
    public void startStroke(int x, int y) {

        currentStroke = new Stroke();
        addPoint(x, y, 0.0);
        startTime = System.nanoTime();
        updateTime = System.nanoTime();
        nowBuilding = true;
    }

    // updates the current stroke
    public void updateStroke(int x, int y) {

        addPoint(x, y);
        updateTime = System.nanoTime();
        nowBuilding = true;
    }

    // places stroke into
    public void finishStroke(int x, int y) {

        nowBuilding = false;
        addPoint(x, y);

        // compute time taken to create stroke
        strokeDuration = (System.nanoTime() - startTime) / 1e9;
        currentStroke.setDuration(strokeDuration);

        strokeList.add(currentStroke);
        notifyListeners();
    }

    // adds point (x, y) to the current stroke
    public void addPoint(int x, int y) {
        double timeDiff = (System.nanoTime() - updateTime) / 1e9;
        currentStroke.addPoint(new Point(x, y), timeDiff);
    }

    // adds point (x, y) to the current stroke
    public void addPoint(int x, int y, Double time) {
        double timeDiff = time;
        currentStroke.addPoint(new Point(x, y), timeDiff);
    }

    // has the cursor been idle for longer than TIMEOUT_SECONDS
    public boolean cursorIsIdle() {
        double timeDiff = (System.nanoTime() - updateTime) / 1e9;
        return timeDiff > TIMEOUT_SECONDS;
    }
}
