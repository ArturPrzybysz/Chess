package piece;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import general.Colour;
import general.Coord;
import general.Move;
import player.Player;

public class Pawn extends aPiece {

	public Pawn(Player p, Board b, Colour c) {
		super(p, b, c);
	}

	@Override
	public void set_attacked_field(boolean[][] tab, Coord coord) {

		int vector = 1;
		if (m_colour == Colour.BLACK) {
			vector = -1;
		}

		boolean attack_left, attack_right;
		if (coord.j > 0) {
			attack_left = true;
		} else {
			attack_left = false;
		}
		if (coord.j < 7) {
			attack_right = true;
		} else {
			attack_right = false;
		}

		if (attack_right) {
			tab[coord.i + vector][coord.j + 1] = true;
		}
		if (attack_left) {
			tab[coord.i + vector][coord.j - 1] = true;
		}

	}

	@Override
	public boolean if_legal(Move move, Player player, Coord coord) {

		// if goal is taken by same colour piece- false
		if (m_board.get_board_tab()[move.get_end().i][move.get_end().j].get_colour() == m_colour) {
			return false;
		}

		// if you try to move wrong piece
		if (m_board.get_board_tab()[move.get_begin().i][move.get_begin().j].get_colour() != player.get_colour()) {
			return false;
		}

		boolean[][] tab = new boolean[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tab[i][j] = false;
			}
		}
		set_attacked_field_pawn_edition(tab, coord);

		return (tab[move.get_end().i][move.get_end().j]);

	}

	private void set_attacked_field_pawn_edition(boolean[][] tab, Coord coord) {
		int vector = 1;
		if (m_colour == Colour.BLACK) {
			vector = -1;
		}
		tab[coord.i + vector][coord.j] = true;

		// in 1st move a pawn might go 2 fields at once so:

		if (coord.i == 1 && m_colour == Colour.WHITE && m_board.occupancy[coord.i + vector][coord.j] == false) {
			tab[coord.i + 2 * vector][coord.j] = true;
		}
		if (coord.i == 6 && m_colour == Colour.BLACK && m_board.occupancy[coord.i + vector][coord.j] == false) {
			tab[coord.i + 2 * vector][coord.j] = true;
		}
	};

	@Override
	public List<Move> available_moves(Player player, Coord coord) {

		if (m_colour != player.get_colour()) {
			return null;
		}

		List<Move> tab = new ArrayList<Move>();

		int vector = 1;
		if (m_colour == Colour.BLACK) {
			vector = -1;
		}

		boolean attack_left, attack_right;
		if (coord.j > 0) {
			attack_left = true;
		} else {
			attack_left = false;
		}
		if (coord.j < 7) {
			attack_right = true;
		} else {
			attack_right = false;
		}

		if (attack_right && m_board.occupancy[coord.i + vector][coord.j + 1]
				&& m_board.get_board_tab()[coord.i + vector][coord.j + 1].get_colour() != m_colour) {

			tab.add(new Move(coord, coord.i + vector, coord.j + 1));
		}
		if (attack_left && m_board.occupancy[coord.i + vector][coord.j - 1]
				&& m_board.get_board_tab()[coord.i + vector][coord.j - 1].get_colour() != m_colour) {
			tab.add(new Move(coord, coord.i + vector, coord.j - 1));
		}

		if (!m_board.occupancy[coord.i + vector][coord.j] && m_board.occupancy[coord.i + vector][coord.j] == false) {
			tab.add(new Move(coord, coord.i + vector, coord.j));
		}

		// in 1st move a pawn might go 2 fields at once so:

		if (coord.i == 1 && m_colour == Colour.WHITE) {
			tab.add(new Move(coord, coord.i + 2 * vector, coord.j));
		}
		if (coord.i == 6 && m_colour == Colour.BLACK) {
			tab.add(new Move(coord, coord.i + 2 * vector, coord.j));
		}

		// en passant

		int required_line;

		if (m_colour == Colour.BLACK) {
			required_line = 3;
		} else {
			required_line = 4;
		}

		if (m_board.last_move != null) {
			if (conditions_en_passant(required_line, coord)) {
				tab.add(new Move(coord, m_board.last_move.get_end().i + vector, m_board.last_move.get_end().j));
			}
		}
		//
		return tab;

	}

	private boolean conditions_en_passant(int required_line, Coord coord) {

		if (coord.i == required_line && m_board.last_move.get_end().i == required_line
				&& Math.abs(m_board.last_move.get_end().j - coord.j) == 1
				&& Math.abs(m_board.last_move.get_end().i - m_board.last_move.get_begin().i) == 2
				&& m_board.get_board_tab()[m_board.last_move.get_end().i][m_board.last_move.get_end().j] instanceof Pawn
				&& m_board.get_board_tab()[m_board.last_move.get_end().i][m_board.last_move.get_end().j]
						.get_colour() != m_colour)
			return true;
		else
			return false;
	}

}
