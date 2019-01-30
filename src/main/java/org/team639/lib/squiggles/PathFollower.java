package org.team639.lib.squiggles;

import one.util.streamex.StreamEx;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class PathFollower {
    private List<Vector> path;
    private double lookahead;
    private RateLimiter limiter;
    private double max_velocity;
    private double kVelocity;
    private double trackWidth;

    public PathFollower(List<Vector> path, double lookahead, RateLimiter limiter, double max_velocity, double kVelocity, double trackWidth) {
        this.path = path;
        this.lookahead = lookahead;
        this.limiter = limiter;
        this.max_velocity = max_velocity;
        this.kVelocity = kVelocity;
        this.trackWidth = trackWidth;
    }

    public static Optional<Vector> nextTarget(double lookaheadDistance, Vector currentLocation, List<Vector> path) {
        var points = StreamEx.of(path)
                .pairMap((v1, v2) -> intersection(currentLocation, lookaheadDistance, v1, v2))
                .filter((Optional::isPresent))
                .collect(Collectors.toList());

        if (!points.isEmpty()) {
            return points.get(points.size() - 1);
        } else {
            return Optional.empty();
        }
    }

    public static Optional<Vector> intersection(Vector center, double radius, Vector start, Vector end) {
        Vector d = end.subtract(start);
        Vector f = start.subtract(center);

        double a = d.dot(d);
        double b = 2 * f.dot(d);
        double c = f.dot(f) - pow(radius, 2);

        double discriminant = pow(b, 2) - 4 * a * c;

        System.out.println(discriminant);

        if (discriminant >= 0) {
            discriminant = sqrt(discriminant);

            System.out.println(discriminant);

            double t1 = (-1 * b - discriminant) / (2 * a);
            double t2 = (-1 * b + discriminant) / (2 * a);

            System.out.println(t1);
            System.out.println(t2);

            if (t2 >= 0 && t2 <= 1) {
                return Optional.of(start.add(d.multiply(t2)));
            } else if (t1 >= 0 && t1 <= 1) {
                return Optional.of(start.add(d.multiply(t1)));
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public static double curvature(Vector robot, Vector target, double robotAngle) {
        Vector lookahead = target.subtract(robot);
        double l  = lookahead.magnitude();

        double a = -1 * tan(robotAngle);
        double b = 1.0;
        double c = tan(robotAngle) * robot.x - robot.y;

        double x = abs(a * lookahead.x + b * lookahead.y + c) / sqrt(pow(a, 2) + pow(b, 2));
        double side = signum(sin(robotAngle) * (target.x - robot.x) - cos(robotAngle) * (target.y - robot.y));

        double curvature = 2.0 * x / pow(l, 2);

        return curvature * side;
    }

    public static int closest(Vector robot, List<Vector> path) {
        int index = 0;
        double magnitude = path.get(0).subtract(robot).magnitude();

        for (int i = 1; i < path.size(); i++) {
            double m = path.get(i).subtract(robot).magnitude();
            if (m < magnitude) {
                index = i;
                magnitude = m;
            }
        }

        return index;
    }

    public static DriveSignal velocities(double velocity, double curvature, double trackWidth) {
        double leftSpeed = velocity * (2.0 + curvature * trackWidth) / 2.0;
        double rightSpeed = velocity * (2.0 - curvature * trackWidth) / 2.0;

        return new DriveSignal(leftSpeed, rightSpeed);
    }

    public static class DriveSignal {
        public final double left;
        public final double right;

        public DriveSignal(double left, double right) {
            this.left = left;
            this.right = right;
        }
    }

    public static class RateLimiter {
        private double lastOutput;
        private double rate;

        public RateLimiter(double rate) {
            this(rate, 0);
        }

        public RateLimiter(double rate, double initialValue) {
            this.rate = abs(rate);
            this.lastOutput = initialValue;
        }

        public double limit(double input, double elapsedTime) {
            this.lastOutput = this.testLimit(input, elapsedTime);

            return this.lastOutput;
        }

        public double testLimit(double val, double time) {
            var change = this.rate * (time / 1000.0);
            double output;
            if (val > this.lastOutput + change) {
                output = this.lastOutput + change;
            } else if (val < this.lastOutput - change) {
                output = this.lastOutput - change;
            } else {
                output = val;
            }

            return output;
        }
    }

    public Optional<DriveSignal> followWithTime(Vector robotPosition, double robotAngle, long timeMS) {
        var next = nextTarget(lookahead, robotPosition, path);
        if (next.isPresent()) {
            var c = curvature(robotPosition, next.get(), robotAngle);
            return Optional.of(velocities(limiter.limit(min(max_velocity, kVelocity / abs(c)), timeMS), trackWidth, c));
        } else {
            return Optional.empty();
        }
    }
}
