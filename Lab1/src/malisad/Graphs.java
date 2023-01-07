/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class Graphs
 * Name: malisad
 * Created 3/15/2021
 */
package malisad;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Course: CS2852
 * Term Spring 2021-2022
 * Graphs purpose:
 *
 * @author malisad
 * @version created on 3/15/2021 at 5:29 PM
 */
public class Graphs {

    public static class ScatterChartSample extends Application {

        private static final double NS_TO_MS = Math.pow(10.0, -6);

        /**
         * measures the time to call remove(0) until a list that
         * starts with nElements elements is empty
         * @param list list being passed into the benchmark
         * @param nElements number of elements to be added to the list
         * @return the time taken to perform the task
         */
        public static double removeFromFrontBenchmark(List<String> list, int nElements) {

            list.clear();
            for (int i = 0; i < nElements; i++) {
                list.add("k");
            }

            final long startTime = System.nanoTime();
            for (int i = 0; i < nElements; i++) {
                list.remove(0);
            }

            final long endTime = System.nanoTime();
            final long elapsed = endTime - startTime;

            return elapsed * NS_TO_MS;
        }

        /**
         * measures the time to call get(nElement/2) 100,000 times
         * @param list list being passed into the benchmark
         * @param nElements number of elements to be added to the list
         * @return the time taken to perform the task
         */
        public static double getMiddleBenchmark(List<String> list, int nElements) {

            list.clear();
            for (int i = 0; i < nElements; i++) {
                list.add("k");
            }

            final long startTime = System.nanoTime();
            for (int i = 0; i < 100_000; i++) {
                list.get(nElements / 2);
            }
            final long endTime = System.nanoTime();
            final long elapsed = endTime - startTime;

            return elapsed * NS_TO_MS;
        }

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) {

            stage.setTitle("Scatter Chart Sample");
            final NumberAxis xAxis = new NumberAxis(0, 430_000, 10_000);
            final NumberAxis yAxis = new NumberAxis(0, 8_000, 100);
            final ScatterChart<Number, Number> sc = new
                    ScatterChart<>(xAxis, yAxis);
            xAxis.setLabel("Number of Elements");
            yAxis.setLabel("Time in Milliseconds");
            sc.setTitle("Remove from Front");

            ArrayList<String> arrayList = new ArrayList<>();
            LinkedList<String> linkedList = new LinkedList<>();

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("ArrayList");
            series1.getData().add(new XYChart.Data(20_000, removeFromFrontBenchmark(arrayList, 20_000)));
            series1.getData().add(new XYChart.Data(40_000, removeFromFrontBenchmark(arrayList, 40_000)));
            series1.getData().add(new XYChart.Data(200_000, removeFromFrontBenchmark(arrayList, 200_000)));
            series1.getData().add(new XYChart.Data(400_000, removeFromFrontBenchmark(arrayList, 400_000)));

            XYChart.Series series2 = new XYChart.Series();
            series2.setName("LinkedList");
            series2.getData().add(new XYChart.Data(20_000, removeFromFrontBenchmark(linkedList, 20_000)));
            series2.getData().add(new XYChart.Data(40_000, removeFromFrontBenchmark(linkedList, 40_000)));
            series2.getData().add(new XYChart.Data(200_000, removeFromFrontBenchmark(linkedList, 200_000)));
            series2.getData().add(new XYChart.Data(400_000, removeFromFrontBenchmark(linkedList, 400_000)));

            final NumberAxis xAxisGraph2 = new NumberAxis(0, 480_000, 10_000);
            final NumberAxis yAxisGraph2 = new NumberAxis(0, 50_000, 10_00);
            final ScatterChart<Number, Number> scGraph2 = new
                    ScatterChart<>(xAxisGraph2, yAxisGraph2);
            xAxisGraph2.setLabel("Number of Elements");
            yAxisGraph2.setLabel("Time in Milliseconds");
            scGraph2.setTitle("Get from middle");

            XYChart.Series series3 = new XYChart.Series();
            series3.setName("ArrayList");
            series3.getData().add(new XYChart.Data(20_000, getMiddleBenchmark(arrayList, 20_000)));
            series3.getData().add(new XYChart.Data(40_000, getMiddleBenchmark(arrayList, 40_000)));
            series3.getData().add(new XYChart.Data(200_000, getMiddleBenchmark(arrayList, 200_000)));
            series3.getData().add(new XYChart.Data(400_000,getMiddleBenchmark(arrayList, 400_000)));

            XYChart.Series series4 = new XYChart.Series();
            series4.setName("LinkedList");
            series4.getData().add(new XYChart.Data(20_000, getMiddleBenchmark(linkedList, 20_000)));
            series4.getData().add(new XYChart.Data(40_000, getMiddleBenchmark(linkedList, 40_000)));
            series4.getData().add(new XYChart.Data(200_000, getMiddleBenchmark(linkedList, 200_000)));
            series4.getData().add(new XYChart.Data(400_000, getMiddleBenchmark(linkedList, 400_000)));

            System.out.println(getMiddleBenchmark(linkedList,200_000));
            System.out.println(getMiddleBenchmark(linkedList,400_000));

            sc.getData().addAll(series1, series2);
            scGraph2.getData().addAll(series3,series4);
            VBox vBox = new VBox(sc,scGraph2);
            Scene scene = new Scene(vBox,600,800);
            stage.setScene(scene);
            stage.show();
        }


    }
}

