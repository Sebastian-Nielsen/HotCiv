package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Player;

public class CityImpl implements City {
    private Player owner;
    private int treasury = 0;

    public CityImpl(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public int getSize() {
        return 1;
    }

    public int getTreasury() {
        return treasury;
    }

    public String getProduction() {
        return "archer";
    }

    public String getWorkforceFocus() {
        return null;
    }

    public void setTreasury(int p) {
        treasury = p;
    }

}
