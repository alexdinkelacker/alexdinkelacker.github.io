package spellchecker;

import java.util.ArrayList;
import java.util.Scanner;

import static sbcc.Core.*;
import static java.lang.System.*;
import static org.apache.commons.lang3.StringUtils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BinaryTree {
	private BinaryTreeNode root;
	private int numNodes;


	public int size() {
		return this.numNodes;
	}


	public BinaryTreeNode getRoot() {
		return root;
	}


	public boolean contains(String word) {
		return get(word) != null;
	}


	public BinaryTreeNode get(String word) {
		return get(root, word);
	}


	private BinaryTreeNode get(BinaryTreeNode node, String word) {
		if (node == null) {
			return null;
		}
		if (word.compareTo(node.value) < 0) {
			return get(node.left, word);
		} else if (word.compareTo(node.value) > 0) {
			return get(node.right, word);
		} else {
			return node;
		}
	}


	public void insert(ArrayList<String> words) {
		root = insert(words, 0, words.size() - 1);
		numNodes += words.size();
	}


	private BinaryTreeNode insert(ArrayList<String> words, int lo, int hi) {
		if (hi < lo) {
			return null;
		}

		int middle = (lo + hi) / 2;
		BinaryTreeNode node = new BinaryTreeNode(words.get(middle));
		node.left = insert(words, lo, middle - 1);
		node.right = insert(words, middle + 1, hi);

		return node;
	}


	public void insert(String word) {
		root = insert(root, word);
	}


	private BinaryTreeNode insert(BinaryTreeNode node, String word) {
		if (node == null) {
			numNodes++;
			node = new BinaryTreeNode(word);
			return node;
		}

		if (word.compareTo(node.value) < 0) {
			node.left = insert(node.left, word);
		} else if (word.compareTo(node.value) > 0) {
			node.right = insert(node.right, word);
		}

		return node;
	}


	public ArrayList<String> preorder() {
		ArrayList<String> result = new ArrayList<>();
		preorder(result, root);

		return result;
	}


	private void preorder(ArrayList<String> result, BinaryTreeNode node) {
		if (node == null) {
			return;
		}
		result.add(node.value);
		preorder(result, node.left);
		preorder(result, node.right);
	}


	public static void main(String[] args) {

		try {
			File file = new File("small_dictionary.txt");
			Scanner sc = new Scanner(file);

			BinaryTree b = new BinaryTree();

			while (sc.hasNext()) {
				b.insert(sc.next());
			}

			sc.close();
			System.out.println("Contains Alexander? " + b.contains("cotton"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
