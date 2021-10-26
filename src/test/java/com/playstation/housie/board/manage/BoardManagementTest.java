package com.playstation.housie.board.manage;

import static org.junit.Assert.*;

import org.junit.Test;

import com.playstation.housie.board.manage.service.BoardManagementService;
import com.playstation.housie.exception.FullBoardException;
import com.playstation.housie.exception.InvalidGameException;

public class BoardManagementTest {

	@Test
	public void test() {
		long gameId = BoardManagementService.createNewBoard();
		assertNotNull("The Game Id should not be null", gameId);
		try {
			for (int i = 0; i < 90; i++) {
				int drawnNumber = BoardManagementService.drawNextNumber(gameId);
				
				System.out.println(BoardManagementService.getDrawnNumbers(gameId));
				assertTrue("The drawn number should be present",
						BoardManagementService.getDrawnNumbers(gameId).contains(drawnNumber));
			}
		} catch (FullBoardException | InvalidGameException e) {
			fail("This is not expected");
		}
		try {
			int drawnNumber = BoardManagementService.drawNextNumber(gameId);
			fail("An Exception is Expected");
		} catch (FullBoardException e) {
			
		} catch (InvalidGameException e) {
			fail("This is not expected");
		}
		try {
			int drawnNumber = BoardManagementService.drawNextNumber(-1);
			fail("Invalid Game Exception is Expected");
		} catch(InvalidGameException invalidGameException) {
			
		} catch (FullBoardException fullBoardException) {
			fail("This is not expected");
		}
	}

}
