package com.ikki.tools;

import com.ikki.tools.exception.UidGenerateException;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class DefaultUidGenerator implements UidGenerater {

    protected int timeBits = 29;
    protected int workerBits = 21;
    protected int seqBits = 13;

    protected String epochStr = "2018-01-01";
    protected long epochSeconds = Instant.now().getEpochSecond();

    protected BitsAllocator bitsAllocator;
    protected long workerId;

    protected volatile long sequence = 0L;
    protected volatile long lastSecond = -1L;

    DefaultUidGenerator() {
        bitsAllocator = new BitsAllocator(timeBits, workerBits, seqBits);
    }

    DefaultUidGenerator(int timeBits, int workerBits, int seqBits) {
        int sum;
        if ((sum = timeBits + workerBits + seqBits) != 63) {
            throw new IllegalArgumentException(String.format("the sum of timeBits, workerBits, seqBits is %d which is not equals 63, refer no args constructor", sum));
        }
        bitsAllocator = new BitsAllocator(timeBits, workerBits, seqBits);
    }

    public synchronized long nextId() {
        long currentSecond = getCurrenctSecond();
        if (currentSecond < lastSecond) {
            throw new UidGenerateException("Clock moved backwards. Refusing for %d seconds", lastSecond - currentSecond);
        }

        if(currentSecond == lastSecond) {
            sequence = (sequence + 1) & bitsAllocator.getMaxSequence();
            //
            if(sequence == 0 ) {
                currentSecond = getNextSecond(lastSecond);
            }
        } else {
            sequence = 0L;
        }
        return bitsAllocator.allocate(currentSecond - epochSeconds, workerId,sequence);
    }

    public void setTimeBits(int timeBits) {
        if (timeBits > 0) {
            this.timeBits = timeBits;
        }
    }

    public void setWorkerBits(int workerBits) {
        if (workerBits > 0) {
            this.workerBits = workerBits;
        }
    }

    public void setSeqBits(int seqBits) {
        if (seqBits > 0) {
            this.seqBits = seqBits;
        }
    }

    public void setEpochStr(String epochStr) {
        // TODO
    }


}
