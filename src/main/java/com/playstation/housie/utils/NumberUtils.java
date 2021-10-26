package com.playstation.housie.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class NumberUtils {

	private static Random rand = new Random();
	/**
	 * This function returns a random number between 0 (inclusive) and the given number(exclusive).
	 * @param upperLimitExclusive
	 * @return random number
	 */

	public static int randomNumber(int upperLimitExclusive) {

		int randomNum = rand.nextInt(upperLimitExclusive);

		return randomNum;
	}
	
	/**
	 * This function generates a list of random number in a given range
	 * @param size
	 * @param count
	 * @return
	 */
	public static List<Integer> rangedMultipleRandomNumberGenerator(int size, int count) {
		List<Integer> pool = new LinkedList<Integer>();
		for(int sizeIter = 0; sizeIter < size; sizeIter++) {
			pool.add(sizeIter);
		}
		List<Integer> pickedNumbers = new LinkedList<Integer>();
		for (int countIter = 0; countIter < count; countIter++) {
			int randomIndex = randomNumber(size-countIter);
			//pick the random number from the pool
			int randomNumber = pool.get(randomIndex);
			pool.remove(randomIndex);
			pickedNumbers.add(randomNumber);
		}
		return pickedNumbers;
	}

}
