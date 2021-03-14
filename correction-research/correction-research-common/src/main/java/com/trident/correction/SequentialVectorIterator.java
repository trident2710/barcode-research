package com.trident.correction;

import cc.redberry.rings.util.ArraysUtil;

import java.util.Arrays;
import java.util.Iterator;

public class SequentialVectorIterator implements Iterator<long[]> {
    private final long[] current;
    private final long min;
    private final long max;

    public SequentialVectorIterator(int size, long min, long max) {
        this.current = ArraysUtil.arrayOf(min, size);
        this.current[current.length - 1] = min - 1;
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean hasNext() {
        return !Arrays.equals(current, ArraysUtil.arrayOf(max - 1, current.length));
    }

    @Override
    public long[] next() {
        if (!hasNext()) {
            throw new RuntimeException();
        }
        increment(current.length - 1);
        return current;
    }

    private void increment(int index) {
        if (index == 0) {
            ++current[index];
        } else {
            ++current[index];
            if (current[index] == max) {
                current[index] = min;
                increment(index - 1);
            }
        }
    }
}