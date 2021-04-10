import java.util.*;

public class RunScrabble{
	
	public static void main(String[] args){
		System.out.println("new scrabble game!");

		// ------------------- The Players -------------------
		int num_players = 3;
		String[] player_names = {"Mum", "Dad", "Caroline"};
		Player[] players = new Player[num_players];
		
		for (int i=0; i<num_players; i++){
			players[i] = new Player(player_names[i]);
		}

		System.out.println("The players are: ");
		for (int i=0; i<num_players; i++){
			System.out.println(players[i].getName());
		}

		// ------------------- The Bag -------------------

		Bag.initBag();

		// initialise players hands
		for (Player p : players) {
			// p.refillHand();
			p.refillHand(7);
			p.printHand();
		}
		Boolean game_finished = false;
		while(!game_finished){
			for (Player p : players) { // TODO, order players as needed pre loop
				// p plays letters and enters into here. 
				System.out.println("\n\n\n***\nIt's  " + p.getName() + "\'s turn.");

				System.out.print("Enter the letters plaid (e.g H E L L O): ");  
				// sanity check that they had those letters. 

				Scanner sc = new Scanner(System.in);  
				String played = sc.nextLine(); 
				String[] played_arr  = played.split(" ");


				// the ratios should already be calculated.

				// refill the hand
				Letter[] new_letters = p.refillHand(played_arr.length);
				String new_letters_str = "";
				for (Letter l : new_letters) { new_letters_str += l.getCharacter() +  "   "; }

				// recalculate the player's ratios

				// let the player know what their new hand is 
				System.out.println("Your new letters are: " + new_letters_str );

				// button to click "end turn". maybe a 10 second time out as well. 
			}
			// game ends when the bag is empty and a player has used all their letters

		}







		// ------------------- To DOs-------------------
		// TODO: prompt user for game specifics 
		// how many players
		// ask for name of each player e.g Name player 1: // note: might need to determine first player by first letter out the bag
	}
}