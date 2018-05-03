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

    public void nativeMouseMoved(NativeMouseEvent e) {

        for (CursorTrackerListener listener : listeners) {
            listener.cursorMoved(e.getX(), e.getY());
        }
    }
    public void nativeMousePressed(NativeMouseEvent e) {

        for (CursorTrackerListener listener : listeners) {
            listener.cursorClicked(e.getX(), e.getY());
        }
    }

    // not in use
    public void nativeMouseDragged(NativeMouseEvent e) {}
    public void nativeMouseReleased(NativeMouseEvent e) {}
    public void nativeMouseClicked(NativeMouseEvent e) {}

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

    public void addListener(CursorTrackerListener toAdd) {
        listeners.add(toAdd);
    }
}
