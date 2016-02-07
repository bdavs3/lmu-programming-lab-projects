package game;

import java.util.HashMap;
import java.util.Map;

public class DiceBag {
    public static final int DEFAULT_NUMBER_OF_DICE = 2;
    public static final int MIN_NUMBER_OF_DICE = 1;
    private Die[] dice;
    
    // Constructs a DiceBag with the default number of Die objects, each 
    // with the default number of sides.
    public DiceBag() {
    	dice = new Die[DEFAULT_NUMBER_OF_DICE];
        for (int i = 0; i < DEFAULT_NUMBER_OF_DICE; i++) {
        	dice[i] = new Die();
        }
    }
    
    // Constructs a DiceBag with the given number of Die objects, each 
    // with the default number of sides.
    public DiceBag(int numberOfDice) {
    	dice = new Die[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
        	dice[i] = new Die();
        }
    }
    
    // Constructs a DiceBag with the given of Die objects, each 
    // with the given number of sides.
    public DiceBag(int numberOfDice, long sidesPerDie) {
    	this.dice = new Die[numberOfDice];
    	for (int i = 0; i < numberOfDice; i++) {
        	dice[i] = new Die(sidesPerDie);
        }
    }
    
    // Constructs a DiceBag with containing one die of each number 
    // of sides within sidesPerDie array.
    public DiceBag(long[] sidesPerDie) {
    	dice = new Die[sidesPerDie.length];
        for (int i = 0; i < sidesPerDie.length; i++) {
        	dice[i] = new Die(sidesPerDie[i]);
        }
    }
    
    // Roll every Die within the DiceBag and reorder the Dice within the 
    // bag randomly.
    public void shakeBag() {
        for (int i = 0; i < dice.length; i++) {
        	dice[i].roll();
        }
        
        this.reorderDice();
    }
    
    // Reorder the Dice within the bag randomly.
    public void reorderDice() {
    	Die[] originalDice = dice;
    	int[] indexes = new int[dice.length];
    	
    	for (int i = 0; i < dice.length; i++) {
    		indexes[i] = i;
    	}
    	
        for (int i = indexes.length - 1; i > 0; i--) {
          int index = (int) Math.floor(Math.random() * (i + 1));
          int a = indexes[index];
          indexes[index] = indexes[i];
          indexes[i] = a;
        }
        
        for (int i = 0; i < dice.length; i++) {
        	dice[i] = originalDice[indexes[i]];
        }
    }
    
    // Return one Die chosen randomly from the DiceBag.
    public Die getRandomDie() {
        int randomIndex = (int) Math.floor(Math.random() * (dice.length));
        return dice[randomIndex];
    }
    
    // Replace the Die at given index within the DiceBag with the provided Die.
    public void setDie(int index, Die die) {
        dice[index] = die;
    }
    
    // Returns the Die at given index within the DiceBag.
    public Die getDie(int index) {
        return dice[index];
    }
    
    // Returns the number of Die objects within the DiceBag.
    public int getBagSize() {
        return dice.length;
    }
    
    // Returns true if this DiceBag contains the equivalent Dice in the same 
    // order, regardless of face-up side.
    public boolean orderedEquals(DiceBag otherBag) {
    	if (dice.length != otherBag.dice.length) {
    		return false;
    	} else {
    		for (int i = 0; i < dice.length; i++) {
    			if (dice[i].getNumberOfSides() != otherBag.dice[i].getNumberOfSides()) {
    				return false;
    			}
    		}
    	}
    	
    	return true;
    }
    
    // Returns a string representation of the DiceBag.
    @Override
    public String toString() {
        String bagString = "{";
        for (int i = 0; i < dice.length; i++) {
            bagString += dice[i].toString();
            if (i != dice.length - 1) {
                bagString += ", ";
            }
        }
        bagString += "}";
        return bagString;
    }
    
    // Returns true if this DiceBag is contains the same number of Dice in 
    // the same ratio of sides.
    // WARNING: Advanced Java - review with caution.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        DiceBag other = (DiceBag)obj;
        if ( this.dice.length != other.dice.length) {
            return false;
        } else {
            return this.tally().equals(other.tally());
        }
        
    }
    
    private Map<Long, Integer> tally() {
        Map<Long, Integer> result = new HashMap<Long, Integer>();
        for (Die die: dice) {
            Integer value = result.get(die.getNumberOfSides());
            if (value == null) {
                result.put(die.getNumberOfSides(), 1);
            } else {
                result.put(die.getNumberOfSides(), value + 1);
            }
        }
        
        return result;
    }
}
