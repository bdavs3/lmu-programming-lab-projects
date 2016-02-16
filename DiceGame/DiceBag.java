import java.util.HashMap;
import java.util.Map;

public class DiceBag {
    public static final int DEFAULT_NUMBER_OF_DICE = 2;
    public static final int MIN_NUMBER_OF_DICE = 1;
    private Die[] dice;
    DiceBag originalBag;        //used in the reorderDice() method.. the reordered array needs to grab
                                //elements from this "original" array
    
    // Constructs a DiceBag with the default number of Die objects, each 
    // with the default number of sides.
    public DiceBag() {
        dice = new Die[DEFAULT_NUMBER_OF_DICE];
        for (int i = 0; i < DEFAULT_NUMBER_OF_DICE; i++) {
            setDie(i, new Die());
        }
    }
    
    // Constructs a DiceBag with the given number of Die objects, each 
    // with the default number of sides.
    public DiceBag(int numberOfDice) {
        if (numberOfDice < 1) {
    	    throw new IllegalArgumentException();
        }
        dice = new Die[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            setDie(i, new Die());
        }
    }
    
    // Constructs a DiceBag with the given of Die objects, each 
    // with the given number of sides.
    public DiceBag(int numberOfDice, long sidesPerDie) {
        if (numberOfDice < 1 || sidesPerDie < 2) {
    	    throw new IllegalArgumentException();
        }
        dice = new Die[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            setDie(i, new Die(sidesPerDie));
        }
    }
    
    // Constructs a DiceBag with containing one die of each number 
    // of sides within sidesPerDie array.
    public DiceBag(long[] sidesPerDie) {
        dice = new Die[sidesPerDie.length];
        for (int i = 0; i < this.getBagSize(); i++) {
    	    if (sidesPerDie[i] < 2) {
    	        throw new IllegalArgumentException();
    	    }
        }
        for (int i = 0; i < sidesPerDie.length; i++) {
            setDie(i, new Die(sidesPerDie[i]));
        }
    }
    
    // Roll every Die within the DiceBag and reorder the Dice within the 
    // bag randomly.
    public void shakeBag() {
        for (int i = 0; i < this.getBagSize(); i++) {
            this.getDie(i).roll();
        }
        
        this.reorderDice();
    }
    
    // Will assist with the reorderDice() method
    public int[] randomizeArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            int randomIndex = ((int) Math.floor(Math.random() * this.getBagSize()));
            int temp = array[i];
            array[i] = array[randomIndex];
            array[randomIndex] = temp;
	}
 
            return array;
	}
    
    // Reorder the Dice within the bag randomly.
    public void reorderDice() {
        int[] indexes = new int[this.getBagSize()];
        for (int i = 0; i < this.getBagSize(); i++) {
            indexes[i] = i;
        }
        randomizeArray(indexes);
    	
        originalBag = new DiceBag(this.getBagSize());           //from line 10
        for (int i = 0; i < this.getBagSize(); i++) {
    	    originalBag.setDie(i, this.getDie(i));              //make originalBag a copy of the current dice bag
        }
        
        for (int i = 0; i < this.getBagSize(); i++) {
            setDie(i, originalBag.getDie(indexes[i]));	        //use the shuffled indexes to populate the current dice bag with
        }                                                       //elements from its copy (this is what randomizes the order)
    	
        if (orderedEquals(originalBag)) {
    	    this.reorderDice();                                 //accounts for undesirable (and rare) case when the shuffled
        }                                                       //bag is identical to the original bag
    	
        return;
    }
    
    // Returns true if this DiceBag contains the equivalent Dice in the same 
    // order, regardless of face-up side.
    public boolean orderedEquals(DiceBag otherBag) {
        if (this.getBagSize() != otherBag.getBagSize()) {
    	    return false;
        } else {
            for (int i = 0; i < this.getBagSize(); i++) {
                if (!Die.sameMake(this.getDie(i), otherBag.getDie(i))) {
    		    return false;
                }
            }
        }
    	
        return true;
    }
    
    // Return one Die chosen randomly from the DiceBag.
    public Die getRandomDie() {
        int randomIndex = (int) Math.floor(Math.random() * (dice.length));
        return this.getDie(randomIndex);
    }
    
    // Replace the Die at given index within the DiceBag with the provided Die.
    public void setDie(int index, Die die) {
        if (index < 0 || index > this.getBagSize()) {
    	    throw new IllegalArgumentException();
        }
        dice[index] = die;
    }
    
    // Returns the Die at given index within the DiceBag.
    public Die getDie(int index) {
        if (index < 0 || index > this.getBagSize()) {
    	    throw new IllegalArgumentException();
        }
        return dice[index];
    }
    
    // Returns the number of Die objects within the DiceBag.
    public int getBagSize() {
        return dice.length;
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
