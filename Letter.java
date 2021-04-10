public class Letter {
	
	private char character;
	private int value;
	private String category; // S, HV, V, C
	private int initQuantity;
	private int remainingQuantity;

	// constructor
	public Letter(char character, int value, String category, int initQuant) {
		// initial letters and their value
		// group them
		this.character = character;
		this.value = value;
		this.category = category;
		this.initQuantity = initQuant;
		this.remainingQuantity = initQuantity;
	}



	// GET METHODS 

	public char getCharacter(){
		return this.character;
	}

	public int getValue(){
		return this.value;
	}

	public String getCategory(){
		return this.category;
	}

	public int getInitQuantity(){
		return this.initQuantity;
	}

	public int getRemainingQuantity(){
		return this.remainingQuantity;
	}

	public void deductRemaining1(){
		this.remainingQuantity = this.remainingQuantity-1;
	}

// OTHER UTILITY METHODS

	public void printLetter(){
		System.out.println("Letter: " + character + 
			", value: " + value + ", category: "  + category
			+ ", initial Quantity: " + initQuantity 
			+", remaining: " + remainingQuantity);
	}





}