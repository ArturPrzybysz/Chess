package tests;

import org.junit.Test;
import board.Board;
import general.Colour;
import player.Player;

public class RookTest {
	@Test
	public void test() {
		Player p1 = new Player(Colour.WHITE);
		Player p2 = new Player(Colour.BLACK);
		Board b = new Board(p1, p2);
		b.test_rook(p1);
		b.read_pieces();
		b.main_move(p2);
		b.read_pieces();
		System.out.println("koniec");
	}

}
