import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segs = null;
    private Point[] pts = null;

    public FastCollinearPoints(Point[] points)
    {
        pts = Arrays.copyOf(points, points.length);

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
        segs = new ArrayList<LineSegment>();
        int length = pts.length;

        for (int i = 0; i < length; i++)
        {
            Point origin = pts[i];
            Comparator<Point> ptComp = origin.slopeOrder();
            Arrays.sort(pts, ptComp);
            int count = 1;
            double currentSlope = Double.NEGATIVE_INFINITY;
            for (int j = 0; j < length; j++)
            {
                double newSlope = origin.slopeTo(pts[j]);
                if (newSlope == currentSlope)
                    count++;
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
        }
        segs.sort(new SegmentationComparator());
    }

    private static class SegmentationComparator implements Comparator<LineSegment>
    {
        public int compare(LineSegment v, LineSegment w)
        {
            return v.toString().compareTo(w.toString());
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