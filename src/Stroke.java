import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Stroke {

    List<Point> pointList = new ArrayList<>();  // list of points along path
    List<Double> timeList = new ArrayList<>();  // time[k]: time from point[k-1] to point[k]

    double duration;

    public void addPoint(Point point, Double time) {
        pointList.add(point);
        timeList.add(time);
    }

    public void setDuration(Double strokeDuration) {
        duration = strokeDuration;
    }

    // Speed

    // Acceleration
}
