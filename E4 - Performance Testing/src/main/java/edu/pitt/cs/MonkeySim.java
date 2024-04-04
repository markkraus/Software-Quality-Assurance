package edu.pitt.cs;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MonkeySim {

	public static boolean verbose = true;
	public static final int HEADER = 50000;

	/**
	 * Print out use message and exit with error code 1.
	 */

	public static void errorAndExit() {
		System.out.println("USAGE:");
		System.out.println("java MonkeySim <num_monkeys>");
		System.out.println("<num_monkeys> must be a positive signed 32-bit integer");
		System.exit(1);
	}

	/**
	 * Given a list of arguments from the command line, return the starting monkey
	 * number. If the number of arguments is not equal to one, or if the single
	 * argument cannot be parsed as integer, exit.
	 * 
	 * @param args - array of args from command line
	 * @return int - starting monkey
	 */

	public static int getStartingMonkeyNum(String[] args) {

		int s = 0;

		if (args.length != 1) {
			errorAndExit();
		}

		try {
			s = Integer.parseInt(args[0]);
		} catch (Exception e) {
			errorAndExit();
		}

		if (s < 1) {
			errorAndExit();
		}

		return s;

	}

	/**
	 * Get a reference to the first monkey in the list.
	 * 
	 * @return Monkey first monkey in list
	 */

	public Monkey getFirstMonkey(List<Monkey> ml) {
      return ml.isEmpty() ? null : ml.get(1);
	}

	/**
	 * Return a String version of a round
	 * 
	 * @param c  Round number
	 * @param m  Monkey thrown from
	 * @param m2 Monkey thrown to
	 * @return String string version of round
	 */

	public String stringifyResults(int c, Monkey m, Monkey m2) throws NoIdException {
      StringBuilder sb = new StringBuilder();

      // Append header
      for (int j = 0; j < HEADER; j++) {
        sb.append("@");
      }

      // Append round number and banana throw details
      sb.append("//Round ").append(c)
          .append(": Threw banana from Monkey (#").append(m.getMonkeyNum())
          .append(" / ID ").append(m.getId())
          .append(") to Monkey (#").append(m2.getMonkeyNum())
          .append(" / ID ").append(m2.getId()).append(")");

      // Return the substring excluding the header
      return sb.substring(HEADER);
    }

	/**
	 * Return the number of the monkey with a banana
	 * 
	 * @param
	 * @return int number of monkey w/ banana
	 */

	public int monkeyWithBanana(List<Monkey> ml) {
		for (int j = 0; j < ml.size(); j++) {
			Monkey m = ml.get(j);
			if (m.hasBanana()) {
				return m.getMonkeyNum();
			}
		}
		return -1;

	}

	public int addMoreMonkeys(int n, List<Monkey> ml) {
		int currentSize = ml.size();
      if (n > currentSize) {
        Supplier<Monkey> monkeySupplier = Monkey::new; // Supplier to create new Monkey instances
        Stream<Monkey> monkeyStream = Stream.generate(monkeySupplier).limit(n - currentSize + 1);
        List<Monkey> newMonkeys = monkeyStream.collect(Collectors.toList());
        ml.addAll(newMonkeys);
      }
      return ml.size();
	}

	public int nextMonkeyAndResize(Monkey m, List<Monkey> ml) {
		int n = m.nextMonkey();
		if (n > ml.size()) {
			addMoreMonkeys(n, ml);
		}

		return n;
	}

	/**
	 * Run the simulation
	 * 
	 * @param ml List of Monkeys
	 * @param mw watcher of monkey
	 * @return int number of rounds taken to get to first monkey
	 */

	public int runSimulation(List<Monkey> ml, MonkeyWatcher mw) throws NoIdException {
		while (!getFirstMonkey(ml).hasBanana()) {
			mw.incrementRounds();
			Monkey m = ml.get(monkeyWithBanana(ml));
			int n = nextMonkeyAndResize(m, ml);
			Monkey m2 = ml.get(n);
			Banana b = m.throwBananaFrom();
			m2.throwBananaTo(b);
			if (verbose) {
				String s = stringifyResults(mw.getRounds(), m, m2);
				System.out.println(s);
			}
		}
		if (verbose) {
			System.out.println("First monkey has the banana!");
		}
		return mw.getRounds();
	}

	/**
	 * Entry point of program - run MonkeySim. Accepts one argument, the starting
	 * monkey number.
	 * 
	 * @param args - Array of arguments from cmd line
	 */

	public static void main(String[] args) throws InfiniteLoopException, NoIdException {
    try {
      Thread.sleep(30000);
    } catch (InterruptedException iex) {
    }
		int s = getStartingMonkeyNum(args);
		
		Monkey tmpMonkey;
		Banana b = new Banana();
		MonkeyWatcher mw = new MonkeyWatcher();

		List<Monkey> monkeyList = new LinkedList<Monkey>();
		for (int j = 0; j < s + 1; j++) {
			tmpMonkey = new Monkey();
			monkeyList.add(tmpMonkey);
		}
		monkeyList.get(s).throwBananaTo(b);

		MonkeySim monkeySim = new MonkeySim();
		int numRounds = monkeySim.runSimulation(monkeyList, mw);
		System.out.println("Completed in " + numRounds + " rounds.");
	}
}
