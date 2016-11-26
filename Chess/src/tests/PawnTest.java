package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import board.Board;
import general.Colour;
import general.Coord;
import piece.Pawn;
import player.Player;

public class PawnTest {

	@Test
	public void test() {
		Player p1 = new Player(Colour.WHITE);
		Player p2 = new Player(Colour.BLACK);
		Board b = new Board(p1, p2);
		b.test_pawn(p1);
		b.read_pieces();
		for(int i=0;i<5;i++) {
		b.main_move(p1);
		b.read_pieces();
		b.main_move(p2);
		b.read_pieces();
		}
	}

}
