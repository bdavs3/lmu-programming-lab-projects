package game;

public class Die {
    public static final long DEFAULT_SIDES = 6;
    public static final long MIN_SIDE_VALUE = 1;
    private long numberOfSides;
    private long faceUpSide;
    
    // Constructs a Die with the default number of sides (6) and with a 
    // random side face up.
    public Die() {
        numberOfSides = DEFAULT_SIDES;
        this.roll();
    }
    
    // Constructs a Die with the given number of sides with a random side
    // face up.
    public Die(long numberOfSides) {
    	if (numberOfSides < 2) {
    		throw new IllegalArgumentException();
    	}
        this.numberOfSides = numberOfSides;
        this.roll();
    }
    
    // Constructs a Die with the default number of sides with the given side
    // face up, provided the face is valid for the number of sides.
    public Die(long numberOfSides, long faceUpSide) {
    	if (numberOfSides < 2 || faceUpSide < 1 || faceUpSide > numberOfSides) {
    		throw new IllegalArgumentException();
    	}
        this.numberOfSides = numberOfSides;
        this.faceUpSide = faceUpSide;
    }
    
    // Constructs a Die with the same number of sides and face of side as the
    // provided Die.
    public Die(Die die) {
        numberOfSides = die.numberOfSides;
        faceUpSide = die.faceUpSide;
    }
    
    // Rolls the Die, changing the face up side to a random face valid for 
    // the number of sides.
    public void roll() {
    	faceUpSide = (long) ((MIN_SIDE_VALUE + Math.floor(Math.random() * (numberOfSides - MIN_SIDE_VALUE)))); 
    }
    
    // Changes the face up side of the Die to the provided face, provided it is 
    // valid for the number of sides.
    public void setFaceUpSide(long side) {
    	if (side < 1 || side > this.numberOfSides) {
    		throw new IllegalArgumentException();
    	}
        faceUpSide = side;
    }
    
    // Returns the current face up side.
    public long getFaceUpSide() {
        return faceUpSide;
    }
    
    // Returns the number of sides.
    public long getNumberOfSides() {
        return numberOfSides;
    }
    
    // Returns true if the first and second Die have the same number of sides.
    public static boolean sameMake(Die firstDie, Die secondDie) {
        return (firstDie.numberOfSides == secondDie.numberOfSides);
    }
    
    // Returns a string representation of the Die.
    @Override
    public String toString() {
        return faceUpSide + " (d" + numberOfSides + ")";
    }
    
    // Indicates whether some other object is "equal to" this one.
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

        Die other = (Die)obj;
        return (this.numberOfSides == other.numberOfSides) && (this.faceUpSide == other.faceUpSide);
    }
}
