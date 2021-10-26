package com.playstation.housie.ticket.manage;

import static org.junit.Assert.*;

import org.junit.Test;

import com.playstation.housie.ticket.manage.service.TicketGeneratorService;

public class TicketGeneratorTest {

	@Test
	public void testGenerate() {
		int[][] ticket = TicketGeneratorService.generate();
		testNumberCount(ticket);
		testColumnContents(ticket);
	}

	private void testColumnContents(int[][] ticket) {
		for (int j = 0; j < 9; j++) {
			for (int i = 0; i < 3; i++) {
				if (ticket[i][j] != 0) {
					assertTrue("Column Value should be within the range",
							(ticket[i][j] > j * 10 && ticket[i][j] <= (j + 1) * 10));
				}
			}
		}
	}

	private void testNumberCount(int[][] ticket) {
		int count = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				if (ticket[i][j] != 0) {
					count++;
				}
			}
		}
		assertEquals("Count of Elements should be 15", 15, count);
	}

}
