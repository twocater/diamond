package com.twocater.kit.id;

public interface IdGen {
	int getIdLength();

	long getCounter();

	String createId();
}
