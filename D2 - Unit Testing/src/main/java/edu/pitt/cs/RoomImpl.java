package edu.pitt.cs;

public class RoomImpl implements Room {

  private String furnishing, adjective, northDoor, southDoor;
  private Item item;
	
	static final String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n")

	/**
	 * Constructor. The northDoor or the southDoor can be null if there no doors leading north or south.
	 * 
	 * @param furnishing Furnishing in the room
	 * @param adjective Adjective describing the room
	 * @param item Item present in the room
	 * @param northDoor Description of north door (null if there is no north door)
	 * @param southDoor Description of south door (null if there is no south door)
	 */
	public RoomImpl(String furnishing, String adjective, Item item, String northDoor, String southDoor) {
    this.furnishing = furnishing;
    this.adjective = adjective;
    this.item = item;
    this.northDoor = northDoor;
    this.southDoor = southDoor;
	}
	
	public String getFurnishing() {
		return furnishing;
	}

	public String getAdjective() {
		return adjective;
	}

	public Item getItem() {
		return item;
	}

	public String getNorthDoor() {
		return northDoor;
	}

	public String getSouthDoor() {
		return southDoor;
	}
	
	public String getDescription() {
    StringBuilder description = new StringBuilder();
    description.append("You see a ").append(adjective).append(" room.").append(newline);
    description.append("It has a ").append(furnishing).append(".").append(newline);

    if (northDoor != null) {
      description.append("A ").append(northDoor).append(" door leads North.").append(newline);
    }

    if (southDoor != null) {
      description.append("A ").append(southDoor).append(" door leads South.").append(newline);
    }

    return description.toString();
  }
}
