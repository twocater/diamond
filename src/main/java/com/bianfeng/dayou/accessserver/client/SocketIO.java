package com.bianfeng.dayou.accessserver.client;

import java.io.IOException;

public interface SocketIO {

	public byte[] readBytes(int length) throws IOException;
	
	public byte[] readBytes() throws IOException;

	public String readLine() throws IOException;

	public void write(byte[] bytes) throws IOException;

	public void flush() throws IOException;

	public void close() throws IOException;

}
