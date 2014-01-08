package org.cralor.fingerchessed;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class FingerChessedWelcome extends JFrame {

	public static void main(String[] args) {

		@SuppressWarnings("unused")
		FingerChessedWelcome welcomeScreen = new FingerChessedWelcome();

	}

	static {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}
	}

	// Widgets.
	private JLabel fingerChessedWelcome;
	private JLabel fingerChessedTitle;
	private JLabel nameTitle;
	private JTextField nameField;
	private JLabel serverTitle;
	private JTextField serverField;
	private JLabel portTitle;
	private JTextField portField;
	private JLabel errorLabel;
	private JButton connectButton;

	public FingerChessedWelcome() {
		super("Connect to a Server");

		// Set up the layout manager.
		GridBagLayout lm = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(lm);

		// Set up the welcome title.
		fingerChessedWelcome = new JLabel("Welcome to the game...");
		fingerChessedTitle = new JLabel("FINGERCHESSED");

		Font welcomeFont = new Font("Helvetica", Font.BOLD, 15);
		fingerChessedWelcome.setFont(welcomeFont);

		Font titleFont = new Font("Verdana", Font.BOLD, 30);
		fingerChessedTitle.setFont(titleFont);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 5;
		c.fill = GridBagConstraints.CENTER;
		lm.setConstraints(fingerChessedWelcome, c);
		add(fingerChessedWelcome);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 5;
		c.fill = GridBagConstraints.CENTER;
		lm.setConstraints(fingerChessedTitle, c);
		add(fingerChessedTitle);

		// Set up the name title.
		nameTitle = new JLabel("Your Name");

		Font labelFont = new Font("Arial", Font.PLAIN, 12);
		nameTitle.setFont(labelFont);

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.CENTER;
		c.ipady = 20;
		lm.setConstraints(nameTitle, c);
		add(nameTitle);

		// Set up the name field.
		nameField = new JTextField();
		nameField.setHorizontalAlignment(JLabel.CENTER);
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;
		lm.setConstraints(nameField, c);
		add(nameField);

		// Set up the server title.
		serverTitle = new JLabel("Server Address");
		serverTitle.setFont(labelFont);

		c.gridx = 1;
		c.gridy = 7;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.CENTER;
		c.ipady = 20;
		lm.setConstraints(serverTitle, c);
		add(serverTitle);

		// Set up the server field.
		serverField = new JTextField();
		serverField.setHorizontalAlignment(JLabel.CENTER);
		c.gridx = 1;
		c.gridy = 8;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;
		lm.setConstraints(serverField, c);
		add(serverField);

		// Set up the port title.
		portTitle = new JLabel("Port");
		portTitle.setFont(labelFont);

		c.gridx = 2;
		c.gridy = 5;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.CENTER;
		c.ipady = 20;
		lm.setConstraints(portTitle, c);
		add(portTitle);

		// Set up the port field.
		portField = new JTextField();
		portField.setHorizontalAlignment(JLabel.CENTER);
		c.gridx = 2;
		c.gridy = 6;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;
		lm.setConstraints(portField, c);
		add(portField);

		// Set up the error message label.
		errorLabel = new JLabel();
		errorLabel.setHorizontalAlignment(JLabel.CENTER);
		errorLabel.setForeground(Color.RED);

		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 6;
		c.fill = GridBagConstraints.CENTER;
		c.ipady = 10;
		lm.setConstraints(errorLabel, c);
		add(errorLabel);

		// Set up the connect button.
		connectButton = new JButton("Connect to Server");
		c.fill = GridBagConstraints.SOUTH;
		c.gridx = 1;
		c.gridy = 10;
		c.gridwidth = 4;
		c.ipady = 20;
		lm.setConstraints(connectButton, c);

		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					errorLabel.setText("");
					String[] args = dataCleanse();
					if (errorLabel.getText().equals("")) {
						FingerChessedClient.main(args);
						setVisible(false);
						dispose();
					}
				} catch (IllegalArgumentException | IOException e1) {
					errorLabel.setText("BAD SERVER CONNECTION");
				}
			}
		});

		add(connectButton);

		// Display all the things.
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getRootPane().setDefaultButton(connectButton);
		setLocationRelativeTo(null);
		setSize(500, 450);
		setVisible(true);
	}

	@SuppressWarnings({ "unused", "resource" })
	public String[] dataCleanse() {

		// Validate and filter the host data.
		String host = serverField.getText();

		if (host.equals("")) {
			errorLabel.setText("PLEASE SPECIFY HOST");
		} else {
			try {
				InetAddress ia = InetAddress.getByName(host);
				host = ia.getHostAddress();
				int thePort = Integer.parseInt(portField.getText());
				Socket s = new Socket(ia, thePort);
				s = null;
			} catch (UnknownHostException e2) {
				errorLabel.setText("INVALID HOST");
			} catch (IOException e) {
				errorLabel.setText("SERVER NOT FOUND");
			}
		}

		// Validate and filter the port data.
		String port = portField.getText();

		if (port.equals("")) {
			errorLabel.setText("PLEASE SPECIFY PORT");
		} else {
			try {
				int portNum = Integer.parseInt(port);
				port = String.valueOf(portNum);
			} catch (NumberFormatException e) {
				errorLabel.setText("INVALID PORT");
			}
		}

		// Validate and filter the player name data.
		String playerName = nameField.getText();

		if (playerName.equals("")) {
			errorLabel.setText("PLEASE SPECIFY A NAME");
		} else {
			boolean validName = Pattern.compile("^[A-Za-z0-9]+$")
					.matcher(playerName).matches();
			if (!validName)
				errorLabel.setText("INVALID NAME");
		}

		String validData[] = { host, port, playerName };
		return validData;
	}
}
