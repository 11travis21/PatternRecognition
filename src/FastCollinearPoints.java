import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segs = null;

    public FastCollinearPoints(Point[] points)
    {
        //Validate input array
        if (points == null)
            throw new NullPointerException();

        Arrays.sort(points);

        //Make sure the first element is not null
        if (points[0] == null)
            throw new NullPointerException();

        for (int i = 1; i < points.length; i++)
        {
            if (points[i] == null)
                throw new NullPointerException();

            if (points[i] == points[i-1])
                throw new IllegalArgumentException();
        }

        /* The input checks out
            Start processing
         */
        segs = new ArrayList<LineSegment>();
        int length = points.length;

        for (int i = 0; i < length; i++)
        {
            Point origin = points[i];
            Comparator<Point> ptComp = origin.slopeOrder();
            Arrays.sort(points, ptComp);
            int count = 1;
            double currentSlope = Double.NEGATIVE_INFINITY;
            for (int j = 0; j < length; j++)
            {
                double newSlope = origin.slopeTo(points[j]);
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
                            pointAry[index++] = points[k];

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