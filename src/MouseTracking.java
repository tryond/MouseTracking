import org.jfree.ui.RefineryUtilities;

public class MouseTracking {

    public static void main(String args[]) {
        CursorTracker cursorTracker = new CursorTracker();
        StrokeBuilder strokeBuilder = new StrokeBuilder();


        StrokeMonitorDisplay strokeMonitorDisplay = new StrokeMonitorDisplay();

        // TEST
        // TargetOriginDisplay targetOriginDisplay = new TargetOriginDisplay();

        // TEST
        // strokeBuilder.addListener(targetOriginDisplay);

        // TEST
        strokeBuilder.addListener(strokeMonitorDisplay);

        // Add StrokeLogger
        // StrokeLogger strokeLogger = new StrokeLogger();
        // StrokeBuilder.addListener(strokeLogger);

        cursorTracker.addListener(strokeBuilder);
    }
}
