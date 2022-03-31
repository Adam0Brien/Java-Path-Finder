package teamproject.ca2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    @FXML
    ImageView view;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("C:\\CA2\\src\\main\\resources\\teamproject\\ca2\\floorplan-level-2-july-2020.jpg");
        Image image = new Image(file.toURI().toString());
        view.setImage(image);
        processedView.setImage(image);
        finalView.setImage(image);
    }


    public void findCoords(ActionEvent event) throws IOException {

        view.setOnMousePressed(e -> {
            view.getImage().getPixelReader().getColor((int) e.getX(), (int) e.getY());
            System.out.println("\nX Cord: " + e.getX());
            System.out.println("\nY Cord: " + e.getY());
        });
    }

    @FXML
    ImageView processedView;
    @FXML
    ImageView finalView;
    Stage stage;
    @FXML
    Label fileName;
    @FXML
    Label fileSize;
    @FXML
    Label fileName1;
    @FXML
    Label fileSize1;






    /**
     * Sliders for Image Processing
     */


    @FXML
    Slider hueSlider;

    @FXML
    Slider brightnessSlider;

    @FXML
    Slider saturationSlider;


    ColorAdjust colorAdjust = new ColorAdjust();

    public void hueChanger(double value) {
        try {

            colorAdjust.setHue(value);

            processedView.setEffect(colorAdjust);


        } catch (Exception e) {
            System.out.println("Error:" + e);
            System.out.println(hueSlider.getValue());
        }

    }

    public void hueSlider() {
        hueChanger(hueSlider.getValue());
    }


    public void brightnessChanger(double value) {

        colorAdjust.setBrightness(value);

        processedView.setEffect(colorAdjust);
    }

    public void brightnessSlider() {
        brightnessChanger(brightnessSlider.getValue());
    }


    public void saturationChanger(double value) {

        colorAdjust.setSaturation(value);
        processedView.setEffect(colorAdjust);



    }

    public void saturationSlider() {
        saturationChanger(saturationSlider.getValue());

    }

//    public void reset(ActionEvent actionEvent) {
//
//        Image original = view.getImage();
//        processedView.setImage(original);
//        colorAdjust.setHue(0);
//        colorAdjust.setSaturation(0);
//        colorAdjust.setBrightness(0);
//
//        hueSlider.setValue(0);
//        saturationSlider.setValue(0);
//        brightnessSlider.setValue(0);
//    }


}