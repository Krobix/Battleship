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
		int choice=0, len=guesses.size(), i=0;
		int[] choiceScores = new int[guesses.size()];
		Coordinate c;
		ArrayList<Coordinate> hits = new ArrayList<Coordinate>();
		ArrayList<Coordinate> adjascent = new ArrayList<Coordinate>();
		ArrayList<Coordinate> adjascent2 = new ArrayList<Coordinate>();
		for(Coordinate tmp:e.board) {
			if(tmp.getHitStatus()=='h') hits.add(tmp);
		}
		for(Coordinate tmp:e.board) {
			int score=0, x=tmp.getX(), y=tmp.getY(), x2, y2;
			adjascent.add(e.getCoordinate(x-1, y));
			adjascent.add(e.getCoordinate(x, y-1));
			adjascent.add(e.getCoordinate(x+1, y));
			adjascent.add(e.getCoordinate(x, y+1));
			if(tmp.getHitStatus()!='~') score-=1000;
			for(Coordinate tmp2:adjascent) {
				if(tmp.getHitStatus()=='m') score-=5;
				else if(tmp.getHitStatus()=='h') {
					score += 10;
					
				}
			}
		}
		c = guesses.get(choice);
		System.out.println("The AI has chosen to fire at (" + c.getX() + ", " + c.getY() + ").");
		e.fireUpon(c.getX(), c.getY());
		guesses.remove(choice);
	}
}
