package org.cralor.fingerchessed;

import java.io.IOException;

public interface ViewListener {

	public void join(ViewProxy proxy, String playerName) throws IOException;

	public void setHand(int playerNum, int leftHand, int rightHand)
			throws IOException;

	public void split() throws IOException;

	public void newGame(String playerOne, String playerTwo, int currentPlayer)
			throws IOException;

	public void quit() throws IOException;

}
