package com.bianfeng.dayou.accessserver.client.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by chenzhiwei on 15-12-17.
 */
public class LoggerConstants {
    public static final String PREFIX = "com.bianfeng.dayou.";
    public static final String SERVER = PREFIX + "server";

    public static final Logger serverLog = LoggerFactory.getLogger(LoggerConstants.SERVER);
}
