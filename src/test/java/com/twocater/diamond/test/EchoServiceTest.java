package com.twocater.diamond.test;

import com.twocater.diamond.client.http.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chenzhiwei on 15-8-27.
 */
public class EchoServiceTest {

    private static Logger log = LoggerFactory.getLogger(EchoServiceTest.class);
    private static final String IP = "localhost";
    private static final int PORT = 8090;
    private static final int TIMEOUT = 10000;
    private static final Charset RESPONSE_ENCODING = StandardCharsets.UTF_8;

    @Test
    public void TestGet() {
        log.info("########## TestGet ##########");
        HttpClient client = new HttpClient(IP, PORT, TIMEOUT, null, null);
        try {
            HttpMessage httpMessage = client.requestByGet("echo?a=a&b=b");
            String data = new String(httpMessage.getData(), RESPONSE_ENCODING);
            log.info(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestPost() {
        log.info("########## TestPost ##########");
        HttpClient client = new HttpClient(IP, PORT, TIMEOUT, null, null);

        try {
            byte[] requestData = "d=d&f=f".getBytes(StandardCharsets.UTF_8);

            HttpMessage httpMessage = client.requestByPost("echo?t=t&tt=tt", requestData);
            String data = new String(httpMessage.getData(), RESPONSE_ENCODING);
            log.info(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestJsonGet() {
        log.info("########## TestJsonGet ##########");
        HttpJsonClient client = new HttpJsonClient(IP, PORT, TIMEOUT, null);
        try {
            HttpMessage httpMessage = client.requestByGet("echoJson?a=a&b=b");
            String data = new String(httpMessage.getData(), RESPONSE_ENCODING);
            log.info(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TestJsonPost() {
        log.info("########## TestJsonPost ##########");
        HttpJsonClient client = new HttpJsonClient(IP, PORT, TIMEOUT, null);
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("pp", "pp");
            map.put("bb", "bb");
            HttpJsonMessage httpMessage = client.requestByPost("echoJson?a=a&b=b", map);
            log.info("{}", new Object[]{httpMessage.getEntryData()});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
