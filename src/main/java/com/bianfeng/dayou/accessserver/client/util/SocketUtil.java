package com.bianfeng.dayou.accessserver.client.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * socket工具
 */
public class SocketUtil {

    private static final int CACHE_SIZE = 2048;
    private static final int DEFAULT_TIMEOUT = 5000;

    /**
     * 发送socket请求
     *
     * @param ip
     * @param port
     * @param timeout
     * @param request
     * @return
     * @throws IOException
     */
    public static byte[] sendShortLinkRequest(String ip, int port, int timeout, byte[] request) throws IOException {
        Socket socket = null;
        byte[] ret = null;
        InputStream in = null;
        OutputStream out = null;
        ByteArrayOutputStream baos = null;
        try {
            socket = new Socket();
            socket.setReuseAddress(true);
            socket.setTcpNoDelay(true);
            socket.setSoLinger(false, -1);
            socket.setKeepAlive(false);
            socket.setSoTimeout(timeout);
            socket.connect(new InetSocketAddress(ip, port), timeout);
            out = socket.getOutputStream();
            in = socket.getInputStream();
            if (request != null && request.length > 0) {
                out.write(request);
                out.flush();
            }
            baos = new ByteArrayOutputStream();
            byte[] buf = new byte[CACHE_SIZE];
            for (; ; ) {
                int length = in.read(buf);
                if (length == -1) {
                    break;
                }
                baos.write(buf, 0, length);
            }
            ret = baos.toByteArray();
        } catch (IOException e) {
            throw e;
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
        return ret;
    }

    public static boolean isLongLink(String ip, int port, byte[] request) throws IOException {
        try {
            byte[] res = sendShortLinkRequest(ip, port, DEFAULT_TIMEOUT, request);
            if (res != null && res.length > 0) {
                return false;
            } else {
                throw new IllegalStateException("response is null.");
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            if (e.getMessage().equals("Read timed out")) {
                return true;
            }
            throw e;
        } catch (IOException e) {
            throw e;
        }
    }
}
