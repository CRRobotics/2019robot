package org.team639.lib.squiggles;

public class Squiggles {
    private static native long createPathFollower(double startingLeft, double startingRight, double x1, double y1,
                                                  double startAngle, double x2, double y2, double endAngle,
                                                  double trackWidth, double kVelocity, double maxVelocity,
                                                  double lookahead, double rate);
    private static native void destroyPathFollower(long followerPtr);
    private static native int updateFollwer(long followerPtr, double x, double y, double angle, int dt);
    private static native double getLeftVelocity(long followerPtr);
    private static native double getRightVelocity(long followerPtr);

    static {
        System.loadLibrary("squigglejni");
    }

    public static class PathFollower implements AutoCloseable {
        private final long ptr;

        private long last;

        public PathFollower(DriveSignal currentSignal, double x, double y, double angle, PFConfig config) {
            ptr = createPathFollower(currentSignal.left, currentSignal.right, 0, 0, Math.PI / 2.0, x, y,
                    angle, config.trackWidth, config.kVelocity, config.maxVelocity, config.lookahead, config.rate);
            last = System.currentTimeMillis();
        }

        public void close() {
            destroyPathFollower(ptr);
        }

        public PathStatus follow(double x, double y, double angle) {
            long now = System.currentTimeMillis();
            long dt = now - last;
            last = now;
            return PathStatus.fromInt(updateFollwer(ptr, x, y, angle, (int)dt));
        }

        public DriveSignal getDriveSignal() {
            return new DriveSignal(getLeftVelocity(ptr), getRightVelocity(ptr));
        }
    }

    public static class PFConfig {
        public final double trackWidth;
        public final double kVelocity;
        public final double maxVelocity;
        public final double lookahead;
        public final double rate;

        public PFConfig(double trackWidth, double kVelocity, double maxVelocity, double lookahead, double rate) {
            this.trackWidth = trackWidth;
            this.kVelocity = kVelocity;
            this.maxVelocity = maxVelocity;
            this.lookahead = lookahead;
            this.rate = rate;
        }
    }

    public enum PathStatus {
        FOLLOWING(1),
        FINISHED(2),
        LOST(3),
        UNKNOWN(4);

        public final int val;

        PathStatus(int val) {
            this.val = val;
        }

        public static PathStatus fromInt(int val) {
            PathStatus status;
            switch (val) {
                case 1:
                    status = PathStatus.FOLLOWING;
                    break;
                case 2:
                    status = PathStatus.FINISHED;
                    break;
                case 3:
                    status = PathStatus.LOST;
                    break;
                default:
                    status = PathStatus.UNKNOWN;
                    break;
            }

            return status;
        }
    }

    public static class DriveSignal {
        public final double left;
        public final double right;

        public DriveSignal(double left, double right) {
            this.left = left;
            this.right = right;
        }
    }

    public static void main(String[] args) {
        Squiggles.PFConfig c = new Squiggles.PFConfig(32, 3, 5, 3, 3);

        Squiggles.PathFollower pf = new Squiggles.PathFollower(new DriveSignal(0, 0), 5, 5, Math.PI / 2.0, c);
    }
}
