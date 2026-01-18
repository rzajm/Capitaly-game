package game;

import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class Main {
    private static final int MAX_ROUNDS = 100;

    public static void main(String[] args) {
        GameParameters gameParameters = new GameParameters();

        try {
            gameParameters.read("capitaly.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            System.exit(-1);
        } catch (InvalidInputException e) {
            System.out.println("Invalid input: " + e.getMessage());
            System.exit(-1);
        } catch (NoSuchElementException e) {
            System.out.println("The file is empty or contains invalid data: " + e.getMessage());
            System.exit(-1);
        } catch (InsufficientNumberOfPlayersException e) {
            System.out.println("Insufficient number of players: " + e.getMessage());
            System.exit(-1);
        } catch (AllFieldsCannotBeLuckyFieldsException e) {
            System.out.println("Field configuration error: " + e.getMessage());
            System.exit(-1);
        } catch (InsufficientFieldSizeException e) {
            System.out.println("Field configuration error: " + e.getMessage());
            System.exit(-1);
        } catch (InvalidDiceRollException e) {
            System.out.println("Invalid dice roll: " + e.getMessage());
            System.exit(-1);
        }

        gameParameters.report();

        GameSimulation simulation = new GameSimulation(gameParameters);
        simulation.simulateGame(MAX_ROUNDS);
    }
}
