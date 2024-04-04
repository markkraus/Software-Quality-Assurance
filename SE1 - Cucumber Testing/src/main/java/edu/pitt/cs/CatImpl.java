package edu.pitt.cs;

public class CatImpl implements Cat {

	String catName;
  int ID;
  boolean rented;

	public CatImpl(int id, String name) {
		catName = name;
    ID = id;
	}

	public void rentCat() {
		rented = true;
	}

	public void returnCat() {
		rented = false;
	}

	public void renameCat(String name) {
		catName = name;
	}

	public String getName() {
		return catName;
	}

	public int getId() {
		return ID;
	}

	public boolean getRented() {
		return rented;
	}

	public String toString() {
		return "ID " + ID + ". " + catName;
	}

}