package org.cralor.fingerchessed;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.cralor.fingerchessed.Game.GameType;

public class FingerChessedServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {

		// Validate user inputs.
		usage(args);

		// Grab user inputs.
		String host = args[0];
		int port = Integer.parseInt(args[1]);
		GameType gameType = GameType.STOP_AT_FIVE;

		if (args.length == 3) {
			try {
				gameType = GameType.valueOf(args[2]);
			} catch (IllegalArgumentException e) {
				System.err.println("\nGame type specified invalid."
						+ "\n\nValid game types:");
				for (GameType gT : GameType.values())
					System.err.println(gT);
				System.exit(0);
			}
		}

		// Establish game components.
		ServerSocket s = new ServerSocket(port);

		SessionManager m = new SessionManager(gameType);

		System.out.printf("\nServer established successfully.\n\n"
				+ "Configuration: <HOST:%s> <PORT:%s> <GAME_TYPE:%s>\n\n"
				+ "Now accepting players...\n", host, port, gameType);

		for (;;) {
			Socket so = s.accept();
			ViewProxy p = new ViewProxy(so);
			p.setViewListener(m);
		}
	}

	/**
	 * Verifies provided arguments are correct for the program.
	 * 
	 * @param args
	 *            - provided arguments
	 */
	private static void usage(String[] args) {
		String errMsg = "java -jar FingerChessedServer.jar <host> <port> [type]";

		if (args.length > 3 || args.length < 2) {
			System.err.println(errMsg);
			System.exit(0);
		}

	}

}
