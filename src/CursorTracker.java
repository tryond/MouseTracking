import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class CursorTracker implements NativeMouseInputListener {

    private List<CursorTrackerListener> listeners = new ArrayList<>();

    public void addListener(CursorTrackerListener toAdd) {
        listeners.add(toAdd);
    }

    public void nativeMouseClicked(NativeMouseEvent e) {

        // System.out.println("Mouse Clicked");

        for (CursorTrackerListener listener : listeners) {
            listener.cursorClicked(e.getX(), e.getY());
        }
    }

    public void nativeMouseMoved(NativeMouseEvent e) {

        // System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());

        for (CursorTrackerListener listener : listeners) {
            listener.cursorMoved(e.getX(), e.getY());
        }
    }

    // Not in use for cursor tracker
    public void nativeMousePressed(NativeMouseEvent e) {

        // System.out.println("Pressed");

        for (CursorTrackerListener listener : listeners) {
            listener.cursorClicked(e.getX(), e.getY());
        }
    }

    public void nativeMouseDragged(NativeMouseEvent e) {

        // System.out.println("Dragged");

//        for (CursorTrackerListener listener : listeners) {
//            listener.cursorClicked(e.getX(), e.getY());
//        }
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        // System.out.println("Mouse Released: " + e.getButton());
    }

    public CursorTracker() {
        // Set global screen hook
        try {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());

            System.exit(1);
        }

        // Disable console logs by jnativehook
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        logger.setUseParentHandlers(false);

        // Add the appropriate listeners.
        GlobalScreen.addNativeMouseListener(this);
        GlobalScreen.addNativeMouseMotionListener(this);
    }

}
