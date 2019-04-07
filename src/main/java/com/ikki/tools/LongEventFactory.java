package com.ikki.tools;

import com.lmax.disruptor.EventFactory;

public class LongEventFactory implements EventFactory {
    @Override
    public Object newInstance() {
        return new LongEvent();
    }
}
