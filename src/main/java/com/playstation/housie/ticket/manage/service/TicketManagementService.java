package com.playstation.housie.ticket.manage.service;

import com.playstation.housie.board.HousieBoard;
import com.playstation.housie.profile.Profile;
import com.playstation.housie.ticket.HousieTicket;
import com.playstation.housie.ticket.dao.TicketDAO;

public class TicketManagementService {
	private static TicketDAO  ticketRepository = new TicketDAO();
	
	public static long createTicket(HousieBoard housieBoard, Profile profile) {
		int[][] ticket = TicketGeneratorService.generate();
		HousieTicket housieTicket = new HousieTicket(housieBoard, profile, ticket[0], ticket[1], ticket[2]);
		ticketRepository.create(housieTicket);
		return housieTicket.getId();
	}

	public static HousieTicket loadTicket(long ticketId) {
		return ticketRepository.load(ticketId);
	}
}
