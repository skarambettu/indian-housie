package com.playstation.housie.ticket.manage;

import org.junit.Test;

import com.playstation.housie.board.HousieBoard;
import com.playstation.housie.board.manage.service.BoardManagementService;
import com.playstation.housie.exception.FullBoardException;
import com.playstation.housie.exception.InvalidGameException;
import com.playstation.housie.profile.Profile;
import com.playstation.housie.profile.manage.service.ProfileManagementService;
import com.playstation.housie.ticket.HousieTicket;
import com.playstation.housie.ticket.manage.service.TicketManagementService;

public class TicketManagementTest {

	@Test
	public void test() {
	  
		long gameId = BoardManagementService.createNewBoard(90);
		HousieBoard board = BoardManagementService.loadBoard(gameId);
		
		ProfileManagementService profileManagementService = new ProfileManagementService();
		
		long profileId = profileManagementService.createProfile("Sandesh");
		Profile profile = profileManagementService.loadProfile(profileId);
		long ticketId = TicketManagementService.createTicket(board, profile);
		HousieTicket ticket = TicketManagementService.loadTicket(ticketId);

		for (int i = 0; i < 100; i++) {
			int drawnNumber = 0;
			try {
				drawnNumber = BoardManagementService.drawNextNumber(gameId);
			} catch (FullBoardException e) {
				e.printStackTrace();
			} catch (InvalidGameException e) {
			    e.printStackTrace();
			}
			if (ticket.claimNumber(drawnNumber)) {
				System.out.println(drawnNumber);
			}
			
			ticket.claimNumbers(profile);
		}

	}

}
