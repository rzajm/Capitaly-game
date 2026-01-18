package game;

public class PropertyField extends Field {
    private Player owner;
    private boolean hasHouse;
    private final int price = 1000;
    private final int houseCost = 4000;
    private final int rentWithoutHouse = 500;
    private final int rentWithHouse = 2000;

    public PropertyField() {
        this.owner = null;
        this.hasHouse = false;
    }

    @Override
    public void onLand(Player player) {
        if (owner == player) {
            player.strategy().tryBuyProperty(player, this);
        } else if (owner == null) {
            player.strategy().tryBuyProperty(player, this);
        } else if (owner.isActive()) {
            int rent = hasHouse ? rentWithHouse : rentWithoutHouse;
            player.pay(rent);
            owner.receive(rent);
            System.out.println(player + " paid " + rent + " rent to " + owner);
        } else {
            owner = null;
            player.strategy().tryBuyProperty(player, this);
        }
    }

    public void buy(Player player) {
        owner = player;
        player.pay(price);
        System.out.println(player + " bought the property for " + price);
    }

    public void buildHouse(Player player) {
        hasHouse = true;
        player.pay(houseCost);
        System.out.println(player + " built a house for " + houseCost);
    }

    public Player getOwner() {
        return owner;
    }

    public boolean hasHouse() {
        return hasHouse;
    }

    public void removeOwner() {
        owner = null;
        hasHouse = false;
        System.out.println("The property is now free to buy.");
    }

    @Override
    public String toString() {
        return "PropertyField [Owner: " + (owner != null ? owner.toString() : "None") + ", Has House: " + hasHouse + "]";
    }
}
