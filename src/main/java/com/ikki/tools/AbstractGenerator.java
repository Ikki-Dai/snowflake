package com.ikki.tools;

import java.time.Instant;

public class AbstractGenerator implements UidGenerater {

    protected int timeBits = 29; // 16 year
    protected int workerBits = 21;
    protected int seqBits = 13; // about 8192/s

    protected String epochStr = "2018-01-01";
    protected long epochSeconds = Instant.now().getEpochSecond();

    protected BitsAllocator bitsAllocator;
    protected long workerId;

    protected volatile long sequence = 0L;
    protected volatile long lastSecond = -1L;

    @Override
    public synchronized long nextId() {
        return 0;
    }

    @Override
    public long getCurrenctSecond() {
        return Instant.now().getEpochSecond();
    }

    @Override
    public long getNextSecond(long lastSecond) {
        long timestamp = getCurrenctSecond();
        while (timestamp <= lastSecond) {
            timestamp = getCurrenctSecond();
        }
        return timestamp;
    }


}
