package game.score;

/**
 * Contains all the information about a player's score while they progress during the game.
 */
public class Highscore {

	private int points;
	private String name;
	private int steps;
	private int score;

	/**
	 * Creates an instance of a {@link Highscore}.
	 */
	public Highscore(){
		this.points = 0;
		this.name = "PLACEHOLDER";
		this.steps = 0;
		this.score = 0;
	}

	public int getScore() {
		return score;
	}

	public int getPoints() {
		return points;
	}

	public int getSteps() {
		return steps;
	}

	/**
	 * Calculates a score with the points and steps in the {@link Highscore}.
	 */
	public void createScore(){
		this.score = Math.round((float) points / steps);
	}

	/**
	 * Adds an amount of points to the {@link Highscore}.
	 * @param points	The amount of points to add.
	 */
	public void add(int points){
		this.points += points;
	}

	public String getName(){
		return this.name;
	}

	public void setName(String name){
		this.name = name;
	}

	/**
	 * Adds an amount of steps to the {@link Highscore}.
	 * @param steps	The amount of steps to add.
	 */
	public void addSteps(int steps){
		this.steps += steps;
	}

	@Override public String toString() {
		return name + ": " + score + " / " + steps + " / " + points;
	}
}
