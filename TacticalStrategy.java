package game;


public class TacticalStrategy implements Strategy {
    private boolean skipTurn = false;

    @Override
    public void tryBuyProperty(Player player, PropertyField property) {
        if(skipTurn==true){
            System.out.println("I skip this turn ");
        }
        if (!skipTurn && player.canAfford(1000) && property.getOwner() == null) {
            property.buy(player);
        } else if (!skipTurn && player.canAfford(4000) && property.getOwner() == player && !property.hasHouse()) {
            property.buildHouse(player);
        }
        skipTurn = !skipTurn;
    }
}
