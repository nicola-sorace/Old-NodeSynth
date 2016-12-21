package core;

public class Jack {
	public Wire source = null;
	public String name = "Value";
	
	public double value = 0;
	public double min = 0;
	public double max = 1;
	//TODO:
	//public int div = 0.1
	
	public Jack(String name){
		this.name = name;
	}
	
	public Jack(String name, double value, double min, double max){
		this.name = name;
		this.value = value;
		this.min = min;
		this.max = max;
	}
	
	public double getValue(double t){
		if (source != null) return source.node1.function(source.jack1, t);
		else return value;
	}
}
