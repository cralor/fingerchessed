package org.cralor.fingerchessed;

import java.io.IOException;

public interface ModelListener {

	public void handSet(int playerNum, int leftHand, int rightHand)
			throws IOException;

	public void bothJoined(String playerOne, String playerTwo, int currPlayer)
			throws IOException;

	public void turnBegins(int currPlayer) throws IOException;

	public void turnDone(int currPlayer, int leftHand, int rightHand)
			throws IOException;

	public void boardDone(int winner) throws IOException;

	public void quit() throws IOException;

}
