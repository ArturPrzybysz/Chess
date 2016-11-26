package board;

import java.util.ArrayList;
import java.util.List;
import game.*;
import general.*;
import piece.*;
import player.Player;

public class Board {

	// Game
	private Game game;

	// Constructor
	public Board(Player p1, Player p2) {
		setBoard_tab(new aPiece[8][8]);
		setBoard_tab(prepare_board_tab());

		last_move = null;

		m_p1 = p1;
		m_p2 = p2;

		occupancy = new boolean[8][8];

		white_attacks = new boolean[8][8];

		black_attacks = new boolean[8][8];

		update_stats();
	}

	// Main Board
	private aPiece board_tab[][];

	// Fields
	private Player m_p1, m_p2;
	private Coord white_kings_position;
	private Coord black_kings_position;

	// Data of board
	public boolean[][] occupancy;
	public boolean[][] white_attacks;
	public boolean[][] black_attacks;

	// Clones
	private aPiece copy_of_piece_begin, copy_of_piece_end;

	// Last move - for en passsant
	public Move last_move;

	// public Move

	public void main_move(Player player) {
		Move move;

		List<Move> legal = legal_moves(player);
		if (legal.size() == 0) {
			game.check_mate();
		}
		if (legal != null) {
			read_array_of_moves(legal);
		}
		do {
			move = get_move();
		} while (!is_on_list(move, legal));

		Type_of_move type = what_type_of(move);

		make_move(move);
		if (type != Type_of_move.NORMAL) {
			passive_effects(move, type);
		}
		set_piece_as_moved(move);
		last_move = move;
		read_pieces();
	}

	// private methods for public MOVE

	private Type_of_move what_type_of(Move move) {
		if (is_en_passant(move)) {
			return Type_of_move.EN_PASSANT;
		}
		if (is_castling_king_side(move)) {
			return Type_of_move.CASTLING_KING_SIDE;
		}
		if (is_castling_queen_side(move)) {
			return Type_of_move.CASTLING_QUEEN_SIDE;
		}
		if (is_promoting(move)) {
			return Type_of_move.PROMOTION;
		}

		return Type_of_move.NORMAL;
	}

	private boolean is_promoting(Move move) {

		if (!(board_tab[move.get_begin().i][move.get_begin().j] instanceof Pawn)) {
			return false;
		}
		int required_line;
		if (board_tab[move.get_begin().i][move.get_begin().j].get_colour() == Colour.BLACK) {
			required_line = 0;
		} else {
			required_line = 7;
		}
		if (move.get_end().i == required_line) {
			return true;
		}

		return false;
	}

	private boolean is_castling_queen_side(Move move) {
		if (board_tab[move.get_begin().i][move.get_begin().j] instanceof King && move.get_end().j == 5
				&& Math.abs(move.get_begin().j - move.get_end().j) == 2)
			return true;

		return false;
	}

	private boolean is_castling_king_side(Move move) {

		if (board_tab[move.get_begin().i][move.get_begin().j] instanceof King && move.get_end().j == 1
				&& Math.abs(move.get_begin().j - move.get_end().j) == 2)
			return true;

		return false;
	}

	private boolean is_en_passant(Move move) {

		if (board_tab[move.get_begin().i][move.get_begin().j] instanceof Pawn && move.get_begin().j != move.get_end().j
				&& board_tab[move.get_end().i][move.get_end().j] instanceof Empty) {
			return true;
		}
		return false;
	}

	private void set_piece_as_moved(Move move) {
		board_tab[move.get_end().i][move.get_end().j].set_as_already_moved();
	}

	private ArrayList<Move> legal_moves(Player player) {
		List<Move> legal = new ArrayList<Move>();
		List<Move> legal_moves_from_one_field;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				legal_moves_from_one_field = list_of_legal(player, i, j);

				if (legal_moves_from_one_field != null) {
					legal.addAll(legal_moves_from_one_field);
				}
			}
		}

		return (ArrayList<Move>) (legal);
	}

	private List<Move> list_of_legal(Player player, int i, int j) {
		List<Move> legal = new ArrayList<Move>();
		Type_of_move type = Type_of_move.NORMAL;

		List<Move> all_available = board_tab[i][j].available_moves(player, new Coord(i, j));

		if (all_available != null) {
			for (int k = 0; k < all_available.size(); k++) {

				type = what_type_of(all_available.get(k));
				// TODO castling : nie musisz sprawdzac czy bedzie szach po
				// ruchu- zrobione w pawn, mo¿na od razu dodaæ do listy
				// legalnych

				make_move(all_available.get(k));

				if (!is_king_checked(player.get_colour())) {
					legal.add(all_available.get(k));
				}
				reverse(all_available.get(k), type);
			}
		} else {
			return null;
		}

		return legal;
	}

	private void reverse(Move move, Type_of_move type) {

		if (type == Type_of_move.EN_PASSANT) {
			int vector = 1;
			Colour colour = Colour.BLACK;
			Player player = m_p1;
			if (board_tab[move.get_end().i][move.get_end().j].get_colour() == Colour.BLACK) {
				vector = -1;
				colour = Colour.WHITE;
				player = m_p2;
			}
			board_tab[move.get_end().i - vector][move.get_end().j] = new Pawn(player, this, colour);

		}
		board_tab[move.get_begin().i][move.get_begin().j] = copy_of_piece_begin;
		board_tab[move.get_end().i][move.get_end().j] = copy_of_piece_end;

		update_stats();
	}

	private boolean is_king_checked(Colour c) {
		if (c == Colour.BLACK) {
			return white_attacks[black_kings_position.i][black_kings_position.j];
		}
		if (c == Colour.WHITE) {
			return black_attacks[white_kings_position.i][white_kings_position.j];
		}
		return false;
	}

	private Move get_move() {
		Move move = new Move();
		return move;
	}

	private void make_move(Move move) {

		copy_of_piece_begin = (aPiece) board_tab[move.get_begin().i][move.get_begin().j].clone();
		copy_of_piece_end = (aPiece) board_tab[move.get_end().i][move.get_end().j].clone();

		board_tab[move.get_begin().i][move.get_begin().j].copy_to_position(move);
		board_tab[move.get_begin().i][move.get_begin().j] = new Empty(this);

		update_stats();
	}

	private void passive_effects(Move move, Type_of_move type) {
		if (type == Type_of_move.EN_PASSANT) {

			board_tab[last_move.get_end().i][last_move.get_end().j] = new Empty(this);
		}
		if (type == Type_of_move.CASTLING_QUEEN_SIDE) {
			make_move(new Move(move.get_begin().i, 7, move.get_begin().i, 4));
		}
		if (type == Type_of_move.CASTLING_KING_SIDE) {
			Move m = new Move(move.get_begin().i, 0, move.get_begin().i, 2);
			make_move(m);
		}
		if (type == Type_of_move.PROMOTION) {
			promotion(move);
		}
	}

	private void promotion(Move move) {
		// TODO: wersja graficzna
		System.out.println("Wybierz figurê: [Q] dla damy, [X] dla konia, [W] dla wie¿y, [B] dla goñca");
		char choice;
		do {
			choice = MyScanner.nextChar();
		} while (choice != 'Q' || choice != 'X' || choice != 'W' || choice != 'B');
		aPiece promoted = null;
		Player player;
		Colour colour;
		if (board_tab[move.get_end().i][move.get_end().i].get_colour() == Colour.WHITE) {
			player = m_p1;
			colour = Colour.WHITE;
		} else {
			player = m_p2;
			colour = Colour.BLACK;
		}

		switch (choice) {
		case 'Q':
			promoted = new Queen(player, this, colour);
			break;
		case 'X':
			promoted = new Knight(player, this, colour);
			break;
		case 'W':
			promoted = new Rook(player, this, colour);
			break;
		case 'B':
			promoted = new Bishop(player, this, colour);
			break;
		}
		board_tab[move.get_end().i][move.get_end().j] = promoted;
	}

	private void copy_end_and_start_pawn(Move move) {
		copy_of_piece_begin = (aPiece) get_board_tab()[move.get_begin().i][move.get_begin().j].clone();
		copy_of_piece_end = (aPiece) get_board_tab()[move.get_end().i][move.get_end().j].clone();
	}

	private static boolean is_on_list(Move move, List<Move> list) {
		for (int i = 0; i < list.size(); i++) {
			if (move.equals(list.get(i))) {
				return true;
			}
		}
		return false;
	}

	// Updating data
	private aPiece[][] prepare_board_tab() {

		aPiece[][] tab = new aPiece[8][8];

		// PAWNS

		for (int i = 0; i < 8; i++) {
			tab[1][i] = new Pawn(m_p1, this, Colour.WHITE);
		}
		for (int i = 0; i < 8; i++) {
			tab[6][i] = new Pawn(m_p2, this, Colour.BLACK);
		}

		// EMPTY

		for (int i = 0; i < 8; i++) {
			for (int j = 2; j < 6; j++) {
				tab[j][i] = new Empty(this);
			}
		}
		Colour clr = null;
		Player p = null;

		// KNIGHTS
		clr = Colour.WHITE;
		p = m_p1;
		tab[0][1] = new Knight(p, this, clr);
		tab[0][6] = new Knight(p, this, clr);
		clr = Colour.BLACK;
		p = m_p2;
		tab[7][1] = new Knight(p, this, clr);
		tab[7][6] = new Knight(p, this, clr);

		// ROOKS
		clr = Colour.WHITE;
		p = m_p1;
		tab[0][0] = new Rook(p, this, clr);
		tab[0][7] = new Rook(p, this, clr);
		clr = Colour.BLACK;
		p = m_p2;
		tab[7][0] = new Rook(p, this, clr);
		tab[7][7] = new Rook(p, this, clr);
		// BISHOPS
		clr = Colour.WHITE;
		p = m_p1;
		tab[0][2] = new Bishop(p, this, clr);
		tab[0][5] = new Bishop(p, this, clr);
		clr = Colour.BLACK;
		p = m_p2;
		tab[7][2] = new Bishop(p, this, clr);
		tab[7][5] = new Bishop(p, this, clr);
		// KINGS&Queens
		clr = Colour.WHITE;
		p = m_p1;
		tab[0][3] = new King(p, this, clr);
		tab[0][4] = new Queen(p, this, clr);
		clr = Colour.BLACK;
		p = m_p2;
		tab[7][3] = new King(p, this, clr);
		tab[7][4] = new Queen(p, this, clr);

		return tab;
	}

	public aPiece[][] get_board_tab() {
		return board_tab;
	}

	public void setBoard_tab(aPiece board_tab[][]) {
		this.board_tab = board_tab;
	}

	private void update_stats() {
		clear_stats();

		// occupancy
		boolean white_king_searched = true, black_king_searched = true;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((get_board_tab()[i][j] instanceof Empty)) {
					occupancy[i][j] = false;
				} else
					occupancy[i][j] = true;
			}
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (get_board_tab()[i][j].get_colour() == Colour.WHITE)
					get_board_tab()[i][j].set_attacked_field(white_attacks, new Coord(i, j));
				if (get_board_tab()[i][j].get_colour() == Colour.BLACK)
					get_board_tab()[i][j].set_attacked_field(black_attacks, new Coord(i, j));
			}
		}

		// TODO: !!!przerzuciæ ustalanie pozycji króla do make_move, zmiana z
		// ka¿dym ruchem, nie przebieganie 2 razy ca³ej planszy!!!
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (white_king_searched) {
					if (get_board_tab()[i][j] instanceof King && get_board_tab()[i][j].get_colour() == Colour.WHITE) {
						white_kings_position = new Coord(i, j);
						white_king_searched = false;
					}
				}
				if (black_king_searched) {
					if (get_board_tab()[i][j] instanceof King && get_board_tab()[i][j].get_colour() == Colour.BLACK) {
						black_kings_position = new Coord(i, j);
						black_king_searched = false;

					}
				}
			}
		}
	}

	// Clearing data
	private void clear_tab() {

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				get_board_tab()[i][j] = new Empty(this);
				occupancy[i][j] = false;
				white_attacks[i][j] = false;
				black_attacks[i][j] = false;
			}
		}
	}

	private void clear_stats() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				white_attacks[i][j] = false;
				black_attacks[i][j] = false;
				occupancy[i][j] = false;
			}
		}
	}

	// Reading data in console
	public void read_pieces() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				if (get_board_tab()[i][j] instanceof Queen && get_board_tab()[i][j].get_colour() == Colour.BLACK) {
					System.out.print("Q" + " ");
				}
				if (get_board_tab()[i][j] instanceof King && get_board_tab()[i][j].get_colour() == Colour.BLACK) {
					System.out.print("K" + " ");
				}
				if (get_board_tab()[i][j] instanceof Pawn && get_board_tab()[i][j].get_colour() == Colour.BLACK) {
					System.out.print("P" + " ");
				}
				if (get_board_tab()[i][j] instanceof Bishop && get_board_tab()[i][j].get_colour() == Colour.BLACK) {
					System.out.print("B" + " ");
				}
				if (get_board_tab()[i][j] instanceof Rook && get_board_tab()[i][j].get_colour() == Colour.BLACK) {
					System.out.print("R" + " ");
				}

				if (get_board_tab()[i][j] instanceof Knight && get_board_tab()[i][j].get_colour() == Colour.BLACK) {
					System.out.print("X" + " ");
				}

				//// czorny

				if (get_board_tab()[i][j] instanceof Queen && get_board_tab()[i][j].get_colour() == Colour.WHITE) {
					System.out.print("q" + " ");
				}
				if (get_board_tab()[i][j] instanceof King && get_board_tab()[i][j].get_colour() == Colour.WHITE) {
					System.out.print("k" + " ");
				}
				if (get_board_tab()[i][j] instanceof Pawn && get_board_tab()[i][j].get_colour() == Colour.WHITE) {
					System.out.print("p" + " ");
				}
				if (get_board_tab()[i][j] instanceof Bishop && get_board_tab()[i][j].get_colour() == Colour.WHITE) {
					System.out.print("b" + " ");
				}
				if (get_board_tab()[i][j] instanceof Rook && get_board_tab()[i][j].get_colour() == Colour.WHITE) {
					System.out.print("r" + " ");
				}
				if (get_board_tab()[i][j] instanceof Empty) {
					System.out.print("-" + " ");
				}
				if (get_board_tab()[i][j] instanceof Knight && get_board_tab()[i][j].get_colour() == Colour.WHITE) {
					System.out.print("x" + " ");
				}

			}
			System.out.println("");
		}
		
	}

	public void read_occupancy() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (occupancy[i][j])
					System.out.print("1" + " ");
				else
					System.out.print("0" + " ");

			}
			System.out.println(" ");

		}
	}

	public void read_white_attacks() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (white_attacks[i][j])
					System.out.print("1" + " ");
				else
					System.out.print("o" + " ");
			}
			System.out.println(" ");
		}
	}

	public void read_black_attacks() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (black_attacks[i][j])
					System.out.print("1" + " ");
				else
					System.out.print("o" + " ");
			}
			System.out.println(" ");
		}
	}

	public void read_colours() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (get_board_tab()[i][j].get_colour() == Colour.BLACK) {
					System.out.print("B" + " ");
				}
				if (get_board_tab()[i][j].get_colour() == Colour.WHITE) {
					System.out.print("W" + " ");
				}
				if (get_board_tab()[i][j].get_colour() == Colour.EMPTY) {
					System.out.print("-" + " ");
				}
			}
			System.out.println(" ");
		}
	}

	public void read_array_of_moves(List<Move> legal) {
		for (int i = 0; i < legal.size(); i++) {
			legal.get(i).read_move();
		}
	};

	// MADE FOR TESTS
	public void test_rook(Player p) {

		clear_tab();
		get_board_tab()[4][4] = new Rook(p, this, Colour.BLACK);
		get_board_tab()[5][4] = new Pawn(p, this, Colour.BLACK);
		get_board_tab()[4][5] = new Pawn(p, this, Colour.BLACK);
		get_board_tab()[3][4] = new Rook(p, this, Colour.WHITE);
		get_board_tab()[4][3] = new Rook(p, this, Colour.WHITE);
		update_stats();

	}

	public void test_bishop(Player p) {

		clear_tab();
		get_board_tab()[0][0] = new Bishop(p, this, Colour.BLACK);
		get_board_tab()[5][5] = new Bishop(p, this, Colour.WHITE);
		update_stats();
		System.out.println("biale: ");
		read_white_attacks();
		System.out.println("czarne: ");
		read_black_attacks();
	}

	public void test_queen(Player p) {

		clear_tab();
		get_board_tab()[0][0] = new Queen(p, this, Colour.BLACK);
		get_board_tab()[5][5] = new Queen(p, this, Colour.WHITE);
		update_stats();
		System.out.println("biale: ");
		read_white_attacks();
		System.out.println("czarne: ");
		read_black_attacks();
	}

	public void test_knight(Player p) {

		clear_tab();
		get_board_tab()[2][7] = new Knight(p, this, Colour.WHITE);

		update_stats();
		System.out.println("biale: ");
		read_white_attacks();
		System.out.println("czarne: ");
		read_black_attacks();
	}

	public void test_pawn(Player p) {

		clear_tab();
		board_tab[3][3] = new Pawn(p, this, Colour.WHITE);
		board_tab[4][4] = new Pawn(p, this, Colour.BLACK);

		update_stats();

	}

	public void test_ep(Player p1, Player p2) {
		clear_tab();
		get_board_tab()[7][7] = new Rook(p2, this, Colour.BLACK);
		get_board_tab()[7][0] = new Rook(p2, this, Colour.BLACK);
		get_board_tab()[7][3] = new King(p2, this, Colour.BLACK);

		get_board_tab()[0][7] = new Rook(p1, this, Colour.WHITE);
		get_board_tab()[0][0] = new Rook(p1, this, Colour.WHITE);
		get_board_tab()[0][3] = new King(p1, this, Colour.WHITE);
	}

	public void test_king(Player p) {

		clear_tab();
		get_board_tab()[7][7] = new King(p, this, Colour.WHITE);
		get_board_tab()[7][0] = new King(p, this, Colour.WHITE);
		get_board_tab()[0][0] = new King(p, this, Colour.WHITE);
		get_board_tab()[0][7] = new King(p, this, Colour.WHITE);
		get_board_tab()[3][4] = new King(p, this, Colour.WHITE);

		update_stats();
		System.out.println("biale: ");
		read_white_attacks();
		System.out.println("czarne: ");
		read_black_attacks();
	}

	public void test_copy(Player p) {
		clear_tab();
		get_board_tab()[7][7] = new King(p, this, Colour.WHITE);
		get_board_tab()[3][3] = new Pawn(p, this, Colour.WHITE);

		Move move = new Move();

		copy_end_and_start_pawn(move);

		get_board_tab()[0][0] = copy_of_piece_begin;
		get_board_tab()[0][1] = copy_of_piece_end;

		update_stats();
		System.out.println("biale: ");
		read_white_attacks();
		System.out.println("czarne: ");
		read_black_attacks();
	}

	public void test_main_move(Player p1, Player p2) {
		clear_tab();
		get_board_tab()[7][7] = new King(p1, this, Colour.WHITE);
		get_board_tab()[3][3] = new Pawn(p1, this, Colour.WHITE);
		get_board_tab()[2][2] = new Queen(p2, this, Colour.BLACK);
	}

	public void test_enpassant(Player p1, Player p2) {
		clear_tab();
		get_board_tab()[6][1] = new Pawn(p1, this, Colour.BLACK);
		get_board_tab()[6][3] = new Pawn(p1, this, Colour.BLACK);

		get_board_tab()[4][2] = new Pawn(p1, this, Colour.WHITE);

		get_board_tab()[5][5] = new King(p1, this, Colour.WHITE);
		get_board_tab()[3][5] = new King(p2, this, Colour.BLACK);

	}

}
