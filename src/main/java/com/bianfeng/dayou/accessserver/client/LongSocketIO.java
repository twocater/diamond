package com.bianfeng.dayou.accessserver.client;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

public class LongSocketIO implements SocketIO {
    private Socket socket;
    private DataInputStream in;
    private BufferedOutputStream out;
    private byte[] recBuf;
    private int recBufSize = 1024;
    private int recIndex = 0;
    private long aliveTimeStamp = 0;

    public LongSocketIO(String host, int port, int timeout) throws IOException {
        this(host, port, timeout, timeout, true, true);
    }

    public LongSocketIO(String host, int port, int connectTimeout, int timeout, boolean noDelay,
                        boolean reuseAddress) throws IOException {
        recBuf = new byte[recBufSize];
        SocketChannel channel = SocketChannel.open();
        socket = channel.socket();
        if (timeout > 0) {
            socket.setSoTimeout(timeout);
        }
        socket.setTcpNoDelay(noDelay);
        socket.setReuseAddress(reuseAddress);
        socket.setKeepAlive(true);
        socket.connect(new InetSocketAddress(host, port), connectTimeout);
        in = new DataInputStream(socket.getInputStream());
        out = new BufferedOutputStream(socket.getOutputStream());
    }

    public void clear() {
        aliveTimeStamp = 0;
        recBuf = new byte[recBufSize];
        recIndex = 0;
    }

    public void destroy() throws IOException {
        aliveTimeStamp = 0;
        recBuf = null;
        recIndex = 0;
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
            }
        }

        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
            }
        }

        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
            }
        }
        in = null;
        out = null;
        socket = null;
    }

    public void close() throws IOException {
        destroy();
    }

    public boolean isConnected() {
        return (socket != null && socket.isConnected());
    }

    public boolean isAlive() {
        return isConnected();
    }

    public byte[] readBytes(int length) throws IOException {
        if (socket == null || !socket.isConnected()) {
            throw new IOException("++++ attempting to read from closed socket");
        }
        byte[] result = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if (recIndex >= length) {
            bos.write(recBuf, 0, length);
            byte[] newBuf = new byte[recBufSize];
            if (recIndex > length) {
                System.arraycopy(recBuf, length, newBuf, 0, recIndex - length);
            }
            recBuf = newBuf;
            recIndex = recIndex - length;
        } else {
            int totalread = length;
            if (recIndex > 0) {
                totalread = totalread - recIndex;
                bos.write(recBuf, 0, recIndex);
                recBuf = new byte[recBufSize];
                recIndex = 0;
            }
            int readCount = 0;
            while (totalread > 0) {
                if ((readCount = in.read(recBuf)) > 0) {
                    if (totalread > readCount) {
                        bos.write(recBuf, 0, readCount);
                        recBuf = new byte[recBufSize];
                        recIndex = 0;
                    } else {
                        bos.write(recBuf, 0, totalread);
                        byte[] newBuf = new byte[recBufSize];
                        System.arraycopy(recBuf, totalread, newBuf, 0,
                                readCount - totalread);
                        recBuf = newBuf;
                        recIndex = readCount - totalread;
                    }
                    totalread = totalread - readCount;
                }
            }

        }
        result = bos.toByteArray();
        if (result == null
                || (result != null && result.length <= 0 && recIndex <= 0)) {
            throw new IOException(
                    "++++ Stream appears to be dead, so closing it down");
        }
        aliveTimeStamp = System.currentTimeMillis();
        return result;
    }

    public String readLine() throws IOException {
        if (socket == null || !socket.isConnected()) {
            throw new IOException("++++ attempting to read from closed socket");
        }
        String result = null;
        StringBuilder content = new StringBuilder();
        int readCount = 0;
        if (recIndex > 0 && read(content)) {
            return content.toString();
        }
        while ((readCount = in.read(recBuf, recIndex, recBuf.length - recIndex)) > 0) {
            recIndex = recIndex + readCount;
            if (read(content))
                break;
        }
        result = content.toString();
        if (result == null
                || (result != null && result.length() <= 0 && recIndex <= 0)) {
            throw new IOException(
                    "++++ Stream appears to be dead, so closing it down");
        }
        aliveTimeStamp = System.currentTimeMillis();
        return result;

    }

    private boolean read(StringBuilder strBuilder) {
        boolean result = false;
        int index = -1;
        for (int i = 0; i < recIndex - 1; i++) {
            if (recBuf[i] == 13 && recBuf[i + 1] == 10) {
                index = i;
                break;
            }
        }
        if (index >= 0) {
            strBuilder.append(new String(recBuf, 0, index));
            byte[] newBuf = new byte[recBufSize];
            if (recIndex > index + 2) {
                System.arraycopy(recBuf, index + 2, newBuf, 0, recIndex - index
                        - 2);
            }
            recBuf = newBuf;
            recIndex = recIndex - index - 2;
            result = true;
        } else {
            if (recBuf[recIndex - 1] == 13) {
                strBuilder.append(new String(recBuf, 0, recIndex - 1));
                recBuf = new byte[recBufSize];
                recBuf[0] = 13;
                recIndex = 1;
            } else {
                strBuilder.append(new String(recBuf, 0, recIndex));
                recBuf = new byte[recBufSize];
                recIndex = 0;
            }

        }
        return result;
    }

    public void flush() throws IOException {
        if (socket == null || !socket.isConnected()) {
            throw new IOException("++++ attempting to write to closed socket");
        }
        out.flush();
        aliveTimeStamp = System.currentTimeMillis();
    }

    public void write(byte[] b) throws IOException {
        if (socket == null || !socket.isConnected()) {
            throw new IOException("++++ attempting to write to closed socket");
        }
        out.write(b);
        aliveTimeStamp = System.currentTimeMillis();
    }

    public long lastUseTime() {
        return aliveTimeStamp;
    }

    public int hashCode() {
        return (socket == null) ? 0 : socket.hashCode();
    }

    public String toString() {
        return (socket == null) ? "" : socket.toString();
    }

    protected void finalize() throws Throwable {
        try {
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (Throwable t) {
        } finally {
            super.finalize();
        }
    }

    @Override
    public byte[] readBytes() throws IOException {
        throw new IOException("cannot access readBytes method.");
    }

}
