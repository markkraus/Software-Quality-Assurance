package edu.pitt.cs;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerUnitTest {

	static final String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n")

	Player player;

	@Before
	public void setup() {
		// 1. Create a Player with no items (no coffee, no cream, no sugar) and assign to player.
		player = Player.createInstance(InstanceType.IMPL);
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test that player has been constructed properly.
	 * 
	 * <pre>
	 * Preconditions: player has been created.
	 * Execution steps: None.
	 * Postconditions: player does not have coffee, cream, nor sugar.
	 *                 player.getInventoryString() returns: "YOU HAVE NO COFFEE!" + newline +
	 *                    "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline.
	 * </pre>
	 */
	@Test
	public void testConstructor() {
		assertEquals("YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline, player.getInventoryString());
	}

	/**
	 * Test adding coffee to player.
	 * 
	 * <pre>
	 * Preconditions: player has been created.
	 * Execution steps: Call player.addItem(Item.COFFEE).
	 * Postconditions: player has coffee but does not have cream and sugar.
	 *                 player.getInventoryString() returns: "You have a cup of delicious coffee." + newline +
	 *                    "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline.
	 * </pre>
	 */
	@Test
	public void testAddCoffee() {
		player.addItem(Item.COFFEE);

    assertEquals("You have a cup of delicious coffee." + newline + "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline,
      player.getInventoryString());
	}

	/**
	 * Test adding coffee and cream to player.
	 * 
	 * <pre>
	 * Preconditions: player has been created.
	 * Execution steps: Call player.addItem(Item.COFFEE).
	 *                  Call player.addItem(Item.CREAM).
	 * Postconditions: player has coffee and cream but does not have sugar.
	 *                 player.getInventoryString() returns: "You have a cup of delicious coffee." + newline +
	 *                    "You have some fresh cream." + newline + "YOU HAVE NO SUGAR!" + newline.
	 * </pre>
	 */
	@Test
	public void testAddCoffeeCream() {
    player.addItem(Item.COFFEE);
    player.addItem(Item.CREAM);

    assertEquals("You have a cup of delicious coffee." + newline + "You have some fresh cream."
     + newline + "YOU HAVE NO SUGAR!" + newline, player.getInventoryString());
	}

	/**
	 * Test adding coffee, cream, and sugar to player.
	 * 
	 * <pre>
	 * Preconditions: player has been created.
	 * Execution steps: Call player.addItem(Item.COFFEE).
	 *                  Call player.addItem(Item.CREAM).
	 *                  Call player.addItem(Item.SUGAR).
	 * Postconditions: player has coffee, cream. and sugar.
	 *                 player.getInventoryString() returns: "You have a cup of delicious coffee." + newline +
	 *                    "You have some fresh cream." + newline + "You have some tasty sugar." + newline.
	 * </pre>
	 */
	@Test
	public void testAddCoffeeCreamSugar() {
		player.addItem(Item.COFFEE);
	  player.addItem(Item.CREAM);
	  player.addItem(Item.SUGAR);

    assertEquals("You have a cup of delicious coffee." + newline + "You have some fresh cream."
      + newline + "You have some tasty sugar." + newline, player.getInventoryString());
	}

	/**
   * Test adding coffee and checking a player has coffee.
   * 
   * <pre>
   * Preconditions: player has been created.
   * Execution steps: Call player.addItem(Item.COFFEE).
   *                  Call player.hasItem(Item.COFFEE).
   * Postconditions: player has coffee.
   *                 Return value is true.
   * </pre>
   */
  @Test
  public void testHasCoffee() {
    player.addItem(Item.COFFEE);
    assertTrue(player.hasItem(Item.COFFEE));
  }
}