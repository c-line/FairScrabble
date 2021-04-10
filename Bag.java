import java.util.*;

public class Bag {

	private static LetterGroup[] letterGroups = new LetterGroup[4];
	public static Boolean bagEmpty;

	// constructor
	public static void initBag() {
		// initial letters and their value
		// group them

		letterGroups[0] = S();
		letterGroups[1] = HighValue();
		letterGroups[2] = Vowels();
		letterGroups[3] = Consonants();
		calculateRatios();
		for (LetterGroup lg : letterGroups){
			lg.print();
		}
		bagEmpty = false;
	}

	public static Letter getRandomLetterFromCat(String category){
		if (bagEmpty) return null;
		// get the right letter group from the letter group array
		LetterGroup lg = getLG(category);
		// get a random letter from that letter group
		Letter chosen = lg.getRandom();
		return chosen;

	}

	public static Letter getRandomLetterBag() {

		double[] cat_probs = new double[4];
		double bag_remaining = 0;
		for (int i=0; i< 4; i++){
			cat_probs[i] = letterGroups[i].count_remaining_letters();
			bag_remaining += cat_probs[i];
		}

		double rand = (new Random()).nextDouble();
		
		double prob_counter = 0;
		int chosen_cat;
		for (chosen_cat=0; chosen_cat< 4; chosen_cat++){
			prob_counter = prob_counter  + (cat_probs[chosen_cat]/bag_remaining);
			if (rand < prob_counter){
				break;
			}
		}

		return letterGroups[chosen_cat].getRandom();

	}

	public static void calculateRatios(){
		// for each group, find it's ratio
		int lg_sum = 0;
		for (LetterGroup lg : letterGroups){
			lg_sum += lg.count_remaining_letters();
		}
		for (LetterGroup lg : letterGroups){
			lg.true_ratio = lg.count_remaining_letters()/(double)(lg_sum - lg.count_remaining_letters());
		}
	}

	public static double getTrueRatio(String cat){
		LetterGroup lg = getLG(cat);
		return lg.true_ratio;
	}

	public static LetterGroup getLG(String cat){
		for (LetterGroup i : letterGroups){
			if (i.group_name == cat){
				return i;
			}
		}
		return null;
	}

	public static void printBag(){
		for (LetterGroup lg : letterGroups){
			lg.print();
		}
	}
	

	public static LetterGroup S(){
		LetterGroup esses = new LetterGroup("S");
		esses.letters = new HashMap<Character, Letter>();
		esses.letters.put('S', new Letter('S', 1, "S", 4));

		esses.remainingLettersCount = esses.count_init_letters();
		System.out.println("Initialised S\'s with count: " + esses.remainingLettersCount);
		return esses;

	}

	public static LetterGroup Vowels(){
		LetterGroup single_vowels = new LetterGroup("V");
		single_vowels.letters = new HashMap<Character, Letter>();
		single_vowels.letters.put('A', new Letter('A', 1, "V", 9));
		single_vowels.letters.put('E', new Letter('E', 1, "V", 9));
		single_vowels.letters.put('I', new Letter('I', 1, "V", 9));
		single_vowels.letters.put('O', new Letter('O', 1, "V", 5));
		single_vowels.letters.put('U', new Letter('U', 1, "V", 4));

		single_vowels.remainingLettersCount = single_vowels.count_init_letters();
		System.out.println("Initialised V\'s with count: " + single_vowels.remainingLettersCount);
		return single_vowels;

	}


	public static LetterGroup HighValue(){
		LetterGroup high_value = new LetterGroup("HV");
		high_value.letters = new HashMap<Character, Letter>();
		high_value.letters.put('F', new Letter('F', 4, "HV", 2));
		high_value.letters.put('H', new Letter('H', 4, "HV", 2));
		high_value.letters.put('J', new Letter('J', 8, "HV", 1));
		high_value.letters.put('K', new Letter('K', 5, "HV", 1));
		high_value.letters.put('Q', new Letter('Q', 10, "HV", 1));
		high_value.letters.put('V', new Letter('V', 4, "HV", 1));
		high_value.letters.put('W', new Letter('W', 4, "HV", 1));
		high_value.letters.put('X', new Letter('X', 8, "HV", 1));
		high_value.letters.put('Z', new Letter('Z', 10, "HV", 1));

		high_value.remainingLettersCount = high_value.count_init_letters();

		System.out.println("Initialised HV\'s with count: " + high_value.remainingLettersCount);
		return high_value;
	}

	public static LetterGroup Consonants(){
		LetterGroup consonants = new LetterGroup("C");
		consonants.letters = new HashMap<Character, Letter>();
		consonants.letters.put('B', new Letter('B', 3, "C", 2));
		consonants.letters.put('C', new Letter('C', 3, "C", 2));
		consonants.letters.put('D', new Letter('D', 2, "C", 4));
		consonants.letters.put('G', new Letter('G', 3, "C", 2));
		consonants.letters.put('L', new Letter('L', 1, "C", 4));
		consonants.letters.put('M', new Letter('M', 3, "C", 2));
		consonants.letters.put('N', new Letter('N', 1, "C", 4));
		consonants.letters.put('P', new Letter('P', 3, "C", 2));
		consonants.letters.put('R', new Letter('R', 1, "C", 4));
		consonants.letters.put('T', new Letter('T', 1, "C", 4));
		consonants.letters.put('Y', new Letter('Y', 2, "C", 2));

		consonants.remainingLettersCount = consonants.count_init_letters();

		System.out.println("Initialised C\'s with count: " + consonants.remainingLettersCount);
		return consonants;
	}


}

class LetterGroup {
	String group_name;
	HashMap<Character, Letter> letters;
	double true_ratio;
	int remainingLettersCount;


	public LetterGroup(String group_name){
		this.group_name = group_name;

	}

	public int count_init_letters(){
		int counter = 0;
		for (Character l : letters.keySet()){
			counter += letters.get(l).getInitQuantity();
		}
		return counter;
	}
	public int count_remaining_letters(){
		int counter = 0;
		for (Character l : letters.keySet()){
			counter += letters.get(l).getRemainingQuantity();
		}
		remainingLettersCount = counter;
		return remainingLettersCount;
	}

	public void print(){
		System.out.println("Letter Group: " + group_name + ". True Ratio: " + true_ratio);
		for (Character s : letters.keySet()){
			letters.get(s).printLetter();
		}
	}

	public Letter getRandom(){
		// make a pool of letters for random selection
		count_remaining_letters();
		if (remainingLettersCount == 0) return null;

		Letter[] remainingLetters = new Letter[remainingLettersCount];

		int i = 0;
		for (Character l : letters.keySet()){
			Letter temp = letters.get(l);
			for (int j = 0;j < temp.getRemainingQuantity(); j++){
				remainingLetters[i] = temp;
				i++;
			}
		}
		//shuffle the selection up
		Random rand = new Random();
		int rand_index = (int) (rand.nextDouble()*remainingLetters.length);
		Letter chosen = remainingLetters[rand_index];
		chosen.deductRemaining1();
		letters.put(chosen.getCharacter(), chosen);

		return chosen;

		// for (int i=0;i<remainingLetters.length;i++){
		// 	char temp = remainingLetters[i];
		// 	remainingLetters[i] = remainingLetters[rand.nextDouble()*remainingLetters.length];
		// }
		// System.out.println("Select random letter from: " + Arrays.tostring(remainingLetters));

	}
	
}