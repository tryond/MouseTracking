import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StrokeBuilder implements CursorTrackerListener {

    // Class Attributes
    private final double TIMEOUT_SECONDS = 0.25;             // amount of time before building times out
    private List<Stroke> strokeList = new ArrayList<>();     // Strokes constructed thus far
    private Stroke currentStroke = new Stroke();             // current Stroke
    private double strokeDuration = 0.0;                     // stroke time from start to finish
    private List<StrokeBuilderListener> listeners = new ArrayList<>();  // listeners need to be notified when new Stroke built
    private boolean nowBuilding = false;                    // true if currently constructing a Stroke
    private long lastPositionUpdate = System.nanoTime();    // time of last move
    private long startTime = System.nanoTime();             // time of last stroke start

    // CursorTrackerListener: runs when cursor is moved
    public void cursorMoved(int x, int y) {

        // if cursor has timed out
        if (cursorIsIdle())
            startStroke(x, y);
        else
            updateStroke(x, y);
    }

    // CursorTrackerListener: runs when cursor is clicked
    public void cursorClicked(int x, int y) {

        // finish current stroke and start new one
        if (nowBuilding) {
            finishStroke(x, y);
            startStroke(x, y);
        }
    }

    // adds point (x, y) to the current stroke
    public void addPoint(int x, int y) {
        currentStroke.addPoint(new Point(x, y));
    }

    // add listener to be notified of new Stroke's contstruction
    public void addListener(StrokeBuilderListener listener) {
        listeners.add(listener);
    }

    // notifies all listeners that a stroke has been created
    public void notifyListeners() {

        // compute time taken to create stroke
        strokeDuration = (System.nanoTime() - startTime) / 1e9;

        // let all listeners know that new stroke has been created
        for (StrokeBuilderListener listener : listeners) {
            listener.strokeBuilt(currentStroke, strokeDuration);
        }
    }

    // starts tracking new Stroke
    public void startStroke(int x, int y) {

        currentStroke = new Stroke();
        addPoint(x, y);
        startTime = System.nanoTime();
        lastPositionUpdate = System.nanoTime();
        nowBuilding = true;
    }

    // updates the current stroke
    public void updateStroke(int x, int y) {

        addPoint(x, y);
        lastPositionUpdate = System.nanoTime();
        nowBuilding = true;
    }

    // places stroke into
    public void finishStroke(int x, int y) {

        nowBuilding = false;
        addPoint(x, y);
        strokeList.add(currentStroke);

        notifyListeners();
    }

    // has the cursor been idle for longer than TIMEOUT_SECONDS
    public boolean cursorIsIdle() {
        double timeDiff = (System.nanoTime() - lastPositionUpdate) / 1e9;
        return timeDiff > TIMEOUT_SECONDS;
    }
}
