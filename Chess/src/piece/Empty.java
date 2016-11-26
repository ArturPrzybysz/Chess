package piece;

import java.util.List;

import board.Board;
import general.Colour;
import general.Coord;
import general.Move;
import player.Player;

public class Empty extends aPiece {
	public Empty(Board b) {
		super(b, Colour.EMPTY);
	}

	@Override
	public void set_attacked_field(boolean[][] tab,Coord coord) {

	}

	@Override
	public List<Move> available_moves(Player player,Coord coord) {
		return null;
	}

}
