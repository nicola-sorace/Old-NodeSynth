package nodes;

import core.Jack;
import core.Node;

public class Transform extends Node{

	public Transform(int x, int y){
		super(x, y);
		width = 280;
		height = 150;
		name = "Transform";
		//TODO check validity

		ins = new Jack[]{
				new Jack("Input"),
				new Jack("Amplitude", 1, 0, 10),
				new Jack("Period", 1, 0.5, 2),
				new Jack("Phase", 0, -1, 1),
				new Jack("Offset", 0, -1, 1)

		};

		outs = new Jack[]{
				new Jack("Output")
		};
	}

	public double function(int output, double t){
		return ins[3].getValue(t)+ins[1].getValue(t)*((ins[0].getValue(t)-ins[3].getValue(t))/ins[2].getValue(t));
	}
}
