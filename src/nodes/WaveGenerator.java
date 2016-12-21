package nodes;

import core.Jack;
import core.Node;

public class WaveGenerator extends Node{
	
	private final double PI2 = 2*Math.PI;

	public WaveGenerator(int x, int y){
		super(x, y);
		width = 275;
		height = 140;
		name = "Wave Generator";
		
		outs = new Jack[]{
				new Jack("Sine"),
				new Jack("Square"),
				new Jack("Triangle"),
				new Jack("Sawtooth"),
				new Jack("x")
		};
		
		ins = new Jack[]{
				new Jack("Amplitude", 1, 0, 10),
				new Jack("Period", 1, 0.1, 4),
				new Jack("Phase", 0, -1, 1),
				new Jack("Offset", 0, -1, 1)
		};
		
		//params     = new double[][]{new double[]{1,0,1,0.01}, new double[]{1,0,2,0.01}, new double[]{0,0,1,0.01}, new double[]{0,-1,1,0.01}};
		//paramNames = new   String[]{"Amplitude",             "Period",                  "Phase",                  "Offset"};
	}
	
	public double function(int output, double t){
		
		double amp = ins[0].value;
		double per = ins[1].value;
		double pha = ins[2].value;
		double off = ins[3].value;
		
		t = t-(pha*PI2);
		t = t/per;
		
		switch(output){
			case 0:
				return off+amp*Math.sin(t);  //Sine
			case 1:
				return off+amp*Math.pow(-1, Math.floor((t*2)/PI2));  //Square
			case 2:
				//TODO Try and find better definition than 'transformed square*sawtooth'.
				return off+amp*(Math.pow(-1, Math.floor(t/PI2*2))) * (((t/PI2*2)-Math.floor(t/PI2*2))*2-1);  //Square
			case 3:
				return off+amp*((t/PI2-Math.floor(t/PI2))*2-1);  //Sawtooth
			case 4:
				return off+amp*t/PI2;  //x
		}
		
		return 0;
	}
}
