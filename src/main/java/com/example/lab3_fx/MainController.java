package com.example.lab3_fx;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.ExifIFD0Directory;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainController {

    @FXML
    private ImageView imageView;

    @FXML
    private Label positionLabel;

    @FXML
    private Label fileInfo;

    @FXML
    private ChoiceBox<String> filterChoice;

    private Iterator<File> iterator;

    private Timeline slideshow;

    @FXML
    public void initialize() {

        filterChoice.getItems().addAll(
                "Все",
                "*.jpg",
                "*.png"
        );

        filterChoice.setValue("Все");
    }

    @FXML
    private void loadFolder() {

        DirectoryChooser chooser = new DirectoryChooser();

        File folder = chooser.showDialog(null);

        if (folder == null)
            return;

        File[] files = folder.listFiles();

        List<File> images = new ArrayList<>();

        String filter = filterChoice.getValue();

        for (File f : files) {

            if (filter.equals("*.jpg") && !f.getName().endsWith(".jpg"))
                continue;

            if (filter.equals("*.png") && !f.getName().endsWith(".png"))
                continue;

            if (f.getName().endsWith(".jpg")
                    || f.getName().endsWith(".png")
                    || f.getName().endsWith(".jpeg"))
                images.add(f);
        }

        if (images.isEmpty()) {

            positionLabel.setText("Нет изображений");
            return;
        }

        ImageCollection collection = new ImageCollection(images);

        iterator = collection.createIterator();

        showImage(iterator.first());
    }

    private String getExifInfo(File file) {

        try {

            Metadata metadata = ImageMetadataReader.readMetadata(file);

            ExifSubIFDDirectory subDir =
                    metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);

            ExifIFD0Directory mainDir =
                    metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

            String date = "";
            String camera = "";

            if (subDir != null && subDir.getDateOriginal() != null) {
                date = "Дата съемки: " + subDir.getDateOriginal();
            }

            if (mainDir != null && mainDir.getString(ExifIFD0Directory.TAG_MODEL) != null) {
                camera = "Камера: " + mainDir.getString(ExifIFD0Directory.TAG_MODEL);
            }

            return file.getName() + "\n" + date + "\n" + camera;

        } catch (Exception e) {

            return file.getName() + "\nEXIF данные не найдены";
        }
    }

    private void showImage(File file) {

        Image img = new Image(file.toURI().toString());

        imageView.setImage(img);

        positionLabel.setText(
                iterator.getPosition()
                        + " из "
                        + iterator.getSize()
        );

        fileInfo.setText(getExifInfo(file));

        playAnimation();
    }

    private void playAnimation() {

        FadeTransition fade = new FadeTransition(
                Duration.seconds(0.5),
                imageView
        );

        fade.setFromValue(0);
        fade.setToValue(1);

        ScaleTransition scale = new ScaleTransition(
                Duration.seconds(0.5),
                imageView
        );

        scale.setFromX(0.9);
        scale.setToX(1);

        ParallelTransition animation =
                new ParallelTransition(fade, scale);

        animation.play();
    }

    @FXML
    private void nextImage() {

        if (iterator == null)
            return;

        showImage(iterator.next());
    }

    @FXML
    private void prevImage() {

        if (iterator == null)
            return;

        showImage(iterator.previous());
    }

    @FXML
    private void firstImage() {

        if (iterator == null)
            return;

        showImage(iterator.first());
    }

    @FXML
    private void lastImage() {

        if (iterator == null)
            return;

        showImage(iterator.last());
    }

    @FXML
    private void autoPlay() {

        if (iterator == null)
            return;

        slideshow = new Timeline(

                new KeyFrame(
                        Duration.seconds(2),
                        e -> nextImage()
                )
        );

        slideshow.setCycleCount(
                Animation.INDEFINITE
        );

        slideshow.play();
    }
}