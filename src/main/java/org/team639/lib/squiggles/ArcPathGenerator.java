package org.team639.lib.squiggles;


import static java.lang.Math.*;

public class ArcPathGenerator {
    public static ArcPath generatePath(Vector robotPos, double robotAngle, Vector endPos, double endAngle) {

        robotAngle %= (2.0 * PI);
        endAngle %= (2.0 * PI);

        var intersect = intersection(robotPos, robotAngle ,endPos, endAngle);

        var sti = robotPos.subtract(intersect);
        var ite = endPos.subtract(intersect);

        var diff = sti.magnitude() - ite.magnitude();

        if (diff < 0) {
            ite = ite.multiply(sti.magnitude() / ite.magnitude());
        } else {
            sti = sti.multiply(ite.magnitude() / sti.magnitude());
        }

        var center = intersection(sti, robotAngle + PI / 2.0, ite, endAngle + PI / 2.0);

        double radius = center.subtract(sti).magnitude();

        return new ArcPath(diff, endAngle, radius, endAngle, diff > 0, (endAngle - robotAngle) > 0 ? Direction.Left : Direction.Right);
    }

    public static Vector intersection(Vector startPos, double startAngle, Vector endPos, double endAngle) {
        double x = (tan(startAngle) * startPos.x - tan(endAngle) * endPos.x - startPos.y + endPos.y) / (tan(startAngle) - tan(endAngle));
        double y = tan(startAngle) * (x - startPos.x) + startPos.y;

        return new Vector(x, y);
    }

    /**
     * Given the faster of two wheel speeds, computes the slower speed necessary to travel the specified arc.
     * @param outerSpeed The speed of the outer wheels.
     * @param radius The radius of the arc.
     * @param trackWidth The distance between the outer and inner wheels.
     * @return The speed the inner wheels should be set to.
     */
    public static double innerSpeed(double outerSpeed, double radius, double trackWidth) {
        return outerSpeed * ((2 * radius / trackWidth) - 1) / ((2 * radius / trackWidth) + 1);
    }

    public static class ArcPath {
        public final double straightDistance;
        public final double straightAngle;
        public final double radius;
        public final double endAngle;
        public final boolean straightFirst;
        public final Direction direction;

        public ArcPath(double straightDistance, double straightAngle, double radius, double endAngle, boolean straightFirst, Direction direction) {
            this.straightDistance = straightDistance;
            this.straightAngle = straightAngle;
            this.radius = radius;
            this.endAngle = endAngle;
            this.straightFirst = straightFirst;
            this.direction = direction;
        }
    }

    public static class PathSegment {
        public final SegmentType type;
        public final StraightSegment straightSegment;
        public final ArcSegment arcSegment;

        private PathSegment(SegmentType type, StraightSegment straightSegment, ArcSegment arcSegment) {
            this.type = type;
            this.straightSegment = straightSegment;
            this.arcSegment = arcSegment;
        }

        public PathSegment straight(StraightSegment segment) {
            return new PathSegment(SegmentType.Straight, segment, null);
        }

        public PathSegment arc(ArcSegment segment) {
            return new PathSegment(SegmentType.Arc, null, segment);
        }
    }

    public static class StraightSegment {
        public final double angle;
        public final double distance;

        public StraightSegment(double angle, double distance) {
            this.angle = angle;
            this.distance = distance;
        }
    }

    public static class ArcSegment {
        public final Direction direction;
        public final double endAngle;
        public final double radius;

        public ArcSegment(Direction direction, double endAngle, double radius) {
            this.direction = direction;
            this.endAngle = endAngle;
            this.radius = radius;
        }
    }

    public enum SegmentType {
        Arc,
        Straight
    }

    public enum Direction {
        Left,
        Right
    }
}
