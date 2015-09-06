package com.twocater.diamond.kit.id;

public interface IdGen {
	int getIdLength();

	long getCounter();

	String createId();
}
