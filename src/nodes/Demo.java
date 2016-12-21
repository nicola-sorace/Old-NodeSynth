package nodes;

import core.Jack;
import core.Node;

public class Demo extends Node{

	public Demo(int x, int y){
		super(x, y);
		width = 150;
		height = 200;
		name = "Demo";
		
		ins = new Jack[]{
				new Jack("Input 1"),
				new Jack("Input 2")
		};
		
		outs = new Jack[]{
				new Jack("Output 1"),
				new Jack("Output 2")
		};
	}
	
	public double function(int output, double t){
		return Math.sin(t);
		//return (2.0 * Math.PI * t);
	}
}
