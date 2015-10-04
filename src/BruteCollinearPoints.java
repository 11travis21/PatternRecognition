import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segs = null;
    private Point[] pts = null;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
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
        int iLength = length - 3;
        int jLength = length - 2;
        int kLength = length - 1;

        for (int i = 0; i < iLength; i++)
        {
            Point iPoint = pts[i];
            Comparator<Point> ptComp = iPoint.slopeOrder();
            jloop:
            for (int j = i+1; j < jLength; j++)
            {
                Point jPoint = pts[j];
                for (int k = j+1; k < kLength; k++)
                {
                    Point kPoint = pts[k];
                    for (int l = k+1; l < length; l++)
                    {
                        Point lPoint = pts[l];
                        if (ptComp.compare(jPoint, kPoint) == 0 && ptComp.compare(kPoint, lPoint) == 0)
                        {
                            Point[] pointAry = new Point[4];
                            pointAry[0] = iPoint;
                            pointAry[1] = jPoint;
                            pointAry[2] = kPoint;
                            pointAry[3] = lPoint;

                            Arrays.sort(pointAry);

                            LineSegment newSeg = new LineSegment(pointAry[0], pointAry[3]);
                            segs.add(newSeg);
                            continue jloop;
                        }
                    }
                }
            }
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