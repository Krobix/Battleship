package battleship;

import java.util.ArrayList;

public class AIPlayer extends Player{
	private ArrayList<Coordinate> guesses;
	
	public AIPlayer() {
		super();
		initBoard();
	}
	
	public void setupShips() {
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();
		char[] ships = {'a','b','s','d','p'};
		char[] orientations = {'h', 'v'};
		char orientation;
		char shipType;
		int x, y;
		guesses = null;
		
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
		int choice=0, len, i=0, borderingCount;
		int[] choiceScores;
		Coordinate c;
		ArrayList<Coordinate> hits = new ArrayList<Coordinate>();
		ArrayList<Coordinate> adjascent = new ArrayList<Coordinate>();
		ArrayList<Coordinate> adjascent2 = new ArrayList<Coordinate>();
		
		if(guesses==null) {
			guesses = new ArrayList<Coordinate>();
			for(int j=0; j<e.board.size(); j++) {
				guesses.add(e.board.get(j));
			}
		}
		len=guesses.size();
		choiceScores= new int[e.board.size()];
		for(Coordinate tmp:e.board) {
			if(tmp.getHitStatus()=='h') hits.add(tmp);
		}
		for(Coordinate tmp:e.board) {
			int score=0, x=tmp.getX(), y=tmp.getY(), x2, y2;
			borderingCount = 0;
			adjascent.add(e.getCoordinate(x-1, y));
			adjascent.add(e.getCoordinate(x, y-1));
			adjascent.add(e.getCoordinate(x+1, y));
			adjascent.add(e.getCoordinate(x, y+1));
			if(tmp.getHitStatus()!='~') score-=1000;
			for(Coordinate tmp2:adjascent) {
				x2 = tmp2.getX();
				y2 = tmp2.getY();
				if(tmp2.getHitStatus()=='m') score-=5;
				else if(tmp2.getHitStatus()=='h') {
					score += 10;
					adjascent2.add(e.getCoordinate(x2-1, y2));
					adjascent2.add(e.getCoordinate(x2, y2-1));
					adjascent2.add(e.getCoordinate(x2+1, y2));
					adjascent2.add(e.getCoordinate(x2, y2+1));
					for(Coordinate tmp3:adjascent2) {
						if(tmp3.getHitStatus()=='h') borderingCount++;
					}
					if(borderingCount>=2) score-=20;
				}
			}
			choiceScores[i]=score;
			i++;
		}
		for(int j=0; j<choiceScores.length; j++) {
			if(choiceScores[j]>choiceScores[choice]) choice=j;
		}
		if(choice==0) c=guesses.get((int)Math.floor(Math.random()*guesses.size()));
		else c = guesses.get(choice);
		System.out.println("The AI has chosen to fire at (" + c.getX() + ", " + c.getY() + ").");
		e.fireUpon(c.getX(), c.getY());
		guesses.remove(c);
	}
}
