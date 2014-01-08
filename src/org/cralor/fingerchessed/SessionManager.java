package org.cralor.fingerchessed;

import java.io.IOException;
import java.util.ArrayList;

import org.cralor.fingerchessed.Game.GameType;

public class SessionManager implements ViewListener {

	// Important class variables.
	private ArrayList<Model> sessions = new ArrayList<Model>();
	private GameType gameType;

	public SessionManager(GameType gameType) {
		this.gameType = gameType;
	}

	@Override
	public void join(ViewProxy p, String playerName) throws IOException {
		// No sessions available.
		if (sessions.size() == 0) {
			Model m = new Model(gameType);
			m.getGame().setOneName(playerName);
			sessions.add(m);
			m.addModelListener(p);
			p.setViewListener(m);
		} else { // At least one session available.
			Model m = sessions.get(sessions.size() - 1);

			// Session found is full, make new one.
			if (!m.getGame().getPlayerTwo().equals("")) {
				m = new Model(gameType);
				m.getGame().setOneName(playerName);
				sessions.add(m);
				m.addModelListener(p);
				p.setViewListener(m);
			} else {
				// Session found only has one player, fill second.
				m.getGame().setTwoName(playerName);
				m.addModelListener(p);
				m.bothJoined(m.getGame().getPlayerOne(), m.getGame()
						.getPlayerTwo(), 1);
				m.setGameType(gameType);
				p.setViewListener(m);
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
