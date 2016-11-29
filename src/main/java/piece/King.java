package piece;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import general.Colour;
import general.Coord;
import general.Move;
import player.Player;

public class King extends aPiece {
	public King(Player p, Board b, Colour c) {
		super(p, b, c);
	}

	@Override
	public void set_attacked_field(boolean[][] tab, Coord coord) {

		boolean A = false, B = false, C = false, D = false;
		if (coord.i < 7) {
			A = true;
		}
		if (coord.j < 7) {
			B = true;
		}
		if (coord.i > 0) {
			C = true;
		}
		if (coord.j > 0) {
			D = true;
		}

		if (A) {
			tab[coord.i + 1][coord.j] = true;
		}
		if (A && B) {
			tab[coord.i + 1][coord.j + 1] = true;
		}
		if (B) {
			tab[coord.i][coord.j + 1] = true;
		}
		if (B && C) {
			tab[coord.i - 1][coord.j + 1] = true;
		}
		if (C) {
			tab[coord.i - 1][coord.j] = true;
		}
		if (C && D) {
			tab[coord.i - 1][coord.j - 1] = true;
		}
		if (D) {
			tab[coord.i][coord.j - 1] = true;
		}
		if (D && A) {
			tab[coord.i + 1][coord.j - 1] = true;
		}

		// CASTLING
		if (!m_board.get_board_tab()[coord.i][coord.j].get_if_moved()) {

		}
	}

	@Override
	public List<Move> available_moves(Player player, Coord coord) {

		if (m_colour != player.get_colour()) {
			return null;
		}

		boolean[][] attacked_fields;
		if (m_colour == Colour.WHITE) {
			attacked_fields = m_board.black_attacks;
		} else {
			attacked_fields = m_board.white_attacks;
		}

		List<Move> tab = new ArrayList<Move>();
		boolean A = false, B = false, C = false, D = false;
		if (coord.i < 7) {
			A = true;
		}
		if (coord.j < 7) {
			B = true;
		}
		if (coord.i > 0) {
			C = true;
		}
		if (coord.j > 0) {
			D = true;
		}

		if (A && m_board.get_board_tab()[coord.i + 1][coord.j].get_colour() != m_colour
				&& attacked_fields[coord.i + 1][coord.j] == false) {
			tab.add(new Move(coord, coord.i + 1, coord.j));
		}
		if (A && B && m_board.get_board_tab()[coord.i + 1][coord.j + 1].get_colour() != m_colour
				&& attacked_fields[coord.i + 1][coord.j + 1] == false) {
			tab.add(new Move(coord, coord.i + 1, coord.j + 1));
		}
		if (B && m_board.get_board_tab()[coord.i][coord.j + 1].get_colour() != m_colour
				&& attacked_fields[coord.i][coord.j + 1] == false) {
			tab.add(new Move(coord, coord.i, coord.j + 1));
		}
		if (B && C && m_board.get_board_tab()[coord.i - 1][coord.j + 1].get_colour() != m_colour
				&& attacked_fields[coord.i - 1][coord.j + 1] == false) {
			tab.add(new Move(coord, coord.i - 1, coord.j + 1));
		}
		if (C && m_board.get_board_tab()[coord.i - 1][coord.j].get_colour() != m_colour
				&& attacked_fields[coord.i - 1][coord.j] == false) {
			tab.add(new Move(coord, coord.i - 1, coord.j));
		}
		if (C && D && m_board.get_board_tab()[coord.i - 1][coord.j - 1].get_colour() != m_colour
				&& attacked_fields[coord.i - 1][coord.j - 1] == false) {
			tab.add(new Move(coord, coord.i - 1, coord.j - 1));
		}
		if (D && m_board.get_board_tab()[coord.i][coord.j - 1].get_colour() != m_colour
				&& attacked_fields[coord.i][coord.j - 1] == false) {
			tab.add(new Move(coord, coord.i, coord.j - 1));
		}
		if (D && A && m_board.get_board_tab()[coord.i + 1][coord.j - 1].get_colour() != m_colour
				&& attacked_fields[coord.i + 1][coord.j - 1] == false) {
			tab.add(new Move(coord, coord.i + 1, coord.j - 1));
		}

		// CASTLING

		if (!already_moved) {
			boolean[][] attacks;
			if (m_colour == Colour.BLACK) {
				attacks = m_board.white_attacks;
			} else {
				attacks = m_board.black_attacks;
			}
			// KING SIDE

			if (!m_board.get_board_tab()[coord.i][0].get_if_moved()
					&& m_board.get_board_tab()[coord.i][0] instanceof Rook
					&& m_board.get_board_tab()[coord.i][1] instanceof Empty
					&& m_board.get_board_tab()[coord.i][2] instanceof Empty && attacks[coord.i][1] == false
					&& attacks[coord.i][2] == false && attacks[coord.i][coord.j] == false) {
				tab.add(new Move(coord, coord.i, 1));
			}
			// QUEEN SIDE

			
			if (!m_board.get_board_tab()[coord.i][7].get_if_moved()
					&& m_board.get_board_tab()[coord.i][7] instanceof Rook
					&& m_board.get_board_tab()[coord.i][4] instanceof Empty
					&& m_board.get_board_tab()[coord.i][5] instanceof Empty
					&& m_board.get_board_tab()[coord.i][6] instanceof Empty && attacks[coord.i][4] == false
					&& attacks[coord.i][5] == false && attacks[coord.i][coord.j] == false

			) {
				tab.add(new Move(coord, coord.i, 5));
			}

		}

		return tab;
	}

}
