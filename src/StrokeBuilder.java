import org.jnativehook.mouse.NativeMouseEvent;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StrokeBuilder implements CursorTrackerListener {

    // amount of time before building times out
    static int TIMEOUT_SECONDS = 2;

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
    LocalDateTime lastPositionUpdate = LocalDateTime.now();

    // last know position cursor
    private int oldx = MouseInfo.getPointerInfo().getLocation().x;
    private int oldy = MouseInfo.getPointerInfo().getLocation().y;

    // add listener to be notified of new Stroke's contstruction
    public void addListener(StrokeBuilderListener listener) {
        listeners.add(listener);
    }

    public void cursorMoved(int newx, int newy) {

        int timeDiff= LocalDateTime.now().getSecond() - lastPositionUpdate.getSecond();

        // System.out.println("Timediff: " + timeDiff);

        if (timeDiff >= TIMEOUT_SECONDS) {

            System.out.println("Timediff: " + timeDiff);

            currentStroke = new Stroke();
            addPoint(newx, newy);
            lastPositionUpdate = LocalDateTime.now();
        }
        else {
            addPoint(newx, newy);
            lastPositionUpdate = LocalDateTime.now();
        }

        nowBuilding = true;
    }

    public void cursorClicked(int newx, int newy) {

        // Stop Building
        if (nowBuilding) {
            nowBuilding = false;
            // System.out.println("Stop Building");
            strokeList.add(currentStroke);
            for (StrokeBuilderListener listener : listeners) {
                listener.strokeBuilt(currentStroke);
            }
            currentStroke = new Stroke();

            System.out.println();
        }
        else {
            System.out.println("Nothing Built");
        }

    }

    public void addPoint(int xpos, int ypos) {
        currentStroke.addPoint(new Point(xpos, ypos));
    }

}
