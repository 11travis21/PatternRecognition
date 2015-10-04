import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segs = null;
    private Point[] pts = null;

    public FastCollinearPoints(Point[] points)
    {
        pts = Arrays.copyOf(points, points.length);
        segs = new ArrayList<LineSegment>();

        //Validate input array
        if (pts == null)
            throw new NullPointerException();

        Arrays.sort(pts);

        //Make sure the first element is not null
        if (pts[0] == null)
            throw new NullPointerException();

        for (int i = 1; i < pts.length; i++)
        {
            if (pts[i] == null)
                throw new NullPointerException();

            if (pts[i].compareTo(pts[i-1]) == 0)
                throw new IllegalArgumentException();
        }

        /* The input checks out
            Start processing
         */
        int length = pts.length;

        for (int i = 0; i < length; i++)
        {
            pts = Arrays.copyOf(points, points.length);
            Point origin = pts[i];
            Comparator<Point> ptComp = origin.slopeOrder();
            Arrays.sort(pts, ptComp);
            int count = 0;
            double currentSlope = Double.NaN;
            for (int j = 0; j < length; j++)
            {
                if (pts[j].compareTo(origin) == 0)
                    continue;

                double newSlope = origin.slopeTo(pts[j]);
                if (newSlope == currentSlope)
                {
                    count++;
                }
                else
                {
                    if (count >= 3)
                    {
                        Point[] pointAry = new Point[count+1];
                        pointAry[0] = origin;
                        int index = 1;
                        for (int k = (j - count); k < j; k++)
                            pointAry[index++] = pts[k];

                        Arrays.sort(pointAry);
                        LineSegment newSeg = new LineSegment(pointAry[0], pointAry[count]);
                        segs.add(newSeg);
                    }
                    count = 1;
                    currentSlope = newSlope;
                }
            }
            if (count >= 3)
            {
                Point[] pointAry = new Point[count+1];
                pointAry[0] = origin;
                int index = 1;
                for (int k = (length - count); k < length; k++)
                    pointAry[index++] = pts[k];

                Arrays.sort(pointAry);
                LineSegment newSeg = new LineSegment(pointAry[0], pointAry[count]);
                segs.add(newSeg);
            }
        }

        segs.sort(new SegmentationComparator());

        for (int i = segs.size() - 1; i > 0; i--)
        {
            if (segs.get(i).toString().equals(segs.get(i - 1).toString()))
                segs.remove(i);
        }

    }

    private static class SegmentationComparator implements Comparator<LineSegment>
    {
        public int compare(LineSegment v, LineSegment w)
        {
            return v.toString().compareTo(w.toString());
        }
    }

    private static class PointComparator implements Comparator<Point>
    {
        public int compare(Point v, Point w)
        {
            return v.compareTo(w);
        }
    }

    //return number of line segments
    public int numberOfSegments()
    {
        return segs.size();
    }

    //return array of line segments
    public LineSegment[] segments()
    {
        return segs.toArray(new LineSegment[segs.size()]);
    }
}