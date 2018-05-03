import org.jfree.ui.RefineryUtilities;

public class MouseTracking {

    public static void main(String args[]) {
        CursorTracker cursorTracker = new CursorTracker();
        StrokeBuilder strokeBuilder = new StrokeBuilder();
        StrokeDisplay strokeDisplay = new StrokeDisplay();

        // TEST
        TargetOriginDisplay targetOriginDisplay = new TargetOriginDisplay();

        // TEST
        strokeBuilder.addListener(targetOriginDisplay);

        strokeBuilder.addListener(strokeDisplay);
        cursorTracker.addListener(strokeBuilder);
    }
}
