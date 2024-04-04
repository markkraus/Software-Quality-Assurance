package edu.pitt.cs;

import java.util.ArrayList;
import java.util.HashSet;

public class CoffeeMakerQuestImpl implements CoffeeMakerQuest {

	static final String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n")
	
  private ArrayList<Room> rooms;
  private Player player;
  private Room currentLocation;
  boolean gameOver;

	/**
	 * Constructor. Rooms are laid out from south to north, such that the
	 * first room in rooms becomes the southernmost room and the last room becomes
	 * the northernmost room. Initially, the player is at the southernmost room.
	 * 
	 * @param player Player for this game
	 * @param rooms  List of rooms in this game
	 */
	CoffeeMakerQuestImpl(Player player, ArrayList<Room> rooms) {
		this.rooms = rooms;
    this.player = player;
    setCurrentRoom(rooms.get(0));
    gameOver = false;
	}

	/**
	 * Whether the game is over. The game ends when the player drinks the coffee.
	 * 
	 * @return true if the game is over, false otherwise
	 */
	public boolean isGameOver() {
    if (gameOver) {
      return true;
    }
    return false;
	}

	/**
	 * The method returns success if and only if: 1) Th southernmost room has a
	 * north door only, 2) The northernmost room has a south door only, and 3) The
	 * rooms in the middle have both north and south doors. If there is only one
	 * room, there should be no doors.
	 * 
	 * @return true if check successful, false otherwise
	 */
	public boolean areDoorsPlacedCorrectly() {
    // Check the end rooms
    Room southernMostRoom = rooms.get(0);
    Room northernMostRoom = rooms.get(rooms.size() - 1);
    if (southernMostRoom.getSouthDoor() != null || northernMostRoom.getNorthDoor() != null) {
      return false;
    }

    // Check the middle rooms
    for (int i = 1; i < rooms.size() - 1; i++) {
      Room mid = rooms.get(i);
      if (mid.getSouthDoor() == null || mid.getNorthDoor() == null) {
        return false;
      }
    }

		return true;
	}

	/**
   * Checks whether each room has a unique adjective and furnishing.
   * 
   * @return true if check successful, false otherwise
   */
  public boolean areRoomsUnique() {
    ArrayList<String> adjectives = new ArrayList<>();
    ArrayList<String> furnishings = new ArrayList<>();

    // Add the current room's adjective & furnishing to a list
    for (int i = 0; i < rooms.size() - 1; i++) {
      Room current = rooms.get(i);
      String adj = current.getAdjective();
      String fur = current.getFurnishing();
      adjectives.add(adj);
      furnishings.add(fur);
    }

    // Check if all adjectives & furnishings are unique
    HashSet<String> uniqueAdjectives = new HashSet<>(adjectives);
    HashSet<String> uniqueFurnishings = new HashSet<>(furnishings);

    return adjectives.size() == uniqueAdjectives.size() && furnishings.size() == uniqueFurnishings.size();
  }

	/**
	 * Returns the room the player is currently in. If location of player has not
	 * yet been initialized with setCurrentRoom, returns null.
	 * 
	 * @return room player is in, or null if not yet initialized
	 */
	public Room getCurrentRoom() {
		return currentLocation;
	}

	/**
	 * Set the current location of the player. If room does not exist in the game,
	 * then the location of the player does not change and false is returned.
	 * 
	 * @param room the room to set as the player location
	 * @return true if successful, false otherwise
	 */
	public boolean setCurrentRoom(Room room) {   
		if (rooms == null || !rooms.contains(room)) {
      return false;
    }

    // Get the room & set the player's location there
    int index = rooms.indexOf(room);
    currentLocation = rooms.get(index);
		return true;
	}

	/**
	 * Get the instructions string command prompt. It returns the following prompt:
	 * " INSTRUCTIONS (N,S,L,I,D,H) > ".
	 * 
	 * @return comamnd prompt string
	 */
	public String getInstructionsString() {
		return " INSTRUCTIONS (N,S,L,I,D,H) > ";
	}

	/**
	 * A helper method for the "H" command.  It returns the following help string:
	 * "N - Go north" + newline + "S - Go south" + newline + "L - Look and collect any items in the room" + newline +
	 * "I - Show inventory of items collected" + newline + "D - Drink coffee made from items in inventory" + newline.
	 * 
	 * @return help string
	 */
	private String getHelpString() {
		return "N - Go north" + newline + "S - Go south" + newline + "L - Look and collect any items in the room" 
            + newline + "I - Show inventory of items collected" + newline + "D - Drink coffee made from items in inventory"
            + newline;
	}

	/**
	 * Processes the user command given in String cmd and returns the response
	 * string. For the list of commands, please see the Coffee Maker Quest
	 * requirements documentation (note that commands can be both upper-case and
	 * lower-case). For the response strings, observe the response strings printed
	 * by coffeemaker.jar. The "N" and "S" commands potentially change the location
	 * of the player. The "L" command potentially adds an item to the player
	 * inventory. The "D" command drinks the coffee and ends the game. Make
	 * sure you use Player.getInventoryString() whenever you need to display
	 * the inventory.
	 * 
	 * @param cmd the user command
	 * @return response string for the command
	 */
	public String processCommand(String cmd) {
    Room room = currentLocation;
    int index = rooms.indexOf(currentLocation);

		switch (cmd) {
      // Move North
      case "n":
      case "N":
        if (room.getNorthDoor() != null) {
          // Move to the northern door
          currentLocation = rooms.get(index + 1);
        } else {
          return "A door in that direction does not exist." + newline;
        }
        break;

      // Move South
      case "s":
      case "S":
        if (room.getSouthDoor() != null) {
          // Move to the southern door
          currentLocation = rooms.get(index - 1);
        } else {
          return "A door in that direction does not exist." + newline;
        }
        break;
      
      // Look for items
      case "l":
      case "L":
        if (currentLocation.getItem() == Item.NONE) {
          return "You don't see anything out of the ordinary" + newline;
        } else {
          // Item was found - find and print its description
          if (currentLocation.getItem() == Item.COFFEE) {
            player.addItem(Item.COFFEE);
            return "There might be something here..." + newline + "You found some caffeinated coffee!" + newline;
          } else if (currentLocation.getItem() == Item.CREAM) {
            player.addItem(Item.CREAM);
            return "There might be something here..." + newline + "You found some creamy cream!" + newline;
          } else {
            player.addItem(Item.SUGAR);
            return "There might be something here..." + newline + "You found some sweet sugar!" + newline;
          }
        }

      // Check Inventory
      case "i":
      case "I":
        return player.getInventoryString();

      // Drink coffee
      case "d":
      case "D":
        gameOver = true;
        if (player.hasItem(Item.COFFEE) && player.hasItem(Item.CREAM) && player.hasItem(Item.SUGAR)) {
          // Player has all items - win
          return player.getInventoryString() + newline + "You drink the beverage and are ready to study!" + newline + "You win!" + newline;
        } else if (player.hasItem(Item.COFFEE) || player.hasItem(Item.CREAM) || player.hasItem(Item.SUGAR)) {
          // Player has some items - lose
          return player.getInventoryString() + newline
              + "You refuse to drink this half-made sludge. You cannot study." + newline + "You lose!" + newline;
        } else {
          // player has no items - lose
          return player.getInventoryString() + newline
              + "You drink thin air and can only dream of coffee. You cannot study." + newline + "You lose!" + newline;
        }

      // Prompt Help
      case "h":
      case "H":
        return getHelpString() + newline;

      default:
        room = getCurrentRoom();
        return "What?";
    }
		return "";
	}

}
