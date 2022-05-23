package battleship;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
	protected ArrayList<Coordinate> board;
	public static Scanner scn = new Scanner(System.in);
	public static final int BOARD_SIZE = 10;
	
	public Player() {
		initBoard();
	}
	
	public static String charToWord(char c) {
		String x = ""+c;
		x = x.toLowerCase();
		if(x=="a") return "Aircraft Carrier";
		else if(x=="b") return "Battleship";
		else if(x=="s") return "Submarine";
		else if(x=="d") return "Destroyer";
		else if(x=="p") return "Patrol Boat";
		else if(x=="h") return "Hit";
		else if(x=="m") return "Miss";
		else return "Empty";
	}
	
	public void initBoard() {
		board = new ArrayList<Coordinate>();
		for(int i=0; i<BOARD_SIZE; i++) {
			for(int j=0; j<BOARD_SIZE; j++) {
				board.add(new Coordinate(i, j, '~'));
			}
		}
	}
	
	public Coordinate getCoordinate(int x, int y) {
		for(Coordinate c: board) {
			if(c.getX()==x && c.getY()==y) {
				return c;
			}
		}
		return board.get(0);
	}
	
	public ArrayList<Coordinate> getBoard(){
		return board;
	}
	
	public void printShipBoard() {
		String b = "";
		b += "-";
		for(int i=0; i<BOARD_SIZE; i++) {
			b += "----";
		}
		b += "\n";
		for(int i=0; i<BOARD_SIZE; i++) {
			b += "|";
			for(int j=0; j < BOARD_SIZE; j++) {
				b += (" " + getCoordinate(i, j).getShipType() + " |");
			}
			b += "\n";
		}
		b += "-";
		for(int i=0; i<BOARD_SIZE; i++) {
			b += "----";
		}
		System.out.println(b);
	}
	
	public void printHitBoard() {
		String b = "";
		b += "-";
		for(int i=0; i<BOARD_SIZE; i++) {
			b += "----";
		}
		b += "\n";
		for(int i=0; i<BOARD_SIZE; i++) {
			b += "|";
			for(int j=0; j < BOARD_SIZE; j++) {
				b += (" " + getCoordinate(i, j).getHitStatus() + " |");
			}
			b += "\n";
		}
		b += "-";
		for(int i=0; i<BOARD_SIZE; i++) {
			b += "----";
		}
		System.out.println(b);
	}
	
	public char fireUpon(int x, int y) {
		Coordinate c = getCoordinate(x, y);
		char shipType = c.getShipType();
		if(shipType != '~') {
			c.setShipType('~');
			c.setHitStatus('H');
			return shipType;
		}
		else {
			c.setHitStatus('M');
			return 'M';
		}
	}
	
	public ArrayList<Coordinate> getShipCoords(int x, int y, char ship, char orientation){
		ArrayList<Coordinate> a = new ArrayList<Coordinate>();
		int len;
		if(ship=='a') len=5;
		else if(ship=='b') len=4;
		else if(ship=='s') len=3;
		else if(ship=='d') len=3;
		else if(ship=='p') len=2;
		else len=3;
		if(orientation=='v') {
			for(int i=y; i<len+y; i++) {
				if(i>=BOARD_SIZE) break;
				else a.add(getCoordinate(x, i));
			}
		}
		
		else if(orientation=='h') {
			for(int i=x; i<len+x; i++) {
				if(i>=BOARD_SIZE) break;
				else a.add(getCoordinate(i, y));
			}
		}
		
		return a;
	}
	
	public boolean validShipPlacement(int x, int y, char ship, char orientation) {
		ArrayList<Coordinate> list = getShipCoords(x, y, ship, orientation);
		for(Coordinate c: list) {
			if(c.getShipType() != '~') return false;
		}
		return true;
	}
	
	public boolean hasLost() {
		return false;
	}
	
	public void setupShips() {
		
	}
	
	public void fire() {
		
	}
}
