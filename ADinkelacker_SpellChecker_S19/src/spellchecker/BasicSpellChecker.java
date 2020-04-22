package spellchecker;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sbcc.Core.readFileAsLines;
import static sbcc.Core.readFile;

public class BasicSpellChecker implements SpellChecker {

	public static void main(String[] args) {

	}

	private BasicDictionary dictionary = new BasicDictionary();
	// private List<String> words;
	private int startNdx;
	private int endNdx;
	private String document;
	private BinaryTreeNode cur;


	// public BasicSpellChecker() {
	// // dictionary = new BasicDictionary();
	// words = new ArrayList<>();
	// }

	@Override
	public void importDictionary(String filename) throws Exception {
		dictionary.importFile(filename);

	}


	@Override
	public void loadDictionary(String filename) throws Exception {

		dictionary.load(filename);

	}


	@Override
	public void saveDictionary(String filename) throws Exception {
		// TODO Auto-generated method stub
		dictionary.save(filename);

	}


	@Override
	public void loadDocument(String filename) throws Exception {

		// words = readFileAsLines(filename);
		document = readFile(filename);

	}


	@Override
	public void saveDocument(String filename) throws Exception {

		PrintWriter writer = new PrintWriter(new File(filename));

		// for (String w : words) {
		// writer.println(w);
		// }
		writer.write(document);
		writer.close();

	}


	@Override
	public String getText() {

		// return words.toString().substring(1, words.toString().length() - 1);
		return document;
	}


	/**
	 * Starts/continues a spell check of the text. Use the regular expression below
	 * to match words (it's not great, but it's simple and works OK for basic text).
	 * 
	 * <pre>
	 * \b[\w']+\b
	 * </pre>
	 * 
	 * The method returns when the first unknown word is located or when the end of
	 * the document is reached (whichever comes first). The index of the character
	 * after the unknown word is retained for use as the starting index for
	 * subsequent calls to spellCheck where continueFromPrevious is true.
	 * 
	 * @param continueFromPrevious
	 *            If false, a new spell check is started from the first character of
	 *            the document. If true, the spell check continues from it's current
	 *            location.
	 * @return If no unknown word is found this method returns null. Otherwise, it
	 *         returns a String array organized as follows:
	 * 
	 *         <pre>
	 *         [0] = Unknown word 
	 *         [1] = Index of start of unknown word.  The index is the position within the entire document.
	 *         [2] = Preceeding word in the dictionary .  If the unknown word is before all words in the dictionary, this element should be "".
	 *         [3] = Succeeding word in the dictionary.  If the unknown word is after all words in the dictionary, this element should be "".
	 *              e.g. 
	 *         [0] = "spelm"
	 *         [1] = "224"
	 *         [2] = "spells" 
	 *         [3] = "spelt"
	 *         </pre>
	 */
	@Override
	public String[] spellCheck(boolean continueFromPrevious) {
		Pattern p = Pattern.compile("\\b[\\w']+\\b");
		Matcher m = p.matcher(document);
		String[] suggestions = new String[2];

		String[] returnVal = new String[4];

		if (!continueFromPrevious) {
			startNdx = 0;
			endNdx = 0;
			// cur = dictionary.getRoot();

		}
		while (m.find(startNdx + 1)) {
			String currentWord = m.group().trim().toLowerCase();
			startNdx = m.start();
			endNdx = startNdx + currentWord.length();

			if ((suggestions = dictionary.find(currentWord)) != null) {
				returnVal[0] = currentWord;
				returnVal[1] = String.valueOf(startNdx);
				returnVal[2] = suggestions[0];
				returnVal[3] = suggestions[1];
				return returnVal;
			}

		}

		return null;
	}


	@Override
	public void addWordToDictionary(String word) {

		dictionary.add(word);

	}


	/**
	 * Replaces text in the document from startIndex to just before endIndex with
	 * the given replacementText.
	 * 
	 * NOTE: Be sure to update your spell checker index by adding the difference
	 * between the length of the replacement text and the length of the text that
	 * was replaced.
	 * 
	 * @param startIndex
	 *            Index of the first character to replace.
	 * @param endIndex
	 *            Index of the character <b>after</b> the last character to replace.
	 * @param replacementText
	 */
	@Override
	public void replaceText(int startIndex, int endIndex, String replacementText) {
		String target = document.substring(startIndex, endIndex);
		document = document.replace(target, replacementText);
	}

}