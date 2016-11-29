package general;

public class Move {
	Coord a;
	Coord b;

	public Move(Coord begin, Coord end) {
		a = begin;
		b = end;
	}

	public Move() {
		System.out.println("Podaj wspolrzedne figury, ktora chcesz przesunac:");
		a = new Coord();
		System.out.println("Podaj wspolrzedne celu:");
		b = new Coord();
	}

	public Move(Coord begin, int i, int j) {
		a = begin;
		b = new Coord(i, j);
	}

	public Move(int x, int y, int i, int j) {
		a = new Coord(x, y);
		b = new Coord(i, j);
	}

	public Coord get_begin() {
		return a;
	}

	public Coord get_end() {
		return b;
	}

	public void read_move() {
		System.out.println("sk¹d: i=" + a.i + " j=" + a.j + "  Dokad: i=" + b.i + " j=" + b.j);

	}

	public boolean equals(Move compared) {
		if (a.i == compared.get_begin().i) {
			if (a.j == compared.get_begin().j) {
				if (b.i == compared.get_end().i) {
					if (b.j == compared.get_end().j) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
