package game;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private int balance;
    private int position;
    private final List<PropertyField> ownedProperties;
    private final Strategy strategy;
    private boolean isActive;

    public Player(String name, Strategy strategy) {
        this.name = name;
        this.balance = 10000;
        this.position = 0;
        this.ownedProperties = new ArrayList<>();
        this.strategy = strategy;
        this.isActive = true;
    }

    public void moveForward(int diceRoll, int boardSize) {
        position = (position + diceRoll) % boardSize;
        System.out.println(name + " is going " + diceRoll + " forward and the new position is " + position);
    }

    public boolean canAfford(int amount) {
        return balance >= amount;
    }

    public void pay(int amount) {
        balance -= amount;
        if (balance < 0) {
            lose();
        }
    }

    public void receive(int amount) {
        balance += amount;
    }

    public void buyProperty(PropertyField property) {
        ownedProperties.add(property);
        property.buy(this);
    }

    public boolean isActive() {
        return isActive;
    }

    public void lose() {
        for (PropertyField property : ownedProperties) {
            property.removeOwner(); 
        }
        ownedProperties.clear();
        isActive = false;
        System.out.println(name + " is bankrupt and out of the game!");
    }

    public int getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public Strategy strategy() {
        return strategy;
    }

    @Override
    public String toString() {
        return name + " (Balance: " + balance + ")";
    }
}
