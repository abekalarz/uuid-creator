package com.github.f4b6a3.uuid.v7;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

public class GUIDv7MinimalTest {

    @Test
    public void testV7Generation() {
        UUID uuid = GUID.v7().toUUID();
        assertEquals(7, uuid.version());
        assertEquals(2, uuid.variant());
    }
}

