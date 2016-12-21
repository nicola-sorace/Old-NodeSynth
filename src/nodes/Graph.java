package nodes;

import java.awt.Color;
import java.awt.Graphics2D;

import core.Jack;
import core.Node;

public class Graph extends Node{
	
	public double xScale = 10;
	
	private int w = 170;
	private int h = 150;

	public Graph(int x, int y){
		super(x, y);
		width = 200;  //small: 60
		height = 195; //small: 75
		name = "Graph";
		
		ins = new Jack[]{
			new Jack("", 0, 0, -1)
		};
		
		outs = new Jack[]{
			new Jack("")
		};
	}
	
	public double function(int output, double t){
		return ins[0].getValue(t);
	}
	
	@Override
	public void subDraw(Graphics2D g){
		
		w = width - 30;
		h = height - 45;
		
		g.setColor(Color.BLACK);
		g.fillRect(x+15, y+30, w, h);
		
		g.setColor(Color.WHITE);
		g.drawLine(x+15, y+30+h/2, x+15+w, y+30+h/2);
		
		boolean clipping = false;
		double a,b = 0;
		//g.setStroke(new Stroke());
		for(int t=1; t<w; t++){
			g.setColor(Color.GREEN);
			
			//a = ins[0].getValue((t-1)/xScale);
			//b = ins[0].getValue(t/xScale);
			
			a = ins[0].getValue((((double)t-1)/w)*4*Math.PI);
			b = ins[0].getValue(((double)t/w)*4*Math.PI);
			
			if(a>1 || a<-1 || b>1 || b<-1){
				clipping = true;
				if(a>1)a=1;
				else if(a<-1)a=-1;
				if(b>1)b=1;
				else if(b<-1)b=-1;
				g.setColor(Color.RED);
			}
			g.drawLine(x+14+t, y+30+(h/2) - ((int)Math.round(a*(h/2))), x+15+t, y+30+(h/2) - ((int)Math.round(b*(h/2))));
		}
		
		if(clipping){
			g.setColor(Color.RED);
			g.drawString("CLIPPING", x+70, y+20);
		}
		
		/*g.fillRect(0, 0, 400, 200);
		g.setColor(Color.RED);
		for(int t=1; t<400; t++){
			g.drawLine(t-1, 100 - ((int)Math.round(ins[0].getValue((t-1)/xScale)*100)), t, 100 - ((int)Math.round(ins[0].getValue(t/xScale)*100)));
		}*/
	}
	
}
