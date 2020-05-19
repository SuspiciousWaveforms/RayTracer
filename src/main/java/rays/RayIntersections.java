package main.java.rays;

public class RayIntersections {
    double intersection1;
    double intersection2;


    public RayIntersections(double intersection1, double intersection2) {
        this.intersection1 = intersection1;
        this.intersection2 = intersection2;
    }

//    public boolean checkIntersection1InBounds(double intersectionMin, double intersectionMax) {
//        return checkIntersectionInBounds(intersection1, intersectionMin, intersectionMax);
//    }
//
//    public boolean checkIntersection2InBounds(double intersectionMin, double intersectionMax) {
//        return checkIntersectionInBounds(intersection1, intersectionMin, intersectionMax);
//    }
//
//    public boolean checkIntersectionInBounds(double intersection, double intersectionMin, double intersectionMax) {
//        return intersection >= intersectionMin && intersection <= intersectionMax;
//    }

    public double getIntersection1() {
        return intersection1;
    }

    public double getIntersection2() {
        return intersection2;
    }
}
