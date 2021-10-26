package com.playstation.housie.ticket;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.playstation.housie.board.HousieBoard;
import com.playstation.housie.profile.Profile;

/**
 * This class represents a housie game ticket
 * @author sandesh.karambettu
 *
 */
@Entity
public class HousieTicket {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private int[] row1;
	private int[] row2;
	private int[] row3;
	
	private List<Integer> mClaimedNumbers1, mClaimedNumbers2, mClaimedNumbers3;
	
	@ManyToOne
	private HousieBoard board;
	
	@ManyToOne
	private Profile profile;
	
	public HousieTicket() {
		
	}

	
	public HousieTicket(HousieBoard pBoard, Profile pProfile, int[] row1, int[] row2, int[] row3) {
		this.board = pBoard;
		this.profile = pProfile;
		this.row1 = row1;
		this.row2 = row2;
		this.row3 = row3;
		this.mClaimedNumbers1 = new LinkedList<Integer>();
		this.mClaimedNumbers2 = new LinkedList<Integer>();
		this.mClaimedNumbers3 = new LinkedList<Integer>();
	}
	
	public boolean claimNumber(int number) {
		return (board.getDrawnNumbers().contains(number)) && (claimNumber(row1, mClaimedNumbers1, number) || claimNumber(row2, mClaimedNumbers2, number) || claimNumber(row3, mClaimedNumbers3, number));
	}
	
	private boolean claimNumber(int[] pNumbers, List<Integer> pClaimedNumbers, Integer pNumber) {
		for(int number: pNumbers) {
			if (number == pNumber) {
				return pClaimedNumbers.add(pNumber);
			}
		}
		return false;
	}
	
	public int claimNumbers(Profile pProfile) {
	  return this.mClaimedNumbers1.size() + this.mClaimedNumbers2.size() + this.mClaimedNumbers3.size();
	}
	
	public int topLine(Profile pProfile) {
	  return this.mClaimedNumbers1.size();
	}

	public long getId() {
		return id;
	}
	
	public Profile getProfile() {
	  return profile;
	}

}
