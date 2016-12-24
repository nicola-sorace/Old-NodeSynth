package nodes;

import core.Jack;
import core.Node;
import core.PlaySound;

public class Output extends Node{

	public Output(int x, int y){
		super(x, y);
		width = 75;
		height = 75;
		name = "Output";

		ins = new Jack[]{
				new Jack("", 0, 0, 0)
		};

		Thread thread = new Thread(new PlaySound(ins[0]), "PlaySound");
		thread.start();
	}

	public double function(int output, double t){
		return 0;
	}

}
