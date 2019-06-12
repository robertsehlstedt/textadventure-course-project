package game.barriers;

import game.extras.Player;
import game.locations.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * A handler which contains a number of {@link Barrier barriers} and handles their logic.
 */
public class BarrierHandler {

	private List<Barrier> barriers;

	/**
	 * Creates a {@link BarrierHandler}.
	 */
	public BarrierHandler(){
		this.barriers = new ArrayList<>();
	}

	/**
	 * Adds every {@link Barrier} in a list to the handler.
	 * @param barriers	The {@link List<Barrier> barriers} to add.
	 */
	public void addAll(List<Barrier> barriers){
		this.barriers.addAll(barriers);
	}

	/**
	 * Opens a barrier if possible with the information provided.
	 * @param player	The {@link Player} of the game.
	 * @param message	The {@link String message} sent by the user
	 * @param loc		The {@link Location} where the player currently is.
	 * @return	True if a barrier was opened, else false.
	 */
	public boolean openBarrier(Player player, String message, Location loc){
		for (Barrier barrier : barriers) {
			if(barrier.open(player, message, loc)){
				removeBarrier(barrier);
				return true;
			}
		}
		return false;
	}

	private void removeBarrier(Barrier barrier){
		barriers.remove(barrier);
	}

	/**
	 * @return	A {@link String} with the information of all the barriers in the handler.
	 */
	public String getInfo(){
		StringBuilder b = new StringBuilder();
		for (Barrier barrier : barriers) {
			b.append(barrier.getInfo());
		}
		return b.toString();
	}
}
