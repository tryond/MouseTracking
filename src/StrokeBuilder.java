import org.jnativehook.mouse.NativeMouseEvent;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StrokeBuilder implements CursorTrackerListener {

    // amount of time before building times out
    static double TIMEOUT_SECONDS = 0.25;

    // cursor must move out of this zone (pixel radius) to start building stroke
    static int BUFFER_ZONE = 5;

    // Strokes constructed thus far
    public List<Stroke> strokeList = new ArrayList<>();

    // current Stroke
    public Stroke currentStroke = new Stroke();

    // listeners need to be notified when new Stroke built
    private List<StrokeBuilderListener> listeners = new ArrayList<>();

    // true if currently constructing a Stroke
    private boolean nowBuilding = false;

    // time of last move
    long lastPositionUpdate = System.nanoTime();
    long startTime = System.nanoTime();

    // add listener to be notified of new Stroke's contstruction
    public void addListener(StrokeBuilderListener listener) {
        listeners.add(listener);
    }

    public void cursorMoved(int newx, int newy) {

        double timeDiff= (System.nanoTime() - lastPositionUpdate) / 1e9;

        if (timeDiff > TIMEOUT_SECONDS) {
            currentStroke = new Stroke();
            addPoint(newx, newy);
            startTime = System.nanoTime();
            lastPositionUpdate = System.nanoTime();
        }
        else {
            addPoint(newx, newy);
            lastPositionUpdate = System.nanoTime();
        }

        nowBuilding = true;
    }

    public void cursorClicked(int newx, int newy) {

        double timeDiff= (double) (System.nanoTime() - lastPositionUpdate) / 1e9;

        // Stop Building
        if (nowBuilding) {
            nowBuilding = false;
            // System.out.println("Stop Building");
            strokeList.add(currentStroke);

            double strokeDuration = (System.nanoTime() - startTime) / 1e9;

            for (StrokeBuilderListener listener : listeners) {
                listener.strokeBuilt(currentStroke, strokeDuration);
            }
            currentStroke = new Stroke();
            startTime = System.nanoTime();
            System.out.println("Stroke Built");
        }
        else {
            System.out.println("Nothing Built");
        }

    }

    public void addPoint(int xpos, int ypos) {
        currentStroke.addPoint(new Point(xpos, ypos));
    }

}
