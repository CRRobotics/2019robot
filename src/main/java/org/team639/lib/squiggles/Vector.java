package org.team639.lib.squiggles;

import static java.lang.Math.*;

public class Vector {
    public final double x;
    public final double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double angle() {
        return atan2(this.y, this.x);
    }

    public Vector add(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    public Vector subtract(Vector other) {
        return new Vector(this.x - other.x, this.y - other.y);
    }

    public double dot(Vector other) {
//        return this.magnitude() * other.magnitude() * cos(atan2(other.y, other.x) - atan2(this.y, this.x));
        return this.x * other.x + this.y * other.y;
    }

    public double magnitude() {
        return sqrt(pow(x, 2) + pow(y, 2));
    }

    public Vector multiply(double scalar) {
        double angle = this.angle();
        return new Vector(this.magnitude() * scalar * cos(angle), this.magnitude() * scalar * sin(angle));
    }

    public String toString() {
        return "<" + this.x + ", " + this.y + ">";
    }

    public boolean equals(Vector other) {
        return this.x == other.x && this.y == other.y;
    }
}
