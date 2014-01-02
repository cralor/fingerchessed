package org.cralor.fingerchessed;

import java.io.IOException;

public interface ModelListener {

	public void handSet(int playerNum, int leftHand, int rightHand)
			throws IOException;

	public void splitSet(int playerNum, int leftHand, int rightHand)
			throws IOException;

	public void bothJoined(String playerOne, String playerTwo, int currPlayer)
			throws IOException;

	public void boardDone(int winner) throws IOException;

	public void newGame(String playerOne, String playerTwo, int currentPlayer)
			throws IOException;

	public void quit() throws IOException;

}
