package game;

import java.util.List;
import java.util.Random;

public class GameSimulation {
    private final List<Player> players;
    private final List<Field> board;
    private final int boardSize;
    private final Random random;
    private final List<Integer> diceRolls; 
    private int diceRollIndex; 

    public GameSimulation(GameParameters parameters) {
        this.players = parameters.getPlayers();
        this.board = parameters.getBoard();
        this.boardSize = parameters.getBoard().size();
        this.random = new Random();
        this.diceRolls = parameters.getDiceRolls(); 
        this.diceRollIndex = 0;
    }

    public void simulateGame(int maxTurns) {
        for (int turn = 0; turn < maxTurns; turn++) {
            System.out.println("\nTurn " + (turn + 1));

            for (Player player : players) {
                if (player.isActive()) {
                    int diceRoll = rollDice(); 
                    player.moveForward(diceRoll, boardSize);
                    Field currentField = board.get(player.getPosition());
                    currentField.onLand(player);

                    if (getActivePlayerCount() == 1) {
                        System.out.println("\nGame over! " + getLastActivePlayer().getName() + " is the winner and his balance is " + getLastActivePlayer().getBalance());
                        return;
                    }
                }
            }
        }

        System.out.println("Max turns reached. Game over.");
    }

    private int getActivePlayerCount() {
        int activeCount = 0;
        for (Player player : players) {
            if (player.isActive()) {
                activeCount++;
            }
        }
        return activeCount;
    }

    private Player getLastActivePlayer() {
        for (Player player : players) {
            if (player.isActive()) {
                return player;
            }
        }
        return null;
    }

    private int rollDice() {
        if (diceRollIndex < diceRolls.size()) {
            return diceRolls.get(diceRollIndex++);
        }
        return random.nextInt(6) + 1;
    }
}