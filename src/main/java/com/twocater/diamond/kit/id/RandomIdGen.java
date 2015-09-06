package com.twocater.diamond.kit.id;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ID生成规则：16位递增数字+16位随机数字=32位ID<br/>
 * 将32位ID再按照一定规则打乱次序<br/>
 * 
 * @author wanglinhua
 * 
 */
public class RandomIdGen implements IdGen {
	private static final int ID_LENGTH = 32;
	private int[] orders = { 6, 18, 26, 10, 11, 29, 27, 21, 28, 20, 12, 2, 24, 13, 14, 19, 1, 15, 30, 5, 9, 8, 22, 3, 4, 0, 31,
			25, 23, 17, 7, 16 };
	private AtomicLong increase = new AtomicLong(0);
	private Random random = new Random();

	@Override
	public String createId() {
		char[] ran = Long.toHexString(random.nextLong()).toCharArray();
		int ranLen = ran.length;
		char[] inc = Long.toHexString(increase.incrementAndGet()).toCharArray();
		int incLen = inc.length;
		char[] ids = new char[ID_LENGTH];
		for (int i = 0; i < ID_LENGTH; i++) {
			int index = orders[i];
			if (index >= 16) {
				ids[i] = index - 16 >= incLen ? '0' : inc[index - 16];
			} else {
				ids[i] = index >= ranLen ? '0' : ran[index];
			}
		}
		return String.valueOf(ids);
	}
	@Override
	public int getIdLength() {
		return ID_LENGTH;
	}
	@Override
	public long getCounter() {
		return increase.get();
	}

}
