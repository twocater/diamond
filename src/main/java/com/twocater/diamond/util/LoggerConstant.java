package com.twocater.diamond.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenzhiwei on 15-9-8.
 */
public class LoggerConstant {
    public static final String PREFIX = "com.twocater.diamond.";
    public static final String FILTER = PREFIX + "filter";
    public static final String SERVER = PREFIX + "server";
    public static final String REQUEST = PREFIX + "request";
    public static final String DEBUG = PREFIX + "debug";
    public static final String NETTY_HANDLER = PREFIX + "nettyHandler";
    public static final String TIMEOUT = PREFIX + "timeout";
    public static final String ERROR = PREFIX + "error";

    public static final Logger filterLog = LoggerFactory.getLogger(LoggerConstant.FILTER);
    public static final Logger timeoutLog = LoggerFactory.getLogger(LoggerConstant.TIMEOUT);
    public static final Logger nettyHandlerLog = LoggerFactory.getLogger(LoggerConstant.NETTY_HANDLER);
    public static final Logger errorLog = LoggerFactory.getLogger(LoggerConstant.ERROR);
    public static final Logger timingLog = LoggerFactory.getLogger("timing");

}
