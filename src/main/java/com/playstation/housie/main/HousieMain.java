package com.playstation.housie.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

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
    sc.nextLine();
    // Number generation and winner announcement
    numberGeneration(range, gameId, ticketList, sc);
  }
  
  private static void numberGeneration(int range, long gameId, List<HousieTicket> ticketList, Scanner sc) throws FullBoardException, InvalidGameException {
    String pressN = sc.nextLine();
    //System.out.println(pressN);
    Map<String, String> map = new HashMap<>();
    
    boolean firstFiveBool = false;
    boolean topFive = false;
    boolean fullHouse = false;
    for (int i = 0; i < range; i++) {
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

        if (claimNumbers == 5 && firstFiveBool == false) {
          System.out.println("We have a winner: " + profile.getName()
              + " has won 'First Five' winning combination");
          map.put(profile.getName(), "FirstFive");
          firstFiveBool = true;
        }
        int topLine = ticketList.get(j).topLine(profile);
        if (topLine == 5 && topFive == false) {
          System.out.println("We have a winner: " + profile.getName()
              + " has won 'Top Line' winning combination");
          if (map.containsKey(profile.getName())) {
            String value = map.get(profile.getName());
            map.put(profile.getName(), value + "," + "TopLine");
          } else {
            map.put(profile.getName(), "TopLine");
          }
          topFive = true;
        }
        if (claimNumbers == 15 && fullHouse == false) {
          System.out.println("We have a winner: " + profile.getName()
              + " has won 'Full House' winning combination");

          if (map.containsKey(profile.getName())) {
            String value = map.get(profile.getName());
            map.put(profile.getName(), value + "," + "FullHouse");
          } else {
            map.put(profile.getName(), "FullHouse");
          }
          fullHouse = true;
          break;
        }
      }
      if (fullHouse) {
        break;
      }
    }
    System.out.println("****** Game Over *******");
    System.out.println("================================");
    System.out.println("Summary");

    SortedSet<String> sortedKeys = new TreeSet<>(map.keySet());
    for (String k : sortedKeys) {
      System.out.println(k + " : " + map.get(k));
    }
  }
}
