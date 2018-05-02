import org.jnativehook.mouse.NativeMouseEvent;

public interface CursorTrackerListener {
    void cursorClicked(int xpos, int ypos);
    void cursorMoved(int xpos, int ypos);
}
