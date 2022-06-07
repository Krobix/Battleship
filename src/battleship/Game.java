package battleship;

public class Game {
	public static void main(String[] args) {
		//AIPlayer test = new AIPlayer();
		//test.setupShips();
		//test.printShipBoard();
		//define objects
		Player p1=new Player(), p2;
		String[] playerTypes = {"Human Player", "AI Player"};
		SelectMenu playerTypeMenu = new SelectMenu("Should the second player be AI or Human?", playerTypes);
		int playerType;
		//action
		System.out.println("Welcome to battleship!");
		playerType = playerTypeMenu.display();
		if(playerType==0) {
			p2=new Player();
		}
		else {
			p2=new AIPlayer();
		}
		play(p1, p2);
	}
	
	public static void play(Player p1, Player p2) {
		//define objects
		String[] yesNo = {"Yes", "No"};
		SelectMenu playAgainMenu = new SelectMenu("Play again?", yesNo);
		int playAgain;
		//call setupShips for both players
		p1.setupShips();
		p2.setupShips();
		//Main game play loop
		while((!p1.hasLost())&&(!p2.hasLost())) {
			System.out.println("It is now player 1's turn!\n\n");
			p1.fire(p2);
			if(p2.hasLost()) break;
			System.out.println("It is now player 2's turn!\n\n");
			p2.fire(p1);
		}
		//either p1 or p2 has lost- check to see who lost
		if(p1.hasLost()) {
			System.out.println("Player 1 lost, and Player 2 won.");
		}
		else if(p2.hasLost()) {
			System.out.println("Player 2 lost, and player 1 won.");
		}
		//choose whether to play again or exit
		playAgain = playAgainMenu.display();
		if(playAgain==0) {
			play(p1, p2);
		}
		else {
			return;
		}
	}
}
