public class DiceGame {

    public enum Player {
        PLAYER_ONE("Player One"), PLAYER_TWO("Player Two");
        
        private String representation;
        private Player(String representation) {
            this.representation = representation;
        }

        @Override
        public String toString() {
            return representation;
        }
    }

    public class Round {
        public Player winner;
        public Die playerOneDie;
        public Die playerTwoDie;
        public long points;
        
        public Round(Die playerOneDie, Die playerTwoDie) {
            this.playerOneDie = new Die(playerOneDie);
            this.playerTwoDie = new Die(playerTwoDie);
        }

        @Override
        public String toString() {
            if (winner == null) {
                return "It's a tie between " + Player.PLAYER_ONE + "'s " + playerOneDie + " and " +
                        Player.PLAYER_TWO + "'s " + playerTwoDie + "!";
            } else {
                Player loser = winner == Player.PLAYER_TWO ? Player.PLAYER_ONE : Player.PLAYER_TWO;
                Die winningDie = winner == Player.PLAYER_TWO ? playerTwoDie : playerOneDie;
                Die losingDie = winner == Player.PLAYER_TWO ? playerOneDie : playerTwoDie;
                return winner + "'s " + winningDie + " beats " + loser + "'s " + losingDie + 
                        ", scoring " + points + " points!";
            }
        }
    }

    private DiceBag playerOneBag;
    private DiceBag playerTwoBag;
    private long playerOneScore;
    private long playerTwoScore;
    private long round;
    
    public DiceGame() {
        this(new DiceBag(), new DiceBag());
    }
    
    public DiceGame(DiceBag playerOneBag, DiceBag playerTwoBag) {
        if (!playerOneBag.equals(playerTwoBag)) {
            throw new IllegalArgumentException();
        }

        playerOneScore = 0;
        playerTwoScore = 0;
        this.playerOneBag = playerOneBag;
        this.playerTwoBag = playerTwoBag;
    }
    
    public DiceBag getPlayerTwoBag() {
        return playerTwoBag;
    }
    
    public DiceBag getPlayerOneBag() {
        return playerOneBag;
    }

    public boolean isMatchOver() {
        return (playerOneScore >= 30 || playerTwoScore >= 30 || round >= 25);
    }
    
    public Player getLeader() {
    	if (playerOneScore == playerTwoScore) {
    		return null;
    	} else {
    		return playerOneScore > playerTwoScore ? Player.PLAYER_ONE : Player.PLAYER_TWO;
    	}
    }
    
    public Round playRound() {
    	playerOneBag.shakeBag();
    	playerTwoBag.shakeBag();
    	
        Round round = new Round(playerOneBag.getRandomDie(), playerTwoBag.getRandomDie());
        
        round.playerOneDie.roll();
        long playerOneRoll = round.playerOneDie.getFaceUpSide();
        round.playerTwoDie.roll();
        long playerTwoRoll = round.playerTwoDie.getFaceUpSide();
        
        if (playerOneRoll == playerTwoRoll) {
        	
        } else if (playerOneRoll > playerTwoRoll) {
        	round.points = (playerOneRoll - playerTwoRoll);
        	playerOneScore += round.points;
        	round.winner = Player.PLAYER_ONE;
        } else {
        	round.points = (playerTwoRoll - playerOneRoll);
        	playerTwoScore += round.points;
        	round.winner = Player.PLAYER_TWO;
        }
        
        return round;
    }

    public Round[] playMatch() {
    	round = 0;
    	Round[] roundAr = new Round[25];
    	System.out.println("DICE GAME!");
    	System.out.printf("\n");
    	System.out.println("Starting Bags: ");
    	System.out.printf("\n");
    	System.out.println("Player One: " + playerOneBag.toString());
    	System.out.println("Player Two: " + playerTwoBag.toString());
    	System.out.printf("\n");
        while (!this.isMatchOver()) {
        	Round currentRound = this.playRound();
        	roundAr[(int) round] = currentRound;
        	System.out.println("Round " + (round + 1) + ":");
        	System.out.println(currentRound);
        	System.out.println("Player One Score: " + playerOneScore);
        	System.out.println("Player Two Score: " + playerTwoScore);
        	System.out.printf("\n");
        	round++;
        }
        if (getLeader() == Player.PLAYER_ONE) {
        	System.out.println("PLAYER ONE WINS!");
        } else if (getLeader() == Player.PLAYER_TWO) {
        	System.out.println("PLAYER TWO WINS!");
        } else {
        	System.out.println("IT'S A TIE!");
        }
        return roundAr;
    }

    public static void main(String[] args) {
    	if (args.length == 0) {
    		DiceGame game = new DiceGame();
    		game.playMatch();
    	} else {
    		long[] typesOfDice = new long[args.length];
    		
    		for (int i = 0; i < args.length; i++) {
        		try {
        			Long.parseLong(args[i]);
        		} catch (NumberFormatException n){
        			System.out.println("Cannot create a dice bag based on the supplied arguments.");
        			return;
        		}
        		if (Long.parseLong(args[i]) % 1 != 0 || Long.parseLong(args[i]) < 2) {
        			System.out.println("Cannot create a dice bag based on the supplied arguments.");
        			return;
        		}
        		typesOfDice[i] = Long.parseLong(args[i]);
        	}
    		
    		DiceGame game = new DiceGame(new DiceBag(typesOfDice), new DiceBag(typesOfDice));
    		game.playMatch();
    	}
    }
}
