package com.twocater.diamond.kit.id;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenzhiwei
 * @since 2015-1-20
 */
public class MyIdGen {
	private static final long base = 1421747552169L;

	private static final long pastTimeBase = 1099511627775L;// 2的40次方减1
	private static final long sequenceBase = 1023;// 2的10次方减1

	private AtomicInteger sequence = new AtomicInteger(0);

	public MyIdGen() {
		sequence = new AtomicInteger(0);
	}

	public long createId() {
		long pastTime = System.currentTimeMillis() - base;
		long clearPastTime = pastTime & pastTimeBase;
		long movePastTime = clearPastTime << 24;

		long sequence = this.sequence.incrementAndGet();

		long clearSequence = sequence & sequenceBase;

		long moveSequence = clearSequence << 14;

		return (movePastTime | moveSequence);
	}

	public static void main(String[] args) {
//		long base = 1421747552169L;
//
//		long pastTime1 = System.currentTimeMillis() - base;
//		System.out.println("pastTime1:" + pastTime1);
//		System.out.println(Long.toBinaryString(pastTime1));
//
//		long pastTime2 = pastTime1 & 1099511627775L;// 2的40次方减1
//		System.out.println("pastTime2:" + pastTime2);
//		System.out.println(Long.toBinaryString(pastTime2));
//
//		long pastTime = pastTime2 << 24;
//		System.out.println("pastTime:" + pastTime);
//		System.out.println(Long.toBinaryString(pastTime));
//
//		pastTime = 154007142137856L;
//		long sequence1 = 2;
//		System.out.println("sequence:" + sequence1);
//		System.out.println(Long.toBinaryString(sequence1));
//
//		long sequence2 = sequence1 & 1023;// 2的10次方减1
//		System.out.println("sequence2:" + sequence2);
//		System.out.println(Long.toBinaryString(sequence2));
//
//		long sequence = sequence2 << 14;
//		System.out.println("sequence:" + sequence);
//		System.out.println(Long.toBinaryString(sequence));
//
//		long r = pastTime | sequence;
//		System.out.println(r);
//		System.out.println(Long.toBinaryString(r));
		MyIdGen myIdGen = new MyIdGen();
		System.out.println(Long.toBinaryString(myIdGen.createId()));
		System.out.println(Long.toBinaryString(myIdGen.createId()));
		System.out.println(Long.toBinaryString(myIdGen.createId()));
		System.out.println(Long.toBinaryString(myIdGen.createId()));
		System.out.println(Long.toBinaryString(myIdGen.createId()));
	}

}
