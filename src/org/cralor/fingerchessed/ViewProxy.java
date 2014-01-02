package org.cralor.fingerchessed;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ViewProxy implements ModelListener {

	// Important class variables.
	private Socket s;
	private DataOutputStream out;
	private DataInputStream in;
	private ViewListener viewListener;

	public ViewProxy(Socket sock) throws IOException {
		this.s = sock;
		out = new DataOutputStream(sock.getOutputStream());
		in = new DataInputStream(sock.getInputStream());
	}

	public void setViewListener(ViewListener viewListener) {
		if (this.viewListener == null) {
			this.viewListener = viewListener;
			new readingProcess().start();
		} else {
			this.viewListener = viewListener;
		}
	}

	@Override
	public void handSet(int playerNum, int leftHand, int rightHand)
			throws IOException {
		out.writeByte('H');
		out.writeInt(playerNum);
		out.writeInt(leftHand);
		out.writeInt(rightHand);
		out.flush();
	}

	@Override
	public void bothJoined(String playerOne, String playerTwo, int currPlayer)
			throws IOException {
		out.writeByte('J');
		out.writeUTF(playerOne);
		out.writeUTF(playerTwo);
		out.writeInt(currPlayer);
		out.flush();
	}

	@Override
	public void turnBegins(int currPlayer) throws IOException {
		out.writeByte('B');
		out.writeInt(currPlayer);
		out.flush();
	}

	@Override
	public void turnDone(int currPlayer, int leftHand, int rightHand)
			throws IOException {
		out.writeByte('D');
		out.writeInt(currPlayer);
		out.writeInt(leftHand);
		out.writeInt(rightHand);
		out.flush();
	}

	@Override
	public void boardDone(int winner) throws IOException {
		out.writeByte('W');
		out.writeInt(winner);
		out.flush();
	}

	@Override
	public void quit() throws IOException {
		out.writeByte('Q');
		out.flush();
	}

	private class readingProcess extends Thread {
		@Override
		public void run() {
			try {
				for (;;) {
					byte b = in.readByte();
					switch (b) {
					case 'J':
						String playerName = in.readUTF();
						viewListener.join(ViewProxy.this, playerName);
						break;
					case 'H':
						int playerNum = in.readInt();
						int leftHand = in.readInt();
						int rightHand = in.readInt();
						viewListener.setHand(playerNum, leftHand, rightHand);
						break;
					case 'D':
						viewListener.done();
						break;
					case 'S':
						viewListener.split();
						break;
					case 'Q':
						viewListener.quit();
						break;
					default:
						System.err.println("Bad client message.");
						break;
					}
				}
			} catch (IOException e) {
			} finally {
				try {
					s.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
