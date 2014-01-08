package org.cralor.fingerchessed;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class FingerChessedClient {

	public static void main(String[] args) throws IOException,
			ConnectException, NumberFormatException, IllegalArgumentException,
			SocketException {

		usage(args);
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		String playerName = args[2];

		Socket s = new Socket();

		try {
			s.connect(new InetSocketAddress(host, port));
		} catch (Exception e) {
			System.err.println("Cannot connect to server."
					+ "\nPlease verify server is running"
					+ " or provided arguments are valid.");
			System.exit(0);
		}

		FingerChessedUI view = new FingerChessedUI();
		ModelProxy model = new ModelProxy(s);
		view.setViewListener(model);
		model.setModelListener(view);

		ViewProxy vp = new ViewProxy(s);
		model.join(vp, playerName);

	}

	/**
	 * Verifies provided arguments are correct for the program.
	 * 
	 * @param args
	 *            - provided arguments
	 */
	public static void usage(String[] args) {
		String errMsg = "java FingerChessedClient <host> <port> <playerName>";

		if (args.length != 3) {
			System.err.println(errMsg);
			System.exit(0);
		}

	}

}
