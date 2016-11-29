package general;

import general.MyScanner;

public class Coord {
	public int i, j;

	public Coord() {
		do {
			i = MyScanner.nextInt();
		} while (i > 7 && i < 0);
		do {
			j = MyScanner.nextInt();
		} while (j > 7 && j < 0);
	}

	public Coord(int a, int b) {
		i = a;
		j = b;
	}
}
