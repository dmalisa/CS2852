/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class Graphs
 * Name: malisad
 * Created 3/15/2021
 */
package malisad;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.*;


/**
 * Course: CS2852
 * Term Spring 2021-2022
 * ItemRequest purpose: to initialize the user and item Id
 * create getters so that the Ids are accessible
 *
 * @author malisad
 * @version created on 3/15/2021 at 5:29 PM
 */

public class Picture {

    private static List<Dot> originalDot;
    double xCoordinate;
    double yCoordinate;
    private static final double NS_TO_MS = Math.pow(10.0, -6);
    private final List<Dot> dots;
    private  List<Dot> originalDots;
    Dot dot;

    public Picture(List<Dot> emptyList) {
        dots = emptyList;
        originalDots = emptyList;
    }

    public Picture(Picture original, List<Dot> emptyList) {
        this.dots = emptyList;
        this.dots.addAll(original.dots);
    }


    /**
     * loads all dots from a .dot file
     *
     * @param path path for .dot file
     * @throws IOException if the input of the file is read incorrectly
     */
    public void load(Path path) throws IOException {

        Scanner input = new Scanner(path);

        while (input.hasNextLine()) {
            String[] xAndY = input.nextLine().split(",");
            xCoordinate = Double.parseDouble((xAndY[0]));
            yCoordinate = Double.parseDouble(xAndY[1]);
            dot = new Dot(xCoordinate, yCoordinate);
            dots.add(dot);
            originalDots.add(dot);
        }
    }

    /**
     * saves all of the dots to a .dot  file
     *
     * @param path the path of a .dot file
     */
    public void save(Path path) throws FileNotFoundException {

        PrintWriter writer = new PrintWriter(path.toFile());
        for (Dot dot : dots) {
            writer.write(dot.getxCoordinate() + ", " + dot.getyCoordinate() + "\n");
        }
        writer.close();

    }

    /**
     * method draws each dot in the picture onto the canvas
     *
     * @param canvas the canvas being drawn to
     */
    public void drawDots(Canvas canvas) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        for (Dot dot : dots) {
            graphicsContext.fillOval(dot.getxCoordinate() * canvas.getWidth(), (1 - dot.getyCoordinate()) * canvas.getHeight(),
                    7, 7);
        }
    }

    /**
     * draws lines between all the neighboring
     * dots in the picture on the canvas
     *
     * @param canvas the canvas being drawn
     */
    public void drawLines(Canvas canvas) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.beginPath();
//        graphicsContext.moveTo(dot.getxCoordinate() * canvas.getWidth(),
//                (1 - dot.getyCoordinate()) * canvas.getHeight());
        for (Dot dot : dots) {
            graphicsContext.lineTo(dot.getxCoordinate() * canvas.getWidth(),
                    (1 - dot.getyCoordinate()) * canvas.getHeight());
        }
        graphicsContext.closePath();
        graphicsContext.stroke();
    }

    /**
     * removes all but the numberDesired most critical val dots in the picture.
     *
     * @param numberDesired the number of dots the user wants to keep
     */
    public void removeDots(int numberDesired, String strategy) {

        if (strategy.equals("remove dots index")) {
            removeDotsIndex(dots, numberDesired);
            System.out.println("removeDotsIndex : " + (double) removeDotsIndex(dots,numberDesired));
//        } else if (strategy.equals("remove dots iterator")) {
//            removeDotsIterator(dots, numberDesired);
        }

    }

    /**
     * method removes all the dots but the number desired by the user
     * and the time taken to execute the method is calculated
     *
     * @param dots          an array of dots being passed into the method
     * @param numberDesired number of dots user wants to remain in the picture
     * @return the number of nanoseconds required for the algorithm to run
     */
    private static long removeDotsIndex(List<Dot> dots, int numberDesired) {

        originalDot = dots;
        final long startTime = System.nanoTime();
        int totalDots = dots.size();
        double value;

        if (numberDesired < 3) {
            throw new IllegalArgumentException();
        }

        if (numberDesired > totalDots) {
            dots = originalDot;
            totalDots = originalDot.size();
        }

        while (totalDots > numberDesired) {

            double lowestCriticalVal = Double.MAX_VALUE;
            int lowestIndex = -1;
            double firstCritical = dots.get(0).criticalValue(dots.get(totalDots - 1), dots.get(1));
            double lastCritical = dots.get(totalDots - 1).criticalValue(dots.get(totalDots - 2), dots.get(0));


            if (firstCritical < lowestCriticalVal) {
                lowestCriticalVal = firstCritical;
                lowestIndex = 0;
            } else if (lastCritical < lowestCriticalVal) {
                lowestCriticalVal = lastCritical;
                lowestIndex = totalDots - 1;
            }

            for (int x = 1; x < totalDots - 1; x++) {
                Dot previous = dots.get(x - 1);
                Dot currentDot = dots.get(x);
                Dot nextDot = dots.get(x + 1);

                value = currentDot.criticalValue(previous, nextDot);

                if (value < lowestCriticalVal) {
                    lowestCriticalVal = value;
                    lowestIndex = x;
                }
            }

            dots.remove(lowestIndex);
            totalDots--;
        }

        final long endTime = System.nanoTime();
        final long elapsed = endTime - startTime;

        return elapsed ;
    }

    /**
     * method removes all the dots but the number desired by the user
     * and the time taken to execute the method is calculated
     * uses one or more iterators to traverse the collection of dots
     *
     * @param dots          the dots being passed into the method
     * @param numberDesired the number of dots desired by the user
     * @return the time taken to perform the tasks
     **/
//    private static  long removeDotsIterator(Collection<Dot> dots, int numberDesired) {
//
//
//        final long startTime = System.nanoTime();
//        int totalDots = dots.size();
//        double value;
//        Dot firstDot, lastDot = null, lowestDot = null;
//        double lowestCriticalVal = Double.MAX_VALUE;
//
//
//        if (numberDesired < 3) {
//            throw new IllegalArgumentException();
//        }
//
//        if (numberDesired > totalDots) {
//            dots = originalDot;
//            totalDots = originalDot.size();
//        }
//
//        while (totalDots > numberDesired) {
//
//            Iterator<Dot> currentIterator = dots.iterator();
//            Iterator<Dot> previousIterator = dots.iterator();
//            Iterator<Dot> nextIterator = dots.iterator();
//
//            firstDot = previousIterator.next();
//           currentIterator.next();
//            Dot currentDot = currentIterator.next();
//            nextIterator.next();
//            nextIterator.next();
//           // Dot thirdDot = nextIterator.next();
//          while (previousIterator.hasNext()) {
//              Dot secondFromLast = null;
//              if (!(nextIterator.hasNext())) {
//                  secondFromLast = currentIterator.next();
//                  lastDot = previousIterator.next();
//              }
//              double firstCritical = firstDot.criticalValue(lastDot, currentDot);
//              double lastCritical = lastDot.criticalValue(secondFromLast, firstDot);
//          }
//
//
//
//            if (lastCritical < lowestCriticalVal) {
//                lowestCriticalVal = lastCritical;
//                lowestDot = lastDot;
//            } else if (firstCritical < lowestCriticalVal) {
//                lowestCriticalVal = firstCritical;
//                lowestDot = firstDot;
//            }
//
//            while (currentIterator.hasNext()){
//                Dot thirdDot = nextIterator.next();
//                value = currentDot.criticalValue(firstDot,thirdDot);
//
//                if (value < lowestCriticalVal) {
//                    lowestCriticalVal = value;
//                    lowestDot = currentDot;
//                }
//                previousIterator.next();
//                currentIterator.next();
//
//                if (!(nextIterator.hasNext())){
//                    Dot secondFromLast = currentIterator.next();
//                    lastDot = nextIterator.next();
//                }
//
//            }
//
//
////            iterator.previous();
////            lastDot = iterator.previous();
////
////            Dot secondToLastDot = iterator.previous();
//
////            double firstCritical = firstDot.criticalValue(lastDot, secondDot);
////            double lastCritical = lastDot.criticalValue(secondToLastDot, firstDot);
//
//
//            if (lastCritical < lowestCriticalVal) {
//                lowestCriticalVal = lastCritical;
//                lowestDot = lastDot;
//            } else if (firstCritical < lowestCriticalVal) {
//                lowestCriticalVal = firstCritical;
//                lowestDot = firstDot;
//            }
//
//            currentIterator = ((List<Dot>) dots).listIterator();
//
//            Dot previousDot = currentIterator.next();
//            Dot currentDot = currentIterator.next();
//            while (currentIterator.hasNext()) {
//                Dot nextDot = currentIterator.next();
//                value = currentDot.criticalValue(previousDot, nextDot);
//
//                if (value < lowestCriticalVal) {
//                    lowestCriticalVal = value;
//                    lowestDot = currentDot;
//                }
//
//                currentDot = previousDot;
//                previousDot = nextDot;
//
//            }
//
//            dots.remove(lowestDot);
//            totalDots--;
//        }
//
//        final long endTime = System.nanoTime();
//        final long elapsed = endTime - startTime;
//
//        return elapsed;
//
//    }

}
