import java.util.*;

public class Player {

	private String name;

	private LGStats[] counters;

	private Letter[] hand; // the tiles they own

	// constructor
	public Player(String name) {
		// initial letters and their value
		// group them
		this.name = name;

		counters = new LGStats[4]; 
		counters[0] = new LGStats("S");
		counters[1] = new LGStats("HV");
		counters[2] = new LGStats("V");
		counters[3] = new LGStats("C");

		hand = new Letter[7]; 
	}

	public String getName(){
		return name;
	}

	public void playLetters(Letter[] letters_played){
		for (Letter l : letters_played){
			for (int m = 0; m < hand.length; m++){
				Letter j = hand[m];
				if (j.getCharacter()==l.getCharacter()){
					// remove them from hand
					hand[m] = null;
				}
			}
		}
	}


	public Letter[] refillHand(int num_letters_needed){
		// depending on how many empty spaces there were, restock
		Letter[] new_letters = new Letter[num_letters_needed];
		for (int i = 0; i < num_letters_needed; i++){
			new_letters[i] = takeLetter();
			
			// put it in a null hand spot 
			for (int h = 0; h < hand.length; h++){
				if (hand[h]==null){
					// remove them from hand
					hand[h] = new_letters[i];
					break;
				}
			}
		}

		return new_letters;

	}

	public Letter takeLetter(){
		// find the highest category with availability 
		// counters is already sorted. (TODO: edgecase in beginning when need to choose random)

		// get a letter from that category in the bag 
		Letter newLetter = null;

		// add 20% random choice
		double rand = (new Random()).nextDouble();
		if (rand < 0.2){
			newLetter = Bag.getRandomLetterBag();
			// System.out.println("Random letter!");
		}
		else {
			for (LGStats lg : counters) {
				// check there are letters left
				newLetter = Bag.getRandomLetterFromCat(lg.name);
				if (newLetter!=null){
					lg.raw_counter++;
					break;
				}
			}
		}
		// add to the appropriate stats 

		// recalculate the ratios
		ratios(); // once a new letter is taken, recalc. ratios.

		// return the letter (null if there are no letters in the bag)
		// System.out.println("Letter taken: " + newLetter.getCharacter());
		return newLetter;
	}



	public Letter[] chuckLetters() {
		return null;
	}

	public void ratios(){
		
		// calculate each groups ratio
		int sum = 0;
		for (LGStats cat : counters) {
			sum += cat.raw_counter;
		}
		for (LGStats cat : counters){
			if (cat.raw_counter==0) cat.ratio = 0;
			else cat.ratio = cat.raw_counter / (double)(sum - cat.raw_counter);

			// TODO: the heuristic is actually the difference from the dream ratio
			cat.heuristic = Bag.getTrueRatio(cat.name) - cat.ratio;
		}

		// sort the counters by their ratios
		Arrays.sort(counters);
		// for (LGStats lg : counters){
		// 	lg.print();
		// }

	}

	public void printHand(){
		String hand_str = "";
		for (Letter l : hand) { hand_str += l.getCharacter() +  "   "; }
		System.out.println(name + "\'s hand is: " + hand_str);
	}


	class LGStats implements Comparable<LGStats>{
		String name;
		int raw_counter;
		double ratio;
		double heuristic;
		public LGStats(String name){
			this.name = name;
			raw_counter = 0;
			ratio = 0; // potentially should be -1? 
			heuristic = 0;
		}

		public void print(){
			System.out.println("Group: " + name 
				+ ", raw count: " + raw_counter
				+ ". Ratio: " + ratio
				+ ", Heuristic: " + heuristic);
		}

		@Override
		public int compareTo(LGStats o){
			// if o is less than this, return -1
			if (this.heuristic > o.heuristic) {return -1;}
			// if the heuristic is the same. need to choose random. 
			else if (this.heuristic == o.heuristic) {
				LetterGroup this_lg = Bag.getLG(this.name);
				LetterGroup other_lg = Bag.getLG(o.name);
				double this_prob = this_lg.count_remaining_letters();
				double other_prob = other_lg.count_remaining_letters();

				this_prob = this_prob/(this_prob + other_prob);
				other_prob = other_prob/(this_prob  + other_prob);

				double rand = (new Random()).nextDouble();

				System.out.println("Group " + this_lg.group_name + ": prob=" + this_prob
					+ ". Group " + other_lg.group_name + ": prob=" + other_prob
					+". Rand = " + rand );

				if (rand > this_prob) { return  -1;}

				return 1;
			}
			else {return 1;}
		}
	}

}