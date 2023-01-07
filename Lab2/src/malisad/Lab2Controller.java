/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class lab2Controller
 * Name: malisad
 * Created 3/19/2021
 */
package malisad;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


/**
 * Course: CS2852
 * Term Spring 2021-2022
 * lab2Controller purpose:
 *
 * @author malisad
 * @version created on 3/19/2021 at 6:39 PM
 */
public class Lab2Controller {

    FileChooser fileChooser = new FileChooser();
    @FXML
    File file;
    Picture picture;
    Picture modifiedPic;
    @FXML
    Canvas canvas;
    @FXML
    MenuButton draw;
    @FXML
    CheckMenuItem arrayList;
    @FXML
    CheckMenuItem linkedList;

    Alert alert = new Alert(Alert.AlertType.WARNING);
    Alert alert2 = new Alert(Alert.AlertType.ERROR);

    /**
     * loads a .dot file and displays the dot in the file
     * and connects neighboring dots with lines
     *
     * @throws IOException in the event that the input is wrong
     */
    @FXML
    public void open() throws IOException {
        file = new File("data");
        fileChooser.setInitialDirectory(file);
        file = fileChooser.showOpenDialog(null);

        if (file != null) {
            picture = new Picture(listType());
            try {
                picture.load(file.toPath());
                modifiedPic = new Picture(picture, listType());
                picture.drawDots(canvas);
                picture.drawLines(canvas);
                draw.setDisable(false);
            } catch (FileNotFoundException e) {
                alert.setTitle("Caution");
                alert.setContentText("The file was not loaded");
                alert.showAndWait();
            }
        }
    }


    /**
     * Exists the program
     */
    @FXML
    public void close() {
        Platform.exit();
    }

    /**
     * saves a dot file with the contents of the
     * picture currently being displayed
     */
    @FXML
    public void save() {
        FileChooser fileChooser = new FileChooser();
        file = new File("data");
        fileChooser.setInitialDirectory(file);
        file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                modifiedPic.save(file.toPath());
            } catch (FileNotFoundException e) {
                alert.setTitle("Caution !");
                alert.setContentText("No file has been chosen");
                alert.showAndWait();
            }
        } else {
            alert.setTitle("Caution !");
            alert.setContentText("No file has been chosen");
            alert.showAndWait();
        }

    }

    /**
     * redraws the loaded picture drawing dots only
     */
    @FXML
    public void linesOnly() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(0.0, 0.0, canvas.getWidth(), canvas.getHeight());
        modifiedPic.drawLines(canvas);
    }

    /**
     * redraws the loaded picture drawing only dots
     */
    @FXML
    public void dotsOnly() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(0.0, 0.0, canvas.getWidth(), canvas.getHeight());
        modifiedPic.drawDots(canvas);
    }

    /**
     * Asks user for number of dots to retain and redraws with fewer dots
     */
    @FXML
    public void numberOfDots() {
        String strategy = null;

        TextInputDialog a = new TextInputDialog();
        a.setContentText("Enter number of dots you want to keep in the image");
        a.setContentText("Desired number of dots");
        Optional<String> dotsWanted = a.showAndWait();

        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>();
        choiceDialog.setTitle("Choice Dialogue");
        choiceDialog.setHeaderText("choose method to remove dots");
        ObservableList<String> list = choiceDialog.getItems();
        list.add("remove dots index");
        list.add("remove dots iterator");
        Optional<String> response = choiceDialog.showAndWait();


        try {
            if (response.isPresent()) {
                strategy = response.get();
            }
            final String FINAL_STRATEGY = strategy;
            dotsWanted.ifPresent(s -> {
                assert FINAL_STRATEGY != null;
                modifiedPic.removeDots(Integer.parseInt(s), FINAL_STRATEGY);
            });
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            graphicsContext.clearRect(0.0, 0.0, canvas.getWidth(), canvas.getHeight());
            modifiedPic.drawDots(canvas);
            modifiedPic.drawLines(canvas);
        } catch (IllegalArgumentException e) {
            alert2.setTitle("Error!");
            alert2.setContentText("That is an invalid number of dots");
            alert2.showAndWait();
        }

    }


    public List<Dot> listType() {
        List<Dot> emptyList;
        if (arrayList.isSelected()) {
            arrayList.setSelected(true);
            linkedList.setSelected(false);
            emptyList = new ArrayList<>();
        } else {
            linkedList.isSelected();
            emptyList = new LinkedList<>();
            arrayList.setSelected(false);
    }
        return emptyList;
}

    @FXML
    public void file() {
        draw.setDisable(true);
    }


}

