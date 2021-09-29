package com.velopayments.blockchain.init;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
 
public class InitializerTest {

    @Test
    void getInstance() {
        assertNotEquals(0, Initializer.getInstance());
    }
}
