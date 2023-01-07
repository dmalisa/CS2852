/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class Graphs
 * Name: malisad
 * Created 3/15/2021
 */
package malisad;

/**
 * Course: CS2852
 * Term Spring 2021-2022
 * ItemRequest purpose: to initialize the user and item Id
 * create getters so that the Ids are accessible
 *
 * @author malisad
 * @version created on 3/15/2021 at 5:29 PM
 */
public class Dot {

    private double xCoordinate;
    private double yCoordinate;

    /**
     * method calculates the distance between two dots
     * @param a the first dot
     * @param b the second dot
     * @return the distance between two dots
     */
    private static double distance(Dot a, Dot b) {
        double xDifference = b.getxCoordinate() - a.getxCoordinate();
        double yDifference = b.getyCoordinate()- a.getyCoordinate();
        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }



    /**
     * constructor for the dot class
     * @param xCoordinate of the dot
     * @param yCoordinate of the dot
     */
    public Dot(double xCoordinate, double yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public double criticalValue(Dot previous, Dot next) {
        double criticalValue, d12, d13, d23;

        d12 = distance(previous, this);
        d13 = distance(previous, next);
        d23 = distance(this, next);
        criticalValue = d12 + d23 - d13;

        return criticalValue;
    }

}