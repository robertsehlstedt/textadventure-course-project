package game.barriers;

import game.extras.Player;
import game.locations.Location;

/**
 * A barrier which can hide parts of the map behind it
 */
public interface Barrier {

    /**
     * @return the information which will be displayed in the game about the barrier
     */
    public String getInfo();

    /**
     * Attempts to open the barrier
     * @param player	The player of the game
     * @param message	the message from the player
     * @param loc	    The location to unpack to
     * @return	true if opened, else false
     */
    public boolean open(Player player, String message, Location loc);
}
