package com.example.popstar;

public class Node {
	public int row;
	public int col;
	public int color;
	
	public Node(int row, int col,int color) {
		this.row = row;
		this.col = col;
		this.color = color;
		
	}

	/*public boolean equals(Object o) {
		Node node = (Node) o;
		return row==node.row && col==node.col;
	}*/
}
