package com.playstation.housie.ticket.manage.service;

import java.util.Collections;
import java.util.List;

import com.playstation.housie.utils.NumberUtils;

public class TicketGeneratorService {
	private static final int ROW_SIZE = 3;
	private static final int COLUMN_SIZE = 9;

	public static int[][] generate() {
		int[][] occupiedPositions = generateIndicesForAllotmentOfNumbers();
		for (int j = 0; j < COLUMN_SIZE; j++) {
			int numbersToGenerateInColumn = 0;
			for (int i = 0; i < ROW_SIZE; i++) {
				if (occupiedPositions[i][j] == 1) numbersToGenerateInColumn ++;
			}
			
			List<Integer> numberOffsets = NumberUtils.rangedMultipleRandomNumberGenerator(10, numbersToGenerateInColumn);
			Collections.sort(numberOffsets);
			int numbersAssigned = 0;
			for (int i = 0; i < ROW_SIZE; i++) {
				if (occupiedPositions[i][j] == 1) {
					occupiedPositions[i][j] = (j * 10) + numberOffsets.get(numbersAssigned++) + 1;
				}
			}
		}
		return occupiedPositions;

	}

	/**
	 * This method is used to generate the indices of the positions for each rows,
	 * where numbers need to be alloted
	 * 
	 * @return an array containing the indices which
	 */
	private static int[][] generateIndicesForAllotmentOfNumbers() {
		int[][] ticketPositions = new int[ROW_SIZE][COLUMN_SIZE];
		for(int i=0;i<3;i++) {
			List<Integer> rowPositions = NumberUtils.rangedMultipleRandomNumberGenerator(9, 5);
			for (int occupiedPosition : rowPositions) {
				ticketPositions[i][occupiedPosition] = 1;
			}
		}
		return ticketPositions;
	}

	

}