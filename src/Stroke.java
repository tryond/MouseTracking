import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Stroke implements Serializable {

    private ArrayList<Point> pointList = new ArrayList<>();  // list of points along path
    private ArrayList<Double> timeList = new ArrayList<>();  // time[k]: time from point[k-1] to point[k]
    private double duration;                            // time from start to finish

    public ArrayList<Point> getPointList() {
        return pointList;
    }

    public ArrayList<Double> getTimeList() {
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

    public void normalizeStroke() {
        pointList = StrokeNormalizer.normalizeTime(this);
    }

    public String getDirection() {

        if (pointList.isEmpty()) return "null";

        int x = pointList.get(pointList.size()-1).x - pointList.get(0).x;
        int y = pointList.get(pointList.size()-1).y - pointList.get(0).y;

        double rotAng = Math.toDegrees(Math.atan2(y, x));

        if (rotAng >= -22.5 && rotAng < 22.5) {
            return "E";
        }
        else if (rotAng >= -67.5 && rotAng < 22.5) {
            return "NE";
        }
        else if (rotAng >= -112.5 && rotAng < 67.5) {
            return "N";
        }
        else if (rotAng >= -157.5 && rotAng < 112.5) {
            return "NW";
        }
        else if (rotAng < -157.5 || rotAng >= 157.5) {
            return "W";
        }
        else if (rotAng < 157.5 && rotAng >= 112.5) {
            return "SW";
        }
        else if (rotAng < 112.5 && rotAng >= 67.5) {
            return "S";
        }
        else if (rotAng < 67.5 && rotAng >= 22.5) {
            return "SE";
        }
        else {
            return "null";
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Point point : pointList) {
            stringBuilder.append(point.x + " ");
            stringBuilder.append(point.y + " ");
        }
        return stringBuilder.toString();
    }

}