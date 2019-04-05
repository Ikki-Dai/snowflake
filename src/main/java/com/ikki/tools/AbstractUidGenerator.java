package com.ikki.tools;

import java.time.Instant;

public abstract class AbstractUidGenerator implements UidGenerater{

    protected int timeBits = 29;
    protected int workerBits = 21;
    protected int seqBits = 13;

    protected String epochStr = "2018-01-01";
    protected long epochSeconds = Instant.now().getEpochSecond();

    protected BitsAllocator bitsAllocator;
    protected long workerId;

    protected volatile long sequence = 0L;
    protected volatile long lastSecond = -1L;

    AbstractUidGenerator(){
        bitsAllocator = new BitsAllocator(timeBits,workerBits,seqBits);

        workerId
    }



}
