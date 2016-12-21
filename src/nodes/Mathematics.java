package nodes;

import core.Jack;
import core.Node;

public class Mathematics extends Node{

	public Mathematics(int x, int y){
		super(x, y);
		width = 160;
		height = 80;
		name = "Math";
		
		ins = new Jack[]{
				new Jack("a"),
				new Jack("b")
		};
		
		outs = new Jack[]{
				new Jack("Add"),
				new Jack("Multiply")
		};
	}
	
	public double function(int output, double t){
		switch(output){
			case 0:
				return ins[0].getValue(t) + ins[1].getValue(t);
			case 1:
				return ins[0].getValue(t) * ins[1].getValue(t);
		}
		
		return 0;
	}
}
