package com.home.projects;
import java.util.Arrays;
import java.util.Scanner;

public class MyTicTacToe {

	private static String[] board;

	private static String turn;

	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);

		String winner = null;
		board = new String[9];
		turn = "X";

		for (int i = 0; i < 9; i++) {
			board[i] = String.valueOf(i + 1);
		}

		printBoard();

		System.out.println("enter for X");

		while (winner == null) {

			int input;

			input = sc.nextInt();
			// 2. check boundary conditions
			if (!(input > 0 && input <= 9)) {
				System.out.println("invalid");
				continue;
			}

			if (board[input - 1].equals(String.valueOf(input))) {
				board[input - 1] = turn;

				// 3. toggle
				turn = turn.equals("X") ? "0" : "X";

				printBoard();

				// 4. check winner
				winner = checkWinner();

			} else {
				System.out.println("slot used- renter");
			}

		}

		if (winner.equals("draw")) {
			System.out.println("draw");
		} else {

			// 5. display winner
			System.out.println("congratulations");
		}

		sc.close();
	}

	// 1. board
	public static void printBoard() {

		System.out.println(board[0] + " " + board[1] + " " + board[2]);
		System.out.println(board[3] + " " + board[4] + " " + board[5]);
		System.out.println(board[6] + " " + board[7] + " " + board[8]);
	}

	public static String checkWinner() {
		for (int i = 0; i < 8; i++) {
			String line = null;

			switch (i) {
			case 0:
				line = board[0] + board[1] + board[2];
				break;

			case 1:
				line = board[3] + board[4] + board[5];
				break;
			case 2:
				line = board[6] + board[7] + board[8];
				break;
			case 3:
				line = board[0] + board[3] + board[6];
				break;
			case 4:
				line = board[1] + board[4] + board[7];
				break;
			case 5:
				line = board[2] + board[5] + board[8];
				break;
			case 6:
				line = board[0] + board[4] + board[8];
				break;
			case 7:
				line = board[2] + board[4] + board[6];
				break;
			}

			if (line.equals("XXX")) {
				return "X";
			} else if (line.equals("000")) {
				return "0";
			}

		}

		for (int i = 0; i < 9; i++) {
			if (Arrays.asList(board).contains(String.valueOf(i + 1))) {
				break;
			} else if (i == 8) {
				return "draw";
			}
		}

		System.out.println(turn + "'s turn enter the slot for " + turn);
		return null;
	}

}
