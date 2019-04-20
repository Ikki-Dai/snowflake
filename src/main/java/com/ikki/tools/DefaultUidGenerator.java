package com.ikki.tools;

import com.ikki.tools.exception.UidGenerateException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DefaultUidGenerator extends AbstractGenerator implements UidGenerater {

/*    protected int timeBits = 29; // 16 year
    protected int workerBits = 21; //
    protected int seqBits = 13; // about 8192/s*/

/*    protected String epochStr;
    protected long epochSecondsl;

    protected BitsAllocator bitsAllocator;
    protected long workerId;

    protected volatile long sequence = 0L;
    protected volatile long lastSecond = -1L;*/

    public DefaultUidGenerator() {
        bitsAllocator = new BitsAllocator(timeBits, workerBits, seqBits);
    }

    public DefaultUidGenerator(int timeBits, int workerBits, int seqBits) {
        int sum;
        if ((sum = timeBits + workerBits + seqBits) != 63) {
            throw new IllegalArgumentException(String.format("the sum of timeBits, workerBits, seqBits is %d which is not equals 63, refer no args constructor", sum));
        }
        setTimeBits(timeBits);
        setWorkerBits(workerBits);
        setSeqBits(seqBits);
        setEpochStr(epochStr);
        bitsAllocator = new BitsAllocator(timeBits, workerBits, seqBits);
    }

    @Override
    public synchronized long nextId() {
        long currentSecond = getCurrenctSecond();

        // Clock moved backwords, refuse to generate uid
        if (currentSecond < lastSecond) {
            throw new UidGenerateException("Clock moved backwards. Refusing for %d seconds", lastSecond - currentSecond);
        }

        if(currentSecond == lastSecond) {
            sequence = (sequence + 1) & bitsAllocator.getMaxSequence();
            // Exceed the max sequence, we wait the next second to generate uid
            if(sequence == 0 ) {
                currentSecond = getNextSecond(lastSecond);
            }
            // at the different second, sequence restart from zero
        } else {
            sequence = 0L;
        }
        // currentSecond > lastSecond
        lastSecond = currentSecond;

        return bitsAllocator.allocate(currentSecond - epochSeconds, workerId,sequence);
    }

    private void setTimeBits(int timeBits) {
        if (timeBits > 0) {
            this.timeBits = timeBits;
        }
    }

    private void setWorkerBits(int workerBits) {
        if (workerBits > 0) {
            this.workerBits = workerBits;
        }
    }

    private void setSeqBits(int seqBits) {
        if (seqBits > 0) {
            this.seqBits = seqBits;
        }
    }

    public void setEpochStr(String epochStr) {
        if(!(null== epochStr || 0 ==epochStr.length())){
            this.epochStr = epochStr;
            this. epochSeconds = LocalDateTime.parse(epochStr,DateTimeFormatter.ISO_LOCAL_DATE)
                    .toEpochSecond(ZoneOffset.UTC);
        }
    }


}
