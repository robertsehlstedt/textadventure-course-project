package game.barriers;

import game.extras.Inventory;
import game.extras.Item;
import game.extras.Player;
import game.locations.Location;

import java.util.List;

/**
 * Implementation of {@link Barrier} in its simplest form
 */
public class GenericBarrier implements Barrier {

    protected String name;
    protected String info;
    protected Inventory inv;
    protected Item key;

    /**
     * @param name  The name of the barrier, also used as a key if the key is a command (e.g open sesame, or open box)
     * @param info  A description which will be displayed at the location if the item is available to the user
     * @param key   An {@link Item} if the barrier requires one to open, else null
     * @param items A list of {@link Item items} which will be hidden behind the barrier
     */
    public GenericBarrier(String name, String info, Item key, List<Item> items){
        this.name = name;
        this.info = info;
        this.key = key;
        this.inv = new Inventory();
        inv.addAll(items);
    }

    public String getInfo() {
        return info;
    }

    public boolean open(Player player, String message, Location loc){
        if (message.equals(name)){
            if (key == null || player.carries(key)){
                unpack(loc);
                return true;
            }
        }
        return false;
    }

    protected void unpack(Location loc){
        loc.addAll(inv);
    }
}
