package com.playstation.housie.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.playstation.housie.board.HousieBoard;
import com.playstation.housie.board.manage.service.BoardManagementService;
import com.playstation.housie.exception.FullBoardException;
import com.playstation.housie.exception.InvalidGameException;
import com.playstation.housie.profile.Profile;
import com.playstation.housie.profile.manage.service.ProfileManagementService;
import com.playstation.housie.ticket.HousieTicket;
import com.playstation.housie.ticket.manage.service.TicketManagementService;

public class HousieMain {

  public static void main(String[] args) throws InvalidGameException, FullBoardException {
    System.out.println("*** Lets Play Housie *******");

    System.out.println(" Note: - Press 'Q' to quit any time");
    Scanner sc = new Scanner(System.in);
    System.out.println(">>Enter the number range(1-n) :");
    int range = sc.nextInt();
    System.out.println(">>Enter Number of players playing the game :");
    int playerCount = sc.nextInt();
    System.out.println(">>Enter Ticket Size : 3 * 9");
    System.out.println(">>Enter numbers per row : 5");

    long gameId = BoardManagementService.createNewBoard(range);
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
    Map<String, String> map = new LinkedHashMap<>();
    for (int i = 0; i < 90; i++) {
      int drawnNumber = 0;
      try {
        drawnNumber = BoardManagementService.drawNextNumber(gameId);
        System.out.println("Next number is: " + drawnNumber);
      } catch (FullBoardException e) {
        throw e;
      } catch (InvalidGameException e) {
        throw e;
      }

      for (int j = 0; j < ticketList.size(); j++) {
        ticketList.get(j).claimNumber(drawnNumber);

        Profile profile = ticketList.get(j).getProfile();
        int claimNumbers = ticketList.get(j).claimNumbers(profile);

        if (claimNumbers == 5) {
          System.out.println("We have a winner: " + profile.getName()
              + " has won 'First Five' winning combination");
          map.put(profile.getName(), "FirstFive");
        }
        int topLine = ticketList.get(i).topLine(profile);
        if (topLine == 5) {
          System.out.println("We have a winner: " + profile.getName()
              + " has won 'Top Line' winning combination");
          if (map.containsKey(profile.getName())) {
            String value = map.get(profile.getName());
            map.put(profile.getName(), value + "," + "TopLine");
          } else {
            map.put(profile.getName(), "TopLine");
          }
        }
        if (claimNumbers == 15) {
          System.out.println("We have a winner: " + profile.getName()
              + " has won 'Full House' winning combination");

          if (map.containsKey(profile.getName())) {
            String value = map.get(profile.getName());
            map.put(profile.getName(), value + "," + "FullHouse");
          } else {
            map.put(profile.getName(), "FullHouse");
          }
        }
      }
    }
    System.out.println("****** Game Over *******");
    System.out.println("================================");
    System.out.println("Summary");

    Set<String> keys = map.keySet();
    for (String k : keys) {
      System.out.println(k + " : " + map.get(k));
    }
  }

}
