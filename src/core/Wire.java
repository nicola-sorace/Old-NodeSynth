package core;

import java.awt.Color;
import java.awt.Graphics2D;

public class Wire {
	public Node node1;
	public int jack1;
	public Node node2;
	public int jack2;
	
	public Wire(Node n1, int j1, Node n2, int j2){
		if(n1!=null){
			node1 = n1;
			jack1 = j1;
		}
		
		if(n2!=null){
			node2 = n2;
			jack2 = j2;
			if(n1!=null)node2.ins[jack2].source = this;
		}
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.WHITE);
		g.fillOval(node1.x+node1.width-3, node1.y+40+20*jack1-3, 6, 6);
		g.fillOval(node2.x-3, node2.y+40+20*jack2-3, 6, 6);
		g.drawLine(node1.x+node1.width, node1.y+40+20*jack1, node2.x, node2.y+40+20*jack2);
	}
	
	public void disconnect(){
		node2.ins[jack2].source = null;
	}
	
}
