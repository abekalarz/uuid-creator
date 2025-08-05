package com.github.f4b6a3.uuid.v7

import java.security.SecureRandom
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

/**
 * Minimal GUID generator that produces UUID version 7 values.
 *
 * Usage:
 * val uuid = GUID.v7().toUUID()
 */
class GUID private constructor(private val msb: Long, private val lsb: Long) {

    fun toUUID(): UUID = UUID(msb, lsb)

    companion object {
        /**
         * Generates a new GUID based on UUID version 7.
         */
        @JvmStatic
        fun v7(): GUID {
            val time = System.currentTimeMillis()
            val rand = TLRandom.nextLong()
            val msb = (time shl 16) or (rand and 0x0fffL)
            val lsb = TLRandom.nextLong()
            return version(msb, lsb, 7)
        }

        private fun version(hi: Long, lo: Long, version: Int): GUID {
            val msb = (hi and -0x0000_0000_0000_F001L) or (version.toLong() shl 12)
            val lsb = (lo and 0x3fff_ffff_ffff_ffffL) or Long.MIN_VALUE
            return GUID(msb, lsb)
        }

        private object TLRandom {
            private val JVM_UNIQUE_NUMBER = SecureRandom().nextLong()
            fun nextLong(): Long = ThreadLocalRandom.current().nextLong() xor JVM_UNIQUE_NUMBER
        }
    }
}
