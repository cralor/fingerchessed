package org.cralor.fingerchessed;

import java.io.IOException;
import java.util.ArrayList;

import org.cralor.fingerchessed.Game.GameType;

public class SessionManager implements ViewListener {

	// Important class variables.
	private ArrayList<Model> sessions = new ArrayList<Model>();
	private GameType gameType;
	private int sessionNum = 0;

	public SessionManager(GameType gameType) {
		this.gameType = gameType;
	}

	private void createFirstSession(ViewProxy p, String n, Model m) {
		m.setPlayerOne(n);
		sessions.add(m);
		m.addModelListener(p);
		p.setViewListener(m);
		sessionNum++;
		System.out.printf("\nINFO: Session %s created by %s.\n", sessionNum, n);
	}

	private void createSecondSession(ViewProxy p, String n, Model m)
			throws IOException {
		m.setPlayerTwo(n);
		m.addModelListener(p);
		m.bothJoined(m.getPlayerOne(), m.getPlayerTwo(), 1);
		m.setGameType(gameType);
		p.setViewListener(m);
		System.out.printf("\nINFO: %s has joined session %s. "
				+ "Session %s gameplay has now started.\n", n, sessionNum,
				sessionNum);
	}

	@Override
	public void join(ViewProxy p, String playerName) throws IOException {
		// No sessions available.
		if (sessions.size() == 0) {
			Model m = new Model(gameType);
			createFirstSession(p, playerName, m);
		} else { // At least one session available.
			Model m = sessions.get(sessions.size() - 1);

			// Session found is full, make new one.
			if (!m.getPlayerTwo().equals("")) {
				Model m2 = new Model(gameType);
				createFirstSession(p, playerName, m2);
			} else {
				// Session found only has one player, fill second.
				createSecondSession(p, playerName, m);
			}
		}
	}

	@Override
	public void setHand(int playerNum, int leftHand, int rightHand)
			throws IOException {
		// The Model handles this.
	}

	@Override
	public void quit() throws IOException {
		// The Model handles this.
	}

	@Override
	public void split() throws IOException {
		// The Model handles this.
	}

	@Override
	public void newGame(String playerOne, String playerTwo, int currentPlayer)
			throws IOException {
		// The Model handles this.
	}

	@Override
	public void setGameType(GameType gameType) throws IOException {
		// The Model handles this.
	}

}
