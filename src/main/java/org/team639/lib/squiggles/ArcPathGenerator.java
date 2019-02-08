package org.team639.lib.squiggles;


import static java.lang.Math.*;

public class ArcPathGenerator {
    public static ArcPath generatePath(Vector robotPos, double robotAngle, Vector endPos, double endAngle) {
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

        return new ArcPath(diff, endAngle, radius, endAngle, diff > 0);
    }

    public static Vector intersection(Vector startPos, double startAngle, Vector endPos, double endAngle) {
        double x = (tan(startAngle) * startPos.x - tan(endAngle) * endPos.x - startPos.y + endPos.y) / (tan(startAngle) - tan(endAngle));
        double y = tan(startAngle) * (x - startPos.x) + startPos.y;

        return new Vector(x, y);
    }

    public static class ArcPath {
        private double straightDistance;
        private double straightAngle;
        private double radius;
        private double endAngle;
        private boolean straightFirst;

        public ArcPath(double straightDistance, double straightAngle, double radius, double endAngle, boolean straightFirst) {
            this.straightDistance = straightDistance;
            this.straightAngle = straightAngle;
            this.radius = radius;
            this.endAngle = endAngle;
            this.straightFirst = straightFirst;
        }
    }
}
