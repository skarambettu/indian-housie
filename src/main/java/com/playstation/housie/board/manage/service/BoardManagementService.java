package com.playstation.housie.board.manage.service;

import java.util.List;

import com.playstation.housie.board.HousieBoard;
import com.playstation.housie.board.dao.BoardDAO;
import com.playstation.housie.exception.FullBoardException;
import com.playstation.housie.exception.InvalidGameException;

public class BoardManagementService {
	private static BoardDAO  boardRepository = new BoardDAO();

	/**
	 * Function to create a new housie Board
	 * 
	 * @return Id of the game created
	 */
	public static long createNewBoard(int boardSize) {
		HousieBoard board = new HousieBoard(boardSize);
		boardRepository.create(board);
		return board.getId();
	}

	/**
	 * Function to draw the next number on the board
	 * 
	 * @param gameId Id of the game
	 * @return the drawn number
	 * @throws FullBoardException
	 * @throws InvalidGameException
	 */
	public static int drawNextNumber(long gameId) throws FullBoardException, InvalidGameException {
		HousieBoard board = boardRepository.load(gameId);
		if (board != null) {
			int drawnNumber = board.drawNextNumber();
			board = boardRepository.update(board);
			return drawnNumber;
		} else {
			throw (new InvalidGameException());
		}
	}

	/**
	 * Function to return the list of drawn numbers on a board
	 * 
	 * @param gameId
	 * @return Drawn numbers on the board
	 * @throws InvalidGameException
	 */
	public static List<Integer> getDrawnNumbers(long gameId) throws InvalidGameException {
		HousieBoard board = boardRepository.load(gameId);
		if (board != null) {
			return board.getDrawnNumbers();
		} else {
			throw (new InvalidGameException());
		}
	}
	
	public static HousieBoard loadBoard(long gameId) {
		return boardRepository.load(gameId);
	}
	
	public static void addTicket() {
	}

}
