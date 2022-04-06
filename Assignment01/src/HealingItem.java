public class HealingItem extends Item{
    public HealingItem(int xCoordinate, int yCoordinate){
        this.setName("HealingItem");
        this.setSymbol("+");
        this.setXCoordinate(xCoordinate);
        this.setYCoordinate(yCoordinate);
    }
    @Override
    public void usedBy(Player player) {
        player.heal();
        System.out.println("Healed!");
    }
}
