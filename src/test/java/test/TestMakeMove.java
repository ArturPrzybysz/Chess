package test;

import static org.junit.Assert.*;
import org.junit.Test;
import board.Board;
import player.Player;
import board.*;
import general.*;
public class TestMakeMove {
	@Test
	public void test() {
		Player p1 = new Player(Colour.WHITE);
		Player p2 = new Player(Colour.BLACK);
		Board b = new Board(p1, p2);
		b.read_pieces();
		for(int i=0;i<24;i++) 
		{
		b.main_move(p1);
		b.read_pieces();
		b.main_move(p2);
		b.read_pieces();
		}
		
		
	}
}
