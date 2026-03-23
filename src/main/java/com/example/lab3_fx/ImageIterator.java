package com.example.lab3_fx;

import java.io.File;
import java.util.List;

public class ImageIterator implements Iterator<File> {

    private List<File> images;
    private int position = 0;

    public ImageIterator(List<File> images) {
        this.images = images;
    }

    @Override
    public File next() {

        position++;

        if (position >= images.size())
            position = 0;

        return images.get(position);
    }

    @Override
    public File previous() {

        position--;

        if (position < 0)
            position = images.size() - 1;

        return images.get(position);
    }

    @Override
    public File first() {
        position = 0;
        return images.get(position);
    }

    @Override
    public File last() {
        position = images.size() - 1;
        return images.get(position);
    }

    @Override
    public boolean hasNext() {
        return !images.isEmpty();
    }

    @Override
    public boolean hasPrevious() {
        return !images.isEmpty();
    }

    @Override
    public int getPosition() {
        return position + 1;
    }

    @Override
    public int getSize() {
        return images.size();
    }
}