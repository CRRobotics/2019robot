package org.team639.lib.squiggles;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class ArcPathGenerator {
    public static SimpleArcPath generateSimplePath(Vector startPos, double startAngle, Vector endPos, double endAngle) {

        startAngle %= (2.0 * PI);
        endAngle %= (2.0 * PI);

        var intersect = intersection(startPos, startAngle, endPos, endAngle);

        var sti = startPos.subtract(intersect);
        var ite = endPos.subtract(intersect);

        var diff = sti.magnitude() - ite.magnitude();

        if (diff < 0) {
            ite = ite.multiply(sti.magnitude() / ite.magnitude());
        } else {
            sti = sti.multiply(ite.magnitude() / sti.magnitude());
        }

        var center = intersection(sti, startAngle + PI / 2.0, ite, endAngle + PI / 2.0);

        double radius = center.subtract(sti).magnitude();

        return new SimpleArcPath(abs(diff), endAngle, radius, endAngle, diff > 0, (endAngle - startAngle) > 0 ? Direction.Left : Direction.Right);
    }

    public static Vector intersection(Vector startPos, double startAngle, Vector endPos, double endAngle) {
        if (startAngle == PI / 2.0 || startAngle == 3.0 * PI / 2.0) startAngle += 0.001;
        if (endAngle == PI / 2.0 || endAngle == 3.0 * PI / 2.0) endAngle += 0.001;
        double x = (tan(startAngle) * startPos.x - tan(endAngle) * endPos.x - startPos.y + endPos.y) / (tan(startAngle) - tan(endAngle));
        double y = tan(startAngle) * (x - startPos.x) + startPos.y;

        return new Vector(x, y);
    }

    public static List<PathSegment> generateComplexPath(Vector startPos, double startAngle, Vector endPos, double endAngle) {
        startAngle %= (2.0 * PI);
        endAngle %= (2.0 * PI);

        var intersect = intersection(startPos, startAngle, endPos, endAngle);

        boolean b1 = endAngle <= PI / 2.0 && endAngle >= -1 * PI / 2.0 && intersect.x < endPos.x;
        boolean b2 = (endAngle >= PI / 2.0 || endAngle <= -1 * PI / 2.0) && intersect.x > endPos.x;

        if (b1 || b2) {
            return generateSimplePath(startPos, startAngle, endPos, endAngle).toComplex();
        } else {
            var midpoint = new Vector((startPos.x + endPos.x) / 2.0, (startPos.y + endPos.y) / 2.0);
            var angle = atan2(endPos.y - startPos.y, endPos.x - startPos.x);
            var avg = ((angle - startAngle) + (angle - endAngle)) / 2.0;

            var midAngle = angle + avg;

            return generateComplexPath(List.of(
                    new Position(startPos, startAngle),
                    new Position(midpoint, midAngle),
                    new Position(endPos, endAngle)
            ));
        }
    }

    public static List<PathSegment> generateComplexPath(List<Position> positions) {
        var path = new ArrayList<PathSegment>();
        for (int i = 0; i < positions.size() - 1; i++) {
            var p1 = positions.get(i);
            var p2 = positions.get(i + 1);

            var segment = generateSimplePath(p1.vector, p1.angle, p2.vector, p2.angle);

            path.addAll(segment.toComplex());
        }

        return path;
    }

    /**
     * Given the faster of two wheel speeds, computes the slower speed necessary to travel the specified arc.
     * @param outerSpeed The speed of the outer wheels.
     * @param radius The radius of the arc.
     * @param trackWidth The distance between the outer and inner wheels.
     * @return The speed the inner wheels should be set to.
     */
    public static double innerSpeed(double outerSpeed, double radius, double trackWidth) {
        return outerSpeed * ((2.0 * radius / trackWidth) - 1.0) / ((2.0 * radius / trackWidth) + 1.0);
    }

    public static class SimpleArcPath {
        public final double straightDistance;
        public final double straightAngle;
        public final double radius;
        public final double endAngle;
        public final boolean straightFirst;
        public final Direction direction;

        public SimpleArcPath(double straightDistance, double straightAngle, double radius, double endAngle, boolean straightFirst, Direction direction) {
            this.straightDistance = straightDistance;
            this.straightAngle = straightAngle;
            this.radius = radius;
            this.endAngle = endAngle;
            this.straightFirst = straightFirst;
            this.direction = direction;
        }

        public List<PathSegment> toComplex() {
            var path = new ArrayList<PathSegment>();

            path.add(PathSegment.arc(new ArcSegment(this.direction, this.endAngle, this.radius)));

            if (this.straightFirst && this.straightDistance > 0) {
                path.add(0, PathSegment.straight(new StraightSegment(this.straightAngle, this.straightDistance)));
            } else if (this.straightDistance > 0) {
                path.add(PathSegment.straight(new StraightSegment(this.straightAngle, this.straightDistance)));
            }

            return path;
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

        public static PathSegment straight(StraightSegment segment) {
            return new PathSegment(SegmentType.Straight, segment, null);
        }

        public static PathSegment arc(ArcSegment segment) {
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

    public static class Position {
        public final Vector vector;
        public final double angle;

        public Position(Vector vector, double angle) {
            this.vector = vector;
            this.angle = angle;
        }
    }
}
