package game;
public class CarefulStrategy implements Strategy {
    @Override
    public void tryBuyProperty(Player player, PropertyField property) {
        if (player.canAfford(1000) && (player.getBalance() / 2) >= 1000 && property.getOwner() == null) {
            property.buy(player);
        } else if (player.canAfford(4000) && (player.getBalance() / 2) >= 4000 && property.getOwner() == player && !property.hasHouse()) {
            property.buildHouse(player);
        }
    }

}

