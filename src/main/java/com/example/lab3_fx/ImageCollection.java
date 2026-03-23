package com.example.lab3_fx;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ImageCollection {

    private List<File> images = new ArrayList<>();

    public ImageCollection(List<File> files) {
        images.addAll(files);
    }

    public Iterator<File> createIterator() {
        return new ImageIterator(images);
    }
}