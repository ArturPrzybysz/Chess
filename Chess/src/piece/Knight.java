package piece;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import general.Colour;
import general.Coord;
import general.Move;
import player.Player;

public class Knight extends aPiece {
	public Knight(Player p, Board b, Colour c) {
		super(p, b, c);
	}

	@Override
	public void set_attacked_field(boolean[][] tab, Coord coord) {
		boolean A, B, C, D, E, F, G, H;
		if (coord.i > 0) {
			A = true;
		} else {
			A = false;
		}
		if (coord.i < 7) {
			B = true;
		} else {
			B = false;
		}
		if (coord.j < 6) {
			C = true;
		} else {
			C = false;
		}
		if (coord.i < 6) {
			D = true;
		} else {
			D = false;
		}
		if (coord.j < 7) {
			E = true;
		} else {
			E = false;
		}
		if (coord.j > 0) {
			F = true;
		} else {
			F = false;
		}
		if (coord.j > 1) {
			G = true;
		} else {
			G = false;
		}
		if (coord.i > 1) {
			H = true;
		} else {
			H = false;
		}

		if (A && C) {
			tab[coord.i - 1][coord.j + 2] = true;
		}
		if (B && C) {
			tab[coord.i + 1][coord.j + 2] = true;
		}
		if (D && E) {
			tab[coord.i + 2][coord.j + 1] = true;
		}
		if (D && F) {
			tab[coord.i + 2][coord.j - 1] = true;
		}
		if (B && G) {
			tab[coord.i + 1][coord.j - 2] = true;
		}
		if (A && G) {
			tab[coord.i - 1][coord.j - 2] = true;
		}
		if (H && F) {
			tab[coord.i - 2][coord.j - 1] = true;
		}
		if (H && E) {
			tab[coord.i - 2][coord.j + 1] = true;
		}

	}

	@Override
	public List<Move> available_moves(Player player, Coord coord) {

		if (m_colour != player.get_colour()) {
			return null;
		}

		boolean A, B, C, D, E, F, G, H;
		if (coord.i > 0) {
			A = true;
		} else {
			A = false;
		}
		if (coord.i < 7) {
			B = true;
		} else {
			B = false;
		}
		if (coord.j < 6) {
			C = true;
		} else {
			C = false;
		}
		if (coord.i < 6) {
			D = true;
		} else {
			D = false;
		}
		if (coord.j < 7) {
			E = true;
		} else {
			E = false;
		}
		if (coord.j > 0) {
			F = true;
		} else {
			F = false;
		}
		if (coord.j > 1) {
			G = true;
		} else {
			G = false;
		}
		if (coord.i > 1) {
			H = true;
		} else {
			H = false;
		}

		List<Move> tab = new ArrayList<Move>();

		if (A && C && m_board.get_board_tab()[coord.i - 1][coord.j + 2].get_colour() != m_colour) {
			tab.add(new Move(coord, coord.i - 1, coord.j + 2));
		}
		if (B && C && m_board.get_board_tab()[coord.i + 1][coord.j + 2].get_colour() != m_colour) {
			tab.add(new Move(coord, coord.i + 1, coord.j + 2));
		}
		if (D && E && m_board.get_board_tab()[coord.i + 2][coord.j + 1].get_colour() != m_colour) {
			tab.add(new Move(coord, coord.i + 2, coord.j + 1));
		}
		if (D && F && m_board.get_board_tab()[coord.i + 2][coord.j - 1].get_colour() != m_colour) {
			tab.add(new Move(coord, coord.i + 2, coord.j - 1));
		}
		if (B && G && m_board.get_board_tab()[coord.i + 1][coord.j - 2].get_colour() != m_colour) {
			tab.add(new Move(coord, coord.i + 1, coord.j - 2));
		}
		if (A && G && m_board.get_board_tab()[coord.i - 1][coord.j - 2].get_colour() != m_colour) {
			tab.add(new Move(coord, coord.i - 1, coord.j - 2));
		}
		if (H && F && m_board.get_board_tab()[coord.i - 2][coord.j - 1].get_colour() != m_colour) {
			tab.add(new Move(coord, coord.i - 2, coord.j - 1));
		}
		if (H && E && m_board.get_board_tab()[coord.i - 2][coord.j + 1].get_colour() != m_colour) {
			tab.add(new Move(coord, coord.i - 2, coord.j + 1));
		}
		return tab;
	}

}
