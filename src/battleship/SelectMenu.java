package battleship;

import java.util.Scanner;

public class SelectMenu {
	private String prompt;
	private String[] options;
	
	public SelectMenu(String p, String[] opts) {
		prompt = p;
		options = opts;
	}
	
	public int display() {
		Scanner scn = new Scanner(System.in);
		int c;
		System.out.println(prompt);
		for(int i=0; i<options.length; i++) {
			System.out.println("[" + i + "] " + options[i]);
		}
		System.out.println("Enter the number of your choice.");
		c = scn.nextInt();
		scn.close();
		return c;
	}
}
