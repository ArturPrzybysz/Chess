package tests;

import static org.junit.Assert.*;
import board.*;
import general.Colour;
import player.Player;

import org.junit.Test;

public class Copytest {

	@Test
	public void test() {
		Player p1 = new Player(Colour.WHITE);
		Player p2 = new Player(Colour.BLACK);
		
		Board b = new Board(p1, p2);
		
		b.read_pieces();
		b.test_copy(p1);
		b.read_pieces();
		System.out.println("koniec");
	}

}
