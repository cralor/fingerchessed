package org.cralor.fingerchessed;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.cralor.fingerchessed.Game.GameType;

@SuppressWarnings("serial")
public class FingerChessedUI extends JFrame implements ModelListener {

	// Important class variables.
	private ViewListener viewListener;
	private int playerNumber = 1;
	private String nameOne;
	private String nameTwo;
	private boolean oneFinishedPlaying = true;
	private GameType gameType;

	private int playerOneLeftValue = 1;
	private int playerOneRightValue = 1;
	private int playerTwoLeftValue = 1;
	private int playerTwoRightValue = 1;
	private int myChoice = 1;

	static {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}
	}

	private static byte[] intArray2ByteArray(int[] src) {
		byte[] dst = new byte[src.length];
		for (int i = 0; i < src.length; ++i)
			dst[i] = (byte) (src[i]);
		return dst;
	}

	// Icons for tiles. handIcon[0] is a blank tile.
	private static final ImageIcon[] handIcon = new ImageIcon[] {
			new ImageIcon(intArray2ByteArray(new int[] { 137, 80, 78, 71, 13,
					10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 53, 0, 0,
					0, 53, 8, 0, 0, 0, 0, 196, 141, 252, 172, 0, 0, 0, 9, 112,
					72, 89, 115, 0, 0, 46, 35, 0, 0, 46, 35, 1, 120, 165, 63,
					118, 0, 0, 0, 53, 73, 68, 65, 84, 120, 156, 237, 214, 177,
					9, 0, 0, 8, 3, 65, 179, 255, 208, 10, 226, 2, 73, 39, 252,
					247, 71, 218, 168, 43, 72, 169, 146, 107, 122, 149, 57, 39,
					20, 10, 133, 66, 161, 80, 40, 212, 23, 229, 161, 251, 135,
					65, 153, 26, 242, 46, 149, 1, 230, 224, 116, 177, 0, 0, 0,
					0, 73, 69, 78, 68, 174, 66, 96, 130, })),
			new ImageIcon(intArray2ByteArray(new int[] { 137, 80, 78, 71, 13,
					10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 53, 0, 0,
					0, 53, 8, 0, 0, 0, 0, 196, 141, 252, 172, 0, 0, 0, 9, 112,
					72, 89, 115, 0, 0, 46, 35, 0, 0, 46, 35, 1, 120, 165, 63,
					118, 0, 0, 0, 129, 73, 68, 65, 84, 120, 156, 99, 252, 207,
					64, 6, 96, 36, 87, 23, 35, 169, 122, 254, 131, 117, 145,
					104, 29, 227, 168, 174, 81, 93, 100, 233, 122, 125, 26, 76,
					201, 233, 16, 171, 235, 199, 190, 203, 103, 15, 190, 130,
					176, 83, 102, 19, 171, 139, 239, 51, 130, 61, 104, 117,
					137, 216, 26, 174, 63, 79, 154, 174, 70, 93, 75, 73, 6, 6,
					159, 173, 164, 233, 130, 128, 81, 93, 163, 186, 70, 117,
					225, 215, 213, 121, 1, 68, 30, 122, 6, 34, 149, 204, 64,
					164, 99, 26, 97, 93, 16, 91, 144, 1, 146, 141, 67, 88, 23,
					94, 48, 170, 107, 72, 234, 34, 77, 19, 180, 125, 72, 6, 32,
					79, 23, 0, 72, 49, 212, 1, 78, 123, 136, 193, 0, 0, 0, 0,
					73, 69, 78, 68, 174, 66, 96, 130, })),
			new ImageIcon(intArray2ByteArray(new int[] { 137, 80, 78, 71, 13,
					10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 53, 0, 0,
					0, 53, 8, 0, 0, 0, 0, 196, 141, 252, 172, 0, 0, 0, 9, 112,
					72, 89, 115, 0, 0, 46, 35, 0, 0, 46, 35, 1, 120, 165, 63,
					118, 0, 0, 1, 55, 73, 68, 65, 84, 120, 156, 99, 252, 207,
					64, 6, 96, 36, 87, 23, 35, 169, 122, 254, 131, 117, 145,
					104, 29, 227, 168, 174, 81, 93, 36, 235, 122, 124, 246,
					206, 245, 31, 223, 153, 184, 229, 76, 221, 56, 136, 211,
					245, 111, 195, 198, 93, 47, 224, 60, 214, 248, 26, 121, 98,
					116, 253, 98, 71, 229, 243, 46, 243, 33, 67, 23, 3, 195,
					62, 71, 162, 117, 177, 179, 125, 251, 11, 21, 145, 187,
					207, 68, 148, 46, 167, 56, 55, 73, 134, 127, 7, 243, 47,
					67, 132, 214, 6, 17, 161, 43, 180, 73, 3, 194, 252, 98, 5,
					209, 150, 54, 147, 160, 174, 63, 39, 173, 225, 236, 213,
					97, 96, 42, 112, 29, 65, 93, 200, 224, 7, 39, 152, 242,
					223, 64, 146, 174, 47, 188, 96, 42, 110, 33, 73, 186, 214,
					5, 131, 169, 37, 209, 36, 233, 50, 63, 5, 34, 37, 30, 178,
					145, 162, 171, 190, 9, 76, 45, 136, 71, 22, 36, 164, 171,
					177, 1, 76, 37, 205, 69, 17, 37, 160, 43, 121, 30, 152,
					242, 221, 132, 42, 140, 87, 215, 183, 176, 173, 96, 218,
					127, 13, 11, 241, 186, 94, 251, 128, 3, 130, 33, 102, 49,
					186, 12, 30, 93, 247, 156, 31, 128, 233, 154, 102, 12, 41,
					220, 186, 78, 250, 189, 2, 211, 243, 18, 49, 229, 112, 234,
					218, 18, 246, 29, 68, 241, 174, 113, 195, 226, 12, 92, 186,
					102, 100, 130, 41, 137, 237, 6, 88, 52, 225, 210, 85, 221,
					6, 166, 180, 119, 74, 99, 211, 132, 67, 215, 91, 17, 8,
					205, 138, 200, 192, 188, 175, 137, 214, 133, 4, 120, 63,
					13, 3, 93, 132, 192, 168, 174, 33, 169, 139, 52, 77, 208,
					246, 33, 25, 128, 60, 93, 0, 154, 248, 226, 1, 254, 146,
					90, 89, 0, 0, 0, 0, 73, 69, 78, 68, 174, 66, 96, 130, })),
			new ImageIcon(intArray2ByteArray(new int[] { 137, 80, 78, 71, 13,
					10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 53, 0, 0,
					0, 53, 8, 0, 0, 0, 0, 196, 141, 252, 172, 0, 0, 0, 9, 112,
					72, 89, 115, 0, 0, 46, 35, 0, 0, 46, 35, 1, 120, 165, 63,
					118, 0, 0, 1, 107, 73, 68, 65, 84, 120, 156, 99, 252, 207,
					64, 6, 96, 36, 87, 23, 35, 169, 122, 254, 131, 117, 145,
					104, 29, 227, 168, 174, 81, 93, 164, 234, 250, 177, 239,
					237, 145, 111, 223, 255, 177, 176, 75, 27, 91, 73, 19, 169,
					107, 127, 238, 85, 36, 158, 69, 105, 16, 81, 186, 230, 166,
					160, 242, 99, 230, 179, 144, 161, 139, 161, 184, 135, 88,
					93, 204, 28, 76, 223, 254, 66, 5, 152, 31, 74, 19, 161,
					171, 56, 216, 205, 66, 158, 129, 225, 223, 217, 246, 245,
					16, 145, 37, 209, 132, 117, 61, 150, 132, 251, 35, 114, 5,
					152, 202, 157, 68, 88, 23, 18, 216, 230, 13, 166, 178, 167,
					144, 164, 107, 151, 59, 152, 90, 20, 75, 146, 174, 250, 38,
					16, 73, 84, 104, 192, 193, 143, 137, 21, 96, 186, 170, 21,
					89, 20, 183, 174, 63, 97, 64, 226, 209, 165, 223, 96, 78,
					97, 31, 3, 113, 186, 126, 177, 195, 88, 252, 225, 217, 122,
					168, 114, 196, 232, 98, 144, 72, 43, 229, 33, 93, 23, 3,
					131, 220, 86, 29, 226, 116, 253, 1, 6, 245, 223, 247, 71,
					191, 67, 173, 187, 34, 76, 148, 46, 8, 248, 183, 33, 255,
					9, 152, 129, 146, 124, 9, 199, 215, 99, 117, 176, 117, 34,
					175, 73, 210, 197, 16, 182, 26, 76, 189, 19, 36, 73, 87,
					242, 60, 48, 117, 200, 150, 36, 93, 214, 199, 192, 212, 43,
					81, 66, 186, 42, 56, 211, 36, 97, 236, 73, 249, 96, 138,
					243, 27, 3, 33, 93, 169, 115, 24, 236, 92, 12, 213, 100,
					152, 206, 223, 154, 115, 4, 34, 148, 48, 159, 24, 93, 104,
					128, 253, 138, 10, 25, 186, 86, 132, 35, 243, 136, 211,
					165, 52, 207, 30, 133, 143, 93, 215, 149, 149, 251, 78,
					194, 74, 39, 6, 86, 151, 200, 104, 38, 6, 34, 116, 1, 193,
					191, 43, 247, 62, 191, 189, 163, 32, 202, 47, 109, 204,
					132, 33, 57, 40, 234, 148, 81, 93, 164, 234, 34, 77, 19,
					180, 125, 72, 6, 32, 79, 23, 0, 58, 162, 241, 1, 91, 221,
					203, 108, 0, 0, 0, 0, 73, 69, 78, 68, 174, 66, 96, 130, })),
			new ImageIcon(intArray2ByteArray(new int[] { 137, 80, 78, 71, 13,
					10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 53, 0, 0,
					0, 53, 8, 0, 0, 0, 0, 196, 141, 252, 172, 0, 0, 0, 9, 112,
					72, 89, 115, 0, 0, 46, 35, 0, 0, 46, 35, 1, 120, 165, 63,
					118, 0, 0, 0, 235, 73, 68, 65, 84, 120, 156, 99, 252, 207,
					64, 6, 96, 36, 87, 23, 35, 169, 122, 254, 131, 117, 145,
					104, 29, 227, 168, 174, 81, 93, 228, 233, 218, 245, 7, 76,
					73, 25, 144, 162, 107, 105, 12, 132, 142, 88, 78, 130, 174,
					151, 154, 239, 201, 208, 229, 179, 149, 129, 116, 93, 139,
					227, 96, 44, 18, 116, 61, 215, 126, 79, 134, 46, 144, 251,
					216, 149, 174, 147, 166, 107, 97, 2, 144, 232, 221, 183,
					149, 36, 93, 96, 247, 217, 28, 246, 33, 77, 23, 72, 57,
					247, 37, 37, 210, 116, 129, 221, 55, 57, 135, 129, 36, 93,
					79, 181, 63, 50, 48, 56, 236, 103, 32, 77, 151, 235, 30, 6,
					6, 222, 171, 178, 164, 233, 154, 149, 14, 36, 230, 36, 51,
					144, 164, 235, 177, 246, 103, 6, 6, 247, 29, 12, 164, 233,
					2, 185, 143, 255, 170, 52, 105, 186, 182, 121, 3, 137, 69,
					177, 12, 164, 233, 90, 25, 193, 192, 192, 26, 12, 97, 31,
					122, 6, 34, 229, 172, 24, 24, 242, 44, 9, 235, 194, 4, 43,
					194, 135, 186, 174, 151, 151, 17, 236, 218, 19, 32, 210,
					161, 154, 129, 65, 87, 28, 191, 46, 100, 64, 98, 78, 25,
					50, 186, 176, 131, 81, 93, 67, 82, 23, 105, 154, 160, 237,
					67, 50, 0, 121, 186, 0, 253, 65, 233, 1, 187, 31, 40, 95,
					0, 0, 0, 0, 73, 69, 78, 68, 174, 66, 96, 130, })),
			new ImageIcon(intArray2ByteArray(new int[] { 137, 80, 78, 71, 13,
					10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 53, 0, 0,
					0, 53, 8, 0, 0, 0, 0, 196, 141, 252, 172, 0, 0, 0, 9, 112,
					72, 89, 115, 0, 0, 46, 35, 0, 0, 46, 35, 1, 120, 165, 63,
					118, 0, 0, 1, 38, 73, 68, 65, 84, 120, 156, 99, 252, 207,
					64, 6, 96, 36, 87, 23, 35, 169, 122, 254, 131, 117, 145,
					104, 29, 227, 168, 174, 81, 93, 164, 233, 122, 127, 28, 77,
					64, 70, 143, 8, 93, 135, 237, 208, 4, 226, 22, 14, 35, 93,
					54, 162, 112, 1, 135, 60, 162, 117, 237, 118, 97, 192, 14,
					6, 141, 174, 6, 142, 27, 127, 88, 100, 244, 156, 132, 73,
					210, 5, 5, 204, 46, 173, 198, 164, 235, 2, 130, 154, 102,
					114, 116, 49, 228, 79, 32, 90, 23, 51, 203, 79, 184, 200,
					118, 15, 98, 116, 41, 121, 59, 88, 139, 51, 252, 59, 187,
					112, 42, 68, 196, 248, 12, 97, 93, 79, 31, 88, 195, 152,
					59, 60, 33, 244, 109, 21, 130, 186, 144, 65, 226, 2, 48,
					181, 34, 156, 36, 93, 27, 2, 193, 84, 83, 45, 73, 186, 182,
					121, 131, 169, 236, 41, 36, 233, 90, 23, 12, 166, 38, 34,
					165, 122, 236, 186, 238, 253, 210, 64, 112, 252, 54, 131,
					169, 245, 1, 132, 116, 237, 241, 136, 44, 128, 37, 162, 41,
					185, 96, 138, 249, 181, 32, 65, 93, 174, 12, 12, 186, 129,
					166, 186, 162, 207, 206, 207, 216, 7, 17, 138, 88, 206, 64,
					140, 46, 84, 192, 127, 81, 158, 116, 93, 156, 27, 220, 144,
					185, 216, 117, 93, 48, 251, 141, 194, 215, 92, 140, 154,
					85, 112, 132, 252, 151, 245, 219, 182, 126, 134, 113, 172,
					98, 211, 152, 24, 136, 209, 5, 2, 79, 207, 127, 252, 113,
					222, 144, 67, 208, 142, 7, 67, 106, 16, 212, 41, 163, 186,
					72, 215, 69, 154, 38, 104, 251, 144, 12, 64, 158, 46, 0,
					149, 75, 229, 1, 156, 28, 30, 84, 0, 0, 0, 0, 73, 69, 78,
					68, 174, 66, 96, 130, })),
			new ImageIcon(intArray2ByteArray(new int[] { 137, 80, 78, 71, 13,
					10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 53, 0, 0,
					0, 53, 8, 0, 0, 0, 0, 196, 141, 252, 172, 0, 0, 0, 9, 112,
					72, 89, 115, 0, 0, 46, 35, 0, 0, 46, 35, 1, 120, 165, 63,
					118, 0, 0, 1, 182, 73, 68, 65, 84, 120, 156, 99, 252, 207,
					64, 6, 96, 36, 87, 23, 35, 169, 122, 254, 131, 117, 145,
					104, 29, 227, 168, 174, 81, 93, 100, 233, 122, 120, 236,
					232, 199, 63, 76, 28, 154, 42, 246, 130, 68, 235, 218, 212,
					126, 2, 206, 214, 159, 105, 78, 148, 174, 183, 201, 27,
					145, 185, 43, 194, 137, 209, 245, 210, 241, 58, 10, 159,
					40, 93, 63, 44, 46, 66, 24, 156, 118, 44, 239, 207, 254,
					36, 82, 87, 73, 47, 152, 114, 104, 179, 4, 81, 23, 54, 206,
					235, 34, 66, 215, 29, 141, 191, 32, 42, 115, 26, 92, 228,
					173, 48, 97, 93, 121, 147, 65, 164, 225, 57, 116, 39, 224,
					213, 245, 79, 232, 35, 136, 90, 21, 74, 146, 174, 163, 54,
					32, 146, 245, 7, 211, 191, 107, 143, 254, 113, 216, 112,
					16, 167, 107, 70, 38, 136, 180, 90, 214, 185, 232, 43, 136,
					97, 156, 148, 193, 68, 132, 174, 196, 5, 32, 82, 230, 245,
					79, 152, 128, 238, 6, 37, 194, 186, 66, 214, 162, 187, 73,
					236, 56, 178, 54, 236, 186, 252, 54, 163, 235, 66, 13, 79,
					236, 186, 124, 182, 66, 104, 169, 62, 127, 134, 61, 249,
					247, 192, 236, 69, 177, 132, 116, 5, 64, 18, 46, 243, 37,
					45, 32, 249, 88, 249, 55, 136, 227, 189, 133, 144, 174,
					232, 101, 96, 202, 115, 27, 152, 138, 92, 1, 34, 217, 127,
					16, 210, 5, 73, 26, 12, 185, 147, 192, 212, 132, 66, 48,
					245, 147, 141, 128, 174, 229, 81, 96, 106, 78, 50, 152, 90,
					28, 71, 156, 174, 91, 234, 96, 170, 161, 30, 76, 205, 79,
					2, 123, 242, 23, 19, 1, 93, 12, 26, 55, 65, 100, 212, 82,
					48, 39, 117, 14, 136, 84, 186, 75, 200, 95, 12, 61, 165,
					32, 146, 251, 25, 31, 144, 252, 165, 252, 4, 196, 65, 202,
					53, 184, 116, 125, 81, 125, 1, 162, 188, 151, 241, 49, 124,
					73, 90, 13, 22, 186, 172, 67, 80, 23, 195, 202, 8, 48, 197,
					106, 203, 116, 24, 146, 24, 145, 173, 194, 93, 218, 148,
					117, 163, 112, 237, 118, 34, 103, 23, 220, 37, 91, 103,
					245, 95, 4, 39, 110, 38, 74, 30, 195, 83, 138, 94, 107, 93,
					13, 78, 74, 12, 204, 46, 181, 214, 168, 82, 120, 75, 236,
					63, 187, 222, 62, 224, 20, 23, 118, 97, 67, 151, 24, 60,
					117, 202, 168, 46, 18, 116, 145, 166, 9, 218, 62, 36, 3,
					144, 167, 11, 0, 21, 104, 4, 16, 35, 197, 209, 254, 0, 0,
					0, 0, 73, 69, 78, 68, 174, 66, 96, 130, })),
			new ImageIcon(intArray2ByteArray(new int[] { 137, 80, 78, 71, 13,
					10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 53, 0, 0,
					0, 53, 8, 0, 0, 0, 0, 196, 141, 252, 172, 0, 0, 0, 9, 112,
					72, 89, 115, 0, 0, 46, 35, 0, 0, 46, 35, 1, 120, 165, 63,
					118, 0, 0, 1, 33, 73, 68, 65, 84, 120, 156, 99, 252, 207,
					64, 6, 96, 36, 87, 23, 35, 169, 122, 254, 131, 117, 145,
					104, 29, 227, 168, 174, 81, 93, 36, 233, 186, 242, 8, 83,
					165, 165, 32, 33, 93, 169, 115, 48, 117, 29, 178, 29, 236,
					186, 174, 107, 16, 210, 53, 109, 15, 18, 103, 61, 152, 180,
					57, 140, 36, 68, 56, 228, 119, 120, 130, 169, 157, 110, 36,
					233, 178, 63, 4, 34, 117, 47, 33, 139, 17, 212, 117, 193,
					16, 76, 205, 75, 36, 73, 87, 244, 50, 16, 41, 243, 144,
					137, 20, 93, 15, 149, 255, 130, 168, 142, 114, 20, 81, 66,
					186, 74, 122, 65, 36, 247, 51, 62, 82, 116, 189, 151, 253,
					10, 162, 138, 123, 80, 133, 9, 232, 106, 173, 1, 145, 204,
					247, 101, 73, 209, 245, 71, 246, 5, 136, 74, 152, 143, 38,
					142, 95, 215, 140, 76, 48, 117, 85, 139, 36, 93, 90, 215,
					65, 164, 203, 110, 116, 113, 188, 186, 182, 121, 131, 169,
					3, 246, 36, 233, 178, 61, 2, 34, 245, 47, 96, 72, 224, 211,
					117, 214, 4, 76, 45, 138, 37, 73, 87, 228, 10, 16, 41, 119,
					159, 9, 67, 6, 143, 174, 135, 10, 96, 170, 183, 8, 83, 10,
					143, 174, 130, 137, 32, 146, 255, 9, 15, 41, 186, 222, 74,
					255, 4, 81, 53, 205, 88, 12, 196, 173, 171, 177, 1, 68,
					178, 62, 22, 39, 69, 215, 31, 233, 87, 32, 42, 109, 38, 22,
					77, 184, 117, 77, 203, 6, 83, 200, 37, 19, 49, 46, 196, 7,
					70, 117, 13, 73, 93, 164, 105, 130, 182, 15, 201, 0, 228,
					233, 2, 0, 209, 158, 209, 1, 78, 76, 192, 200, 0, 0, 0, 0,
					73, 69, 78, 68, 174, 66, 96, 130, })),
			new ImageIcon(intArray2ByteArray(new int[] { 137, 80, 78, 71, 13,
					10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 53, 0, 0,
					0, 53, 8, 0, 0, 0, 0, 196, 141, 252, 172, 0, 0, 0, 9, 112,
					72, 89, 115, 0, 0, 46, 35, 0, 0, 46, 35, 1, 120, 165, 63,
					118, 0, 0, 1, 208, 73, 68, 65, 84, 120, 156, 99, 252, 207,
					64, 6, 96, 36, 87, 23, 35, 169, 122, 254, 131, 117, 145,
					104, 29, 227, 168, 174, 81, 93, 164, 235, 250, 177, 235,
					236, 163, 175, 127, 88, 56, 229, 140, 157, 248, 136, 213,
					117, 167, 126, 245, 111, 24, 155, 57, 168, 73, 131, 40, 93,
					155, 34, 190, 35, 115, 217, 23, 134, 19, 161, 235, 146,
					201, 111, 52, 145, 99, 150, 132, 117, 57, 239, 3, 83, 188,
					254, 252, 31, 183, 190, 7, 51, 109, 14, 19, 212, 245, 139,
					235, 47, 136, 82, 61, 42, 202, 192, 240, 214, 254, 42, 88,
					236, 39, 27, 33, 93, 167, 205, 192, 212, 178, 72, 16, 185,
					50, 2, 204, 217, 231, 72, 72, 215, 81, 27, 48, 117, 192,
					30, 68, 62, 84, 0, 115, 118, 186, 17, 116, 33, 59, 152,
					218, 237, 130, 100, 196, 27, 97, 66, 186, 24, 236, 15, 129,
					200, 242, 14, 16, 89, 219, 2, 34, 173, 142, 50, 16, 212,
					117, 214, 28, 28, 28, 109, 25, 130, 239, 103, 85, 128, 69,
					142, 88, 19, 214, 197, 176, 41, 234, 43, 152, 102, 6, 235,
					102, 224, 94, 20, 196, 64, 132, 46, 134, 199, 237, 11, 224,
					169, 131, 51, 161, 68, 137, 129, 40, 93, 119, 38, 45, 127,
					3, 99, 139, 132, 23, 168, 16, 165, 107, 82, 9, 74, 146, 98,
					238, 40, 33, 66, 87, 79, 41, 152, 10, 141, 224, 248, 177,
					113, 17, 152, 217, 86, 73, 80, 215, 67, 101, 112, 32, 76,
					206, 1, 145, 115, 83, 192, 182, 221, 82, 34, 164, 171, 177,
					1, 68, 42, 221, 133, 240, 116, 192, 9, 177, 166, 153, 144,
					174, 160, 245, 32, 50, 110, 33, 132, 23, 15, 118, 99, 224,
					58, 66, 186, 124, 182, 130, 200, 164, 185, 16, 94, 242, 60,
					16, 233, 185, 141, 144, 46, 191, 205, 32, 210, 127, 3, 132,
					23, 178, 22, 68, 250, 110, 34, 164, 11, 98, 58, 235, 67,
					73, 16, 245, 82, 22, 28, 9, 9, 243, 9, 233, 218, 16, 8,
					166, 244, 87, 169, 1, 195, 51, 226, 4, 152, 179, 34, 156,
					144, 174, 127, 170, 247, 32, 12, 109, 133, 39, 87, 32, 41,
					81, 238, 62, 19, 33, 93, 12, 199, 29, 127, 162, 10, 176,
					238, 68, 202, 202, 56, 83, 212, 193, 168, 103, 200, 92,
					177, 165, 46, 200, 92, 156, 169, 247, 203, 212, 249, 55,
					97, 108, 245, 216, 124, 30, 6, 162, 116, 1, 193, 235, 147,
					31, 191, 94, 210, 225, 229, 55, 21, 71, 151, 25, 36, 117,
					202, 168, 46, 210, 116, 145, 166, 9, 218, 62, 36, 3, 144,
					167, 11, 0, 79, 110, 14, 16, 173, 0, 234, 64, 0, 0, 0, 0,
					73, 69, 78, 68, 174, 66, 96, 130, })),
			new ImageIcon(intArray2ByteArray(new int[] { 137, 80, 78, 71, 13,
					10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82, 0, 0, 0, 53, 0, 0,
					0, 53, 8, 0, 0, 0, 0, 196, 141, 252, 172, 0, 0, 0, 9, 112,
					72, 89, 115, 0, 0, 46, 35, 0, 0, 46, 35, 1, 120, 165, 63,
					118, 0, 0, 1, 175, 73, 68, 65, 84, 120, 156, 99, 252, 207,
					64, 6, 96, 36, 87, 23, 35, 169, 122, 254, 131, 117, 145,
					104, 29, 227, 168, 174, 81, 93, 100, 232, 58, 120, 246,
					242, 15, 6, 46, 67, 59, 61, 226, 117, 253, 155, 208, 255,
					4, 202, 212, 111, 242, 35, 82, 215, 83, 255, 179, 72, 188,
					132, 217, 44, 196, 232, 122, 105, 242, 4, 133, 31, 188,
					134, 24, 93, 174, 123, 192, 148, 161, 225, 191, 211, 87,
					193, 172, 222, 34, 194, 186, 118, 185, 131, 169, 166, 90,
					32, 145, 53, 29, 196, 228, 190, 43, 78, 80, 87, 216, 106,
					16, 169, 125, 5, 68, 254, 147, 125, 6, 162, 234, 26, 9,
					234, 226, 251, 12, 34, 139, 123, 192, 156, 216, 37, 32, 82,
					234, 41, 33, 93, 191, 216, 193, 212, 146, 104, 48, 213,
					216, 0, 166, 174, 106, 17, 208, 245, 133, 23, 76, 45, 139,
					4, 83, 125, 197, 200, 102, 224, 214, 245, 141, 27, 76, 117,
					151, 128, 169, 212, 57, 96, 42, 109, 38, 33, 127, 113, 252,
					4, 145, 222, 91, 192, 28, 243, 83, 96, 42, 106, 41, 33, 93,
					126, 155, 65, 36, 243, 73, 99, 32, 185, 195, 19, 34, 22,
					184, 142, 144, 174, 117, 193, 96, 74, 176, 28, 24, 203,
					157, 95, 33, 98, 80, 155, 241, 232, 98, 112, 222, 199, 128,
					14, 8, 219, 197, 240, 210, 234, 30, 186, 46, 194, 254, 98,
					96, 120, 29, 187, 19, 198, 148, 226, 191, 14, 162, 178,
					167, 16, 214, 5, 204, 147, 51, 54, 126, 7, 134, 136, 113,
					100, 134, 197, 69, 16, 31, 26, 123, 4, 116, 1, 193, 143,
					215, 60, 252, 76, 12, 95, 4, 254, 130, 56, 215, 53, 136,
					212, 5, 1, 211, 178, 65, 164, 210, 93, 36, 33, 194, 186,
					190, 104, 130, 51, 40, 56, 215, 16, 208, 245, 148, 151, 15,
					166, 41, 16, 156, 65, 249, 239, 10, 19, 214, 181, 50, 54,
					52, 192, 14, 152, 15, 95, 175, 238, 126, 0, 22, 152, 156,
					195, 64, 132, 174, 8, 32, 193, 202, 241, 235, 39, 148, 239,
					191, 129, 129, 88, 93, 8, 224, 190, 129, 131, 100, 93, 204,
					213, 141, 104, 210, 56, 116, 221, 168, 219, 242, 29, 202,
					228, 143, 43, 80, 98, 32, 78, 23, 16, 220, 185, 242, 249,
					237, 61, 125, 30, 117, 3, 76, 169, 65, 83, 167, 140, 234,
					34, 73, 23, 105, 154, 160, 237, 67, 50, 0, 121, 186, 0, 49,
					195, 3, 16, 231, 189, 30, 37, 0, 0, 0, 0, 73, 69, 78, 68,
					174, 66, 96, 130, })), };

	// Widgets.
	private JLabel playerOneTitle;
	private JLabel playerTwoTitle;
	private JButton playerOneLeftHand;
	private JButton playerOneRightHand;
	private JButton playerTwoLeftHand;
	private JButton playerTwoRightHand;
	private JButton newGameButton;
	private JButton splitButton;
	private JTextField messageArea;

	public FingerChessedUI() {
		super("FingerChessed");

		// Set up the layout manager.
		GridBagLayout lm = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		getContentPane().setLayout(lm);

		// Set up the player titles.
		playerOneTitle = new JLabel("You");
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		lm.setConstraints(playerOneTitle, c);

		getContentPane().add(playerOneTitle);

		playerTwoTitle = new JLabel("Waiting...");
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 2;
		lm.setConstraints(playerTwoTitle, c);

		getContentPane().add(playerTwoTitle);

		// Set up the two hands for player and opponent.
		playerOneLeftHand = new JButton(handIcon[1]);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 1;
		lm.setConstraints(playerOneLeftHand, c);

		playerOneRightHand = new JButton(handIcon[1]);
		c.gridx = 2;
		c.gridy = 2;
		lm.setConstraints(playerOneRightHand, c);

		playerTwoLeftHand = new JButton(handIcon[1]);
		c.gridx = 1;
		c.gridy = 5;
		lm.setConstraints(playerTwoLeftHand, c);

		playerTwoRightHand = new JButton(handIcon[1]);
		c.gridx = 2;
		c.gridy = 5;
		lm.setConstraints(playerTwoRightHand, c);

		// Collect the number of fingers we have on the left hand.
		playerOneLeftHand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (playerNumber == 1) {
					myChoice = playerOneLeftValue;
					enablePlayerOne(false);
					enablePlayerTwo(true);
				} else {
					int newValue = myChoice + playerOneLeftValue;
					if (gameType == GameType.EXACTLY_FIVE) {
						newValue = (newValue > 5 ? newValue - 5 : newValue);
						newValue = (newValue == 5 ? 0 : newValue);
					} else {
						newValue = (newValue >= 5 ? 0 : newValue);
					}
					int oppNum = (playerNumber == 1 ? 2 : 1);
					try {
						messageArea.setText("THIS IS REALLY WORKING!");
						viewListener.setHand(oppNum, newValue,
								playerOneRightValue);
					} catch (IOException e1) {
					}
					enablePlayerTwo(false);
				}
			}
		});

		// Collect the number of fingers we have on the right hand.
		playerOneRightHand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (playerNumber == 1) {
					myChoice = playerOneRightValue;
					enablePlayerOne(false);
					enablePlayerTwo(true);
				} else {
					int newValue = myChoice + playerOneRightValue;
					if (gameType == GameType.EXACTLY_FIVE) {
						newValue = (newValue > 5 ? newValue - 5 : newValue);
						newValue = (newValue == 5 ? 0 : newValue);
					} else {
						newValue = (newValue >= 5 ? 0 : newValue);
					}
					int oppNum = (playerNumber == 1 ? 2 : 1);
					try {
						messageArea.setText("THIS IS REALLY WORKING!");
						viewListener.setHand(oppNum, playerOneLeftValue,
								newValue);
					} catch (IOException e1) {
					}
					enablePlayerTwo(false);
				}
			}
		});

		// Apply the left or right hand choice to the opponent's left hand.
		playerTwoLeftHand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (playerNumber == 2) {
					myChoice = playerTwoLeftValue;
					enablePlayerOne(true);
					enablePlayerTwo(false);
				} else {
					int newValue = myChoice + playerTwoLeftValue;
					if (gameType == GameType.EXACTLY_FIVE) {
						newValue = (newValue > 5 ? newValue - 5 : newValue);
						newValue = (newValue == 5 ? 0 : newValue);
					} else {
						newValue = (newValue >= 5 ? 0 : newValue);
					}
					int oppNum = (playerNumber == 1 ? 2 : 1);
					try {
						viewListener.setHand(oppNum, newValue,
								playerTwoRightValue);
					} catch (IOException e1) {
					}
					enablePlayerOne(false);
				}
			}
		});

		// Apply the left or right hand choice to the opponent's right hand.
		playerTwoRightHand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (playerNumber == 2) {
					myChoice = playerTwoRightValue;
					enablePlayerOne(true);
					enablePlayerTwo(false);
				} else {
					int newValue = myChoice + playerTwoRightValue;
					if (gameType == GameType.EXACTLY_FIVE) {
						newValue = (newValue > 5 ? newValue - 5 : newValue);
						newValue = (newValue == 5 ? 0 : newValue);
					} else {
						newValue = (newValue >= 5 ? 0 : newValue);
					}
					int oppNum = (playerNumber == 1 ? 2 : 1);
					try {
						messageArea.setText("THIS IS REALLY WORKING!");
						viewListener.setHand(oppNum, playerTwoLeftValue,
								newValue);
					} catch (IOException e1) {
					}
					enablePlayerOne(false);
				}
			}
		});

		getContentPane().add(playerOneLeftHand);
		getContentPane().add(playerOneRightHand);
		getContentPane().add(playerTwoLeftHand);
		getContentPane().add(playerTwoRightHand);

		// Set up the split button.
		splitButton = new JButton("Split");
		c.gridx = 0;
		c.gridy = 3;
		lm.setConstraints(splitButton, c);

		splitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					viewListener.split();
					splitButton.setEnabled(false);
				} catch (IOException e1) {
				}
			}
		});

		getContentPane().add(splitButton);

		// Set up the new game button.

		newGameButton = new JButton("New Game");
		c.gridx = 3;
		c.gridy = 3;
		lm.setConstraints(newGameButton, c);

		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					viewListener.newGame(nameOne, nameTwo, 1);
				} catch (IOException e1) {
				}
			}
		});

		getContentPane().add(newGameButton);

		// Set up the message area.
		messageArea = new JTextField();
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		lm.setConstraints(messageArea, c);
		getContentPane().add(messageArea);
		messageArea.setEditable(false);

		// Set up the close window button.
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					viewListener.quit();
				} catch (IOException e1) {
				}
			}
		});

		// Display all the things.
		pack();
		setSize(getPreferredSize());
		setLocationRelativeTo(null);
		messageArea.setText("Waiting for opponent");
		setVisible(true);
		enablePlayerOne(false);
		enablePlayerTwo(false);
		splitButton.setEnabled(false);
		newGameButton.setEnabled(false);
	}

	public void setViewListener(ViewListener viewListener) {
		this.viewListener = viewListener;
	}

	public void enablePlayerOne(boolean enabled) {
		if (playerOneLeftValue != 0) {
			playerOneLeftHand.setEnabled(enabled);
		} else {
			playerOneLeftHand.setEnabled(false);
		}

		if (playerOneRightValue != 0) {
			playerOneRightHand.setEnabled(enabled);
		} else {
			playerOneRightHand.setEnabled(false);
		}
	}

	public void enablePlayerTwo(boolean enabled) {
		if (playerTwoLeftValue != 0) {
			playerTwoLeftHand.setEnabled(enabled);
		} else {
			playerTwoLeftHand.setEnabled(false);
		}

		if (playerTwoRightValue != 0) {
			playerTwoRightHand.setEnabled(enabled);
		} else {
			playerTwoRightHand.setEnabled(false);
		}
	}

	@Override
	public void splitSet(int playerNum, int leftHand, int rightHand)
			throws IOException {
		int currentNum = (playerNum == 1 ? 1 : 2);

		if (playerNum == 1) {
			playerOneLeftHand.setIcon(handIcon[leftHand]);
			playerOneLeftValue = leftHand;
			playerOneRightHand.setIcon(handIcon[rightHand]);
			playerOneRightValue = rightHand;
		} else {
			playerTwoLeftHand.setIcon(handIcon[leftHand]);
			playerTwoLeftValue = leftHand;
			playerTwoRightHand.setIcon(handIcon[rightHand]);
			playerTwoRightValue = rightHand;
		}

		if (oneFinishedPlaying) {
			if (currentNum == playerNumber) {
				enablePlayerOne(true);
			}
		} else {
			if (currentNum == playerNumber) {
				enablePlayerTwo(true);
			}
		}
	}

	@Override
	public void handSet(int playerNum, int leftHand, int rightHand)
			throws IOException {
		if (playerNum == 1) {
			playerOneLeftHand.setIcon(handIcon[leftHand]);
			playerOneLeftValue = leftHand;
			playerOneRightHand.setIcon(handIcon[rightHand]);
			playerOneRightValue = rightHand;
		} else {
			playerTwoLeftHand.setIcon(handIcon[leftHand]);
			playerTwoLeftValue = leftHand;
			playerTwoRightHand.setIcon(handIcon[rightHand]);
			playerTwoRightValue = rightHand;
		}

		splitButton.setEnabled(false);

		turnDone(playerNum, leftHand, rightHand);
	}

	public void setMessage(String msg) {
		messageArea.setText(msg);
	}

	@Override
	public void bothJoined(String playerOne, String playerTwo, int currPlayer)
			throws IOException {
		this.nameOne = playerOne;
		this.nameTwo = playerTwo;
		this.playerOneTitle.setText(playerOne);
		this.playerTwoTitle.setText(playerTwo);
		setMessage("Let the games begin!");
		this.playerNumber = currPlayer;

		if (currPlayer == 1)
			enablePlayerOne(currPlayer == playerNumber ? true : false);
	}

	public boolean splitCheck(int leftHand, int rightHand) {
		if (leftHand == 4 || leftHand == 2) {
			if (rightHand == 0)
				return true;
		} else if (rightHand == 4 || rightHand == 2) {
			if (leftHand == 0)
				return true;
		}
		return false;
	}

	public void turnDone(int currPlayer, int leftHand, int rightHand)
			throws IOException {
		int currentNum = (currPlayer == 1 ? 1 : 2);

		if (oneFinishedPlaying) { // Player two's turn.
			// Check if game is over.
			if (playerTwoLeftValue == 0 && playerTwoRightValue == 0) {
				boardDone(1);
				return;
			}

			// Otherwise, continue as normal... player two's turn.
			oneFinishedPlaying = !oneFinishedPlaying;
			enablePlayerOne(false);
			enablePlayerTwo(false);
			if (currentNum == playerNumber)
				enablePlayerTwo(true);

			// Enable the split button if split is possible.
			if (splitCheck(playerTwoLeftValue, playerTwoRightValue)) {
				if (currentNum == playerNumber)
					splitButton.setEnabled(true);
			}

		} else { // Player one's turn.
			// Check if game is over.
			if (playerOneLeftValue == 0 && playerOneRightValue == 0) {
				boardDone(2);
				return;
			}

			oneFinishedPlaying = !oneFinishedPlaying;
			enablePlayerTwo(false);
			enablePlayerOne(false);
			if (currentNum == playerNumber)
				enablePlayerOne(true);

			// Enable the split button if split is possible.
			if (splitCheck(playerOneLeftValue, playerOneRightValue)) {
				if (currentNum == playerNumber)
					splitButton.setEnabled(true);
			}
		}

		if (currentNum == playerNumber) {
			setMessage("YOUR TURN!");
		} else {
			if (currPlayer == 1) {
				setMessage(nameOne + "'s turn!");
			} else {
				setMessage(nameTwo + "'s turn!");
			}
		}
	}

	@Override
	public void boardDone(int winner) throws IOException {
		String theWinner = "";
		if (winner == 1) {
			theWinner = nameOne + " wins!";
		} else if (winner == 2) {
			theWinner = nameTwo + " wins!";
		}

		enablePlayerOne(false);
		enablePlayerTwo(false);

		newGameButton.setEnabled(true);

		setMessage(theWinner);
	}

	@Override
	public void quit() throws IOException {
		// We do nothing here.
	}

	@Override
	public void newGame(String playerOne, String playerTwo, int currentPlayer)
			throws IOException {
		playerOneLeftHand.setIcon(handIcon[1]);
		playerOneRightHand.setIcon(handIcon[1]);
		playerTwoLeftHand.setIcon(handIcon[1]);
		playerTwoRightHand.setIcon(handIcon[1]);

		playerOneLeftValue = 1;
		playerOneRightValue = 1;
		playerTwoLeftValue = 1;
		playerTwoRightValue = 1;

		oneFinishedPlaying = true;

		newGameButton.setEnabled(false);

		bothJoined(playerOne, playerTwo, currentPlayer);
	}

	@Override
	public void receiveGameType(GameType gameType) throws IOException {
		this.gameType = gameType;
	}
}
