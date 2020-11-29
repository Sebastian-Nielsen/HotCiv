package hotciv.common;

import hotciv.framework.City;
import hotciv.framework.Player;

import java.util.UUID;

import static hotciv.framework.GameConstants.ARCHER;
import static hotciv.framework.GameConstants.foodFocus;

public class CityImpl implements City {
	private final String id;
	private Player owner;
	private int treasury = 0;
	private String currentProduction = ARCHER;
	private int currentSize = 1;

	public CityImpl(Player owner) {
		this.owner = owner;
		// Create the object ID to bind server and client side
		// Servant-ClientProxy objects together
		this.id = UUID.randomUUID().toString();
	}

	public Player getOwner() {
		return owner;
	}

	public int getSize() {
		return currentSize;
	}

	public void setSize(int n) { currentSize = n; }

	public int getTreasury() {
		return treasury;
	}

	public String getProduction() {
		return currentProduction;
	}

	public String getWorkforceFocus() {
		return foodFocus;
	}

	public void setTreasury(int p) {
		treasury = p;
	}

	public void setOwner(Player newOwner) { owner = newOwner; }

	public void setProduction(String newProduction) { currentProduction = newProduction; }

	public String getId() {
		return id;
	}
}
