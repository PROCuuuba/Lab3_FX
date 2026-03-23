package com.example.lab3_fx;

public interface Iterator<T> {

    T next();

    T previous();

    T first();

    T last();

    boolean hasNext();

    boolean hasPrevious();

    int getPosition();

    int getSize();
}