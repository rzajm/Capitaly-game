package game;

public class GreedyStrategy implements Strategy {
    @Override
    public void tryBuyProperty(Player player, PropertyField property) {
        if (property.getOwner() == player && !property.hasHouse() && player.canAfford(4000)) {
            property.buildHouse(player);
        }

        if (property.getOwner() == null && player.canAfford(1000)) {
            property.buy(player);
        }
    }
}
