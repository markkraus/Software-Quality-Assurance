package edu.pitt.cs;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public interface Player {
	static final String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n")
	
	public static Player createInstance(InstanceType type) {
		switch (type) {
			case IMPL:
				return new PlayerImpl();
			case BUGGY:
				return new PlayerBuggy();
			case SOLUTION:
				return new PlayerSolution();
			case MOCK:
        Player mockPlayer = mock(Player.class);
        when(mockPlayer.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline);
				return mockPlayer;
			default:
				assert(false);
				return null;
		}
	}

	// WARNING: You are not allowed to change any part of the interface.
	// That means you cannot add any method nor modify any of these methods.
	
	public void addItem(Item item);

	public boolean hasItem(Item item);

	public String getInventoryString();
}
