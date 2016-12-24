package nodes;

import java.awt.Color;
import java.awt.Graphics2D;

import core.Jack;
import core.Node;

public class Graph extends Node{

	private int w = 170;
	private int h = 150;

	public Graph(int x, int y){
		super(x, y);
		width = 250;
		height = 250;
		name = "Graph";

		ins = new Jack[]{
			new Jack("", 0, 0, 0),
			new Jack("Max x", 4, 1, 1000)
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
		h = height - 105;

		int marginT = 90;

		g.setColor(Color.BLACK);
		g.fillRect(x+15, y+marginT, w, h);

		g.setColor(Color.WHITE);
		g.drawLine(x+15, y+marginT+h/2, x+15+w, y+marginT+h/2);

		boolean clipping = false;
		double a,b = 0;
		//g.setStroke(new Stroke());
		for(int t=1; t<w; t++){
			g.setColor(Color.GREEN);

			//a = ins[0].getValue((t-1)/xScale);
			//b = ins[0].getValue(t/xScale);

			a = ins[0].getValue((((double)t-1)/w)*ins[1].getValue((double)t-1)*Math.PI);
			b = ins[0].getValue(((double)t/w)*ins[1].getValue((double)t/w)*Math.PI);

			if(a>1 || a<-1 || b>1 || b<-1){
				clipping = true;
				if(a>1)a=1;
				else if(a<-1)a=-1;
				if(b>1)b=1;
				else if(b<-1)b=-1;
				g.setColor(Color.RED);
			}
			g.drawLine(x+14+t, y+marginT+(h/2) - ((int)Math.round(a*(h/2))), x+15+t, y+marginT+(h/2) - ((int)Math.round(b*(h/2))));
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
