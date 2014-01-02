package org.cralor.fingerchessed;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ModelProxy implements ViewListener {

	// Important class variables.
	private Socket s;
	private DataOutputStream out;
	private DataInputStream in;
	private ModelListener modelListener;

	public ModelProxy(Socket sock) throws IOException {
		this.s = sock;
		out = new DataOutputStream(sock.getOutputStream());
		in = new DataInputStream(sock.getInputStream());
	}

	public void setModelListener(ModelListener modelListener) {
		this.modelListener = modelListener;
		new readingProcess().start();
	}

	@Override
	public void join(ViewProxy proxy, String playerName) throws IOException {
		out.writeByte('J');
		out.writeUTF(playerName);
		out.flush();
	}

	@Override
	public void setHand(int playerNum, int leftHand, int rightHand)
			throws IOException {
		out.writeByte('H');
		out.writeInt(playerNum);
		out.writeInt(leftHand);
		out.writeInt(rightHand);
		out.flush();
	}

	@Override
	public void quit() throws IOException {
		out.writeByte('Q');
		out.flush();
	}

	@Override
	public void split() throws IOException {
		out.writeByte('S');
		out.flush();
	}

	@Override
	public void newGame(String playerOne, String playerTwo, int currentPlayer)
			throws IOException {
		out.writeByte('N');
		out.writeUTF(playerOne);
		out.writeUTF(playerTwo);
		out.writeInt(currentPlayer);
		out.flush();
	}

	private class readingProcess extends Thread {

		@Override
		public void run() {
			try {
				for (;;) {
					byte cmd = in.readByte();
					switch (cmd) {
					case 'J':
						String playerOne = in.readUTF();
						String playerTwo = in.readUTF();
						int currPlayer = in.readInt();
						modelListener.bothJoined(playerOne, playerTwo,
								currPlayer);
						break;
					case 'H':
						int playerNum = in.readInt();
						int leftHand = in.readInt();
						int rightHand = in.readInt();
						modelListener.handSet(playerNum, leftHand, rightHand);
						break;
					case 'W':
						int winningPlayer = in.readInt();
						modelListener.boardDone(winningPlayer);
						break;
					case 'S':
						int playerNumber = in.readInt();
						int leftHandValue = in.readInt();
						int rightHandValue = in.readInt();
						modelListener.splitSet(playerNumber, leftHandValue,
								rightHandValue);
						break;
					case 'N':
						String playerOneName = in.readUTF();
						String playerTwoName = in.readUTF();
						int currentPlayer = in.readInt();
						modelListener.newGame(playerOneName, playerTwoName,
								currentPlayer);
						break;
					case 'Q':
						s.close();
						System.exit(0);
						break;
					default:
						System.err.println("Bad server message.");
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
