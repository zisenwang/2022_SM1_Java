/**
 * A class for usable Item in this game.
 * Containing Healing Item, Damage Perk, Warp Stone
 */
public class Item extends Entity{
    public Item(int xCoordinate, int yCoordinate,String symbol){
        this.setXCoordinate(xCoordinate);
        this.setYCoordinate(yCoordinate);

    }

    public Item() {
    }

    public void usedBy(Player player){

    }
}
