package com.java_academy.logic.tools;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;

public class BSLogTest {

    private Logger logger;

    @BeforeTest
    public void createLoggerInstance() {
        logger = BSLog.getLogger(BSLogTest.class);
    }

    @Test
    public void loggerIsNotNull() {
        assertFalse(logger == null);
    }

    @Test
    public void testErrorLog() {
        BSLog.error(logger, "ERROR");
    }

    @Test
    public void testInfoLog() {
        BSLog.info(logger, "INFO");
    }

    @Test
    public void testWarnLog() {
        BSLog.warn(logger, "WARN");
    }

    @Test
    public void testDebugLog() {
        BSLog.debug(logger, "DEBUG");
    }
}

