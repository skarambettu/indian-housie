package com.playstation.housie.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.playstation.housie.board.HousieBoard;
import com.playstation.housie.board.manage.service.BoardManagementService;
import com.playstation.housie.exception.FullBoardException;
import com.playstation.housie.exception.InvalidGameException;
import com.playstation.housie.profile.Profile;
import com.playstation.housie.profile.manage.service.ProfileManagementService;
import com.playstation.housie.ticket.HousieTicket;
import com.playstation.housie.ticket.manage.service.TicketManagementService;

public class HousieMain {

  public static void main(String[] args) {
    System.out.println("*** Lets Play Housie *******");

    System.out.println(" Note: - Press 'Q' to quit any time");
    Scanner sc = new Scanner(System.in);
    System.out.println(">>Enter the number range(1-n) :");
    int range = sc.nextInt();
    System.out.println(">>Enter Number of players playing the game :");
    int playerCount = sc.nextInt();
    System.out.println(">>Enter Ticket Size : 3 * 9");
    System.out.println(">>Enter numbers per row : 5");

    long gameId = BoardManagementService.createNewBoard();
    HousieBoard board = BoardManagementService.loadBoard(gameId);

    ProfileManagementService profileManagementService =
        new ProfileManagementService();
    List<HousieTicket> ticketList = new ArrayList<>();
    for (int i = 1; i <= playerCount; i++) {
      long profileId = profileManagementService.createProfile("Player#" + i);
      Profile profile = profileManagementService.loadProfile(profileId);
      long ticketId = TicketManagementService.createTicket(board, profile);
      HousieTicket ticket = TicketManagementService.loadTicket(ticketId);
      ticketList.add(ticket);
    }
    System.out.println("**Ticket created Successfully ****");
    System.out.println(">> Press 'N' to generate next number :");
    String pressN = sc.nextLine();
    //if ("N".equals(pressN)) {
      for (int i = 0; i < 90; i++) {
        int drawnNumber = 0;
        try {
          drawnNumber = BoardManagementService.drawNextNumber(gameId);
          System.out.println("Next number is: " + drawnNumber);
        } catch (FullBoardException e) {
          e.printStackTrace();
        } catch (InvalidGameException e) {
          e.printStackTrace();
        }

        for (int j = 0; j < ticketList.size(); j++) {
          ticketList.get(j).claimNumber(drawnNumber);

          Profile profile = ticketList.get(j).getProfile();
          int claimNumbers = ticketList.get(j).claimNumbers(profile);

          if (claimNumbers == 5) {
            System.out.println("We have a winner: " + profile.getName()
                + " has won 'First Five' winning combination");
          }
          int topLine = ticketList.get(i).topLine(profile);
          if (topLine == 5) {
            System.out.println("We have a winner: " + profile.getName()
                + " has won 'Top Line' winning combination");
          }
          if (claimNumbers == 15) {
            System.out.println("We have a winner: " + profile.getName()
                + " has won 'Full House' winning combination");
          }
        }
      }
      System.out.println("****** Game Over *******");
    //}
  }

}
