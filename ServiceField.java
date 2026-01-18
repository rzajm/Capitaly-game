package game;

public class ServiceField extends Field {
    private final int cost;

    public ServiceField(int cost) {
        this.cost = cost;
    }

    @Override
    public void onLand(Player player) {
        player.pay(cost);
        System.out.println(player + " paid service fee of " + cost);
    }

    @Override
    public String toString() {
        return "ServiceField [Cost: " + cost + "]";
    }
}