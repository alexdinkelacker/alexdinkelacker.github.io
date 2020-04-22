package spellchecker;

import java.io.FileWriter;
import java.util.*;
import java.io.BufferedWriter;
import java.io.File;

import static sbcc.Core.*;
import static java.lang.System.*;
import static org.apache.commons.lang3.StringUtils.*;

public class BasicDictionary implements Dictionary {

	private BinaryTree tree;
	private BinaryTreeNode top;
	private BinaryTreeNode prev;
	private BinaryTreeNode next;
	private BinaryTreeNode cur;


	public BasicDictionary() {
		tree = new BinaryTree();
	}


	/**
	 * Replaces the current dictionary with words imported from the specified text
	 * file. Words in the file are in lexicographical (ASCII) order, one word per
	 * line.
	 * 
	 * @param filename
	 *            Name (possibly including a path) of the file to import.
	 * @throws Exception
	 */
	@Override
	public void importFile(String filename) throws Exception {
		tree = new BinaryTree();

		var myFile = readFileAsLines(filename);

		// ArrayList<String> words = new ArrayList<>();
		//
		// for (var word : myFile) {
		// word = trim(word);
		// words.add(word);
		// }
		// make a distinct set of lowercase words

		Set<String> distinctWords = new HashSet();
		for (var word : myFile) {
			distinctWords.add(word.trim().toLowerCase());
		}
		ArrayList<String> wordsToInsert = new ArrayList<>();
		wordsToInsert.addAll(distinctWords);

		tree.insert(wordsToInsert);
	}


	@Override
	public void load(String filename) throws Exception {
		tree = new BinaryTree();

		var myFile = readFileAsLines(filename);

		for (var word : myFile) {
			add(word);
		}

	}


	@Override
	public void save(String filename) throws Exception {
		ArrayList<String> preorder = tree.preorder();
		FileWriter fw = new FileWriter(filename);
		BufferedWriter bw = new BufferedWriter(fw);

		for (String word : preorder) {
			bw.write(word);
			bw.newLine();
		}
		bw.close();
	}


	/**
	 * 
	 * @param word
	 * @return If the word is <b>found</b> this method returns <b>null</b>.
	 *         Otherwise, it returns a String array organized as follows:
	 * 
	 *         <pre>
	 *         [0] = Preceeding word in the dictionary 
	 *         [1] = Succeeding word in the dictionary 
	 *         
	 *              e.g. if the unknown word was "spelm", the result might be:
	 *              
	 *         [0] = "spells" 
	 *         [1] = "spelt"
	 *         
	 *         If there is no preceeding or succeeding word in the dictionary, set the element to "".
	 *         </pre>
	 */
	@Override
	public String[] find(String word) {
		word = word.toLowerCase().trim();
		// BinaryTreeNode node = tree.get(word.toLowerCase().trim());
		String[] arr = new String[2];
		arr[0] = "";
		arr[1] = "";
		// if (node != null) {
		// return null;
		// }

		// tree.
		ArrayList<String> strings = tree.preorder();
		for (int i = 0; i < strings.size(); i++) {
			if (strings.get(i).compareTo(word) == 0) {
				return null;
			}

			if (strings.get(i).compareTo(word) < 0) {
				if (arr[0].equals("")) {
					arr[0] = strings.get(i);
				} else if (strings.get(i).compareTo(arr[0]) > 0 && strings.get(i).compareTo(word) < 0) {
					arr[0] = strings.get(i);
				}
			}
			if (strings.get(i).compareTo(word) > 0) {
				if (arr[1].equals("")) {
					arr[1] = strings.get(i);
				} else if (strings.get(i).compareTo(arr[1]) < 0 && strings.get(i).compareTo(word) > 0) {
					arr[1] = strings.get(i);
				}
			}
		}
		return arr;
	}


	@Override
	public void add(String word) {
		tree.insert(word.trim());
	}


	@Override
	public BinaryTreeNode getRoot() {
		return tree.getRoot();
	}


	@Override
	public int getCount() {
		return tree.size();
	}

	// public static void main(String[] args) throws Exception {
	// int totalScore = 0;
	// BasicDictionary dictionary = new BasicDictionary();
	// String[] words = new String[] { "bull", "are", "genetic", "cotton", "dolly",
	// "florida", "each", "bull" };
	// for (String word : words)
	// dictionary.add(word);

	// dictionary.save("test_save.pre");
	// String s = readFile("test_save.pre");
	// String[] parts = s.split("\n");

	// assertEquals(words.length - 1, parts.length);
	// for (int ndx = 0; ndx < parts.length; ndx++)
	// assertEquals(words[ndx], parts[ndx].trim().toLowerCase());

	// totalScore += 8;
	// }
}
