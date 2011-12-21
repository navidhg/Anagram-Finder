import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Scanner;

public class AnagramFinder {

	static HashMap<String, String> words = new HashMap<String, String>(300000);

	public static HashMap<String, String> getSortedDictionary() throws IOException {

		BufferedReader br = new BufferedReader(new FileReader("SINGLE.txt"));
		String input = "";

		while ((input = br.readLine()) != null) {

			//sort the string
			char[] inputChars = input.toCharArray();
			Arrays.sort(inputChars);
			String sortedInput = String.valueOf(inputChars);

			if (words.get(sortedInput) == null) {
				words.put(sortedInput, input);
			}

			else {
				String previousValue = words.get(sortedInput);
				words.put(sortedInput, previousValue + "; " + input);
			}
		}

		br.close();

		return words;
	}

	public static void printAnagrams(String s) {

		int length = s.length();
		int combsSize = (int) (Math.pow(2, (double)length)-1);

		//hash set to indicate if a combination has been tried before to prevent printing duplicates
		HashMap<String, String> tried = new HashMap<String, String>(300000);

		for (int j=0; j<combsSize; j++) {

			String mask = Integer.toBinaryString(j+1);
			String buffer = "";

			int maskLength = mask.length();

			for (int k=maskLength-1; k>=0; k--) {

				if (mask.charAt(k)=='1') {
					int getChar = length - (maskLength - k);
					buffer = s.charAt(getChar) + buffer;
				}
			}
			//sort buffer
			char[] bufferChars = buffer.toCharArray();
			Arrays.sort(bufferChars);
			String sortedBuffer = String.valueOf(bufferChars);

			//if the combination has been tried before, skip to the next combination
			if (tried.get(sortedBuffer) != null) { }
			
			//print the anagram if it is found in the dictionary
			else {
				//add the tried combination to the hash set
				tried.put(sortedBuffer, "");
				String dictVal = "";
				if ((dictVal = words.get(sortedBuffer)) != null) {
					String[] dictValSplit = dictVal.split("; ");
					for (int m=0; m<dictValSplit.length; m++) {
						System.out.println(dictValSplit[m] +"\t("+dictValSplit[m].length()+")");
					}
				}
			}
		}
	}

	public static void main (String args[]) {

		//import dictionary
		long start = System.currentTimeMillis();
		try {words = getSortedDictionary(); }
		catch (IOException e) {e.printStackTrace(); }
		long finish = System.currentTimeMillis();

		System.out.println("Time taken to import dictionary: " + (finish-start) + " ms\n");

		Scanner input = new Scanner(System.in);

		while(true) {

			String word = "";
			System.out.print("Enter the word: ");
			word = input.nextLine();

			long start2 = System.currentTimeMillis();
			printAnagrams(word);
			long finish2 = System.currentTimeMillis();

			System.out.println("Time taken to generate anagrams: " + (finish2 - start2) + " ms");
			System.out.println("Length of word: " + word.length());
		}
	}
}
