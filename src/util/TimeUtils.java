package util;

public class TimeUtils {
	private static long time = 0;
	/**
	 * 重置计数器的时间
	 */
	public static void reset() {
		time = 0;
	}
	/**
	 * 开始计时
	 */
	public static void start() {
		time = System.nanoTime();
	}
	/**
	 * 结束计时
	 */
	public static void over(boolean toZero) {
		time = (System.nanoTime() - time);
		print();
		if(toZero)
			time = 0;
	}
	public static void print() {
		System.out.println("cost: " + time/1000000 + " ms");
	}
}
