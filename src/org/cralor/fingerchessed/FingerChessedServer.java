package org.cralor.fingerchessed;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class FingerChessedServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {

		usage(args);

		String host = args[0];
		int port = Integer.parseInt(args[1]);

		ServerSocket s = new ServerSocket();
		s.bind(new InetSocketAddress(host, port));

		SessionManager m = new SessionManager();

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
		String errMsg = "java FingerChessedServer <host> <port>";

		if (args.length != 2) {
			System.err.println(errMsg);
			System.exit(0);
		}

	}

}
