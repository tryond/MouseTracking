package main.java;

public class MouseTracking {

    public static void main(String args[]) {
        CursorTracker cursorTracker = new CursorTracker();
        StrokeBuilder strokeBuilder = new StrokeBuilder();

        StrokeMonitorDisplay strokeMonitorDisplay = new StrokeMonitorDisplay();

        strokeBuilder.addListener(strokeMonitorDisplay);
        cursorTracker.addListener(strokeBuilder);
    }
}
