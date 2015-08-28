package com.twocater.diamond.client.http;

import com.twocater.diamond.api.protocol.http.HttpContentType;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by chenzhiwei on 15-8-27.
 */
public class HttpClient {
    public static final int CACHE_SIZE = 2048;
    public static final int DEFAULT_PORT = 80;
    public static final int DEFAULT_TIMEOUT = 20000;
    public static final String DEFAULT_CONTENT_ENCODING = StandardCharsets.UTF_8.name();
    public static final String DEFAULT_CONTENT_TYPE = HttpContentType.plain.getContentType();


    private String host;
    private int port;
    private int timeout;

    private String contentType;
    private String contentEncoding;
    private String formatType;


    public HttpClient(String host, int port, int timeout, String contentType, String contentEncoding) {
        if (port <= 0) {
            port = DEFAULT_PORT;
        }

        if (timeout <= 0) {
            timeout = DEFAULT_TIMEOUT;
        }

        if (contentType == null) {
            contentType = DEFAULT_CONTENT_TYPE;
        }

        if (contentEncoding == null) {
            contentEncoding = DEFAULT_CONTENT_ENCODING;
        }
        this.host = host;
        this.port = port;
        this.timeout = timeout;

        this.contentType = contentType;
        this.contentEncoding = contentEncoding;

        this.formatType = this.contentType + ";charset=" + this.contentEncoding;
    }

    private String getUrl(String ip, int port, String command) {
        if (port == 80) {
            return "http://" + ip + formatCommand(command);
        }

        return "http://" + ip + ":" + port + formatCommand(command);
    }

    private String formatCommand(String command) {
        if (command == null) {
            command = "";
        }
        if (!command.startsWith("/")) {
            command = "/" + command;
        }
        return command;
    }

    public HttpMessage requestByGet(String command) throws IOException, HttpStatusException {
        String url = getUrl(host, port, command);
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        ByteArrayOutputStream baos = null;
        HttpMessage response = null;
        try {
            urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(timeout);
            urlConnection.setReadTimeout(timeout);
            String hostName = host;
            if (port != 80) {
                hostName += ":" + port;
            }
            urlConnection.setRequestProperty(HttpHeaders.Names.HOST, hostName);
            urlConnection.setRequestProperty(HttpHeaders.Names.CONTENT_TYPE, formatType);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.connect();
            int code = urlConnection.getResponseCode();
            if (code != 200) {
                throw new HttpStatusException(HttpResponseStatus.valueOf(code));
            }
            in = urlConnection.getInputStream();
            baos = new ByteArrayOutputStream();
            byte[] buf = new byte[CACHE_SIZE];
            for (; ; ) {
                int length = in.read(buf);
                if (length == -1) {
                    break;
                }
                baos.write(buf, 0, length);
            }
            response = new HttpMessage(urlConnection.getHeaderFields(), baos.toByteArray());
        } catch (IOException e) {
            throw e;
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return response;
    }

    public HttpMessage requestByPost(String command, byte[] data) throws IOException, HttpStatusException {
        String url = getUrl(host, port, command);
        HttpURLConnection urlConnection = null;
        InputStream in = null;
        ByteArrayOutputStream baos = null;
        HttpMessage response = null;
        try {
            urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(timeout);
            urlConnection.setReadTimeout(timeout);
            String hostName = host;
            if (port != 80) {
                hostName += ":" + port;
            }
            urlConnection.setRequestProperty(HttpHeaders.Names.HOST, hostName);
            urlConnection.setRequestProperty(HttpHeaders.Names.CONTENT_TYPE, formatType);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.connect();
            if (data != null) {
                OutputStream out = urlConnection.getOutputStream();
                out.write(data);
                out.flush();
                out.close();
            }
            int code = urlConnection.getResponseCode();
            if (code != 200) {
                throw new HttpStatusException(HttpResponseStatus.valueOf(code));
            }
            in = urlConnection.getInputStream();
            baos = new ByteArrayOutputStream();
            byte[] buf = new byte[CACHE_SIZE];
            for (; ; ) {
                int length = in.read(buf);
                if (length == -1) {
                    break;
                }
                baos.write(buf, 0, length);
            }
            response = new HttpMessage(urlConnection.getHeaderFields(), baos.toByteArray());
        } catch (IOException e) {
            throw e;
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return response;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }
}
