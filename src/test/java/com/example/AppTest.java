package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test
    void greet_returnsExpectedMessage() {
        assertEquals("Hello, Jenkins!", App.greet("Jenkins"));
    }
}