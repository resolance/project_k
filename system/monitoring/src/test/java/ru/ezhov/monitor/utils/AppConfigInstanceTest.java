package ru.ezhov.monitor.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppConfigInstanceTest {
    @Test
    public void getConfig() throws Exception {
        AppConfig appConfig = AppConfigInstance.getConfig();
        assertEquals(3, appConfig .attemptsCount());
    }

}