package player;

import board.Board;
import general.Colour;

public class Player {

	public Player(Colour c) {
		m_colour = c;
	}

	public void set_board(Board b) {
		m_board = b;
	}

	private Colour m_colour;
	private Board m_board;

	public Board get_board() {
		return m_board;
	}

	public Colour get_colour() {
		// TODO Auto-generated method stub
		return m_colour;
	}
}
