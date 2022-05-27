package battleship;

public class Game {
	public static void main(String[] args) {
		//AIPlayer test = new AIPlayer();
		//test.setupShips();
		//test.printShipBoard();
		Player p1=new Player(), p2;
		String[] playerTypes = {"Human Player", "AI Player"};
		SelectMenu playerTypeMenu = new SelectMenu("Should the second player be AI or Human?", playerTypes);
		int playerType;
		
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
		String[] yesNo = {"Yes", "No"};
		SelectMenu playAgainMenu = new SelectMenu("Play again?", yesNo);
		int playAgain;
		p1.setupShips();
		p2.setupShips();
		while((!p1.hasLost())&&(!p2.hasLost())) {
			System.out.println("It is now player 1's turn!\n\n");
			p1.fire(p2);
			if(p2.hasLost()) break;
			p2.fire(p1);
		}
		if(p1.hasLost()) {
			System.out.println("Player 1 lost, and Player 2 won.");
		}
		else if(p2.hasLost()) {
			System.out.println("Player 2 lost, and player 1 won.");
		}
		playAgain = playAgainMenu.display();
		if(playAgain==0) {
			play(p1, p2);
		}
		else {
			return;
		}
	}
}
