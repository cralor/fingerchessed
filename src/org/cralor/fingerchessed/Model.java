package org.cralor.fingerchessed;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class Model implements ViewListener {

	// Important class variables.
	private Board board = new Board();
	private LinkedList<ModelListener> listeners = new LinkedList<ModelListener>();

	public Model() {
	}

	public synchronized void addModelListener(ModelListener modelListener) {
		listeners.add(modelListener);
	}

	public Board getBoard() {
		return this.board;
	}

	@Override
	public synchronized void join(ViewProxy proxy, String playerName)
			throws IOException {
		// We do nothing here.
	}

	@Override
	public synchronized void setHand(int playerNum, int leftHand, int rightHand)
			throws IOException {
		Iterator<ModelListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			ModelListener listener = iter.next();
			try {
				listener.handSet(playerNum, leftHand, rightHand);
			} catch (IOException e) {
				iter.remove();
			}
		}
	}

	@Override
	public synchronized void done() throws IOException {
		int currentPlayer = board.getCurrentTurn();
		int leftHand = 0;
		int rightHand = 0;

		if (currentPlayer == 1) {
			leftHand = board.getOneHand()[0];
			rightHand = board.getOneHand()[1];
		} else {
			leftHand = board.getTwoHand()[0];
			rightHand = board.getTwoHand()[1];
		}

		board.switchTurns(currentPlayer == 1 ? 2 : 1);

		Iterator<ModelListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			ModelListener listener = iter.next();
			try {
				if (currentPlayer == 1) {
					listener.turnDone(currentPlayer, leftHand, rightHand);
					// board.setOneScore(playerScore);
					listener.turnBegins(2);
				} else {
					listener.turnDone(currentPlayer, leftHand, rightHand);
					// board.setTwoScore(playerScore);
					listener.boardDone(board.getWinner());
				}
			} catch (IOException e) {
				iter.remove();
			}
		}
	}

	@Override
	public synchronized void quit() throws IOException {
		Iterator<ModelListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			ModelListener listener = iter.next();
			try {
				listener.quit();
			} catch (IOException e) {
				iter.remove();
			}
		}
	}

	public synchronized void bothJoined(String playerOne, String playerTwo,
			int currPlayer) {
		Iterator<ModelListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			ModelListener listener = iter.next();
			try {
				listener.bothJoined(playerOne, playerTwo, currPlayer++);
				listener.turnBegins(1);
			} catch (IOException e) {
				iter.remove();
			}
		}
	}

	@Override
	public void split() throws IOException {
		int currentPlayer = board.getCurrentTurn();
		int amountToSplit = 0;

		if (currentPlayer == 1) {
			amountToSplit = Math.max(board.getOneHand()[0],
					board.getOneHand()[1]);
			amountToSplit = amountToSplit / 2;
			board.setOneHand(amountToSplit, amountToSplit);
		} else {
			amountToSplit = Math.max(board.getTwoHand()[0],
					board.getTwoHand()[1]);
			amountToSplit = amountToSplit / 2;
			board.setTwoHand(amountToSplit, amountToSplit);
		}

		Iterator<ModelListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			ModelListener listener = iter.next();
			try {
				listener.handSet(currentPlayer, amountToSplit, amountToSplit);
			} catch (IOException e) {
				iter.remove();
			}
		}
	}
}
