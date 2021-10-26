package com.playstation.housie.board;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.playstation.housie.exception.FullBoardException;
import com.playstation.housie.ticket.HousieTicket;
import com.playstation.housie.utils.NumberUtils;

/***
 * This class represents the housie board
 * 
 * @author sandesh.karambettu
 *
 */
@Entity
public class HousieBoard {
	/**
	 * Id of the Game Board
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  long id;
	/**
	 * Numbers drawn on the board
	 */
	private List<Integer> drawnNumbers;
	/**
	 * List of Undrawn Numbers on the Board
	 */
	@Transient
	private List<Integer> undrawnNumbers;
	
	@OneToMany
	private List<HousieTicket> tickets;

	/**
	 * Constructor to create a new housie Board
	 */
	public HousieBoard() {
		this.drawnNumbers = new ArrayList<Integer>();
		updateUndrawnNumbers();
	}
	
	public HousieBoard(List<Integer> pDrawnNumbers) {
		this.drawnNumbers = pDrawnNumbers;
		updateUndrawnNumbers();
	}

	/**
	 * Method to draw the next number on board
	 * 
	 * @return the drawn number
	 * @throws FullBoardException
	 */
	public int drawNextNumber() throws FullBoardException {
		int undrawnNumberCount = undrawnNumbers.size();
		int drawnNumber = 0;
		if (undrawnNumberCount > 0) {
			int randomNumberIndex = NumberUtils.randomNumber(undrawnNumberCount);
			drawnNumber = undrawnNumbers.remove(randomNumberIndex);
			drawnNumbers.add(drawnNumber);
		} else {
			throw new FullBoardException();
		}
		return drawnNumber;
	}

	public List<Integer> getDrawnNumbers() {
		return drawnNumbers;
	}

	/**
	 * @return the gameId
	 */
	public long getId() {
		return id;
	}
	/**
	 * Function to get a list of the undrawn numbers
	 * @return list of undrawn numbers
	 */
	public List<Integer> getUndrawnNumbers() {
		return undrawnNumbers;
	}

	/**
	 * @param drawnNumbers the drawnNumbers to set
	 */
	public void setDrawnNumbers(List<Integer> drawnNumbers) {
		this.drawnNumbers = drawnNumbers;
	}

	/**
	 * @param id the gameId to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param undrawnNumbers the undrawnNumbers to set
	 */
	public void setUndrawnNumbers(List<Integer> undrawnNumbers) {
		this.undrawnNumbers = undrawnNumbers;
	}

	/**
	 * Update the list of drawn numbers from the list of drawn numbers
	 * 
	 */
	public void updateUndrawnNumbers() {
		int boardSize = 90;
		this.undrawnNumbers = new ArrayList<Integer>(boardSize);
		for (int i = 0; i < boardSize; i++) {
			undrawnNumbers.add(i, i + 1);
		}
		for (Integer drawnNumber : drawnNumbers) {
			undrawnNumbers.remove(drawnNumber);
		}
	}

}
