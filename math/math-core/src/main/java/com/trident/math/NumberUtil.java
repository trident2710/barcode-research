package com.trident.math;

import java.util.ArrayList;

public final class NumberUtil {
    private NumberUtil() {
    }

    public static long[] toNBased(long value, long radix) {
        var result = new ArrayList<Long>();
        long current = value;
        do {
            var digit = current % radix;
            current = (current - digit) / radix;
            result.add(digit);
        } while (current > 0);
        return result.stream().mapToLong(l -> l).toArray();
    }

    public static long fromNBased(long[] nBased, long radix) {
        var base = 1;
        var result = 0;
        for (long l : nBased) {
            result += l * base;
            base *= radix;
        }
        return result;
    }
}
