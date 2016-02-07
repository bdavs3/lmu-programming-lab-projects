package game;

public class DiceGameTestHarness {

    private static int attempts = 0;
    private static int successes = 0;
    private static final int ROLL_TEST_COUNT = 5;

    public static void main(String[] args) {
        attempts = 0;
        successes = 0;

        test_DieCreation();
        test_DieEquals();
        test_DieSameMake();
        test_DieSetFace();
        test_DieRoll();
        test_DiceBagCreation();
        test_DiceBagEquals();
        test_DiceBagOrderedEquals();
        test_DiceBagShake();
        test_DiceBagRandomDie();

        System.out.println(successes + "/" + attempts + " tests passed.");
    }

    private static void displaySuccessIfTrue(boolean value) {
        attempts++;
        successes += value ? 1 : 0;

        System.out.println(value ? "success" : "failure");
    }

    private static void test_DieCreation() {
        System.out.println("Testing Die constructors...");

        // Due to randomization, test conditions are somewhat limited.
        // Further, some defects might not emerge consistently, again
        // due to randomization.
        Die die = new Die();
        try {
            displaySuccessIfTrue(6 == die.getNumberOfSides());
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(die.getFaceUpSide() <= 6 && die.getFaceUpSide() >= 1);
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        die = new Die(12);
        try {
            displaySuccessIfTrue(12 == die.getNumberOfSides());
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(die.getFaceUpSide() <= 12 && die.getFaceUpSide() >= 1);
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        die = new Die(256, 42);
        try {
            displaySuccessIfTrue(256 == die.getNumberOfSides());
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(die.getFaceUpSide() == 42);
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        Die die2 = new Die(die);
        try {
            displaySuccessIfTrue(256 == die.getNumberOfSides());
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(die.getFaceUpSide() == 42);
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        // We expect these to throw an exception.
        try {
            die = new Die(1);
            // If we get here, the constructor failed.
            displaySuccessIfTrue(false);
        } catch(Exception e) {
            displaySuccessIfTrue(true);
        }
        
        try {
            die = new Die(-12);
            displaySuccessIfTrue(false);
        } catch(Exception e) {
            displaySuccessIfTrue(true);
        }
        
        try {
            die = new Die(20,21);
            displaySuccessIfTrue(false);
        } catch(Exception e) {
            displaySuccessIfTrue(true);
        }
    }

    private static void test_DieEquals() {
        System.out.println("Testing Die equality...");

        Die die = new Die(12, 4);
        Die otherDie = new Die(12, 4);

        try {
            displaySuccessIfTrue(die.equals(otherDie));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(otherDie.equals(die));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        otherDie = new Die(12, 10);
        try {
            displaySuccessIfTrue(!die.equals(otherDie));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(!otherDie.equals(die));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        otherDie = new Die(4, 4);
        try {
            displaySuccessIfTrue(!die.equals(otherDie));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(!otherDie.equals(die));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
    }
    
    private static void test_DieSameMake() {
        System.out.println("Testing Die sameMake method...");

        Die die = new Die(8, 3);
        Die otherDie = new Die(8, 3);

        try {
            displaySuccessIfTrue(Die.sameMake(die, otherDie));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(Die.sameMake(otherDie, die));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        otherDie = new Die(8, 7);
        try {
            displaySuccessIfTrue(Die.sameMake(die, otherDie));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(Die.sameMake(otherDie, die));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        otherDie = new Die(4, 3);
        try {
            displaySuccessIfTrue(!Die.sameMake(die, otherDie));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }

        try {
            displaySuccessIfTrue(!Die.sameMake(otherDie, die));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
    }
    
    private static void test_DieSetFace() {
        System.out.println("Testing Die setFaceUpSide method...");
        
        Die die = new Die(6,1);
        
        try {
            die.setFaceUpSide(2);
            displaySuccessIfTrue(2 == die.getFaceUpSide());
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            die.setFaceUpSide(4);
            displaySuccessIfTrue(4 == die.getFaceUpSide());
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            die.setFaceUpSide(7);
            displaySuccessIfTrue(false);
        } catch(Exception e) {
            displaySuccessIfTrue(true);
        }
        
        try {
            die.setFaceUpSide(0);
            displaySuccessIfTrue(false);
        } catch(Exception e) {
            displaySuccessIfTrue(true);
        }
        
        try {
            die.setFaceUpSide(-3);
            displaySuccessIfTrue(false);
        } catch(Exception e) {
            displaySuccessIfTrue(true);
        }
    }
    
    private static void test_DieRoll() {
        System.out.println("Testing Die roll method...");
        System.out.println("(Due to randomization, these tests are not comprehensive.)");
        
        Die die = new Die(4);
        
        try {
            displaySuccessIfTrue(4 == die.getNumberOfSides());
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(die.getFaceUpSide() <= 4 && die.getFaceUpSide() >= 1);
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        
        for (int i = 0; i < ROLL_TEST_COUNT; i++) {
            die.roll();
            
            try {
                displaySuccessIfTrue(4 == die.getNumberOfSides());
            } catch(Exception e) {
                displaySuccessIfTrue(false);
            }
        
            try {
                displaySuccessIfTrue(die.getFaceUpSide() <= 4 && die.getFaceUpSide() >= 1);
            } catch(Exception e) {
                displaySuccessIfTrue(false);
            }
        }
    }
    
    private static void test_DiceBagCreation() {
        System.out.println("Testing DiceBag constructors...");
        
        DiceBag db = new DiceBag();
        
        try {
            displaySuccessIfTrue(db.getBagSize() == 2);
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(db.getDie(0).getNumberOfSides() == 6
                && db.getDie(1).getNumberOfSides() == 6);
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        db = new DiceBag(4);
        
        try {
            displaySuccessIfTrue(db.getBagSize() == 4);
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(db.getDie(0).getNumberOfSides() == 6
                && db.getDie(1).getNumberOfSides() == 6
                && db.getDie(2).getNumberOfSides() == 6
                && db.getDie(3).getNumberOfSides() == 6);
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        db = new DiceBag(3, 12);
        
        try {
            displaySuccessIfTrue(db.getBagSize() == 3);
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(db.getDie(0).getNumberOfSides() == 12
                && db.getDie(1).getNumberOfSides() == 12
                && db.getDie(2).getNumberOfSides() == 12);
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        db = new DiceBag( new long[] {4, 6, 8, 10, 12, 20} );
        
        try {
            displaySuccessIfTrue(db.getBagSize() == 6);
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(db.getDie(0).getNumberOfSides() == 4
                && db.getDie(1).getNumberOfSides() == 6
                && db.getDie(2).getNumberOfSides() == 8
                && db.getDie(3).getNumberOfSides() == 10
                && db.getDie(4).getNumberOfSides() == 12
                && db.getDie(5).getNumberOfSides() == 20);
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
    }
    
    private static void test_DiceBagEquals() {
        System.out.println("Testing equals method...");
        
        DiceBag db1 = new DiceBag( new long[] {4, 6, 8, 10, 12, 20} );
        DiceBag db2 = new DiceBag( new long[] {4, 6, 8, 10, 12, 20} );
        
        try {
            displaySuccessIfTrue(db1.equals(db2));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(db2.equals(db1));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        db2 = new DiceBag( new long[] {20, 10, 4, 8, 12, 6} );
        
        try {
            displaySuccessIfTrue(db1.equals(db2));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(db2.equals(db1));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        db1 = new DiceBag( new long[] {4, 6, 9, 10, 12, 20} );
        
        try {
            displaySuccessIfTrue(!(db1.equals(db2)));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(!(db2.equals(db1)));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        db1 = new DiceBag( new long[] {4, 6, 8, 10, 12, 20, 20} );
        
        try {
            displaySuccessIfTrue(!(db1.equals(db2)));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(!(db2.equals(db1)));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
    }
    
    private static void test_DiceBagOrderedEquals() {
        System.out.println("Testing orderedEquals method...");
        
        DiceBag db1 = new DiceBag( new long[] {4, 6, 8, 10, 12, 20} );
        DiceBag db2 = new DiceBag( new long[] {4, 6, 8, 10, 12, 20} );
        
        try {
            displaySuccessIfTrue(db1.orderedEquals(db2));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(db2.orderedEquals(db1));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        db2 = new DiceBag( new long[] {20, 10, 4, 8, 12, 6} );
        
        try {
            displaySuccessIfTrue(!(db1.orderedEquals(db2)));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(!(db2.orderedEquals(db1)));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        db1 = new DiceBag( new long[] {4, 6, 9, 10, 12, 20} );
        
        try {
            displaySuccessIfTrue(!(db1.orderedEquals(db2)));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(!(db2.orderedEquals(db1)));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        db1 = new DiceBag( new long[] {4, 6, 8, 10, 12, 20, 20} );
        
        try {
            displaySuccessIfTrue(!(db1.orderedEquals(db2)));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(!(db2.orderedEquals(db1)));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
    }
    
    private static void test_DiceBagShake() {
        System.out.println("Testing shakeBag method...");
        DiceBag db1 = new DiceBag( new long[] {2, 6, 20} );
        DiceBag db2 = new DiceBag( new long[] {2, 6, 20} );
        
        db2.shakeBag();
        try {
            displaySuccessIfTrue(!(db1.orderedEquals(db2)));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(!(db2.orderedEquals(db1)));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(db1.equals(db2));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(db2.equals(db1));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        db1 = new DiceBag( new long[] {5, 7, 11, 13} );
        db2 = new DiceBag( new long[] {5, 7, 11, 13} );
        
        db2.shakeBag();
        try {
            displaySuccessIfTrue(!(db1.orderedEquals(db2)));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(!(db2.orderedEquals(db1)));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(db1.equals(db2));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        try {
            displaySuccessIfTrue(db2.equals(db1));
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
    }
    
    private static void test_DiceBagRandomDie() {
        System.out.println("Testing getRandomDie method...");
        
        DiceBag db = new DiceBag();
        Die randomDie = db.getRandomDie();
        
        try {
            displaySuccessIfTrue(randomDie.getNumberOfSides() == 6);
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
        
        db = new DiceBag(new long[] {14, 16, 52});
        randomDie = db.getRandomDie();
        
        try {
            displaySuccessIfTrue(randomDie.getNumberOfSides() == 14 ||
                randomDie.getNumberOfSides() == 16 ||
                randomDie.getNumberOfSides() == 52);
        } catch(Exception e) {
            displaySuccessIfTrue(false);
        }
    }
}
