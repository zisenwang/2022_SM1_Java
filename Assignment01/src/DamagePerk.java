public class DamagePerk extends Item{
    public DamagePerk(int xCoordinate, int yCoordinate){
        this.setName("DamagePerk");
        this.setSymbol("^");
        this.setXCoordinate(xCoordinate);
        this.setYCoordinate(yCoordinate);
    }
    @Override
    public void usedBy(Player player) {
        player.setBonusDamage(1);
        System.out.println("Attack up!");
    }
}
