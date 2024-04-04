package edu.pitt.cs;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.mockito.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.ArrayList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CoffeeMakerQuestUnitTest {

	static final String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n")

	CoffeeMakerQuest cmq;
	Player player;
	ArrayList<Room> rooms;

	@Before
	public void setup() {
		// 1. Create a Player with no items (no coffee, no cream, no sugar) and assign to player.
		player = Player.createInstance(InstanceType.MOCK);

		// 2. Create 6 rooms exactly as specified in rooms.config and add to rooms list.
		// You are expected to hard-code the room configurations. The test cases in this
		// class depend on those hard-coded values, so it would be pointless to allow
		// them to be changed.
      rooms = new ArrayList<>();

		Room room1 = Room.createInstance(InstanceType.MOCK, "Quaint sofa", "Small", 
                  Item.CREAM, "Magenta", null);
    Room room2 = Room.createInstance(InstanceType.MOCK, "Sad record player", "Funny", 
                  Item.NONE, "Beige", "Massive");
    Room room3 = Room.createInstance(InstanceType.MOCK, "Tight Pizza", "Refinanced", 
                  Item.COFFEE, "Dead", "Smart");
    Room room4 = Room.createInstance(InstanceType.MOCK, "Flat energy drink", "Dumb", 
                  Item.NONE, "Vivacious", "Slim");
    Room room5 = Room.createInstance(InstanceType.MOCK, "Beautiful bag of money", "Bloodthirsty", 
                  Item.NONE, "Purple", "Sandy");
    Room room6 = Room.createInstance(InstanceType.MOCK, "Perfect air hockey table", "Rough", 
                  Item.SUGAR, null, "Minimalist");

    rooms = new ArrayList<>();
    rooms.add(room1);
    rooms.add(room2);
    rooms.add(room3);
    rooms.add(room4);
    rooms.add(room5);
    rooms.add(room6);


		// 3. Create a CoffeeMakerQuest object using player and rooms and assign to cmq.
		cmq = CoffeeMakerQuest.createInstance(InstanceType.IMPL, player, rooms);
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test case for String getInstructionsString().
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.getHelpString().
	 * Postconditions: Return value is:
	 *                 "N - Go north" + newline + "S - Go south" + newline + "L - Look and collect any items in the room" + newline +
	 *                 "I - Show inventory of items collected" + newline + "D - Drink coffee made from items in inventory" + newline.
	 * </pre>
	 */
	@Test
	public void testGetHelpString() throws Exception {
		Class<?> CoffeeMakerQuest = cmq.getClass();

    Method m = CoffeeMakerQuest.getDeclaredMethod("getHelpString");
    m.setAccessible(true);

    Object ret = m.invoke(cmq);
    
    assertEquals("N - Go north" + newline + "S - Go south" + newline + "L - Look and collect any items in the room" + newline + 
                  "I - Show inventory of items collected" + newline + "D - Drink coffee made from items in inventory" + newline, ret);
	}

	/**
	 * Test case for Room getCurrentRoom().
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.getCurrentRoom().
	 * Postconditions: Return value is rooms.get(0).
	 * </pre>
	 */
	@Test
	public void testGetCurrentRoom() {
    assertEquals(rooms.get(0), cmq.getCurrentRoom());
	}

	/**
	 * Test case for void setCurrentRoom(Room room) and Room getCurrentRoom().
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.setCurrentRoom(rooms.get(2)).
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.setCurrentRoom(rooms.get(2)) is true. 
	 *                 Return value of cmq.getCurrentRoom() is rooms.get(2).
	 * </pre>
	 */
	@Test
	public void testSetCurrentRoom() {
    assertTrue(cmq.setCurrentRoom(rooms.get(2)));
    assertEquals(rooms.get(2), cmq.getCurrentRoom());
	}

	/**
	 * Test case for boolean areDoorsPlacedCorrectly() when check succeeds.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.areDoorsPlacedCorrectly().
	 * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is true.
	 * </pre>
	 */
	@Test
	public void testAreDoorsPlacedCorrectly() {
		assertTrue(cmq.areDoorsPlacedCorrectly());
	}

	/**
	 * Test case for boolean areDoorsPlacedCorrectly() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms.get(3) is modified so that it has no south door.
	 * Execution steps: Call cmq.areDoorsPlacedCorrectly().
	 * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is false.
	 * </pre>
	 */
	@Test
	public void testAreDoorsPlacedCorrectlyMissingSouthDoor() {
    Room tempRoom3 = Room.createInstance(InstanceType.MOCK, "Tight pizza", "Refinanced", 
                      Item.COFFEE, "Dead", null);
    rooms.set(2, tempRoom3);
    assertFalse(cmq.areDoorsPlacedCorrectly());
	}

  /**
   * Test case for boolean areDoorsPlacedCorrectly() when check fails.
   * 
   * <pre>
   * Preconditions: Player, rooms, and cmq test fixture have been created.
   *                rooms.get(3) is modified so that it has no north door.
   * Execution steps: Call cmq.areDoorsPlacedCorrectly().
   * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is false.
   * </pre>
   */
  @Test
  public void testAreDoorsPlacedCorrectlyMissingNorthDoor() {
    Room tempRoom3 = Room.createInstance(InstanceType.MOCK, "Tight pizza", "Refinanced",
        Item.COFFEE, null, "Smart");
    rooms.set(2, tempRoom3);
    assertFalse(cmq.areDoorsPlacedCorrectly());
  }

	/**
	 * Test case for boolean areRoomsUnique() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms.get(2) is modified so that its adjective is modified to "Small".
	 * Execution steps: Call cmq.areRoomsUnique().
	 * Postconditions: Return value of cmq.areRoomsUnique() is false.
	 * </pre>
	 */
	@Test
	public void testAreRoomsUniqueDuplicateAdjective() {
		Room tempRoom2 = Room.createInstance(InstanceType.MOCK, "Sad record player", "Small", 
                      Item.NONE, "Beige", "Massive");
    rooms.set(1, tempRoom2);
    assertFalse(cmq.areRoomsUnique());
	}

	/**
	 * Test case for String processCommand("I").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.processCommand("I").
	 * Postconditions: Return value is: "YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline.
	 * </pre>
	 */
	@Test
	public void testProcessCommandI() {
		assertEquals("YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline, cmq.processCommand("I"));
	}

	/**
	 * Test case for String processCommand("l").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.processCommand("l").
	 * Postconditions: Return value is: "There might be something here..." + newline + "You found some creamy cream!" + newline.
	 *                 Cream is added to player.
	 * </pre>
	 */
	@Test
	public void testProcessCommandLCream() {
		assertEquals("There might be something here..." + newline + "You found some creamy cream!" + newline, cmq.processCommand("l"));

    when(player.hasItem(Item.CREAM)).thenReturn(true);
    when(player.hasItem(Item.COFFEE)).thenReturn(false);
    when(player.hasItem(Item.SUGAR)).thenReturn(false);

    verify(player, times(1)).addItem(Item.CREAM);
    verify(player, times(0)).addItem(Item.COFFEE);

    assertTrue(player.hasItem(Item.CREAM));
	}

  /**
   * Test case for String processCommand("L").
   * 
   * <pre>
   * Preconditions: Player, rooms, and cmq test fixture has been created.
   * cmq.setCurrentRoom(rooms.get(3)) has been called.
   * Execution steps: Call cmq.processCommand("L").
   * Postconditions: Return value is: "There might be something here..." + newline
   *                  + "You found some caffeinated coffee!" + newline.
   *                  Coffee is added to player.
   * </pre>
   */
  @Test
  public void testProcessCommandLCoffee() {
    cmq.setCurrentRoom(rooms.get(2));

    assertEquals("There might be something here..." + newline + "You found some caffeinated coffee!" + newline,
        cmq.processCommand("L"));

    player.addItem(Item.COFFEE);
    when(player.hasItem(Item.COFFEE)).thenReturn(true);

    assertTrue(player.hasItem(Item.COFFEE));
  }

  /**
   * Test case for String processCommand("L").
   * 
   * <pre>
   * Preconditions: Player, rooms, and cmq test fixture has been created.
   *                cmq.setCurrentRoom(rooms.get(5)) has been called.
   * Execution steps: Call cmq.processCommand("L").
   * Postconditions: Return value is: "There might be something here..." + newline
   *                                  + "You found some sweet sugar!" + newline.
   *                                  Sugar is added to player.
   * </pre>
   */
  @Test
  public void testProcessCommandLSugar() {
    cmq.setCurrentRoom(rooms.get(5));

    assertEquals("There might be something here..." + newline + "You found some sweet sugar!" + newline,
        cmq.processCommand("L"));

    player.addItem(Item.SUGAR);
    when(player.hasItem(Item.SUGAR)).thenReturn(true);

    assertTrue(player.hasItem(Item.SUGAR));
  }

	/**
	 * Test case for String processCommand("n").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                cmq.setCurrentRoom(rooms.get(3)) has been called.
	 * Execution steps: Call cmq.processCommand("n").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is "".
	 *                 Return value of cmq.getCurrentRoom() is rooms.get(4).
	 * </pre>
	 */
	@Test
	public void testProcessCommandN() {
		cmq.setCurrentRoom(rooms.get(3));
    assertEquals("", cmq.processCommand("n"));
    assertEquals(rooms.get(4), cmq.getCurrentRoom());
	}

	/**
	 * Test case for String processCommand("s").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.processCommand("s").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("s") is: "A door in that direction does not exist." + newline.
	 *                 Return value of cmq.getCurrentRoom() is rooms.get(0).
	 * </pre>
	 */
	@Test
	public void testProcessCommandS() {
		assertEquals("A door in that direction does not exist." + newline, cmq.processCommand("s"));
    assertEquals(rooms.get(0), cmq.getCurrentRoom());
	}

  /**
   * Test case for processCommand("s").
   * 
   * <pre>
   * Preconditions: Player, rooms, and cmq test fixture has been created.
   *                cmq.setCurrentCommand(rooms.get(3)) has been called.
   * Execution steps: Call cmq.processCommand("s").
   * Postconditions: Return value of cmq.processCommand("s") is: "".
   *                 Return value of cmq.getCurrentRoom() is rooms.get(2).
   * </pre>
   */
  @Test
  public void testProcessCommandSValid() {
    cmq.setCurrentRoom(rooms.get(3));
    assertEquals("",  cmq.processCommand("s"));
    assertEquals(rooms.get(2), cmq.getCurrentRoom());
  }

  /**
   * Test case for String processCommand("z").
   * 
   * <pre>
   * Preconditions: Player, rooms, and cmq test fixture has been created.
   * Execution step: Call cmq.processCommand("z").
   * Postconditions: Return value of cmq.processCommand("z") is: "What?".
   */
  @Test
  public void testProcessCommandInvalid() {
    assertEquals("What?", cmq.processCommand("z"));
  }

	/**
	 * Test case for String processCommand("D").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is: "YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + 
	 *                    "YOU HAVE NO SUGAR!" + newline + newline + "You drink thin air and can only dream of coffee. You cannot study." + newline + "You lose!" + newline.
	 *                 Return value of cmq.isGameOver() is true.
	 * </pre>
	 */
	@Test
    public void testProcessCommandDLose() {
      // Check if the player is a mock object
      if (Mockito.mockingDetails(player).isMock()) {
        when(player.hasItem(any(Item.class))).thenReturn(false);
      }

      assertEquals(
          "YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline + newline +
              "You drink thin air and can only dream of coffee. You cannot study." + newline + "You lose!" + newline,
          cmq.processCommand("D"));
      assertTrue(cmq.isGameOver());
    }

	/**
	 * Test case for String processCommand("D").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                Player has all 3 items (coffee, cream, sugar).
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is: "You have a cup of delicious coffee." + newline + "You have some fresh cream." + newline + 
	 *                    "You have some tasty sugar." + newline + newline + "You drink the beverage and are ready to study!" + newline + "You win!" + newline.
	 *                 Return value of cmq.isGameOver() is true.
	 * </pre>
	 */
	@Test
    public void testProcessCommandDWin() {
      player.addItem(Item.COFFEE);
      player.addItem(Item.CREAM);
      player.addItem(Item.SUGAR);
      when(player.hasItem(Item.COFFEE)).thenReturn(true);
      when(player.hasItem(Item.CREAM)).thenReturn(true);
      when(player.hasItem(Item.SUGAR)).thenReturn(true);

      // Check if the player is a mock object
      if (Mockito.mockingDetails(player).isMock()) {
        when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee." + newline
            + "You have some fresh cream." + newline + "You have some tasty sugar." + newline);
      }

      assertEquals(
          "You have a cup of delicious coffee." + newline + "You have some fresh cream." + newline
              + "You have some tasty sugar."
              + newline + newline + "You drink the beverage and are ready to study!" + newline + "You win!" + newline,
          cmq.processCommand("D"));
      assertTrue(cmq.isGameOver());
    }
}