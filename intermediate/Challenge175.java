    import java.util.ArrayList;
    import java.util.HashMap;
    
    
    public class Challenge175 {
    
    	private HashMap<String, Integer> availableChars =
    			new HashMap<String, Integer>();
    	private ArrayList<String> wordList =
    			new ArrayList<String>();
    	private ArrayList<String> longestWords =
    			new ArrayList<String>();
    	
    	public Challenge175(String words, String characters) {
    		for (String word: words.split(" ")) {
    			wordList.add(word);
    		}
    		
    		for (String character: characters.split(" ")) {
    			if (availableChars.containsKey(character)) {
    				availableChars.put(character,
    						availableChars.get(character) + 1);
    			} else {
    				availableChars.put(character, 1);
    			}
    		}
    		
    		this.findLongestWord();
    	}
    	
    	public ArrayList<String> getLongestWords() {
    		return longestWords;
    	}
    	
    	public void findLongestWord() {
    		HashMap<String, Integer> availableChars = null;
    		int amountAvailable;
    		boolean creatable;
    		
    		for (String word: wordList) {
    			availableChars = new HashMap<String, Integer>(this.availableChars);
    			creatable = true;
    			
    			for(char c: word.toCharArray()) {
    				if (availableChars.containsKey(String.valueOf(c))) {
    					amountAvailable = availableChars.get(String.valueOf(c));
    				} else {
    					creatable = false;
    					break;
    				}
    				
    				if (amountAvailable-- > 0) {
    					availableChars.put(String.valueOf(c), amountAvailable);
    				} else {
    					creatable = false;
    					break;
    				}
    			}
    			
    			if (creatable) {
    				int lastWordLength;
    				
    				if (longestWords.isEmpty()) {
    					longestWords.add(word);
    				} else {
    					lastWordLength = 
    							longestWords.get(longestWords.size() - 1).length();
    					
    					if (lastWordLength < word.length()) {
    						longestWords.clear();
    						longestWords.add(word);
    					} else if (lastWordLength == word.length()) {
    						longestWords.add(word);
    					}
    				}
    			}
    		}
    	}
    	
    	public static void main(String[] args) {
    		
    		Challenge175 challenge =
    				new Challenge175(System.console().readLine(),
    						System.console().readLine());
    		
    		for (String word: challenge.getLongestWords()) {
    			System.out.print(word + " ");
    		}
    		System.out.println();
    	}
    
    }
