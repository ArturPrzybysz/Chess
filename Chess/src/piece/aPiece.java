package piece;

import board.Board;
import general.*;
import player.Player;
import java.util.List;

public abstract class aPiece implements Cloneable {
	protected Board m_board;
	protected Player m_player;
	protected Colour m_colour;
	protected boolean already_moved;

	public abstract void set_attacked_field(boolean[][] tab, Coord coord);

	public abstract List<Move> available_moves(Player player, Coord coord);

	public aPiece(Player p, Board b, Colour c) {
		m_player = p;
		m_colour = c;
		m_board = b;
		already_moved = false;
	}

	public aPiece(Board b, Colour colour) {
		m_board = b;
		m_colour = colour;
		already_moved = false;
	}

	public int min(int a, int b) {
		if (a < b) {
			return a;
		} else
			return b;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (Exception e) {
			return null;
		}
	}

	public void copy_to_position(Move move) {
		aPiece piece = (aPiece) m_board.get_board_tab()[move.get_begin().i][move.get_begin().j].clone();
		m_board.get_board_tab()[move.get_end().i][move.get_end().j] = piece;
	}

	public boolean if_legal(Move move, Player player, Coord coord) {

		// if goal is taken by same colour piece - reject
		if (m_board.get_board_tab()[move.get_end().i][move.get_end().j].get_colour() == m_colour) {
			return false;
		}
		// if you try to move not your piece or empty field - reject
		if (m_board.get_board_tab()[move.get_begin().i][move.get_begin().j].get_colour() != player.get_colour()) {
			return false;
		}

		boolean[][] tab = new boolean[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tab[i][j] = false;
			}
		}
		set_attacked_field(tab, coord);

		if (tab[move.get_end().i][move.get_end().j]) {
		}
		return (tab[move.get_end().i][move.get_end().j]);
	}

	public Colour get_colour() {
		return m_colour;
	}

	public void set_as_already_moved() {
		already_moved = true;
	}

	public boolean get_if_moved() {
		return already_moved;
	}


}
