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
		//Used for "You sunk my ship" message and other similar messages
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
		//creates empty board that replaces the last one of size BOARD_SIZE
		board = new ArrayList<Coordinate>();
		for(int i=0; i<BOARD_SIZE; i++) {
			for(int j=0; j<BOARD_SIZE; j++) {
				board.add(new Coordinate(i, j, '~'));
			}
		}
	}
	
	public Coordinate getCoordinate(int x, int y) {
		//return the coordinate at x and y on the board. If the coordinate does not exist, return coordinate at (0, 0)
		for(Coordinate c: board) {
			if(c.getX()==x && c.getY()==y) {
				return c;
			}
		}
		return board.get(0);
	}
	
	public ArrayList<Coordinate> getBoard(){
		//returns the board ArrayList
		return board;
	}
	
	public void printShipBoard() {
		//prints the board, showing the letters for the ships
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
		//Prints the board, showing the letters for hit status
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
		//fire on this player's board at location (x, y)
		//define objects
		Coordinate c = getCoordinate(x, y);
		boolean sunk = true;
		char shipType = c.getShipType();
		//action
		//Check if a ship was hit or not
		if(shipType != '~') {
			System.out.println("It's a hit!");
			c.setHitStatus('h');
			for(Coordinate d:board) {
				//check if the whole ship was sunk
				if(d.getShipType()==c.getShipType()&&(d.getHitStatus()=='m'||d.getHitStatus()=='~')) {
					sunk = false;
					break;
				}
			}
			if(sunk) {
				System.out.println("You sunk my " + charToWord(c.getShipType()) + "!");
			}
			//remove the ship from the hit space
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
		//determines the coordinates of a ship based on x, y, ship type (which determines the length) and orientation (either 'h' or 'v'). does not actually create ship there.
		ArrayList<Coordinate> a = new ArrayList<Coordinate>();
		int len;
		//set len (the length of the ship) based on ship type
		if(ship=='a') len=5;
		else if(ship=='b') len=4;
		else if(ship=='s') len=3;
		else if(ship=='d') len=3;
		else if(ship=='p') len=2;
		else len=3;
		//loop through the ship coords and add them to ArrayList to be returned
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
		//using getShipCoords, check if a ship placement is valid or not (i.e. it doesn't overlap with existing ships)
		ArrayList<Coordinate> list = getShipCoords(x, y, ship, orientation);
		for(Coordinate c: list) {
			if(c.getShipType() != '~') return false;
		}
		return true;
	}
	
	public boolean hasLost() {
		//returns true if there are no more ships on the board
		for(Coordinate c:board) {
			if(!(c.getHitStatus()=='m')&&!(c.getShipType()=='~')) {
				return false;
			}
		}
		return true;
	}
	
	public void setupShips() {
		//uses user input to decide ship placement for player
		//define objects and variables
		int x, y, orientationNum, isCorrect;
		char orientation, shipType;
		boolean valid;
		//these two will be used for ChoiceMenus later
		String[] orientations = {"Vertical", "Horizontal"};
		String[] yesNo = {"Yes", "No"};
		//These are here so that instead of repeating the ship placement code we can just write a for loop, changing the variables for each ship
		char[] shipTypes = {'a', 'b', 's', 'd', 'p'};
		int[] lengths = {5, 4, 3, 3, 2};
		//SelectMenus
		SelectMenu orientationMenu = new SelectMenu("Finally, choose the ship's orientation:", orientations);
		SelectMenu isCorrectMenu = new SelectMenu("This is your board. Is this correct?", yesNo);
		ArrayList<Coordinate> coords;
		//action
		//init board again because otherwise we would have two sets of ships
		initBoard();
		System.out.println("Now setup your ships. Note that the board is a " + BOARD_SIZE + " by " + BOARD_SIZE + 
				" grid, with both the x and y axes starting at 0 and ending at " + (BOARD_SIZE-1));
		System.out.println("Here is your empty board.");
		printShipBoard();
		//for loop for convenience- large amount of input code doesnt need to be repeated
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
		//counterpart to fireUpon- allows player to choose which board space to fire at. Really it just accepts input of the x and y and passes that to e.fireUpon
		int x, y;
		
		System.out.println("It is now the next player's turn to fire!");
		System.out.println("Here's your board:");
		printShipBoard();
		System.out.println("And here is a map of your previous shots on the enemy's board: h represents a hit, and m represents a miss.");
		e.printHitBoard();
		System.out.println("Enter the x coordinate of where you want to hit:");
		x = scn.nextInt();
		System.out.println("Enter the y coordinate of where you want to hit:");
		y = scn.nextInt();
		e.fireUpon(x, y);
	}
}
