public class WarpStone extends Item{
    public WarpStone(int xCoordinate, int yCoordinate){
        this.setName("WarpStone");
        this.setSymbol("@");
        this.setXCoordinate(xCoordinate);
        this.setYCoordinate(yCoordinate);
    }
    @Override
    public void usedBy(Player player) {
        player.levelUp();
        System.out.println("World complete! (You leveled up!)");
    }
}
