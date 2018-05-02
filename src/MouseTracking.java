import org.jfree.ui.RefineryUtilities;

public class MouseTracking {

    public static void main(String args[]) {
        CursorTracker cursorTracker = new CursorTracker();
        StrokeBuilder strokeBuilder = new StrokeBuilder();
        StrokeDisplay strokeDisplay = new StrokeDisplay();

        strokeDisplay.pack();
        RefineryUtilities.centerFrameOnScreen(strokeDisplay);
        strokeDisplay.setVisible(true);

        strokeBuilder.addListener(strokeDisplay);
        cursorTracker.addListener(strokeBuilder);
    }
}
