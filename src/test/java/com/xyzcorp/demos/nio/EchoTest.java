package com.xyzcorp.demos.nio;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EchoTest {

    static EchoClient client;

    @BeforeAll
    public static void setUp() throws IOException, InterruptedException {
        client = EchoClient.start();
        System.out.println("client = " + client);
    }

    @Test
    public void givenServerClient_whenServerEchosMessage_thenCorrect() {
        System.out.println("clientTest = " + client);
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        assertEquals("hello", resp1);
        assertEquals("world", resp2);
    }

    @AfterAll
    public static void tearDown() throws IOException {
        EchoClient.stop();
    }
}
