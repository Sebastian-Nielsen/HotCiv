package hotciv.common;

import hotciv.framework.City;
import hotciv.framework.Player;

import static hotciv.framework.GameConstants.ARCHER;

public class CityImpl implements City {
    private Player owner;
    private int treasury = 0;
    private String currentProduction = ARCHER;
    private int currentSize = 1;

    public CityImpl(Player owner) {
        this.owner = owner;
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
        return null;
    }

    public void setTreasury(int p) {
        treasury = p;
    }

    public void setOwner(Player newOwner) { owner = newOwner; }

    public void setProduction(String newProduction) { currentProduction = newProduction; }
}
