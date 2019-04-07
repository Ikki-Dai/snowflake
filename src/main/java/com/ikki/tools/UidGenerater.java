package com.ikki.tools;

import java.time.Instant;

public interface UidGenerater {

/*    int timeBigs = 29;

    int workerBig = 21;

    int seqBit = 13;*/

    long nextId();

    default long getCurrenctSecond() {
        return Instant.now().getEpochSecond();
    }

    default long getNextSecond(long lastSecond) {
        long timestamp = getCurrenctSecond();
        while (timestamp <= lastSecond) {
            timestamp = getCurrenctSecond();
        }
        return timestamp;
    }

}
