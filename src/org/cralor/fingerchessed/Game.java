package org.cralor.fingerchessed;

public class Game {

	// Important class variables.
	private String playerOne = "";
	private String playerTwo = "";
	private int currentPlayer = 1;
	private int oneLeftHand = 1;
	private int oneRightHand = 1;
	private int twoLeftHand = 1;
	private int twoRightHand = 1;
	private GameType gameType;

	// private int oneWins;
	// private int twoWins;

	public Game(GameType gameType) {
		this.gameType = gameType;
	}

	public enum GameType {
		STOP_AT_FIVE, EXACTLY_FIVE
	}

	public synchronized void switchTurns(int currentTurn) {
		this.currentPlayer = currentTurn;
	}

	public synchronized int getCurrentTurn() {
		return this.currentPlayer;
	}

	public synchronized String getPlayerOne() {
		return playerOne;
	}

	public synchronized String getPlayerTwo() {
		return playerTwo;
	}

	public synchronized void setOneName(String playerName) {
		this.playerOne = playerName;
	}

	public synchronized void setTwoName(String playerName) {
		this.playerTwo = playerName;
	}

	public synchronized void setOneHand(int leftHand, int rightHand) {
		this.oneLeftHand = leftHand;
		this.oneRightHand = rightHand;
	}

	public synchronized int[] getOneHand() {
		int[] oneHand = new int[2];
		oneHand[0] = this.oneLeftHand;
		oneHand[1] = this.oneRightHand;
		return oneHand;
	}

	public synchronized int[] getTwoHand() {
		int[] twoHand = new int[2];
		twoHand[0] = this.twoLeftHand;
		twoHand[1] = this.twoRightHand;
		return twoHand;
	}

	public synchronized void setTwoHand(int leftHand, int rightHand) {
		this.twoLeftHand = leftHand;
		this.twoRightHand = rightHand;
	}

	public synchronized int getWinner() {
		int oneScore = this.oneLeftHand + this.oneRightHand;
		int twoScore = this.twoLeftHand + this.twoRightHand;
		if (oneScore >= 10) {
			return 2;
		} else if (twoScore >= 10) {
			return 1;
		} else {
			return 0; // Not a tie, but an ERROR.
		}
	}
}
