package battleship;

import java.util.ArrayList;

public class AIPlayer extends Player{
	private ArrayList<Coordinate> guesses;
	
	public AIPlayer() {
		super();
		initBoard();
	}
	
	public void setupShips() {
		//picks ship locations at random
		//define objects
		ArrayList<Coordinate> list = new ArrayList<Coordinate>();
		char[] ships = {'a','b','s','d','p'};
		char[] orientations = {'h', 'v'};
		char orientation;
		char shipType;
		int x, y;
		//guesses is set to null so that we know it's empty and we can set it to contain the enemy's board on the first turn
		guesses = null;
		//action
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
		//AI method to decide where to fire
		//define objects
		int choice=0, len, i=0, borderingCount;
		//Each location on the enemy board is given a score, depending on how good of a choice it is to fire at. The AI chooses the highest score unless the highest is less than zero,
		//in which case it chooses a random available space
		int[] choiceScores;
		Coordinate c;
		ArrayList<Coordinate> hits = new ArrayList<Coordinate>();
		ArrayList<Coordinate> adjascent = new ArrayList<Coordinate>();
		ArrayList<Coordinate> adjascent2 = new ArrayList<Coordinate>();
		//action
		//note: guesses only contains board locations that have not yet been fired at
		if(guesses==null) {
			guesses = new ArrayList<Coordinate>();
			for(int j=0; j<e.board.size(); j++) {
				guesses.add(e.board.get(j));
			}
		}
		len=guesses.size();
		//choiceScores contains all of the spaces on the enemy board, but already fired-at spaces have a -1000 score and so will never be picked
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
				//check if tmp is next to any spaces that are confirmed hits- if it's next to a miss a -5 score modifier is added
				if(tmp2.getHitStatus()=='m') score-=5;
				else if(tmp2.getHitStatus()=='h') {
					score += 10;
					adjascent2.add(e.getCoordinate(x2-1, y2));
					adjascent2.add(e.getCoordinate(x2, y2-1));
					adjascent2.add(e.getCoordinate(x2+1, y2));
					adjascent2.add(e.getCoordinate(x2, y2+1));
					for(Coordinate tmp3:adjascent2) {
						//If tmp2 already borders 2 or more known hits, that would mean that hitting tmp would be going for the wrong orientation. deduct 20 from score
						if(tmp3.getHitStatus()=='h') borderingCount++;
					}
					if(borderingCount>=2) score-=20;
					borderingCount = 0;
				}
			}
			choiceScores[i]=score;
			i++;
		}
		for(int j=0; j<choiceScores.length; j++) {
			if(choiceScores[j]>choiceScores[choice]) choice=j;
		}
		if(choiceScores[choice]<=0) c=guesses.get((int)Math.floor(Math.random()*guesses.size()));
		else c = e.board.get(choice);
		System.out.println("The AI has chosen to fire at (" + c.getX() + ", " + c.getY() + ").");
		e.fireUpon(c.getX(), c.getY());
		guesses.remove(c);
	}
}
