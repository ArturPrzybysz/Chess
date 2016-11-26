package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import board.Board;
import general.Colour;
import player.Player;

public class Test_enpassant {

	@Test
	public void test() {
		Player p1 = new Player(Colour.WHITE);
		Player p2 = new Player(Colour.BLACK);
		Board b = new Board(p1, p2);
		b.test_ep(p1,p2);
		b.read_pieces();

		while(true) {
		b.main_move(p2);
		b.read_pieces();
		b.main_move(p1);
		b.read_pieces();
		System.out.println(" ");}
	}
}
