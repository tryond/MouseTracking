import org.jfree.ui.RefineryUtilities;

public class MouseTracking {

    public static void main(String args[]) {
        CursorTracker cursorTracker = new CursorTracker();
        StrokeBuilder strokeBuilder = new StrokeBuilder();
        StrokeMonitorDisplay strokeMonitorDisplay = new StrokeMonitorDisplay();

        // TEST
        TargetOriginDisplay targetOriginDisplay = new TargetOriginDisplay();

        // TEST
        strokeBuilder.addListener(targetOriginDisplay);

        // TEST
        strokeBuilder.addListener(strokeMonitorDisplay);

        cursorTracker.addListener(strokeBuilder);
    }
}
