package edu.pitt.cs;

import java.util.ArrayList;
import java.util.List;

class PlayerImpl implements Player {

	List<Item> inventory;
  Room currentLocation;
	
	static final String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n")

	public PlayerImpl() {
		inventory = new ArrayList<>();
    currentLocation = null;
	}
	
	public void addItem(Item item) {
		inventory.add(item);
	}

	public boolean hasItem(Item item) {
		if (inventory.contains(item)) {
      return true;
    }
		return false;
	}
	
	public String getInventoryString() {
    StringBuilder sb = new StringBuilder();
		if (!inventory.contains(Item.COFFEE)) {
      sb.append("YOU HAVE NO COFFEE!" + newline);
    } else {
      sb.append("You have a cup of delicious coffee." + newline);
    }
    if (!inventory.contains(Item.CREAM)) {
      sb.append("YOU HAVE NO CREAM!" + newline);
    } else {
      sb.append("You have some fresh cream." + newline);
    }
    if (!inventory.contains(Item.SUGAR)) {
      sb.append("YOU HAVE NO SUGAR!" + newline);
    } else {
      sb.append("You have some tasty sugar." + newline);
    }
    return sb.toString();
	}
}