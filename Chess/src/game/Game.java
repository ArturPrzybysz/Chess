package game;

import board.*;
import general.Colour;
import player.*;

public class Game {

	private Board board;

	private boolean game_is_on;
	private Player white, black;

	public Game() {
		white = new Player(Colour.WHITE);
		black = new Player(Colour.BLACK);
		board = new Board(white, black);
		game_is_on = true;
	}

	public void start() {

		Player[] players = { white, black };
		int i = 0;
		board.read_pieces();
		do {
			board.main_move(players[i % 2]);
			i++;
			System.out.println("");
		} while (game_is_on);
	}

	public void check_mate() {
		game_is_on = false;
	};

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	};

}
