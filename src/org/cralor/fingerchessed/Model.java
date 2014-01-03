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
		int currentPlayer = board.getCurrentTurn();

		if (playerNum == 1) {
			board.setOneHand(leftHand, rightHand);
		} else {
			board.setTwoHand(leftHand, rightHand);
		}

		board.switchTurns(currentPlayer == 1 ? 2 : 1);

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
				listener.splitSet(currentPlayer, amountToSplit, amountToSplit);
			} catch (IOException e) {
				iter.remove();
			}
		}
	}

	@Override
	public void newGame(String playerOne, String playerTwo, int currentPlayer)
			throws IOException {
		board.setOneHand(1, 1);
		board.setTwoHand(1, 1);

		board.switchTurns(1);

		Iterator<ModelListener> iter = listeners.iterator();
		while (iter.hasNext()) {
			ModelListener listener = iter.next();
			try {
				listener.newGame(playerOne, playerTwo, currentPlayer++);
			} catch (IOException e) {
				iter.remove();
			}
		}
	}
}
