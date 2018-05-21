import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Stroke implements Serializable {

    private List<Point> pointList = new ArrayList<>();  // list of points along path
    private List<Double> timeList = new ArrayList<>();  // time[k]: time from point[k-1] to point[k]
    private double duration;                            // time from start to finish

    public List<Point> getPointList() {
        return pointList;
    }

    public List<Double> getTimeList() {
        return timeList;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(Double strokeDuration) {
        duration = strokeDuration;
    }

    public void addPoint(Point point, Double time) {
        pointList.add(point);
        timeList.add(time);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");

        return stringBuilder.toString();
    }

//    public void normalizeStroke() {
//
//    }

    // Speed

    // Acceleration
}