import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    ArrayList<LineSegment> segs = null;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
    {
        segs = new ArrayList<LineSegment>();

        if (points == null)
            throw new NullPointerException();

        int length = points.length;
        int iLength = length - 3;
        int jLength = length - 2;
        int kLength = length - 1;

        for (int i = 0; i < length; i++)
        {
            if (points[i] == null)
                throw new NullPointerException();
        }

        for (int i = 0; i < iLength; i++)
        {
            Point iPoint = points[i];
            Comparator<Point> ptComp = iPoint.slopeOrder();
            jloop:
            for (int j = i+1; j < jLength; j++)
            {
                Point jPoint = points[j];
                for (int k = j+1; k < kLength; k++)
                {
                    Point kPoint = points[k];
                    for (int l = k+1; l < length; l++)
                    {
                        Point lPoint = points[l];
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