package org.cralor.fingerchessed;

import java.io.IOException;
import java.util.ArrayList;

public class SessionManager implements ViewListener {

	// Important class variables.
	private ArrayList<Model> sessions = new ArrayList<Model>();

	public SessionManager() {
	}

	@Override
	public void join(ViewProxy p, String playerName) throws IOException {
		// No sessions available.
		if (sessions.size() == 0) {
			Model m = new Model();
			m.getBoard().setOneName(playerName);
			sessions.add(m);
			m.addModelListener(p);
			p.setViewListener(m);
		} else { // At least one session available.
			Model m = sessions.get(sessions.size() - 1);

			// Session found is full, make new one.
			if (!m.getBoard().getPlayerTwo().equals("")) {
				m = new Model();
				m.getBoard().setOneName(playerName);
				sessions.add(m);
				m.addModelListener(p);
				p.setViewListener(m);
			} else {
				// Session found only has one player, fill second.
				m.getBoard().setTwoName(playerName);
				m.addModelListener(p);
				m.bothJoined(m.getBoard().getPlayerOne(), m.getBoard()
						.getPlayerTwo(), 1);
				p.setViewListener(m);
			}
		}
	}

	@Override
	public void setHand(int playerNum, int leftHand, int rightHand)
			throws IOException {
		// We do nothing here.
	}

	@Override
	public void done() throws IOException {
		// We do nothing here.
	}

	@Override
	public void quit() throws IOException {
		// We do nothing here.
	}

	@Override
	public void split() throws IOException {
		// We do nothing here.
	}

}
