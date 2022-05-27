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
		String x = String.valueOf(c);
		x = x.toLowerCase();
		if(x.equals("a")) return "Aircraft Carrier";
		else if(x.equals("b")) return "Battleship";
		else if(x.equals("s")) return "Submarine";
		else if(x.equals("d")) return "Destroyer";
		else if(x.equals("p")) return "Patrol Boat";
		else if(x.equals("h")) return "Hit";
		else if(x.equals("m")) return "Miss";
		else return x;
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
		String b = "   ";
		for(int i=0; i<BOARD_SIZE; i++) {
			b += " " + (i/10) + (i-(10*(i/10))) + " ";
		}
		b += "\n  -";
		for(int i=0; i<BOARD_SIZE; i++) {
			b += "----";
		}
		b += "\n";
		for(int i=0; i<BOARD_SIZE; i++) {
			b += i;
			b += " |";
			for(int j=0; j < BOARD_SIZE; j++) {
				b += (" " + getCoordinate(j, i).getShipType() + " |");
			}
			b += "\n";
		}
		b += "  -";
		for(int i=0; i<BOARD_SIZE; i++) {
			b += "----";
		}
		System.out.println(b);
	}
	
	public void printHitBoard() {
		String b = "   ";
		for(int i=0; i<BOARD_SIZE; i++) {
			b += " " + (i/10) + (i-(10*(i/10))) + " ";
		}
		b += "\n  -";
		for(int i=0; i<BOARD_SIZE; i++) {
			b += "----";
		}
		b += "\n";
		for(int i=0; i<BOARD_SIZE; i++) {
			b += i;
			b += " |";
			for(int j=0; j < BOARD_SIZE; j++) {
				b += (" " + getCoordinate(j, i).getHitStatus() + " |");
			}
			b += "\n";
		}
		b += "  -";
		for(int i=0; i<BOARD_SIZE; i++) {
			b += "----";
		}
		System.out.println(b);
	}
	
	public char fireUpon(int x, int y) {
		Coordinate c = getCoordinate(x, y);
		boolean sunk = true;
		char shipType = c.getShipType();
		if(shipType != '~') {
			System.out.println("It's a hit!");
			c.setHitStatus('h');
			for(Coordinate d:board) {
				if(d.getShipType()==c.getShipType()&&d.getHitStatus()=='m') {
					sunk = false;
					break;
				}
			}
			if(sunk) {
				System.out.println("You sunk my " + charToWord(c.getShipType()) + "!");
			}
			c.setShipType('~');
			return shipType;
		}
		else {
			System.out.println("it's a miss!");
			c.setHitStatus('m');
			return 'm';
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
		for(Coordinate c:board) {
			if(!(c.getHitStatus()=='m')&&!(c.getShipType()=='~')) {
				return false;
			}
		}
		return true;
	}
	
	public void setupShips() {
		int x, y, orientationNum, isCorrect;
		char orientation, shipType;
		boolean valid;
		String[] orientations = {"Vertical", "Horizontal"};
		String[] yesNo = {"Yes", "No"};
		char[] shipTypes = {'a', 'b', 's', 'd', 'p'};
		int[] lengths = {5, 4, 3, 3, 2};
		SelectMenu orientationMenu = new SelectMenu("Finally, choose the ship's orientation:", orientations);
		SelectMenu isCorrectMenu = new SelectMenu("This is your board. Is this correct?", yesNo);
		ArrayList<Coordinate> coords;
		
		initBoard();
		System.out.println("Now setup your ships. Note that the board is a " + BOARD_SIZE + " by " + BOARD_SIZE + 
				" grid, with both the x and y axes starting at 0 and ending at " + (BOARD_SIZE-1));
		System.out.println("Here is your empty board.");
		printShipBoard();
		for(int i=0; i<5; i++) {
			shipType = shipTypes[i];
			System.out.println("Place your " +charToWord(shipType) + ". Note that its length is " + lengths[i] + ".");
			System.out.println("Enter its x:");
			x = scn.nextInt();
			System.out.println("Enter its y:");
			y = scn.nextInt();
			orientationNum = orientationMenu.display();
			if(orientationNum==0) orientation='v';
			else orientation='h';
			valid = validShipPlacement(x, y, shipType, orientation);
			if(!valid) {
				System.out.println("That ship placement is not valid! As a reminder, ships can't be overlapping. Here's what your board looks like:");
				printShipBoard();
				i--;
			}
			else {
				coords = getShipCoords(x, y, shipType, orientation);
				for(Coordinate c:coords) {
					c.setShipType(shipTypes[i]);
					c.setHitStatus('~');
				}
				printShipBoard();
			}
		}
		printShipBoard();
		isCorrect = isCorrectMenu.display();
		if(isCorrect==1) {
			System.out.println("Understood. Ship setup will now start over.");
			setupShips();
		}
	}
	
	public void fire(Player e) {
		int x, y;
		
		System.out.println("It is now the next player's turn to fire!");
		System.out.println("Here is a map of your previous shots on the enemy's board: h represents a hit, and m represents a miss.");
		e.printHitBoard();
		System.out.println("Enter the x coordinate of where you want to hit:");
		x = scn.nextInt();
		System.out.println("Enter the y coordinate of where you want to hit:");
		y = scn.nextInt();
		e.fireUpon(x, y);
	}
}
