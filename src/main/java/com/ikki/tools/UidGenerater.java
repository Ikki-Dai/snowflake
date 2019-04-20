package com.ikki.tools;

public interface UidGenerater {

    long nextId();

    long getCurrenctSecond();

    long getNextSecond(long lastSecond);
}
