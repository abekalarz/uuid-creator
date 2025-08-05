package com.github.f4b6a3.uuid.v7;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Minimal GUID generator that produces UUID version 7 values.
 * <p>
 * Usage:
 * <pre>{@code
 * UUID uuid = GUID.v7().toUUID();
 * }</pre>
 */
public final class GUID {

    private final long msb;
    private final long lsb;

    private GUID(long msb, long lsb) {
        this.msb = msb;
        this.lsb = lsb;
    }

    /**
     * Generates a new GUID based on UUID version 7.
     *
     * @return a new GUID instance
     */
    public static GUID v7() {
        long time = System.currentTimeMillis();
        long rand = TLRandom.nextLong();
        long msb = (time << 16) | (rand & 0x0fffL);
        long lsb = TLRandom.nextLong();
        return version(msb, lsb, 7);
    }

    /**
     * Converts this GUID to a {@link UUID} instance.
     *
     * @return a standard {@link UUID}
     */
    public UUID toUUID() {
        return new UUID(this.msb, this.lsb);
    }

    private static GUID version(long hi, long lo, int version) {
        long msb = (hi & 0xffff_ffff_ffff_0fffL) | ((long) version << 12);
        long lsb = (lo & 0x3fff_ffff_ffff_ffffL) | 0x8000_0000_0000_0000L;
        return new GUID(msb, lsb);
    }

    private static class TLRandom {
        static final long JVM_UNIQUE_NUMBER = new SecureRandom().nextLong();

        static long nextLong() {
            return ThreadLocalRandom.current().nextLong() ^ JVM_UNIQUE_NUMBER;
        }
    }
}

