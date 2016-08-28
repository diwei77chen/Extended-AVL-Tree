package net.datastructures;

import java.util.ArrayList;
import java.util.Comparator;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.datastructures.AVLTree.AVLNode;

// Author: Diwei Chen
// Date Revised: 10 May 2015

public class ExtendedAVLTree<K,V> extends AVLTree<K,V>{
	public ExtendedAVLTree(Comparator<K> c)  { super(c); }
	public ExtendedAVLTree() { super(); }
	
	/**
	 * This class method creates an identical copy of the AVL tree specified by the parameter and 
	 * returns a reference to the new AVL tree.
	 */
	public static <K,V> AVLTree<K,V> clone(AVLTree<K,V> tree){
		
		AVLTree<K,V> cloneTree = new AVLTree<K,V>();
		cloneTree.root = cloneTree.createNode(
				tree.root.element(), null, null , null);		// clone the root node of the original tree
		((AVLNode<K,V>) cloneTree.root).setHeight(
				((AVLNode<K,V>) tree.root).getHeight());		//set the height of the clone root from the original one
		 System.out.println(((AVLNode<K,V>) cloneTree.root).height);
		BTPosition<Entry<K,V>> rootLeftNode = cloneTree.createNode(
				tree.root.getLeft().element(), cloneTree.root, null, null);		//clone the left node of the root node of the original tree
		BTPosition<Entry<K,V>> rootRightNode = cloneTree.createNode(
				tree.root.getRight().element(), cloneTree.root, null, null);	//clone the right node of the root node of the original tree
		cloneTree.root.setLeft(rootLeftNode);
		cloneTree.root.setRight(rootRightNode);
		
		Position<Entry<K,V>> root = tree.root();			//get the position of the root of the tree
		Position<Entry<K,V>> left = tree.left(root);		//get the position of the left child of the root
		Position<Entry<K,V>> right = tree.right(root);		//get the position of the right child of the root
		
		Position<Entry<K,V>> cloneRoot = cloneTree.root();		//get the position of the root of the clone tree
		Position<Entry<K,V>> cloneLeft = cloneTree.left(cloneRoot);		//get the position of the left child of the clone root
		Position<Entry<K,V>> cloneRight = cloneTree.right(cloneRoot);		//get the position of the right child of the clone root
		 
		cloneLeftSubtree(tree, left, cloneTree, cloneLeft);
		cloneRightSubtree(tree, right, cloneTree, cloneRight);
		 
		return cloneTree;
	}
	/**
	 * This class method clones the left subtree of the given node but
	 * returns void
	 */
	public static <K,V> void cloneLeftSubtree(AVLTree<K,V> tree, Position<Entry<K,V>> left,
											  AVLTree<K,V> cloneTree, Position<Entry<K,V>> cloneLeft){
		 if(tree.isInternal(left)){
			 //change the data type from Position<E> to BTPosition<E>
			 BTPosition<Entry<K,V>> BTLeft = tree.checkPosition(left);
			 BTPosition<Entry<K,V>> cloneBTLeft = cloneTree.checkPosition(cloneLeft);
			 Position<Entry<K,V>> tmpLeft = null; 		
			 Position<Entry<K,V>> cloneTmpLeft = null;
			 //set the element of the clone left node from the original one
			 if(cloneBTLeft.element() == null)
				 cloneBTLeft.setElement(BTLeft.element());
			 //set the height of the clone left node from the original one
			 ((AVLNode<K,V>) cloneLeft).setHeight(((AVLNode<K,V>) left).getHeight());
			 System.out.println(((AVLNode<K,V>) cloneLeft).height);
			 //clone the left sub node of the given node
			 if(tree.hasLeft(left)){
				 cloneBTLeft.setLeft(cloneTree.createNode(null, cloneBTLeft, null, null));
				 tmpLeft = left;
				 tmpLeft = tree.left(tmpLeft);
				 cloneTmpLeft = cloneLeft;
				 cloneTmpLeft = cloneTree.left(cloneTmpLeft);
				 cloneLeftSubtree(tree, tmpLeft, cloneTree, cloneTmpLeft);		//recursively clone the left node of the given node
			 }
			 //clone the right sub node of the given node
			 if(tree.hasRight(left)){
				 cloneBTLeft.setRight(cloneTree.createNode(null, cloneBTLeft, null, null));
				 tmpLeft = tree.right(left);
				 cloneTmpLeft = cloneTree.right(cloneLeft);
				 cloneLeftSubtree(tree, tmpLeft, cloneTree, cloneTmpLeft);		//recursively clone the right sub node of the given node
			 }
		 }
	}
	/**
	 * This class method clones the right subtree of the given node but
	 * returns void
	 */
	public static <K,V> void cloneRightSubtree(AVLTree<K,V> tree, Position<Entry<K,V>> right,
											   AVLTree<K,V> cloneTree, Position<Entry<K,V>> cloneRight){
		 
		if(tree.isInternal(right)){
			//change the data type from Position<E> to BTPosition<E>
			BTPosition<Entry<K,V>> BTRight = tree.checkPosition(right);
			BTPosition<Entry<K,V>> cloneBTRight = cloneTree.checkPosition(cloneRight);
		    Position<Entry<K,V>> tmpRight = null; 
		    Position<Entry<K,V>> cloneTmpRight = null;
			//set the element of the clone left node from the original one
		    if(cloneBTRight.element() == null)
		    	cloneBTRight.setElement(BTRight.element());
			 //set the height of the clone left node from the original one
			 ((AVLNode<K,V>) cloneRight).setHeight(((AVLNode<K,V>) right).getHeight());
			 System.out.println(((AVLNode<K,V>) cloneRight).height);
			 
			if(tree.hasRight(right)){			//clone the right sub node of the given node
				cloneBTRight.setRight(cloneTree.createNode(null, cloneBTRight, null, null));
				tmpRight = right;
				tmpRight = tree.right(tmpRight);
				cloneTmpRight = cloneRight;
				cloneTmpRight = cloneTree.right(cloneTmpRight);
				cloneRightSubtree(tree, tmpRight, cloneTree, cloneTmpRight);		//recursively clone the right sub node of the given node
			}
			
			if(tree.hasLeft(right)){			//clone the left sub node of the given node
				cloneBTRight.setLeft(cloneTree.createNode(null, cloneBTRight, null, null));
				tmpRight = tree.left(right);
				cloneTmpRight = cloneTree.left(cloneRight);
				cloneRightSubtree(tree, tmpRight, cloneTree, cloneTmpRight);		//recursively clone the left node of the given node
			}
		}
	}

	/**
	 * This class method merges two AVL trees, tree1 and tree2, into a new tree. 
	 * After the merge, this method reclaims the unused original AVL trees and 
	 * returns the new AVL tree. 
	 */
	public static <K,V> AVLTree<K,V> merge(AVLTree<K,V> tree1, AVLTree<K,V> tree2){
		AVLTree<K,V> newTree = new AVLTree<K,V>();
		//creates an array list stores the nodes of tree1
		ArrayList<AVLNode<K,V>> arr1 = new ArrayList<AVLNode<K,V>>();
		//creates an array list stores the nodes of tree2
		ArrayList<AVLNode<K,V>> arr2 = new ArrayList<AVLNode<K,V>>();
		//creates an array list stores the combination of array list 1 and 2
		ArrayList<AVLNode<K,V>> arr = new ArrayList<AVLNode<K,V>>();
		
		Position<Entry<K,V>> root1 = tree1.root();
		Position<Entry<K,V>> root2 = tree2.root();
		
		inorderAddNode(arr1, tree1, root1);		//adds the nodes of tree1 to arr1 by in-order traversal
		inorderAddNode(arr2, tree2, root2);		//adds the nodes of tree2 to arr2 by in-order traversal
		
		arr = mergeTwoArrList(arr, arr1, arr2, newTree);		//merges arr1 and arr2 into arr
		
		constructTreeByList(arr, null, newTree);		//constructs a new tree by the given array list
		
		arr = null;			//reclaims the unused original AVL tree1 and tree2
		arr1 = null;
		arr2 = null;
		
		setAllHeight(newTree, (AVLNode<K,V>)newTree.root);		//sets the heights of nodes of the given tree
		
		return newTree;
	}
   	/**
   	 * Running time analysis:
   	 * 1.Adds m nodes from tree1 to arr1 by in-order traversal. 
   	 * 	 As each node is visited and added once, the time complexity is O(m).
   	 * 	 And after that, the nodes in arr1 are sorted.
   	 * 2.Adds n nodes from tree1 to arr2 by in-order traversal,
   	 * 	 so the time complexity is O(n) and the nodes in arr2 are sorted.
   	 * 3.Merges arr1 and arr2 into arr. As the nodes are sorted in arr1 and arr2,
   	 *   after m + n times comparison between arr1 and arr2, we can get a sorted array list arr.
   	 *   So the time complexity here is O(m+n).
   	 * 4.Constructs a new tree by the nodes stored in arr. As each time it makes use of the middle 
   	 * 	 node as a parent node in the sorted array, each node will be visited only once. So the
   	 * 	 time complexity here is O(m+n).
   	 * 5.Sets the height of each node. As each node will be only visited once and do one comparison
   	 * 	 between its sub nodes, the time complexity here is O(m+n).
   	 * 
   	 * Therefore, the time complexity of the merge method is O(m) + O(n) + 3 * O(m+n), i.e., O(m+n).
   	 * 
   	 */
	
	
	/**
	 * This class method makes use of in-order traversal to transfer the nodes from a given tree to a array list but
	 * returns void.
	 */
	public static <K,V> void inorderAddNode(ArrayList<AVLNode<K,V>> arr,
															AVLTree<K,V> tree, Position<Entry<K,V>> node){
		BTPosition<Entry<K,V>> bNode = tree.checkPosition(node);
		Position<Entry<K,V>> left = bNode.getLeft();
		Position<Entry<K,V>> right = bNode.getRight();
		//makes bNode points to the left bottom node of the given node
		if(tree.isInternal(node)){
			//adds the left bottom node into the array list							
			if(tree.isInternal(left))
				inorderAddNode(arr, tree, left);
			arr.add((AVLNode<K,V>) node);
		}
		if(tree.isInternal(right)){		//adds the right node to the array list
			inorderAddNode(arr, tree, right);
		}
	}

	/**
	 * merges two array lists and returns a new array list
	 */
	public static <K,V> ArrayList<AVLNode<K,V>> mergeTwoArrList(ArrayList<AVLNode<K,V>> arr, ArrayList<AVLNode<K,V>> arr1,
														        ArrayList<AVLNode<K,V>> arr2, AVLTree<K,V> newTree){
		//adds nodes from arr1 and arr2 to arr by keys in ascending order
		for(int i = 0, j = 0; i < arr1.size() || j < arr2.size(); ){
			int comp =newTree.C.compare(arr1.get(i).element().getKey(), 
									    arr2.get(j).element().getKey());
			if(comp < 0){
				arr.add(arr1.get(i));
				++i;
			}
			else if(comp == 0){
				arr.add(arr1.get(i));
				arr.add(arr2.get(j));
				++i;
				++j;
			}
			else{
				arr.add(arr2.get(j));
				++j;
			}	
		}
		
		return arr;
	}
	
	/**
	 * This class method constructs a new tree by using the AVLNode stored in the given array list and
	 * returns void
	 */
	public static <K,V> void constructTreeByList(ArrayList<AVLNode<K,V>> arr, 
												BTPosition<Entry<K,V>> parent, AVLTree<K,V> newTree){
		int count1 = 0,
			count2 = 0,
			count3 = 0;
		ArrayList<AVLNode<K,V>> rightArr = new ArrayList<AVLNode<K,V>>();
		ArrayList<AVLNode<K,V>> leftArr = new ArrayList<AVLNode<K,V>>();
		BTPosition<Entry<K,V>> rightMid, leftMid;
		
		if(arr.size() > 2){
			if(arr.size() % 2 == 0){
				count1 = arr.size() / 2;		//the index of the middle node of the array list
				count2 = (arr.size() - count1) / 2 + count1;		//the index of the middle node of the right hand
				count3 = ((arr.size() - count1 - 1) / 2) + 1;		//the index of the middle node of the left hand
			}
			if(arr.size() % 2 == 1){
				count1 = (arr.size() / 2) + 1;		//the index of the middle node of the array list
				count2 = (arr.size() - count1) / 2 + 1 + count1;		//the index of the middle node of the right hand
				count3 = (arr.size() - count1) / 2 + 1;			//the index of the middle node of the left hand
			}

			//parent = newTree.createNode(arr.get(count1 - 1).element(), null, null, null);
			//System.out.println(parent.element().getKey());
			if(parent == null){
				newTree.addRoot(arr.get(count1 - 1).element());
				parent = newTree.root;
			}
			
			rightMid = newTree.createNode(arr.get(count2 - 1).element(), parent, null, null);
			leftMid = newTree.createNode(arr.get(count3 - 1).element(), parent, null, null);
			
			parent.setRight(rightMid);
			parent.setLeft(leftMid);
			
			for(int i = count1; i < arr.size(); ++i){		//adds the right side of the arr to rightArr
				rightArr.add(arr.get(i));
			}
			for(int j = 0; j < count1 - 1; ++j){		//adds the left side of the arr to leftArr
				leftArr.add(arr.get(j));
			}
			
			constructTreeByList(rightArr, rightMid, newTree);
			constructTreeByList(leftArr, leftMid, newTree);
		}
		else if(arr.size() == 2){
			int count = arr.size() / 2;
			rightMid = newTree.createNode(null, parent, null, null);		//right points to a new external node
			leftMid = newTree.createNode(arr.get(count - 1).element(), parent, null, null);
			newTree.expandExternal(leftMid, null, null);		//creates two external nodes under left
			parent.setRight(rightMid);
			parent.setLeft(leftMid);
		}
		else if(arr.size() == 1){
			newTree.expandExternal(parent, null, null);
		}
	}

	/**
	 * This class method sets the height of every node of the merge tree and
	 * returns void
	 */
	public static <K,V> void setAllHeight(AVLTree<K,V> newTree, AVLNode<K,V> node){
		if(node.getLeft() != null){
			setAllHeight(newTree, (AVLNode<K,V>) node.getLeft());
			node.height = Math.max(node.height, 1 + ((AVLNode<K,V>) node.getLeft()).getHeight());
		}
		if (node.getRight() != null){
	        setAllHeight(newTree, (AVLNode<K,V>) node.getRight());
	        node.height = Math.max(node.height, 1 + ((AVLNode<K,V>) node.getRight()).getHeight());
	    }
	}
	
	/**
	 * This class method creates a new window and prints the AVL tree specified by the parameter on the new window. 
	 * @param tree
	 */
	public static <K,V> void print(AVLTree<K,V> tree){
		new TreeView(tree);
	}

	/**	This nested class is used to set up a visible window for the given AVL tree*/	
	public static class TreeView<K,V> extends JFrame{
		protected JPanel contentPane;
		protected AVLTree<K,V> tree;
		protected DrawTree drawer;
		
		/** creates the frame and sets JPanel to JFrame*/
		public TreeView(AVLTree<K,V> tree){
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 600, 600);
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			drawer = new DrawTree(tree);
			contentPane.add(drawer);
			setContentPane(contentPane);
			this.tree = tree;
			setVisible(true);
		}
	}
	
	/** This nested class is used to draw a given AVL tree */
	public static class DrawTree<K,V> extends JPanel{
		protected AVLTree<K,V> tree;
		
		public DrawTree(){}
		public DrawTree(AVLTree<K,V> tree){
			this.tree = tree;
		}
		
		public void drawTree(Graphics g, int startWidth, int endWidth, int startHeight, 
						int level, BTPosition<Entry<K,V>> node ){
			//if this node is an internal node, draw the key of the node with an oval and lines between it and its sub nodes
			if(node.element() != null){		
				String data = String.valueOf(node.element().getKey());
				g.setFont(new Font("Tahoma", Font.BOLD, 20));
				FontMetrics fm = g.getFontMetrics();
				int dataWidth = fm.stringWidth(data);
				g.drawString(data, (startWidth + endWidth) / 2 - dataWidth / 2, startHeight + level / 2);
				g.drawOval((startWidth + endWidth) / 2 - dataWidth / 2 - 8, startHeight + level / 2 - 24,
							dataWidth + 16, 30);
				
				if(node.getLeft() != null){			//if this node has left sub node
					g.drawLine((startWidth + endWidth) / 2, startHeight + level / 2 + 6, 
							   (3 * startWidth + endWidth) / 4, startHeight + 3 * level / 2 - 24);
					drawTree(g, startWidth, (startWidth + endWidth) / 2, startHeight + level, 
							level, node.getLeft());
					
				if(node.getRight() != null){		//if this node has right sub node
					g.drawLine((startWidth + endWidth) / 2, startHeight + level / 2 + 6, 
							(startWidth + 3 * endWidth) / 4, startHeight + 3 * level / 2 - 24);
					drawTree(g, (startWidth + endWidth) / 2, endWidth, startHeight + level,
							level, node.getRight());
					}
				}
				
			}
			else if(node.element() == null){		//if this node is an external node, draw a rectangle
				g.drawRect((startWidth + endWidth) / 2 - 10, startHeight + level / 2 - 25,
							10, 30);
			}
		}
		
		//overrides the method
		protected void paintComponent(Graphics g){
			g.setFont(new Font("Tahoma", Font.BOLD, 20));
			drawTree(g, 0, getWidth(), 0, getHeight() / (((AVLNode) tree.root).getHeight() + 1), tree.root);
		}
	}
	
}
