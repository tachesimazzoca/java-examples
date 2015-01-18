package com.github.tachesimazzoca.java.examples.netty3;

import java.util.Date;

public class RFC868Time {
    // According to TIME protocol, the time is the number of seconds
    // since 00:00 1 January 1900 UTC. For 70 years (1900..1970),
    // it takes 2208988800L seconds.
    private static final long OFFSET_IN_SECONDS = 2208988800L;

    private final long value; // 32-bit unsigned integer

    public RFC868Time(long value) {
        this.value = value & 0xffffffff;
    }

    public long getValue() {
        return value;
    }

    public static RFC868Time fromUnixTime(long time) {
        return new RFC868Time(time / 1000L + OFFSET_IN_SECONDS);
    }

    @Override
    public String toString() {
        return new Date((value - OFFSET_IN_SECONDS) * 1000L).toString();
    }
}
