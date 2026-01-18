package game;


public class LuckyField extends Field {
    private final int reward;

    public LuckyField(int reward) {
        this.reward = reward;
    }

    @Override
    public void onLand(Player player) {
        player.receive(reward);
        System.out.println(player + " received lucky reward of " + reward);
    }

    @Override
    public String toString() {
        return "LuckyField [Reward: " + reward + "]";
    }
}