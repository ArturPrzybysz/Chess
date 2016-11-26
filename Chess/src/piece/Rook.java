package piece;

import java.util.ArrayList;
import java.util.List;

import board.Board;
import general.Colour;
import general.Coord;
import general.Move;
import player.Player;

public class Rook extends aPiece {
	public Rook(Player p, Board b, Colour c) {
		super(p, b, c);
	}

	public void set_attacked_field(boolean[][] tab, Coord coord) {

		// setting distance from border of a board
		int right = 7 - coord.i, left = coord.i, up = 7 - coord.j, down = coord.j;

		// marking
		// --------------------------------------------------------------Will it
		// stay?

		for (int i = 1; i <= right; i++) {
			if (m_board.occupancy[coord.i + i][coord.j]) {
				tab[coord.i + i][coord.j] = true;
				break;
			}
			tab[coord.i + i][coord.j] = true;
		}

		for (int i = 1; i <= left; i++) {
			if (m_board.occupancy[coord.i - i][coord.j]) {
				tab[coord.i - i][coord.j] = true;
				break;
			}
			tab[coord.i - i][coord.j] = true;
		}
		for (int i = 1; i <= up; i++) {
			if (m_board.occupancy[coord.i][coord.j + i]) {
				tab[coord.i][coord.j + i] = true;
				break;
			}
			tab[coord.i][coord.j + i] = true;
		}
		for (int i = 1; i <= down; i++) {
			if (m_board.occupancy[coord.i][coord.j - i]) {
				tab[coord.i][coord.j - i] = true;
				break;
			}
			tab[coord.i][coord.j - i] = true;
		}
	}

	@Override
	public List<Move> available_moves(Player player, Coord coord) {
		if (m_colour != player.get_colour()) {
			return null;
		}
		List<Move> tab = new ArrayList<Move>();

		// setting distance from border of a board
		int right = 7 - coord.i, left = coord.i, up = 7 - coord.j, down = coord.j;

		for (int i = 1; i <= right; i++) {
			if (m_board.occupancy[coord.i + i][coord.j]) {
				if (m_board.get_board_tab()[coord.i + i][coord.j].get_colour() != m_colour) {
					tab.add(new Move(coord, coord.i + i, coord.j));

				}
				break;
			}
			tab.add(new Move(coord, coord.i + i, coord.j));
		}

		for (int i = 1; i <= left; i++) {
			if (m_board.occupancy[coord.i - i][coord.j]) {
				if (m_board.get_board_tab()[coord.i - i][coord.j].get_colour() != m_colour) {
					tab.add(new Move(coord, coord.i - i, coord.j));
				}
				break;
			}
			tab.add(new Move(coord, coord.i - i, coord.j));
		}
		for (int i = 1; i <= up; i++) {
			if (m_board.occupancy[coord.i][coord.j + i]) {
				if (m_board.get_board_tab()[coord.i][coord.j + i].get_colour() != m_colour) {
					tab.add(new Move(coord, coord.i, coord.j + i));
				}
				break;
			}
			tab.add(new Move(coord, coord.i, coord.j + i));
		}
		for (int i = 1; i <= down; i++) {
			if (m_board.occupancy[coord.i][coord.j - i]) {
				if (m_board.get_board_tab()[coord.i][coord.j - i].get_colour() != m_colour) {
					tab.add(new Move(coord, coord.i, coord.j - i));
				}
				break;
			}
			tab.add(new Move(coord, coord.i, coord.j - i));
		}

		return tab;
	}

}
