package com.cloud.notification.util;

import java.util.Random;

/**
 * 随机码生成工具
 * @author zhouyu
 *
 */
public class SmsUtils {
	
	private static String[] NUMBERS = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	
	private static Random RANDOM = new Random();
	
	/**
	 * 生成bitNum位随机码
	 * @param bitNum
	 * @return
	 */
	public static String randomCode(int bitNum) {
		StringBuffer buffer = new StringBuffer();
		
		for(int i = 0; i < bitNum; i++) {
			buffer.append(NUMBERS[RANDOM.nextInt(NUMBERS.length)]);
		}
		return buffer.toString();
	}

}
