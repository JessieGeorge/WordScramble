import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WordScramble {
	
	private String scrambledWord;
	
	private static Set<String> validWords = new HashSet<>();

	private static Set<String> dictionary = new HashSet<>();
	
    public void dictionary() throws IOException
    {
        Path path = Paths.get("dictionary.txt");
        byte[] readBytes = Files.readAllBytes(path);
        String wordListContents = new String(readBytes, "UTF-8");
        String[] words = wordListContents.split("\n");
        Collections.addAll(dictionary, words);
    }
    
    public static boolean checkValidWord(String word) throws IOException 
	{
		return dictionary.contains(word);
    }
    
	public void input()throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter scrambled word (should contain less than 11 characters, only alphabets)");
		scrambledWord = br.readLine();
	}
	
	public boolean validInput(String str)
	{
		if(str==null || str.equals(""))
		{
			System.out.println("Invalid input. Word should contain alphabets.");
			return false;
		}
		
		if(str.length()>=11)
		{
			System.out.println("Invalid input. Word is too long.");
			return false;
		}
			
	    for(int i=0; i<str.length(); i++)
	    {
	        if(!Character.isLetter(str.charAt(i))) 
	        {
	        	System.out.println("Invalid input. Word should contain only alphabets.");
	            return false;
	        }
	    }

	    return true;
		
	}
	
	public void permutation(String str)throws IOException
	{
		permutation("", str); //the prefix is empty until you start generating permutations
	}
	
	private void permutation(String prefix, String str)throws IOException
	{
		int len = str.length();
		if(len == 0)
		{
			
			if(checkValidWord(prefix))
			{
				//you have to check if the validWords set is empty before you call the contains method to prevent nullpointer. 
				//we use the contains method to prevent from printing the same word multiple times, when the scrambled word has duplicate letters (example: banana).
				if(validWords.isEmpty() || !validWords.contains(prefix))
				{
					validWords.add(prefix);
					System.out.println(prefix);
				}	
			}
			
		}
		else
		{
			for(int i=0; i<len; i++)
			{
				//prefix is the letter your choosing to start with
				//str.substring(0,i) is everything before that letter, which is "" if it's the first letter i.e. str.substring(0,0) is ""
				//str.substring(i+1) is everything after that letter
				//therefore str will become the permutation of everything except the letter you fixed
				permutation(prefix+str.charAt(i), str.substring(0,i)+str.substring(i+1));
			}
		}
	}
	
	
	
	public static void main(String args[])throws IOException
	{
		WordScramble WS = new WordScramble();
		WS.dictionary();
		WS.input();
		if(WS.validInput(WS.scrambledWord))
		{
			WS.permutation(WS.scrambledWord);
			
			if(validWords.isEmpty())
				System.out.println("No valid word found.");
		}
		
	}

}
