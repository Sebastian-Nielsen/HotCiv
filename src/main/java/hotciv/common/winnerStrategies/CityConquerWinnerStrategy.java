package hotciv.common.winnerStrategies;

import hotciv.common.GameImpl;
import hotciv.framework.City;
import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

import java.util.Collection;
import java.util.Iterator;


public class CityConquerWinnerStrategy implements WinnerStrategy {

    @Override
    public Player determineWinner(GameImpl game) {
        // Retrieve all cities
        Collection<City> allCities = game.getAllCities();
        // Create an iterator over the cities
        Iterator<City> it = allCities.iterator();
        // Save the first owner
        Player prevOwner = it.next().getOwner();
        while (it.hasNext()) {
            // All subsequent owners should be equal to the first owner
            // or null is returned
            if (it.next().getOwner() != prevOwner) { return null; }
        }
        // All owners are the same, so it is returned
        return prevOwner;
    }
}
