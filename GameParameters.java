package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GameParameters {
    private int boardSize;
    private final List<Field> board;
    private int numberOfPlayers;
    private final List<Player> players;
    private final List<Integer> diceRolls;

    public GameParameters() {
        board = new ArrayList<>();
        players = new ArrayList<>();
        diceRolls = new ArrayList<>();
    }

    public void read(String filename) throws FileNotFoundException, InvalidInputException, NoSuchElementException, InsufficientNumberOfPlayersException, AllFieldsCannotBeLuckyFieldsException, InsufficientFieldSizeException, InvalidDiceRollException {
        try (Scanner scanner = new Scanner(new File(filename))) {
            if (!scanner.hasNextLine()) {
                throw new NoSuchElementException();
            }

            boardSize = Integer.parseInt(scanner.nextLine());
            if (boardSize <= 0) {
                throw new InsufficientFieldSizeException("Field size must be greater than 0.");
            }

            for (int i = 0; i < boardSize; i++) {
                String fieldData = scanner.nextLine();
                String[] fieldTokens = fieldData.split(" ");
                String fieldType = fieldTokens[0];
                Field field;

                if (fieldType.equals("P")) {
                    field = new PropertyField();
                } else if (fieldType.equals("S")) {
                    if (fieldTokens.length < 2) {
                        throw new InvalidInputException("Missing cost for Service Field.");
                    }
                    int cost = Integer.parseInt(fieldTokens[1]);
                    field = new ServiceField(cost);
                } else if (fieldType.equals("L")) {
                    if (fieldTokens.length < 2) {
                        throw new InvalidInputException("Missing reward for Lucky Field.");
                    }
                    int reward = Integer.parseInt(fieldTokens[1]);
                    field = new LuckyField(reward);
                } else {
                    throw new InvalidInputException("Unknown field type: " + fieldType);
                }
                board.add(field);
            }

            String playersLine = scanner.nextLine();
            try {
                numberOfPlayers = Integer.parseInt(playersLine);
            } catch (NumberFormatException e) {
                throw new InvalidInputException("Invalid number of players: " + playersLine);
            }

            if (numberOfPlayers < 2) {
                throw new InsufficientNumberOfPlayersException("There must be at least 2 players.");
            }

            for (int i = 0; i < numberOfPlayers; i++) {
                String[] playerData = scanner.nextLine().split(" ");
                String playerName = playerData[0];
                String strategyType = playerData[1];

                Strategy strategy = createStrategy(strategyType);
                Player player = new Player(playerName, strategy);
                players.add(player);
            }

            if (scanner.hasNextLine()) {
                String diceRollsLine = scanner.nextLine();
                for (char roll : diceRollsLine.toCharArray()) {
                    int diceValue = Character.getNumericValue(roll);
                    if (diceValue < 1 || diceValue > 6) {
                        throw new InvalidDiceRollException("Dice roll must be between 1 and 6: " + roll);
                    }
                    diceRolls.add(diceValue);
                }
            }

            if (areAllFieldsLucky()) {
                throw new AllFieldsCannotBeLuckyFieldsException("All fields cannot be lucky fields.");
            }
        }
    }

    private boolean areAllFieldsLucky() {
        for (Field field : board) {
            if (!(field instanceof LuckyField)) {
                return false;
            }
        }
        return true;
    }

    private Strategy createStrategy(String strategyType) throws InvalidInputException {
        switch (strategyType) {
            case "G" -> {
                return new GreedyStrategy();
            }
            case "C" -> {
                return new CarefulStrategy();
            }
            case "T" -> {
                return new TacticalStrategy();
            }
            default -> throw new InvalidInputException("Unknown strategy type: " + strategyType);
        }
    }

    public void report() {
        System.out.println("Board size: " + boardSize);
        System.out.println("Board configuration:");
        for (int i = 0; i < board.size(); i++) {
            System.out.println("Field " + (i + 1) + ": " + board.get(i));
        }
        System.out.println("\nNumber of players: " + numberOfPlayers);
        System.out.println("Players:");
        for (Player player : players) {
            System.out.println(player);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Field> getBoard() {
        return board;
    }

    public List<Integer> getDiceRolls() {
        return diceRolls;
    }
}
