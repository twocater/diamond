package com.twocater.kit.id;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * ID生成规则：2位随机数字+2位时间数字+6位递增数字=10位ID<br/>
 * 将后8位按照一定规则打乱次序<br/>
 * 
 * @author wanglinhua
 * 
 */
public class IntIdGen implements IdGen {
	private static final int ID_LENGTH = 10;
	private static final int MOD = 1000000;
	private int[] orders = { 6, 2, 1, 5, 3, 4, 0, 7 };
	private AtomicLong increase = new AtomicLong(0);
	private char[] timeChars;

	private Random random = new Random();

	public IntIdGen() {
		String time = System.currentTimeMillis() / 1000 + "";
		time = time.substring(time.length() - 2);
		timeChars = time.toCharArray();
	}

	@Override
	public String createId() {
		String rand = random.nextInt(22) + "";
		if (rand.length() == 1) {
			rand = "0" + rand;
		}
		String incr = increase.incrementAndGet() % MOD + "";
		if (incr.length() < 6) {
			incr = "000000".substring(incr.length()) + incr;
		}
		char[] inc = incr.toCharArray();
		char[] ids = new char[ID_LENGTH];
		char[] ran = rand.toCharArray();
		for (int i = 0; i < 2; i++) {
			ids[i] = ran[i];
		}
		for (int i = 2; i < ID_LENGTH; i++) {
			int index = orders[i - 2];
			if (index >= 2) {
				ids[i] = inc[index - 2];
			} else {
				ids[i] = timeChars[index];
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
