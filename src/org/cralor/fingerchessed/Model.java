package org.cralor.fingerchessed;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class Model implements ViewListener {

	// Important class variables.
	private Game engine = new Game();
	private LinkedList<ModelListener> listeners = new LinkedList<ModelListener>();

	public Model() {
	}

	public synchronized void addModelListener(ModelListener modelListener) {
		listeners.add(modelListener);
	}

	public Game getGame() {
		return this.engine;
	}

	@Override
	public synchronized void join(ViewProxy proxy, String playerName)
			throws IOException {
		// We do nothing here.
	}

	@Override
	public synchronized void setHand(int playerNum, int leftHand, int rightHand)
			throws IOException {
		int currentPlayer = engine.getCurrentTurn();

		if (playerNum == 1) {
			engine.setOneHand(leftHand, rightHand);
		} else {
			engine.setTwoHand(leftHand, rightHand);
		}

		engine.switchTurns(currentPlayer == 1 ? 2 : 1);

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
		int currentPlayer = engine.getCurrentTurn();
		int amountToSplit = 0;

		if (currentPlayer == 1) {
			amountToSplit = Math.max(engine.getOneHand()[0],
					engine.getOneHand()[1]);
			amountToSplit = amountToSplit / 2;
			engine.setOneHand(amountToSplit, amountToSplit);
		} else {
			amountToSplit = Math.max(engine.getTwoHand()[0],
					engine.getTwoHand()[1]);
			amountToSplit = amountToSplit / 2;
			engine.setTwoHand(amountToSplit, amountToSplit);
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
		engine.setOneHand(1, 1);
		engine.setTwoHand(1, 1);

		engine.switchTurns(1);

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
