package battleship;

import java.util.ArrayList;

public class AIPlayer extends Player{
	private ArrayList<Coordinate> guesses;
	
	public AIPlayer() {
		super();
		initBoard();
		guesses = new ArrayList<Coordinate>();
		for(int i=0; i<board.size(); i++) {
			guesses.add(board.get(i));
		}
	}
	
	public void setupShips() {
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();
		char[] ships = {'a','b','s','d','p'};
		char[] orientations = {'h', 'v'};
		char orientation;
		char shipType;
		int x, y;
		for(int i=0; i<5; i++) {
			shipType = ships[i];
			orientation = orientations[(int)Math.floor(Math.random()*2)];
			x = (int)Math.floor(Math.random()*BOARD_SIZE);
			y = (int)Math.floor(Math.random()*BOARD_SIZE);
			while(!validShipPlacement(x, y, shipType, orientation)) {
				orientation = orientations[(int)Math.floor(Math.random()*2)];
				x = (int)Math.floor(Math.random()*BOARD_SIZE);
				y = (int)Math.floor(Math.random()*BOARD_SIZE);
			}
			list = getShipCoords(x, y, shipType, orientation);
			for(Coordinate c:list) {
				c.setShipType(shipType);
				c.setHitStatus('~');
			}
		}
		System.out.println("The AI has finished setting up its ships.");
	}
	
	public void fire(Player e) {
		int choice, len=guesses.size();
		Coordinate c;
		choice = (int) Math.floor(Math.random()*len);
		c = guesses.get(choice);
		System.out.println("The AI has chosen to fire at (" + c.getX() + ", " + c.getY() + ").");
		e.fireUpon(c.getX(), c.getY());
		guesses.remove(choice);
	}
}
